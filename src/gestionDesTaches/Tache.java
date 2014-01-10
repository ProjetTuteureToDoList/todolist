package gestionDesTaches;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.util.Log;

public class Tache {
	private String nom;
	private Date date; 				// à completer
	private boolean etat;			// à faire (False) ou fait (True) 
	private int importance;			// de 1 à 10
	private String description; 	// décrit la tâche
//	Lieu lieu; 						// comporte un point (coordonnées GPS) et un rayon
//	Date faitLe; 					//date de la création de la tâche
	
	private int numTache;			//permettra de facilement retrouver une tâche dans le tableau pour modif/suppr
	
	
	private List<String> tag;		//Liste des tags d'une tâche spécifique
	private static String []tagParDef = {"Maison", "Travail", "Ecole", "Famille"};
									//Liste de tous les tags existants	
	static List<String> tabTag = Arrays.asList(tagParDef);
	
	private boolean animation; 		//permet de déterminer si elle a été animé ou non
	private boolean afficheSelection; 	//permet de déterminer si la tache est en mode option ou non (affichage des icônes options après long clic)
	
		// liste des constructeurs :
	// constructeur par Défaut
	public Tache(){				
		this.nom = "Anonyme";
		this.date = new Date();				// initialise la date à la date actuel lors de la construction
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
		this.animation = false;
	}
	
	// constructeur de tâche "rapide"
	public Tache(String nom){
		this.nom = nom;
		this.date = new Date();
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";
	}
	// constructeur complet
	public Tache(String nom, Date d, int importance, String description){
		this.nom = nom;
		this.date = new Date(d.getYear(), d.getMonth(), d.getDay(), d.getMinutes(), d.getSeconds());
		this.etat = false;
		this.importance = importance;
		this.description = description;		
	}
	
		// liste des méthodes:
	
	public void rappeler(){
		// sonnerie + élément visuel envoyés à l'utilisateur	
	}
	
	public void valider(){
		// permet de valider une tâche (passer de l'état non-fait à fait)
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
	public void setAnimation(boolean animTache){
		this.animation = animTache;
	}
	public void setAfficheSelection(boolean afficheSelection){
		this.afficheSelection = afficheSelection;
	}
}
