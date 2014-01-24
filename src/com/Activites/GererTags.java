package com.Activites;

import Adapters.ListeTagAdapter;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
	
	ImageView couleur = null;
	Button ajouterTag = null;
	EditText texteNomTag = null;
	
	ImageView suppr = null;
	ImageView preference = null;
	ImageView retour = null;
	
	ListeTagAdapter lta = null;
	ListView tagList = null;
	
	boolean modeSelection = false;		//Permet de savoir si au moins un tag à été sélectionné ou non

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerer_tags);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		((TextView) findViewById(R.id.titre)).setTypeface(Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf"));
		
		//Initialisation de l'ImageView représentant la couleur du tag
		couleur = (ImageView) findViewById(R.id.couleur);
		couleur.setOnClickListener(click);
		
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
		ImageView suppr = (ImageView) findViewById(R.id.corbeille);
		suppr.setOnTouchListener(touchClick);
		ImageView preference = (ImageView) findViewById(R.id.icone_preference);
		
		
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {
			switch(v.getId()){
				case R.id.couleur:
					ColorPickerDialog colorPickerDialog = new ColorPickerDialog(GererTags.this, 0, new OnColorSelectedListener() {

				        @Override
				        public void onColorSelected(int color) {
				            couleur.setBackgroundColor(color);
				        }
					});
					colorPickerDialog.show();
					break;
					
				case R.id.bouton:
					if(texteNomTag.getText().toString().replace(" ", "").length() > 0){
						
					}
					
				  
			}

		}
		
	};
	
	private OnItemClickListener tagClick = new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			
			
		}
		
	};
	
	private OnItemLongClickListener tagLongClick = new OnItemLongClickListener(){

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			return false;
		}
		
	};
	
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
			suppr.startAnimation(animApparition);
		}
			
	}
}

