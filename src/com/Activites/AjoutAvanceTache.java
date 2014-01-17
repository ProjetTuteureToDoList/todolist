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
			Log.e("Nom", nomDeTache);
			Log.e("Description", details);
			Log.e("importance", String.valueOf(importance));
		}
		
	};
	
}
