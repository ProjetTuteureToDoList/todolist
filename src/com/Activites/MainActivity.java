package com.Activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.Activites.ListeTacheAdapter.CroixAdapterListener;
import com.todolist.R;

public class MainActivity extends Activity implements CroixAdapterListener {
	Button boutonAjouter = null;		//la variable du bouton	"Ajouter !"		
	EditText entreeText = null;			//enntrée de texte pour le rajout de tâche
	ListeTacheAdapter lta = null;		//représente la listView adapté pour la ListeTache
	LinearLayout allScreen = null;		//représente le layout englobant tout l'écran
	
	//Elément de la barre d'en haut
	ImageView retour = null;
	ImageView preference = null;
	ImageView menu = null;
	ImageView suppr = null;
	
	/*Permet de contrôler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;
	
	boolean modeSelection = false;		//Permet de savoir si au moins une tâche à été sélectionné ou non
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initilalisation de la barre d'en haut
		//		- masquage des éléments inutiles au départ (retour, suppr)
		retour = (ImageView) findViewById(R.id.retour);
		preference = (ImageView) findViewById(R.id.icone_preference);
		menu = (ImageView) findViewById(R.id.icone_menu);
		suppr = (ImageView) findViewById(R.id.corbeille);
		
		retour.setOnClickListener(petitClick);
		suppr.setOnClickListener(petitClick);
		
		retour.setVisibility(8);
		suppr.setVisibility(8);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire, initialisation de la ListeTacheAdapter, liaison à une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    
	    //Initialisation d'un LinearLayout représentant la totalité de l'écran
	    allScreen = (LinearLayout) findViewById(R.id.allScreen);
	    allScreen.setOnClickListener(petitClick);
	    
	    //Initialisation et gestion de l'évènement clic du bouton ajouter
	    boutonAjouter = (Button) findViewById(R.id.bouton);
	    boutonAjouter.setOnClickListener(petitClick);
	    
	    //Initialisation du Listener de la Croix
	    lta.addListener(this);
	    
	}

	//////EVENEMENTS LISTENERS
	private OnClickListener petitClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
			    		lta.ajoutTacheAdapter(entreeText.getText().toString());
				    	entreeText.setText(null);
				    	entreeText.clearFocus();
				    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				    	inputMethodManager.hideSoftInputFromWindow(entreeText.getWindowToken(), 0);
			    	}
			    	break;
			    	
			    case R.id.retour:
			    	for(int i = 0 ; i < lta.getCount() ; i++)
			    		lta.setSelectionTacheVisibilite(false, i);
			    	modeSelection = false;
			    	actualiserHeader();	
			    	break;

			    case R.id.corbeille:
			    	for(int i = 0 ; i < lta.getCount() ; i++){
			    		if(lta.getItem(i).getAfficheOption()){
			    			lta.suppressionTacheAdapter(i);
			    			i--;
			    		}
			    	}
			    	break;
			    	
			    case R.id.allScreen:
			    	entreeText.clearFocus();		    	
			    	break;
			    default:
			    	v.setBackgroundResource(R.color.bleuSelection);
			}
			
			actualiserListe();
			
		}
	};
	
	private AdapterView.OnItemClickListener tacheListener = new AdapterView.OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			Intent descriptifTache = new Intent(MainActivity.this, DescriptifTache.class);
			startActivity(descriptifTache);
			actualiserListe();
        }
	};
	
	private AdapterView.OnItemLongClickListener tacheLongListener = new AdapterView.OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
			if(!lta.getItem(position).getAfficheOption()){
				lta.setSelectionTacheVisibilite(true, position);
				modeSelection = true;			
			}
			else{
				lta.setSelectionTacheVisibilite(false, position);
				if(!lta.isSelectionned())
					modeSelection = false;
			}
			actualiserHeader();	
			lta.notifyDataSetChanged();
			return true;
        }
	};
	
	@Override
	public void onClickCroix(int position) {
		lta.suppressionTacheAdapter(position);
		actualiserListe();
	}
	//////////////
	
	/*
	 * Actualisation de la liste grâce à la méthode setAdapter
	 */
	public void actualiserListe(){
		checkList.setAdapter(lta);
	}
	
	/*
	 *		Modification de modeSelection et vérification de l'activation ou non de la sélection de tâche. Si c'est le cas :
	 * 		- Rendre invisible les boutons menu et préférence
	 * 		- Rendre visible les boutons supprimer, retour et tag
	 */
	public void actualiserHeader(){	
		
		if(!modeSelection){
			preference.setVisibility(0);
			menu.setVisibility(0);
			retour.setVisibility(8);
			suppr.setVisibility(8);
		}
		else{
			preference.setVisibility(8);
			menu.setVisibility(8);
			retour.setVisibility(0);
			suppr.setVisibility(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
