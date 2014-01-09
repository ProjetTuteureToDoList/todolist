package com.Activites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.todolist.R;

public class DescriptifTache extends Activity{
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tache_descriptif);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
		
		TextView tache = (TextView) findViewById(R.id.tache);
		tache.setText("La tâche s'appelle " + getIntent().getStringExtra("nom") + 
					  "\nSa description est : " + getIntent().getStringExtra("description") +
					  "\nDate : " + getIntent().getIntExtra("dateJour", 1) + 
					  "/" + getIntent().getIntExtra("dateMois", 1) + 
					  "/" + getIntent().getIntExtra("dateAnnee", 1970) + 
					  "\nImportance de la tâche : " + getIntent().getIntExtra("importance", 1) + 
					  "\nEtat : ");
		if(getIntent().getBooleanExtra("etat", true))
			tache.setText(tache.getText() + "Fait");
		else
			tache.setText(tache.getText() + "Non fait");
		
		
	}
	
}