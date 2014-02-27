package com.Activites;

import gestionDesTaches.Tache;
import Adapters.ListeTacheAdapter;
import Adapters.ListeTagAdapter;
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
import android.util.Log;
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
	EditText entreeText = null;			//enntr�e de texte pour le rajout de t�che
	ListeTacheAdapter lta = null;		//repr�sente la listView adapt� pour la ListeTache
	ListeTagAdapter lTagsAdapter = null;//repr�sente la lsite compl�te des tags
	LinearLayout allScreen = null;		//repr�sente le layout englobant tout l'�cran
	ListView menu = null;				//repr�sente la listView du menu apr�s clic sur iconeMenu
	LinearLayout menuLayout = null;		//repr�sente le layout englobant le menu
	LinearLayout principalLayout = null;//repr�sente le layout englobant la page principal hors menu
	
	
	//El�ment de la barre d'en haut
	ImageView retour = null;
	ImageView preference = null;
	ImageView iconeMenu = null;
	ImageView suppr = null;
	ImageView tag = null;
	TextView titre = null;
	
	/*Permet de contr�ler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;
	
	boolean modeSelection = false;		//Permet de savoir si au moins une t�che � �t� s�lectionn� ou non
	boolean modeMenu = false;			//Permet de savoir si on est dans le menu o� non
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		//Initilalisation de la barre d'en haut
		//		- masquage des �l�ments inutiles au d�part (retour, suppr, tag)
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
		
		//Initialisation EditText pour la gestion les �ven�ments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Initialisation de l'adapter de la liste compl�te des tags
	    lTagsAdapter = new ListeTagAdapter(this);
	    
	    //Liste des choses � faire, initialisation de la ListeTacheAdapter, liaison � une ListView
	    lta = new ListeTacheAdapter(this);
	    
	    	//v�rification si une t�che a �t� supprim� sur l'activit� DescriptifTache
	    int tacheASuppr = getIntent().getIntExtra("descriptif_tache_id", -1);
	    if(tacheASuppr != -1)
			lta.suppressionTacheAdapter(tacheASuppr);
	    
	    	//v�rification si une t�che � �t� cr��e sur l'activit� AjoutAvanceTache
	    int tacheAAjouter = getIntent().getIntExtra("ajout_avancee", -1);
	    if(tacheAAjouter == 1){
	    	Tache t = new Tache(getIntent().getStringExtra("nom"),
								getIntent().getIntExtra("dateJour", 0),
								getIntent().getIntExtra("dateMois", 0),
								getIntent().getIntExtra("dateAnnee", 0),
								getIntent().getIntExtra("dateHeure", 0),
								getIntent().getIntExtra("dateMinute", 0),
								getIntent().getIntExtra("importance", 0),
								getIntent().getStringExtra("description"));
	    	t.setAnimation(true);
	    	lta.ajoutTacheAdapter(t);
	    }
	    	//v�rification si une t�che � �t� modifi� sur l'activit� AjoutAvanceTache
	    if(getIntent().getIntExtra("modification", -1) == 1){
	    	Tache t = new Tache(getIntent().getIntExtra("id", -1),
	    			getIntent().getStringExtra("nom"),
					getIntent().getIntExtra("dateJour", 0),
					getIntent().getIntExtra("dateMois", 0),
					getIntent().getIntExtra("dateAnnee", 0),
					getIntent().getIntExtra("dateHeure", 0),
					getIntent().getIntExtra("dateMinute", 0),
					getIntent().getIntExtra("importance", 0),
					getIntent().getStringExtra("description"),
	    			getIntent().getBooleanExtra("etat", false));
	    	t.setListeTagsString(getIntent().getStringExtra("listeTags"));
	    	t.setAnimation(true);
	    	lta.modificationTacheAdapter(t);
	    	Log.e("ListeTags", t.getListeTagsString());
	    	
	    }
	    
	    
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    checkList.setOnTouchListener(tacheSwipeListener);
	   
	    //Initialisation d'un LinearLayout repr�sentant la totalit� de l'�cran
	    allScreen = (LinearLayout) findViewById(R.id.allScreen);
	    allScreen.setOnClickListener(petitClick);
	    
	    //Initialisation du LinearLayout repr�sentant la page principal hors menu
	    principalLayout = (LinearLayout) findViewById(R.id.principalLayout);
	    
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
			    		lta.ajoutTacheAdapter(new Tache(entreeText.getText().toString()));
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
							    		if(lta.getItem(i).getAfficheSelection())
							    			nbTacheSelectionnees++;
									}
									
									Builder confirmationSuppr = new AlertDialog.Builder(MainActivity.this);
									if(nbTacheSelectionnees == 1)
										confirmationSuppr.setTitle("�tes-vous s�r de vouloir supprimer cette t�che ?");
									else
										confirmationSuppr.setTitle("�tes-vous s�r de vouloir supprimer les t�ches ?");
									
									confirmationSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
											for(int i = 0 ; i < lta.getCount() ; i++){
									    		if(lta.getItem(i).getAfficheSelection()){
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
							    	actualiserListe();
							    	break;	
							    
								case R.id.icone_tags:
									ListView lv = new ListView(MainActivity.this);
									lv.setAdapter(lTagsAdapter);
									lv.setBackgroundColor(getResources().getColor(R.color.blanc));
									
									int nbTacheSelectionnes = 0, positionTache = -1;
									
									for(int i = 0 ; i < lta.getCount() ; i++){
										if(lta.getItem(i).getAfficheSelection()){
											nbTacheSelectionnes++;
											positionTache = i;
										}
									}
																		
									if(nbTacheSelectionnes == 1){
										int nbTag = lta.getItem(positionTache).getListeTags().getTabTag().size();
										for(int i = 0 ; i < nbTag ; i++){
											for(int j = 0 ; j < lTagsAdapter.getCount() ; j++){
												if(lTagsAdapter.getItem(j).getId() == lta.getItem(positionTache).getListeTags().getTabTag().get(i).getId()){
													lTagsAdapter.getItem(j).setAfficheSelection(true);
												}
											}
										}
										
									}						
								
									
									lv.setOnItemClickListener(new ListView.OnItemClickListener(){
										@Override
										public void onItemClick(
											AdapterView<?> av, View v, int position, long arg3) {
												if(lTagsAdapter.getItem(position).getAfficheSelection())
													lTagsAdapter.getItem(position).setAfficheSelection(false);
												else
													lTagsAdapter.getItem(position).setAfficheSelection(true);
												lTagsAdapter.notifyDataSetChanged();
										}
									});
									
									AlertDialog.Builder listeTags = new AlertDialog.Builder(MainActivity.this);
									listeTags.setTitle("Choisissez le/les tags");
									listeTags.setCancelable(false);
									listeTags.setView(lv);
									
									listeTags.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(
											DialogInterface dialog, int position) {
												int nbTacheSelectionnes = 0;
												for(int i = 0 ; i < lta.getCount() ; i++){
													if(lta.getItem(i).getAfficheSelection())
														nbTacheSelectionnes++;
												}
												
												for(int i = 0 ; i < lta.getCount() ; i++){
													if(lta.getItem(i).getAfficheSelection()){
														boolean haveAModificationTag = false;
														for(int j = 0 ; j < lTagsAdapter.getCount() ; j++){
															
															//cochage
															if(lTagsAdapter.getItem(j).getAfficheSelection() && 
																	!lta.getItem(i).getListeTags().isInside(lTagsAdapter.getItem(j).getId())){
																lta.getItem(i).ajouterTag(lTagsAdapter.getItem(j));
																haveAModificationTag = true;
															}
															
															//d�cochage
															if(!lTagsAdapter.getItem(j).getAfficheSelection() &&
																	lta.getItem(i).getListeTags().isInside(lTagsAdapter.getItem(j).getId()) &&
																	nbTacheSelectionnes == 1){
																lta.getItem(i).supprimerTag(lTagsAdapter.getItem(j).getId());
																haveAModificationTag = true;
															}
																
														}
														
														if(haveAModificationTag)
															lta.modificationTacheAdapter(lta.getItem(i));
													}
												}
												
												for(int i = 0 ; i < lTagsAdapter.getCount() ; i++){
													lTagsAdapter.getItem(i).setAfficheSelection(false);
												}
												changerModeSelection();
										    	actualiserHeader();
										    	actualiserListe();
										}
									});
									
									listeTags.setNegativeButton("Annuler", new DialogInterface.OnClickListener(){
										@Override
										public void onClick(
											DialogInterface dialog, int position) {
												dialog.dismiss();
												for(int i = 0 ; i < lTagsAdapter.getCount() ; i++){
													lTagsAdapter.getItem(i).setAfficheSelection(false);
												}
												changerModeSelection();
										    	actualiserHeader();
										    	actualiserListe();
										}
									});
									
									listeTags.create().show();
									break;
							}
							
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
						lta.modificationTacheAdapter(lta.getItem(position));
					}
				}
				else{
					Intent descriptifTache = new Intent(MainActivity.this, DescriptifTache.class);
					Bundle donneesTache = new Bundle();
					donneesTache.putInt("id",lta.getItem(position).getIdTache());
					donneesTache.putString("nom", lta.getItem(position).getNom());
					donneesTache.putString("description", lta.getItem(position).getDescription());
					donneesTache.putBoolean("hasDate", lta.getItem(position).getHasDate());
					donneesTache.putBoolean("hasHeure", lta.getItem(position).getHasHour());
					donneesTache.putInt("dateJour", lta.getItem(position).getDate().getDate());
					donneesTache.putInt("dateMois", lta.getItem(position).getDate().getMonth());
					donneesTache.putInt("dateAnnee", lta.getItem(position).getDate().getYear());
					donneesTache.putInt("dateHeure", lta.getItem(position).getDate().getHours());
					donneesTache.putInt("dateMinute", lta.getItem(position).getDate().getMinutes());
					donneesTache.putInt("importance", lta.getItem(position).getImportance());
					donneesTache.putBoolean("etat", lta.getItem(position).getEtat());
					donneesTache.putString("listeTags", lta.getItem(position).getListeTagsString());
					descriptifTache.putExtras(donneesTache);
					startActivity(descriptifTache);
				}
			}
			else{
				
				if(!lta.getItem(position).getAfficheSelection())
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
			if(!lta.getItem(position).getAfficheSelection()){
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
				case 1:
					break;
				case 2:
					Intent gererTag = new Intent(MainActivity.this, GererTags.class);
					startActivity(gererTag);
					menuLayout.setVisibility(8);
					principalLayout.setVisibility(0);
					modeMenu = false;	
			}
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
	
	public void changerModeSelection(){
		Animation animApparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
		Animation animDecalageRL = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_rl);
		Animation animDecalageLR = AnimationUtils.loadAnimation(this, R.anim.tanslate_titre_lr);
		
		if(modeSelection){
			modeSelection = false;
			
			for(int i = 0 ; i < lta.getCount() ; i++){
				lta.getItem(i).setAfficheSelection(false);
			}
			
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
		
		Animation animTransitionMenuLR = AnimationUtils.loadAnimation(this, R.anim.translate_menu_lr);
		Animation animTransitionMenuRL = AnimationUtils.loadAnimation(this, R.anim.translate_menu_rl);
		
		animTransitionMenuRL.setAnimationListener(new Animation.AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				menuLayout.setVisibility(LinearLayout.GONE);
				principalLayout.setVisibility(LinearLayout.VISIBLE);
				modeMenu = false;
			}

			@Override
			public void onAnimationRepeat(Animation animation) {				
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}
			
		});

		
		if(modeMenu){
			menuLayout.startAnimation(animTransitionMenuRL);
		}
			
		else{
			modeMenu = true;
			principalLayout.setVisibility(LinearLayout.GONE);
			menuLayout.setVisibility(LinearLayout.VISIBLE);
			menuLayout.startAnimation(animTransitionMenuLR);
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
