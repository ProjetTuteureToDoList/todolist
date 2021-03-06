package gestionDesTaches;

import gestionDesTags.ListeTags;

import java.util.ArrayList;




public class ListeTaches {
	private ArrayList<Tache> tabTache = new ArrayList<Tache>();
	private int compteurTache;
	private BDDTache db;
	
	public ListeTaches(BDDTache db){
		compteurTache = db.getSize();
		for(int i = 0 ; i < compteurTache ; i++){
			Tache t = db.selectionner(i);
			t.setAnimation(true);
			tabTache.add(t);
		}
		this.db = db;
	}
	
	public ListeTaches(BDDTache db, ArrayList<Integer> tabTagIdTri){
		for(int i = 0 ; i < db.getSize() ; i++){
			boolean estDedans = true;
			Tache t = db.selectionner(i);
			ArrayList<Integer> tabTagIdTache = t.readTags();
			int j = 0;
			while(j < tabTagIdTri.size()){
				if(!tabTagIdTache.contains(tabTagIdTri.get(j)))
					estDedans = false;
				j++;
			}
			
			if(estDedans){
				t.setAnimation(true);
				tabTache.add(t);
			}
		}
		
	}
	
	public void ajouterAllTags(Tache t, ListeTags lTags){
		ArrayList<Integer> tabTagId = t.readTags();
		for(int id : tabTagId){
			if(lTags.isInside(id))
				t.ajouterTag(lTags.getTag(id));
		}
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
	
	public void modificationTache(Tache t, int position){
		db.modifier(t);
		tabTache.set(position, t);
	}
	
	public ArrayList<Tache> getTabTache(){
		return tabTache;
	}
}
