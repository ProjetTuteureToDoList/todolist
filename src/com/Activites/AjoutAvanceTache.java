package com.Activites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RatingBar;

import com.todolist.R;

public class AjoutAvanceTache extends Activity{
	
	String nomDeTache = null;
	String details = null;
	int importance;
	Button creation = null;
	String date = null;
	String heure = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_avance_tache);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
		
		
		creation = (Button) findViewById(R.id.Creer);
		
		creation.setOnClickListener(click);
		
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {
			
			nomDeTache = (((EditText) findViewById(R.id.nomDeTache)).getText()).toString();
			details = (((EditText) findViewById(R.id.detailTache)).getText()).toString();
			importance = (((RatingBar) findViewById(R.id.importanceTache)).getProgress());
			date = (((EditText) findViewById(R.id.dateTache)).getText()).toString() ;
			heure = (((EditText) findViewById(R.id.heureTache)).getText()).toString();
			
			Log.e("Nom", nomDeTache);
			Log.e("Description", details);
			Log.e("importance", String.valueOf(importance));
			
			// la date ici :3
			int i = 2, jour, mois, annee, heure, minute;
			String j, mo, an, h, min;
			//	Have fun Juju tu vas t'ammuser à découper des string de date que l'utilisateur va peut être foirer :3
			/*
			if (compareTo(date.charAt(i), "/")) {
				j = date.charAt(i-1);
				i++; i++;
			}
			else {
				j = date.charAt(i-1) + date.charAt(2);
				i++; i++; i++;
			}
			*/
			//mo = date.charAt(4
			  
			
			
		}
		
	};
	
}
