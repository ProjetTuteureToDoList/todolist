package com.Activites;

import Autres.OnSwipeTouchListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
	ImageView tag = null;
	TextView titre = null;
	
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
		//		- masquage des �l�ments inutiles au d�part (retour, suppr, tag)
		retour = (ImageView) findViewById(R.id.retour);
		preference = (ImageView) findViewById(R.id.icone_preference);
		menu = (ImageView) findViewById(R.id.icone_menu);
		suppr = (ImageView) findViewById(R.id.corbeille);
		tag = (ImageView) findViewById(R.id.icone_tags);
		
		menu.setOnTouchListener(touchClick);
		preference.setOnTouchListener(touchClick);
		retour.setOnTouchListener(touchClick);
		suppr.setOnTouchListener(touchClick);
		tag.setOnTouchListener(touchClick);
		
		retour.setVisibility(8);
		suppr.setVisibility(8);
		tag.setVisibility(8);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		titre = (TextView) findViewById(R.id.titre);
		titre.setTypeface(Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf"));
		
		//Initialisation EditText pour la gestion les �ven�ments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses � faire, initialisation de la ListeTacheAdapter, liaison � une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    checkList.setOnTouchListener(tacheSwipeListener);
	    
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
			    	if(entreeText.getText().toString().replace(" ", "").length() > 0){
			    		lta.ajoutTacheAdapter(entreeText.getText().toString());
				    	entreeText.setText(null);
				    	entreeText.clearFocus();
				    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				    	inputMethodManager.hideSoftInputFromWindow(entreeText.getWindowToken(), 0);
				    	actualiserListe();
			    	}
			    	break;
			    	
			    case R.id.allScreen:
			    	entreeText.clearFocus();	
			    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			    	inputMethodManager.hideSoftInputFromWindow(entreeText.getWindowToken(), 0);
			    	break;
			}
			
		}
	};
	
	private View.OnTouchListener touchClick = new OnTouchListener(){
		boolean isInside = true;
		float viewLargeur = 0;
		float viewHauteur = 0;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if(v.getId() == R.id.icone_menu || v.getId() == R.id.icone_preference ||
			   v.getId() == R.id.corbeille || v.getId() == R.id.retour ||
			   v.getId() == R.id.icone_tags){
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						viewLargeur = v.getRight() - v.getLeft();
						viewHauteur = v.getBottom() - v.getTop();
						v.setBackgroundResource(R.color.bleuSelection);
						break;
					case MotionEvent.ACTION_UP:
						v.setBackgroundResource(R.color.bleu);
						if(isInside){
							switch(v.getId()){
								case R.id.icone_menu:
									break;
									
								case R.id.icone_preference:
									break;
									
								case R.id.corbeille:
									int nbTacheSelectionnees = 0;
									for(int i = 0 ; i < lta.getCount() ; i++){
							    		if(lta.getItem(i).getAfficheOption())
							    			nbTacheSelectionnees++;
									}
									
									Builder confirmationSuppr = new AlertDialog.Builder(MainActivity.this);
									if(nbTacheSelectionnees == 1)
										confirmationSuppr.setTitle("�tes-vous s�r de supprimer " + nbTacheSelectionnees + " t�che ?");
									else
										confirmationSuppr.setTitle("�tes-vous s�r de supprimer " + nbTacheSelectionnees + " t�ches ?");
									
									confirmationSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
											for(int i = 0 ; i < lta.getCount() ; i++){
									    		if(lta.getItem(i).getAfficheOption()){
									    			lta.suppressionTacheAdapter(i);
									    			i--;
									    		}
									    	}
											actualiserListe();
											changerMode();
									    	actualiserHeader();
										}
									});
									
									confirmationSuppr.setNegativeButton("Non", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
										}
									});
									confirmationSuppr.show();
							    	break;
							    	
								case R.id.retour:
							    	for(int i = 0 ; i < lta.getCount() ; i++)
							    		lta.getItem(i).setAfficheSelection(false);
							    	changerMode();
							    	actualiserHeader();	
							    	break;	
							    
								case R.id.icone_tags:
									break;
							}
							actualiserListe();
						}
						break;
						
					case MotionEvent.ACTION_MOVE:
						if(event.getX() < viewLargeur && event.getY() < viewHauteur && event.getX() > 0 && event.getY() > 0)
							isInside = true;
						else
							isInside = false;
						break;
				}
				
				return true;
			}
			else
				return false;
		}
		
	};
	
	final OnSwipeTouchListener tacheSwipeListener = new OnSwipeTouchListener();
	
	private AdapterView.OnItemClickListener tacheListener = new AdapterView.OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			/*
			 * Si on est en mode s�lection, on ne peut pas atteindre le descriptif d'une t�che
			 * On peut alors gr�ce au simple clic cocher/d�cocher la t�che pour la s�lection
			 */
		    
			if(!modeSelection){
				
				if(tacheSwipeListener.swipeDetected()){
					if(tacheSwipeListener.getAction() == OnSwipeTouchListener.Action.LR || tacheSwipeListener.getAction() == OnSwipeTouchListener.Action.RL){
						if(!lta.getItem(position).getEtat())
							lta.getItem(position).setEtat(true);
						else
							lta.getItem(position).setEtat(false);
					}
				}
				else{
					Intent descriptifTache = new Intent(MainActivity.this, DescriptifTache.class);
					Bundle donneesTache = new Bundle();
					donneesTache.putString("nom", lta.getItem(position).getNom());
					donneesTache.putString("description", lta.getItem(position).getDescription());
					donneesTache.putInt("dateJour", lta.getItem(position).getDate().getDay());
					donneesTache.putInt("dateMois", lta.getItem(position).getDate().getMonth());
					donneesTache.putInt("dateAnnee", lta.getItem(position).getDate().getYear());
					donneesTache.putInt("importance", lta.getItem(position).getImportance());
					donneesTache.putBoolean("etat", lta.getItem(position).getEtat());
					descriptifTache.putExtras(donneesTache);
					startActivity(descriptifTache);
				}
			}
			else{
				
				if(!lta.getItem(position).getAfficheOption())
		    		lta.getItem(position).setAfficheSelection(true);
				else{
		    		lta.getItem(position).setAfficheSelection(false);
					if(!lta.isSelectionned())
						changerMode();
				}
				actualiserHeader();
			}
			actualiserListe();
				
        }
	};
	
	private AdapterView.OnItemLongClickListener tacheLongListener = new AdapterView.OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
			if(!lta.getItem(position).getAfficheOption()){
	    		lta.getItem(position).setAfficheSelection(true);
				if(!modeSelection)
					changerMode();			
			}
			else{
	    		lta.getItem(position).setAfficheSelection(false);
				if(!lta.isSelectionned())
					changerMode();
			}
			actualiserHeader();	
			actualiserListe();
			return true;
        }
	};
	///
	
	/*
	 * Actualisation de la liste gr�ce � la m�thode setAdapter
	 */
	public void actualiserListe(){
		lta.notifyDataSetChanged();
	}
	
	/*
	 *		Modification de modeSelection et v�rification de l'activation ou non de la s�lection de t�che. Si c'est le cas :
	 * 		- Rendre invisible les boutons menu et pr�f�rence
	 * 		- Rendre visible les boutons supprimer, retour et tag
	 */
	
	public void changerMode(){
		Animation animApparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
		Animation animDecalageRL = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_rl);
		Animation animDecalageLR = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_lr);
		
		if(modeSelection){
			modeSelection = false;
			menu.startAnimation(animApparition);
			preference.startAnimation(animApparition);
			titre.startAnimation(animDecalageLR);
		}
		else{
			modeSelection = true;
			retour.startAnimation(animApparition);
			suppr.startAnimation(animApparition);
			tag.startAnimation(animApparition);
			titre.startAnimation(animDecalageRL);
		}
			
	}
	
	public void actualiserHeader(){	
		
		if(!modeSelection){
			preference.setVisibility(0);
			menu.setVisibility(0);
			retour.setVisibility(8);
			suppr.setVisibility(8);
			tag.setVisibility(8);
		}
		else{
			preference.setVisibility(8);
			menu.setVisibility(8);
			retour.setVisibility(0);
			suppr.setVisibility(0);
			tag.setVisibility(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
