package com.Activites;

import gestionDesTags.Tag;
import Adapters.ListeTagAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.chiralcode.colorpicker.ColorPickerDialog.OnColorSelectedListener;
import com.todolist.R;

public class GererTags extends Activity{
	
	private int couleur = - 1;
	
	private ImageView couleurTag = null;
	private Button ajouterTag = null;
	private EditText texteNomTag = null;
	
	private ImageView suppr = null;
	private ImageView preference = null;
	private ImageView retour = null;
	
	private ListeTagAdapter lta = null;
	private ListView tagList = null;
	
	private boolean modeSelection = false;		//Permet de savoir si au moins un tag à été sélectionné ou non

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerer_tags);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		((TextView) findViewById(R.id.titre)).setTypeface(Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf"));
		
		//Initialisation de l'ImageView représentant la couleur du tag
		couleurTag = (ImageView) findViewById(R.id.couleur);
		couleurTag.setOnClickListener(click);
		
		//Initialisation du bouton pour l'ajout de tag
		ajouterTag = (Button) findViewById(R.id.bouton);
		ajouterTag.setOnClickListener(click);
		
		//Initialisation du EditText pour le nom du nouveau tag
		texteNomTag = (EditText) findViewById(R.id.entreeTexte);
		
		//Initialisation de l'adapter et de la listView contenant la liste complète des tags
		lta = new ListeTagAdapter(this);
		tagList = (ListView) findViewById(R.id.listview);
		tagList.setAdapter(lta);
		tagList.setOnItemClickListener(tagClick);
		tagList.setOnItemLongClickListener(tagLongClick);
		
		//Initialisation des ImageView "boutons" Retour, Préférences et Suppression
		suppr = (ImageView) findViewById(R.id.corbeille);
		suppr.setOnTouchListener(touchClick);
		suppr.setVisibility(View.GONE);
		preference = (ImageView) findViewById(R.id.icone_preference);
		preference.setOnTouchListener(touchClick);
		retour = (ImageView) findViewById(R.id.retour);
		retour.setOnTouchListener(touchClick);		
		
		//Initialisation du layout allScreen
		findViewById(R.id.allScreen).setOnClickListener(click);
		
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.couleur:
					ColorPickerDialog colorPickerDialog = new ColorPickerDialog(GererTags.this, 0, new OnColorSelectedListener() {

				        @Override
				        public void onColorSelected(int color) {
				        	couleur = color;
				            couleurTag.setBackgroundColor(couleur);
				        }

		/*				@Override
						public void onColorRefused() {
							couleur = -1;
							couleurTag.setBackgroundResource(R.drawable.border);
						}
		*/
					});
					colorPickerDialog.show();
					break;
					
				case R.id.bouton:
					if(texteNomTag.getText().toString().replace(" ", "").length() > 0){
						if(couleur != -1)
							lta.ajoutTagAdapter(new Tag(texteNomTag.getText().toString(), Integer.toHexString(couleur)));
						else
							lta.ajoutTagAdapter(new Tag(texteNomTag.getText().toString()));
						
						couleurTag.setBackgroundResource(R.drawable.border);
						texteNomTag.setText(null);
						texteNomTag.clearFocus();
				    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				    	inputMethodManager.hideSoftInputFromWindow(texteNomTag.getWindowToken(), 0);
				    	actualiserListe();
					}
					
				case R.id.allScreen:
			    	texteNomTag.clearFocus();	
			    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			    	inputMethodManager.hideSoftInputFromWindow(texteNomTag.getWindowToken(), 0);
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
			
			if(v.getId() == R.id.icone_preference || v.getId() == R.id.corbeille || v.getId() == R.id.retour){
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
								case R.id.icone_preference:
									break;
									
								case R.id.corbeille:
									int nbTagSelectionnes = 0;
									for(int i = 0 ; i < lta.getCount() ; i++){
							    		if(lta.getItem(i).getAfficheSelection())
							    			nbTagSelectionnes++;
									}
									
									Builder confirmationSuppr = new AlertDialog.Builder(GererTags.this);
									if(nbTagSelectionnes == 1)
										confirmationSuppr.setTitle("Êtes-vous sûr de vouloir supprimer ce tag ?");
									else
										confirmationSuppr.setTitle("Êtes-vous sûr de vouloir supprimer ces tags ?");
									
									confirmationSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
											for(int i = 0 ; i < lta.getCount() ; i++){
									    		if(lta.getItem(i).getAfficheSelection()){
									    			lta.suppressionTagAdapter(i);
									    			i--;
									    		}
									    	}
											actualiserListe();
											changerModeSelection();
										}
									});
									
									confirmationSuppr.setNegativeButton("Non", new DialogInterface.OnClickListener(){
										public void onClick(DialogInterface dialog, int id){
										}
									});
									confirmationSuppr.show();
							    	break;
							    	
								case R.id.retour:
									if(modeSelection){
								    	for(int i = 0 ; i < lta.getCount() ; i++)
								    		lta.getItem(i).setAfficheSelection(false);
								    	changerModeSelection();
									}
									else{
										Intent mainActivity = new Intent(GererTags.this, MainActivity.class);
										startActivity(mainActivity);
									}
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
	
	private OnItemClickListener tagClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			if(modeSelection){
				if(!lta.getItem(position).getAfficheSelection())
					lta.getItem(position).setAfficheSelection(true);
				else{
					lta.getItem(position).setAfficheSelection(false);
					if(!lta.isSelectionned())
						changerModeSelection();
				}
			}
			actualiserListe();
		}
		
	};
	
	private OnItemLongClickListener tagLongClick = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View v,
				int position, long arg3) {
			if(modeSelection){
				if(!lta.getItem(position).getAfficheSelection())
					lta.getItem(position).setAfficheSelection(true);
				else{
					lta.getItem(position).setAfficheSelection(false);
					if(!lta.isSelectionned())
						changerModeSelection();
				}
			}
				
			else
				changerModeSelection();
			actualiserListe();
			return false;
		}
		
	};
	
	public void actualiserListe(){
		lta.notifyDataSetChanged();
	}
	
	public void changerModeSelection(){
		Animation animApparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
		
		if(modeSelection){
			modeSelection = false;
			suppr.setVisibility(View.GONE);
			preference.startAnimation(animApparition);
			preference.setVisibility(View.VISIBLE);
			
		}
		else{
			modeSelection = true;
			preference.setVisibility(View.GONE);
			suppr.startAnimation(animApparition);
			suppr.setVisibility(View.VISIBLE);
		}
			
	}
}

