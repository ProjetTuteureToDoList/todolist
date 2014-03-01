package gestionDesTags;

import java.util.ArrayList;



public class ListeTags {
	private ArrayList<Tag> tabTag = new ArrayList<Tag>();
	private int compteurTag;
	private BDDTag db;
	
	//constructeur pour listeTag d'une t�che
	public ListeTags(){
		compteurTag = 0;
	}
	
	//constructeur pour listeTag compl�te
	public ListeTags(BDDTag bdd){
		compteurTag = bdd.getSize();
		this.db = bdd;
		
		for(int i = 0 ; i < compteurTag ; i++){
			Tag t = db.selectionner(i);
			tabTag.add(t);
		}
		
	}
	
	public void ajoutTag(Tag t, boolean tache){
		if(!tache)
			db.ajouter(t);
		tabTag.add(t);
		compteurTag++;
	}
	
	public void suppressionTagByPosition(int position){
		tabTag.remove(position);
		compteurTag--;
		db.toutSupprimer();
		for(int i = 0 ; i < compteurTag ; i++)
			db.ajouter(tabTag.get(i));
				
	}
	
	public void suppressionTagById(int id){
		int i = 0;
		boolean find = false;
		while(i < this.getTabTag().size() && !find){
			if(tabTag.get(i).getId() == id)
				find = true;
			else
				i++;
		}
		if(find){
			tabTag.remove(i);
			compteurTag--;
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
