package gestionDesTaches;

import gestionDesTags.ListeTags;

import java.util.ArrayList;

import android.util.Log;

public class ListeTaches {
	private ArrayList<Tache> tabTache = new ArrayList<Tache>();
	private int compteurTache;
	private BDDTache db;
	
	public ListeTaches(BDDTache db){
		compteurTache = db.getSize();
		for(int i = 0 ; i < compteurTache ; i++){
			Tache t = db.selectionner(i);
			t.setAnimation(true);
			t.setListeTags(new ListeTags());
			tabTache.add(t);
		}
		this.db = db;
	}
	
	public void ajoutTache(Tache t){
		t.setIdTache(compteurTache);
		tabTache.add(t);
		db.ajouter(t);
		compteurTache++;
	}
	
	public void suppressionTache(int position){		
		if(tabTache.size() > position){
			tabTache.remove(position);	
			db.toutSupprimer();
			compteurTache--;
			for(int i = 0 ; i < compteurTache ; i++){
				tabTache.get(i).setIdTache(i);
				db.ajouter(tabTache.get(i));
			}
			
		}
		
	}
	
	public void modificationTache(Tache t){
		Log.e("coucou2", t.getListeTagsString());
		db.modifier(t);
		tabTache.set(t.getIdTache(), t);
	}
	
	public ArrayList<Tache> getTabTache(){
		return tabTache;
	}
}
