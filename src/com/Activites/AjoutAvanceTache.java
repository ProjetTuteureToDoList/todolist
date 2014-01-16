package com.Activites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.todolist.R;

public class AjoutAvanceTache extends Activity{
	String nomDeTache = (((EditText) findViewById(R.id.nomDeTache)).getText()).toString();
	String details = (((EditText) findViewById(R.id.detailTache)).getText()).toString();
	int importance = Integer.parseInt((((EditText) findViewById(R.id.importanceTacheText)).getText()).toString());

	
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
