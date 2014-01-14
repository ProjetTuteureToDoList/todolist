package com.Activites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.todolist.R;

public class AjoutAvanceTache extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_avance_tache);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
	}
}
