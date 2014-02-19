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
		if(!tache){
			t.setId(compteurTag);
			db.ajouter(t);
		}
		tabTag.add(t);
		compteurTag++;
	}
	
	public void suppressionTag(int id, boolean tache){
		int i = 0;
		boolean find = false;
		while(i < this.getTabTag().size() && !find){
			if(tabTag.get(i).getId() == id)
				find = true;
			i++;
		}
		if(find){
			tabTag.remove(i - 1);
			compteurTag--;
			if(!tache){
				db.toutSupprimer();
				for(i = 0 ; i < compteurTag ; i++)
					db.ajouter(tabTag.get(i));
			}	
		}
	}
	
	public ArrayList<Tag> getTabTag(){
		return tabTag;
	}
	
	public boolean isInside(int id){
		boolean result = false;
		int i = 0;
		while(i < this.getTabTag().size() && !result){
			if(this.getTabTag().get(i).getId() == id)
				result = true;
			i++;
		}
		
		return result;
	}
	
	public Tag getTag(int id){
		Tag t = null;
		int i = 0;
		boolean find = false;
		while(i < getTabTag().size() && !find){
			if(getTabTag().get(i).getId() == id){
				t = getTabTag().get(i);
				find = true;
			}
			i++;
		}
		return t;
	}
}
