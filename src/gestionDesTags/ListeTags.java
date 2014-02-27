package gestionDesTags;

import java.util.ArrayList;

import android.util.Log;


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
			else
				i++;
		}
		if(find){
			if(tache)
				Log.e("ListeTags", "Suppression tag id " + String.valueOf(id) + " et position " + String.valueOf(i) + " dans t�che");
			else
				Log.e("ListeTags", "Suppression tag id " + String.valueOf(id) + " et position " + String.valueOf(i) + " dans la liste compl�te");
			tabTag.remove(i);
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
