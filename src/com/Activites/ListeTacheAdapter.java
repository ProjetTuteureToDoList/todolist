package com.Activites;

import java.util.ArrayList;

import gestionDesTaches.ListeTaches;
import gestionDesTaches.Tache;

import com.todolist.R;

import android.view.View.OnClickListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListeTacheAdapter extends BaseAdapter{
	LayoutInflater mInflater;
	ListeTaches lt = null;
	Context listeContexte;
	
	public ListeTacheAdapter(Context context){
		this.listeContexte = context;
		this.lt = new ListeTaches();
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	
	/**
	 * Récupère une tâche de la liste de tâche
	 * en fonction de sa position dans la liste
	 */
	@Override
	public Object getItem(int position) {
		return lt.getTabTache().get(position);
	}
	
	/**
	 * Récupère l'identifiant d'une tâche de la liste de tâche
	 * en fonction de sa position dans la liste
	 */
	@Override
	public long getItemId(int position) {
		return lt.getTabTache().get(position).getIdTache();
	}
	
	/**
	 * Méthode appelé à chaque fois qu'un élément de ListeTacheAdapter est affiché
	 * Besoin ici d'utiliser des inflaters.
	 * convertView vaut null uniquement les premières fois qu'on affiche la liste à chaque nouvelle
	 * création de tâche, et ne vaut plus null par exemple lorsqu'on défile l'écran pour 
	 * l'affichage d'une vue.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = null;
		// Si la vue est recyclée, il contient déjà le bon layout, donc pas besoin de la recréer
		// pour des soucis d'optimisation du processus
		if(convertView != null)
			// On n'a plus qu'à le récupérer
			layout = (LinearLayout) convertView;
		else{
			// Sinon, il faut utiliser le LayoutInflater
			
			layout = (LinearLayout) mInflater.inflate(R.layout.tache_page_principale, parent, false);
			
			//ANIMATION SUR AJOUT, MAGNIFIQUE !!!!!!
			if(position == lt.getTabTache().size() - 1 && !lt.getTabTache().get(position).getAnimation()){
				Animation anim = AnimationUtils.loadAnimation(this.listeContexte, R.anim.tanslate);
				layout.startAnimation(anim);
				lt.getTabTache().get(position).setAnimation(true);
			}
		}
		
		TextView tacheNom = (TextView) layout.findViewById(R.id.tache);
		ImageView imageSuppr = (ImageView) layout.findViewById(R.id.croix_suppr);
		
		
		tacheNom.setText(lt.getTabTache().get(position).getNom());
		imageSuppr.setTag(position);
		
		imageSuppr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Lorsque l'on clique sur la croix, on récupère la position de la Tache
				Integer position = (Integer)v.getTag();
						
				//On prévient les listeners qu'il y a eu un clic sur l'ImageView "mCroixSuppr".
				sendListener(position);
						
			}
		        	
		});
		
		return layout;	
	}
	
	/** retourne la taille de l'adapter
	 * c'est à dire la taille de la liste de tâche
	 */
	
	@Override
	public int getCount() {
		return this.lt.getTabTache().size();
	}
	
	
	public void ajoutTacheAdapter(String nouvelleTache){
		lt.ajoutTache(new Tache(nouvelleTache));
	}
	
	public void suppressionTacheAdapter(int position){
		lt.suppressionTache(position);
	}
	
////////INTERFACE LISTENER POUR LA CROIX
	public interface CroixAdapterListener {
	    public void onClickCroix(int position);
	}
	
	private ArrayList<CroixAdapterListener> mListListener = new ArrayList<CroixAdapterListener>();
	/**
	 * Pour ajouter un listener sur notre adapter
	 */
	public void addListener(CroixAdapterListener aListener) {
	    mListListener.add(aListener);
	}

	private void sendListener(int position) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onClickCroix(position);
	    }
	}
/////////
	
}
