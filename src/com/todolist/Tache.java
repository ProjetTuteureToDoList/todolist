package com.todolist;

import java.util.Date;

public class Tache {
	private String nom;
	private Date date; 				// à completer
//	Date faitLe; 			//date de la création de la tâche
	private boolean etat;			// à faire (False) ou fait (True) 
	private int importance;			// de 1 à 10
	private String description; 	// décrit la tâche
//	Lieu lieu; 			// comporte un point (coordonnées GPS) et un rayon
	
	
	public Tache(){
		this.nom = "Anonyme";
		this.date = new Date();				// à completer
		this.etat = false;
		this.importance = 1;
		this.description = "Pas de description";		
	}
	
	public Tache(String nom, Date date, int importance, String description){
		this.nom = nom;
		this.date = date;				// à completer
		this.etat = false;
		this.importance = importance;
		this.description = description;		
	}
	
	// liste des méthodes:
	
	public void rappeler(){
		// sonnerie + élément visuel envoyés à l'utilisateur	
	}
	
	
	// Getters
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
	
	// Setters
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
