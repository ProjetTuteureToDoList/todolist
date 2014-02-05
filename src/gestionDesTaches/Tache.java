package gestionDesTaches;

import gestionDesTags.ListeTags;
import gestionDesTags.Tag;

import java.util.Calendar;
import java.util.Date;



public class Tache {
	private String nom;
	private Date date; 				// à completer
	private boolean etat;			// à faire (False) ou fait (True) 
	private int importance;			// de 1 à 10
	private String description; 	// décrit la tâche
//	Lieu lieu; 						// comporte un point (coordonnées GPS) et un rayon
//	Date faitLe; 					//date de la création de la tâche
	
	private int numTache;			//permettra de facilement retrouver une tâche dans le tableau pour modif/suppr
	private boolean hasDate;		//indique si la tâche à une date donnée ou non
	private boolean hasHour;		//indique si la tâche à une heure donnée ou non
	
	
	private ListeTags lt;
	
	private boolean animation; 		//permet de déterminer si elle a été animé ou non
	private boolean afficheSelection; 	//permet de déterminer si la tache est en mode option ou non (affichage des icônes options après long clic)
	
		// liste des constructeurs :
	// constructeur par Défaut
	public Tache(){				
		this.nom = "Anonyme";
		Calendar dateActuelle = Calendar.getInstance(); 			// initialise la date à la date actuel lors de la construction
		this.date = new Date(dateActuelle.get(Calendar.YEAR), dateActuelle.get(Calendar.MONTH) + 1, 
							 dateActuelle.get(Calendar.DAY_OF_MONTH), dateActuelle.get(Calendar.HOUR_OF_DAY), 
							 dateActuelle.get(Calendar.MINUTE));				
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
		this.animation = false;
	}
	
	// constructeur de tâche "rapide"
	public Tache(String nom){
		this.nom = nom;
		Calendar dateActuelle = Calendar.getInstance(); 			// initialise la date à la date actuel lors de la construction
		this.date = new Date(dateActuelle.get(Calendar.YEAR), dateActuelle.get(Calendar.MONTH) + 1, 
							 dateActuelle.get(Calendar.DAY_OF_MONTH), dateActuelle.get(Calendar.HOUR_OF_DAY), 
							 dateActuelle.get(Calendar.MINUTE));	
		this.hasDate = false;
		this.hasHour = false;
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
		this.lt = new ListeTags();
		
	}
	// constructeur (presque) complet
	public Tache(String nom, int jour, int mois, int annee, int heure, int minute, int importance, String description){
		this.nom = nom;
		
		if(jour == -1)
			this.hasDate = false;
		else
			this.hasDate = true;
		if(heure == -1)
			this.hasHour = false;
		else
			this.hasHour = true;
		this.date = new Date(annee, mois, jour, heure, minute);
		this.etat = false;
		this.importance = importance;
		this.description = description;	
		this.lt = new ListeTags();
	}
	
	// constructeur complet
	public Tache(int id, String nom, int jour, int mois, int annee, int heure, int minute, int importance, String description){
		this.nom = nom;
		
		if(jour == -1)
			this.hasDate = false;
		else
			this.hasDate = true;
		if(heure == -1)
			this.hasHour = false;
		else
			this.hasHour = true;
		this.date = new Date(annee, mois, jour, heure, minute);
		this.etat = false;
		this.importance = importance;
		this.description = description;	
		this.lt = new ListeTags();
		this.numTache = id;
	}
	
		// liste des méthodes:
	
	public void rappeler(){
		// sonnerie + élément visuel envoyés à l'utilisateur	
	}
	
	public void valider(){
		// permet de valider une tâche (passer de l'état non-fait à fait)
		this.etat = true;
	}
	
	public void ajouterTag(Tag t){
		this.lt.ajoutTag(t, true);
	}
	
	public void supprimerTag(int id){
		this.lt.suppressionTag(id, true);
	}
	
	public String writeTags(){
		String tagsInString = new String();
		for(int i = 0 ; i < lt.getTabTag().size() ; i++){
			tagsInString.concat(" " + lt.getTabTag().get(i).getNom());
		}
		return tagsInString;
	}
	
	public int [] readTags(String tagsInString){
		int i = 0, indiceTab = 0;
		int [] tabIdTag;
		boolean finString = false;
		String tag = "";
		while(i < tagsInString.length() && !finString){
			if(tagsInString.charAt(i) == ' ' || tagsInString.charAt(i) != '\n')	
				tag.concat(String.valueOf(tagsInString.charAt(i)));
			/*else if(tagsInString.charAt(i) == ' '){
				tabIdTag[indiceTab++] = */
			//}
			i++;
		}
		return null;
	}
		
		// liste des Getters
	public String getNom() {
		return nom;
	}
	public Date getDate() {
		return date;
	}
	public boolean getEtat() {
		return etat;
	}
	public int getImportance() {
		return importance;
	}
	public String getDescription() {
		return description;
	}
	public int getIdTache(){
		return numTache;
	}
	public boolean getHasDate(){
		return hasDate;
	}
	public boolean getHasHour(){
		return hasHour;
	}
	public boolean getAnimation(){
		return animation;
	}
	public boolean getAfficheSelection(){
		return afficheSelection;
	}
	public ListeTags getListeTags(){
		return lt;
	}
	
		// liste des Setters
	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIdTache(int idTache){
		this.numTache = idTache;
	}
	public void setHasDate(boolean rep){
		this.hasDate = rep;
	}
	public void setHasHour(boolean rep){
		this.hasHour = rep;
	}
	public void setAnimation(boolean animTache){
		this.animation = animTache;
	}
	public void setAfficheSelection(boolean afficheSelection){
		this.afficheSelection = afficheSelection;
	}
	public void setListeTags(ListeTags lt){
		this.lt = lt;
	}
}
