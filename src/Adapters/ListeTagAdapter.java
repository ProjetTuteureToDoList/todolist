package Adapters;

import com.todolist.R;

import gestionDesTags.BDDTag;
import gestionDesTags.ListeTags;
import gestionDesTags.Tag;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import gestionDesTaches.*;

public class ListeTagAdapter extends BaseAdapter{
	LayoutInflater mInflater;
	ListeTags lt = null;
	ListeTaches lTaches = null;			//besoin d'instancier la liste de Tache en cas de suppression de tag
	Context listeContexte;
	
	public ListeTagAdapter(Context context){			//Constructeur pour GérerTags, car besoin de la ListeTache pour la suppresion de tag
		this.listeContexte = context;
		BDDTag db = new BDDTag(context, "Tag", null, 1);
		this.lt = new ListeTags(db);
		this.lTaches = new ListeTaches(new BDDTache(context, "Tache", null, 1));
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	public ListeTagAdapter(Context context, int flags){	//Constructeur pour les autres activités
		this.listeContexte = context;
		BDDTag db = new BDDTag(context, "Tag", null, 1);
		this.lt = new ListeTags(db);
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	@Override
	public int getCount() {
		return lt.getTabTag().size();
	}

	@Override
	public Tag getItem(int position) {
		return lt.getTabTag().get(position);
	}

	@Override
	public long getItemId(int position) {
		return lt.getTabTag().get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = null;
		// Si la vue est recyclée, il contient déjà le bon layout, donc pas besoin de la recréer
		// pour des soucis d'optimisation du processus
		if(convertView != null)
			// On n'a plus qu'à le récupérer
			layout = (LinearLayout) convertView;	
		else
			// Sinon, il faut utiliser le LayoutInflater
			layout = (LinearLayout) mInflater.inflate(R.layout.tag_gerer_tags, parent, false);
		
		
		TextView tacheNom = (TextView) layout.findViewById(R.id.tag);
		
		//Vérification de la longueur du tag
		if(lt.getTabTag().get(position).getNom().length() > 30)
			tacheNom.setText(lt.getTabTag().get(position).getNom().subSequence(0, 28) + "...");
		else
			tacheNom.setText(lt.getTabTag().get(position).getNom());
		
		//Affichage de la case cochée/non cochée
		ImageView caseCochee = (ImageView) layout.findViewById(R.id.caseCochee);
		ImageView caseNonCochee = (ImageView) layout.findViewById(R.id.caseNonCochee);
		if(isSelectionned()){
			if(lt.getTabTag().get(position).getAfficheSelection()){
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
		
		//Affichage de la couleur du tag
		ImageView couleur = (ImageView) layout.findViewById(R.id.couleur);
		couleur.setBackgroundColor(Color.parseColor("#"+ lt.getTabTag().get(position).getCoul()));
		
		return layout;	
	}
	
	public boolean isSelectionned() {
		boolean result = false;
		int i = 0;
		while(i < this.getCount() && !result){
			if(this.getItem(i).getAfficheSelection())
				result = true;
			i++;
		}
		
		return result;
	}

	public void ajoutTagAdapter(Tag t){
		lt.ajoutTag(t, false);
	}
	
	public void suppressionTagAdapter(int position){
		for(int i = 0 ; i < lTaches.getTabTache().size() ; i++){
			if(lTaches.getTabTache().get(i).contientTag(lt.getTabTag().get(position).getId())){
				lTaches.getTabTache().get(i).supprimerTag(lt.getTabTag().get(position).getId());
				lTaches.modificationTache(lTaches.getTabTache().get(i), i);
			}
		}
		
		lt.suppressionTagByPosition(position);
	}
	
	public ListeTags getListeTag(){
		return lt;
	}

}
