package gestionDesTags;

import java.util.ArrayList;

public class ListeTags {
	private ArrayList<Tag> tabTag = new ArrayList<Tag>();
	private int compteurTag;
	private BDDTag db;
	
	//constructeur pour listeTag d'une tâche
	public ListeTags(){
		compteurTag = 0;
	}
	
	//constructeur pour listeTag complète
	public ListeTags(BDDTag bdd){
		compteurTag = bdd.getSize();
		this.db = bdd;
		
		for(int i = 0 ; i < compteurTag ; i++){
			Tag t = db.selectionner(i);
			tabTag.add(t);
		}
		
	}
	
	public void ajoutTag(Tag t, boolean tache){
		t.setId(compteurTag);
		tabTag.add(t);
		if(!tache)
			db.ajouter(t);
		compteurTag++;
	}
	
	public void suppressionTag(int position){		
		if(tabTag.size() > position){
			tabTag.remove(position);	
			db.toutSupprimer();
			compteurTag--;
			for(int i = 0 ; i < compteurTag ; i++){
				tabTag.get(i).setId(i);
				db.ajouter(tabTag.get(i));
			}
			
		}
		
	}
	
	public ArrayList<Tag> getTabTag(){
		return tabTag;
	}
}
