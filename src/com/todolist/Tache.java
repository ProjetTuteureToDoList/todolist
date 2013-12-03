package com.todolist;

import java.util.Date;

public class Tache {
	private String nom;
	private Date date; 				// � completer
	private boolean etat;			// � faire (False) ou fait (True) 
	private int importance;			// de 1 � 10
	private String description; 	// d�crit la t�che
//	Lieu lieu; 						// comporte un point (coordonn�es GPS) et un rayon
//	Date faitLe; 					//date de la cr�ation de la t�che
	
	
		// liste des constructeurs :
	// constructeur par D�faut
	public Tache(){				
		this.nom = "Anonyme";
		this.date = new Date();				// initialise la date � la date actuel lors de la construction
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
}
