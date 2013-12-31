package gestionDesTaches;

import java.util.ArrayList;

import android.util.Log;

public class ListeTaches {
	private ArrayList<Tache> tabTache = new ArrayList<Tache>();
	private int compteurTache;
	private BDDTache db;
	
	public ListeTaches(BDDTache db){
		compteurTache = db.getSize();
		Log.e("Essai", String.valueOf(compteurTache));
		for(int i = 0 ; i < compteurTache ; i++)
			tabTache.add(db.selectionner(i));
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
	
	public ArrayList<Tache> getTabTache(){
		return tabTache;
	}

	
	public String toutesLesTachesStr(){ //mets les tâches en string pour les afficher dans un seul TextView tout moche avec une anim toute moche
		int i = 0;
		String toutesLesTaches = new String();
		while(i<compteurTache){
			toutesLesTaches = toutesLesTaches + "\n\t" + tabTache.get(i).getNom();
			i++;
		}
		return toutesLesTaches;
	}
}
