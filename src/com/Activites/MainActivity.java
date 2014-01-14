package com.Activites;

import Adapters.ListeTacheAdapter;
import Adapters.MenuAdapter;
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
import android.view.ViewGroup.LayoutParams;
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
	EditText entreeText = null;			//enntrée de texte pour le rajout de tâche
	ListeTacheAdapter lta = null;		//représente la listView adapté pour la ListeTache
	LinearLayout allScreen = null;		//représente le layout englobant tout l'écran
	ListView menu = null;				//représente la listView du menu après clic sur iconeMenu
	LinearLayout menuLayout = null;		//représente le layout englobant le menu
	LinearLayout principalLayout = null;//représente le layout englobant la page principal hors menu
	
	//Elément de la barre d'en haut
	ImageView retour = null;
	ImageView preference = null;
	ImageView iconeMenu = null;
	ImageView suppr = null;
	ImageView tag = null;
	TextView titre = null;
	
	/*Permet de contrôler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;
	
	boolean modeSelection = false;		//Permet de savoir si au moins une tâche à été sélectionné ou non
	boolean modeMenu = false;			//Permet de savoir si on est dans le menu où non
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initilalisation de la barre d'en haut
		//		- masquage des éléments inutiles au départ (retour, suppr, tag)
		retour = (ImageView) findViewById(R.id.retour);
		preference = (ImageView) findViewById(R.id.icone_preference);
		iconeMenu = (ImageView) findViewById(R.id.icone_menu);
		suppr = (ImageView) findViewById(R.id.corbeille);
		tag = (ImageView) findViewById(R.id.icone_tags);
		
		iconeMenu.setOnTouchListener(touchClick);
		preference.setOnTouchListener(touchClick);
		retour.setOnTouchListener(touchClick);
		suppr.setOnTouchListener(touchClick);
		tag.setOnTouchListener(touchClick);
		
		retour.setVisibility(8);
		suppr.setVisibility(8);
		tag.setVisibility(8);
		
		//Initialisation du menu, et de son layout englobant
		menu = (ListView) findViewById(R.id.menu);
		menu.setAdapter(new MenuAdapter(this));
		menuLayout = (LinearLayout) findViewById(R.id.menuLayout);
		menuLayout.setLayoutParams(new LinearLayout.LayoutParams((int) (getWindowManager().getDefaultDisplay().getWidth() * 0.80), 	//width
																		LayoutParams.FILL_PARENT));									//height
		menuLayout.setVisibility(8);
		menu.setOnItemClickListener(menuListener);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		titre = (TextView) findViewById(R.id.titre);
		titre.setTypeface(Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf"));
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire, initialisation de la ListeTacheAdapter, liaison à une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    checkList.setOnTouchListener(tacheSwipeListener);
	    
	    //Initialisation d'un LinearLayout représentant la totalité de l'écran
	    allScreen = (LinearLayout) findViewById(R.id.allScreen);
	    allScreen.setOnClickListener(petitClick);
	    
	    //Initialisation du LinearLayout représentant la page principal hors menu
	    principalLayout = (LinearLayout) findViewById(R.id.principalLayout);
	    
	    //Initialisation et gestion de l'évènement clic du bouton ajouter
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
			    	if(modeMenu){
			    		setMenuShowed();
			    	}
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
									setMenuShowed();
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
										confirmationSuppr.setTitle("Êtes-vous sûr de supprimer " + nbTacheSelectionnees + " tâche ?");
									else
										confirmationSuppr.setTitle("Êtes-vous sûr de supprimer " + nbTacheSelectionnees + " tâches ?");
									
									confirmationSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
											for(int i = 0 ; i < lta.getCount() ; i++){
									    		if(lta.getItem(i).getAfficheOption()){
									    			lta.suppressionTacheAdapter(i);
									    			i--;
									    		}
									    	}
											actualiserListe();
											changerModeSelection();
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
							    	changerModeSelection();
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
			 * Si on est en mode sélection, on ne peut pas atteindre le descriptif d'une tâche
			 * On peut alors grâce au simple clic cocher/décocher la tâche pour la sélection
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
						changerModeSelection();
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
					changerModeSelection();			
			}
			else{
	    		lta.getItem(position).setAfficheSelection(false);
				if(!lta.isSelectionned())
					changerModeSelection();
			}
			actualiserHeader();	
			actualiserListe();
			return true;
        }
	};
	
	private AdapterView.OnItemClickListener menuListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			switch(position){
				case 0:
					Intent ajoutAvanceTache = new Intent(MainActivity.this, AjoutAvanceTache.class);
					startActivity(ajoutAvanceTache);
					menuLayout.setVisibility(8);
					principalLayout.setVisibility(0);
					modeMenu = false;
					
					break;
			}
		}
		
	};
	
	///
	
	/*
	 * Actualisation de la liste grâce à la méthode setAdapter
	 */
	public void actualiserListe(){
		lta.notifyDataSetChanged();
	}
	
	/*
	 *		Modification de modeSelection et vérification de l'activation ou non de la sélection de tâche. Si c'est le cas :
	 * 		- Rendre invisible les boutons menu et préférence
	 * 		- Rendre visible les boutons supprimer, retour et tag
	 */
	
	public void changerModeSelection(){
		Animation animApparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
		Animation animDecalageRL = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_rl);
		Animation animDecalageLR = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_lr);
		
		if(modeSelection){
			modeSelection = false;
			iconeMenu.startAnimation(animApparition);
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
	
	public void setMenuShowed(){
		
		LinearLayout layoutSansHeader = (LinearLayout) findViewById(R.id.menuEtPrincipalLayout);
		Animation animTransitionMenuLR = AnimationUtils.loadAnimation(this, R.anim.translate_menu_lr);
		
		Animation animTransitionMenuRL = AnimationUtils.loadAnimation(this, R.anim.translate_menu_rl);
		animTransitionMenuRL.setAnimationListener(new Animation.AnimationListener(){

			@Override
			public void onAnimationEnd(Animation animation) {
				menuLayout.setVisibility(8);
				principalLayout.setVisibility(0);
				modeMenu = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {				
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
			
		});

		
		if(modeMenu)
			layoutSansHeader.startAnimation(animTransitionMenuRL);
		else{
			modeMenu = true;
			principalLayout.setVisibility(8);
			menuLayout.setVisibility(0);
			layoutSansHeader.startAnimation(animTransitionMenuLR);
		}
	}
	
	public void actualiserHeader(){	
		
		if(!modeSelection){
			preference.setVisibility(0);
			iconeMenu.setVisibility(0);
			retour.setVisibility(8);
			suppr.setVisibility(8);
			tag.setVisibility(8);
		}
		else{
			preference.setVisibility(8);
			iconeMenu.setVisibility(8);
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
