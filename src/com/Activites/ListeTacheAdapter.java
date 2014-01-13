package com.Activites;

import gestionDesTaches.BDDTache;
import gestionDesTaches.ListeTaches;
import gestionDesTaches.Tache;

import com.todolist.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
		BDDTache db = new BDDTache(context, "Tache", null, 1);
		this.lt = new ListeTaches(db);
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	
	/**
	 * Récupère une tâche de la liste de tâche
	 * en fonction de sa position dans la liste
	 */
	@Override
	public Tache getItem(int position) {
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
			
		}
		
		//ANIMATION SUR AJOUT, MAGNIFIQUE !!!!!!
		if(position == lt.getTabTache().size() - 1 && !lt.getTabTache().get(position).getAnimation()){
			Animation animTranslate = AnimationUtils.loadAnimation(this.listeContexte, R.anim.tanslate_tache);
			layout.startAnimation(animTranslate);
			lt.getTabTache().get(position).setAnimation(true);
		}
		
		TextView tacheNom = (TextView) layout.findViewById(R.id.tache);
		
		//Vérification de la longueur de la tâche
		if(lt.getTabTache().get(position).getNom().length() > 30)
			tacheNom.setText(lt.getTabTache().get(position).getNom().subSequence(0, 28) + "...");
		else
			tacheNom.setText(lt.getTabTache().get(position).getNom());
		
		//Affichage de la case cochée/non cochée
		ImageView caseCochee = (ImageView) layout.findViewById(R.id.caseCochee);
		ImageView caseNonCochee = (ImageView) layout.findViewById(R.id.caseNonCochee);
		if(isSelectionned()){
			if(lt.getTabTache().get(position).getAfficheOption()){
				caseCochee.setVisibility(0);
				caseNonCochee.setVisibility(8);
			}
			else{
				caseCochee.setVisibility(8);
				caseNonCochee.setVisibility(0);
			}
		}
		else{
			caseCochee.setVisibility(8);
			caseNonCochee.setVisibility(8);
		}
		
		//Affichage du texte barré et grisé si la tâche est effectué
		if(getItem(position).getEtat()){
		 	tacheNom.getPaint().setStrikeThruText(true);
		 	tacheNom.setTextColor(Color.parseColor("#818181"));
		}
		else{
			tacheNom.getPaint().setStrikeThruText(false);
	 		tacheNom.setTextColor(Color.parseColor("#000000"));
		}
		
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
}
