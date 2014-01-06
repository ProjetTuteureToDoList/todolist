package com.Activites;

import java.util.ArrayList;

import gestionDesTaches.BDDTache;
import gestionDesTaches.ListeTaches;
import gestionDesTaches.Tache;

import com.todolist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListeTacheAdapter extends BaseAdapter{
	LayoutInflater mInflater;
	ListeTaches lt = null;
	Context listeContexte;
	
	public ListeTacheAdapter(Context context){
		this.listeContexte = context;
		BDDTache db = new BDDTache(context, "Tache", null, 1);
		this.lt = new ListeTaches(db);
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	
	/**
	 * R�cup�re une t�che de la liste de t�che
	 * en fonction de sa position dans la liste
	 */
	@Override
	public Tache getItem(int position) {
		return lt.getTabTache().get(position);
	}
	
	
	
	/**
	 * R�cup�re l'identifiant d'une t�che de la liste de t�che
	 * en fonction de sa position dans la liste
	 */
	@Override
	public long getItemId(int position) {
		return lt.getTabTache().get(position).getIdTache();
	}
	
	
	
	/**
	 * M�thode appel� � chaque fois qu'un �l�ment de ListeTacheAdapter est affich�
	 * Besoin ici d'utiliser des inflaters.
	 * convertView vaut null uniquement les premi�res fois qu'on affiche la liste � chaque nouvelle
	 * cr�ation de t�che, et ne vaut plus null par exemple lorsqu'on d�file l'�cran pour 
	 * l'affichage d'une vue.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = null;
		// Si la vue est recycl�e, il contient d�j� le bon layout, donc pas besoin de la recr�er
		// pour des soucis d'optimisation du processus
		if(convertView != null)
			// On n'a plus qu'� le r�cup�rer
			layout = (LinearLayout) convertView;
				
		else{
			// Sinon, il faut utiliser le LayoutInflater
			layout = (LinearLayout) mInflater.inflate(R.layout.tache_page_principale, parent, false);
			
			//ANIMATION SUR AJOUT, MAGNIFIQUE !!!!!!
			if(position == lt.getTabTache().size() - 1 && !lt.getTabTache().get(position).getAnimation()){
				Animation animTranslate = AnimationUtils.loadAnimation(this.listeContexte, R.anim.tanslate);
				layout.startAnimation(animTranslate);
				lt.getTabTache().get(position).setAnimation(true);
			}	
		}
		
		
		TextView tacheNom = (TextView) layout.findViewById(R.id.tache);
		tacheNom.setText(lt.getTabTache().get(position).getNom());
		
		if(lt.getTabTache().get(position).getAfficheOption())
			layout.setBackgroundResource(R.color.bleuSelection);
		else
			layout.setBackgroundResource(R.color.blanc);
		
		return layout;	
	}
	
	/** retourne la taille de l'adapter
	 * c'est � dire la taille de la liste de t�che
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
	
	public void setSelectionTacheVisibilite(boolean selection, int position){
		lt.getTabTache().get(position).setAfficheSelection(selection);
	}
	
	public boolean isSelectionned(){
		boolean result = false;
		int i = 0;
		while(i < this.getCount() && !result){
			if(this.getItem(i).getAfficheOption())
				result = true;
			i++;
		}
		
		return result;
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
	/**
	 * Pour envoyer les listeners
	 */
	private void sendListener(int position) {
	    for(int i = mListListener.size()-1; i >= 0; i--) {
	    	mListListener.get(i).onClickCroix(position);
	    }
	}
/////////
	
}
