package gestionDesTaches;

import gestionDesTags.ListeTags;
import gestionDesTags.Tag;

import java.util.Calendar;
import java.util.Date;



public class Tache {
	private String nom;
	private Date date; 				// � completer
	private boolean etat;			// � faire (False) ou fait (True) 
	private int importance;			// de 1 � 10
	private String description; 	// d�crit la t�che
//	Lieu lieu; 						// comporte un point (coordonn�es GPS) et un rayon
//	Date faitLe; 					//date de la cr�ation de la t�che
	
	private int numTache;			//permettra de facilement retrouver une t�che dans le tableau pour modif/suppr
	private boolean hasDate;		//indique si la t�che � une date donn�e ou non
	private boolean hasHour;		//indique si la t�che � une heure donn�e ou non
	
	
	private ListeTags lt;
	
	private boolean animation; 		//permet de d�terminer si elle a �t� anim� ou non
	private boolean afficheSelection; 	//permet de d�terminer si la tache est en mode option ou non (affichage des ic�nes options apr�s long clic)
	
		// liste des constructeurs :
	// constructeur par D�faut
	public Tache(){				
		this.nom = "Anonyme";
		Calendar dateActuelle = Calendar.getInstance(); 			// initialise la date � la date actuel lors de la construction
		this.date = new Date(dateActuelle.get(Calendar.YEAR), dateActuelle.get(Calendar.MONTH) + 1, 
							 dateActuelle.get(Calendar.DAY_OF_MONTH), dateActuelle.get(Calendar.HOUR_OF_DAY), 
							 dateActuelle.get(Calendar.MINUTE));				
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
		this.animation = false;
	}
	
	// constructeur de t�che "rapide"
	public Tache(String nom){
		this.nom = nom;
		Calendar dateActuelle = Calendar.getInstance(); 			// initialise la date � la date actuel lors de la construction
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
	// constructeur complet
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
	
		// liste des m�thodes:
	
	public void rappeler(){
		// sonnerie + �l�ment visuel envoy�s � l'utilisateur	
	}
	
	public void valider(){
		// permet de valider une t�che (passer de l'�tat non-fait � fait)
		this.etat = true;
	}
	
	public void ajouterTag(Tag t){
		this.lt.ajoutTag(t, true);
	}
	
	public void supprimerTag(int id){
		this.lt.suppressionTag(id, true);
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
