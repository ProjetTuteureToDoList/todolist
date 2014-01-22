package gestionDesTaches;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
	
	
	private List<String> tag;		//Liste des tags d'une t�che sp�cifique
	private static String []tagParDef = {"Maison", "Travail", "Ecole", "Famille"};
									//Liste de tous les tags existants	
	static List<String> tabTag = Arrays.asList(tagParDef);
	
	private boolean animation; 		//permet de d�terminer si elle a �t� anim� ou non
	private boolean afficheSelection; 	//permet de d�terminer si la tache est en mode option ou non (affichage des ic�nes options apr�s long clic)
	
		// liste des constructeurs :
	// constructeur par D�faut
	public Tache(){				
		this.nom = "Anonyme";
		this.date = new Date();				// initialise la date � la date actuel lors de la construction
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
		this.animation = false;
	}
	
	// constructeur de t�che "rapide"
	public Tache(String nom){
		this.nom = nom;
		this.date = new Date();
		this.hasDate = false;
		this.hasHour = false;
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
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
	}
	
		// liste des m�thodes:
	
	public void rappeler(){
		// sonnerie + �l�ment visuel envoy�s � l'utilisateur	
	}
	
	public void valider(){
		// permet de valider une t�che (passer de l'�tat non-fait � fait)
		this.etat = true;
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
	public boolean getAfficheOption(){
		return afficheSelection;
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
}
