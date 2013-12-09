package gestionDesTaches;

import java.util.ArrayList;

public class ListeTaches {
	private ArrayList<Tache> tabTache = new ArrayList<Tache>();
	private int compteurTache;
	
	public ListeTaches(){
		compteurTache = 0;
	}
	
	public void ajoutTache(Tache t){
		tabTache.add(t);
		compteurTache++;
	}
	
	
	public void suppressionTache(int position){		
		if(tabTache.size() > position)
			tabTache.remove(position);	
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
