package com.Activites;

import gestionDesTaches.ListeTaches;
import gestionDesTaches.Tache;

import com.todolist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
	 * R�cup�re une t�che de la liste de t�che
	 * en fonction de sa position dans la liste
	 */
	@Override
	public Object getItem(int position) {
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
		  layout  = (LinearLayout) convertView;
		else
		  // Sinon, il faut utiliser le LayoutInflater
		  layout = (LinearLayout) mInflater.inflate(R.layout.tache_page_principale, parent, false);
		 
		TextView laTache = (TextView) layout.findViewById(R.id.tache);
		laTache.setText(lt.getTabTache().get(position).getNom());		

	  return layout;	
	}

	/* retourne la taille de l'adapter
	 * c'est � dire la taille de la liste de t�che
	 */
	
	@Override
	public int getCount() {
		return this.lt.getTabTache().size();
	}
	
	public void ajoutTacheAdapter(String nouvelleTache){
		lt.ajoutTache(new Tache(nouvelleTache));
	}
}
