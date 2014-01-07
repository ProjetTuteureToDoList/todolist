package com.Activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.todolist.R;

public class MainActivity extends Activity{
	Button boutonAjouter = null;		//la variable du bouton	"Ajouter !"		
	EditText entreeText = null;			//enntr�e de texte pour le rajout de t�che
	ListeTacheAdapter lta = null;		//repr�sente la listView adapt� pour la ListeTache
	LinearLayout allScreen = null;		//repr�sente le layout englobant tout l'�cran
	
	//El�ment de la barre d'en haut
	ImageView retour = null;
	ImageView preference = null;
	ImageView menu = null;
	ImageView suppr = null;
	
	/*Permet de contr�ler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;
	
	boolean modeSelection = false;		//Permet de savoir si au moins une t�che � �t� s�lectionn� ou non
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initilalisation de la barre d'en haut
		//		- masquage des �l�ments inutiles au d�part (retour, suppr)
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
		
		//Initialisation EditText pour la gestion les �ven�ments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses � faire, initialisation de la ListeTacheAdapter, liaison � une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    
	    //Initialisation d'un LinearLayout repr�sentant la totalit� de l'�cran
	    allScreen = (LinearLayout) findViewById(R.id.allScreen);
	    allScreen.setOnClickListener(petitClick);
	    
	    //Initialisation et gestion de l'�v�nement clic du bouton ajouter
	    boutonAjouter = (Button) findViewById(R.id.bouton);
	    boutonAjouter.setOnClickListener(petitClick);
	    
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
			    	changerMode();
			    	actualiserHeader();	
			    	break;

			    case R.id.corbeille:
			    	for(int i = 0 ; i < lta.getCount() ; i++){
			    		if(lta.getItem(i).getAfficheOption()){
			    			lta.suppressionTacheAdapter(i);
			    			i--;
			    		}
			    	}
			    	modeSelection = false;
			    	actualiserHeader();
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
			/*
			 * Si on est en mode s�lection, on ne peut pas atteindre le descriptif d'une t�che
			 * On peut alors gr�ce au simple clic cocher/d�cocher la t�che pour la s�lection
			 */
			
			if(!modeSelection){
				Intent descriptifTache = new Intent(MainActivity.this, DescriptifTache.class);
				startActivity(descriptifTache);
			}
			else{
				if(!lta.getItem(position).getAfficheOption())
					lta.setSelectionTacheVisibilite(true, position);
				else{
					lta.setSelectionTacheVisibilite(false, position);
					if(!lta.isSelectionned())
						changerMode();
				}
				actualiserListe();
				actualiserHeader();
			}
        }
	};
	
	private AdapterView.OnItemLongClickListener tacheLongListener = new AdapterView.OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
			if(!lta.getItem(position).getAfficheOption()){
				lta.setSelectionTacheVisibilite(true, position);
				changerMode();			
			}
			else{
				lta.setSelectionTacheVisibilite(false, position);
				if(!lta.isSelectionned())
					changerMode();
			}
			actualiserHeader();	
			lta.notifyDataSetChanged();
			return true;
        }
	};
	
	/*
	 * Actualisation de la liste gr�ce � la m�thode setAdapter
	 */
	public void actualiserListe(){
		checkList.setAdapter(lta);
	}
	
	/*
	 *		Modification de modeSelection et v�rification de l'activation ou non de la s�lection de t�che. Si c'est le cas :
	 * 		- Rendre invisible les boutons menu et pr�f�rence
	 * 		- Rendre visible les boutons supprimer, retour et tag
	 */
	
	public void changerMode(){
		Animation animApparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
		
		if(modeSelection){
			modeSelection = false;
			menu.startAnimation(animApparition);
			preference.startAnimation(animApparition);
		}
		else{
			modeSelection = true;
			retour.startAnimation(animApparition);
			suppr.startAnimation(animApparition);
		}
			
	}
	
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
