package com.Activites;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.TimePicker;

import com.todolist.R;

public class AjoutAvanceTache extends Activity{
	
	String nomDeTache = null;
	String details = null;
	int importance;
	int jour, mois, annee;
	int heure, minute;
	
	Button boutonCreation = null;
	Button boutonDate = null;
	Button boutonHeure = null;
	
	TextView dateChoisie = null;
	TextView heureChoisie = null;
	ImageView croixDate = null;
	ImageView croixHeure = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_avance_tache);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
		
		//Initialisation du bouton Créer
		boutonCreation = (Button) findViewById(R.id.Creer);
		boutonCreation.setOnClickListener(click);
		
		//Initialisation des boutons Date & Heure
		boutonDate = (Button) findViewById(R.id.boutonDate);
		boutonDate.setOnClickListener(click);
		boutonHeure = (Button) findViewById(R.id.boutonHeure);
		boutonHeure.setOnClickListener(click);
		
		//Initialisation des TextView permettant de savoir le choix de l'utilisateur pour la date et l'heure
		dateChoisie = (TextView) findViewById(R.id.dateChoisie);
		heureChoisie = (TextView) findViewById(R.id.heureChoisie);
		dateChoisie.setText("");
		heureChoisie.setText("");
		
		//Initialisation des croix suppression pour la date et l'heure
		croixDate = (ImageView) findViewById(R.id.croixDate);
		croixHeure = (ImageView) findViewById(R.id.croixHeure);
		croixDate.setOnClickListener(click);
		croixHeure.setOnClickListener(click);
		
		changerVisibilityDateEtHeure();
		
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {

			Calendar calender = Calendar.getInstance();
			
			switch(v.getId()){
				case R.id.Creer:
					nomDeTache = (((EditText) findViewById(R.id.nomDeTache)).getText()).toString();
					details = (((EditText) findViewById(R.id.detailTache)).getText()).toString();
					importance = (((RatingBar) findViewById(R.id.importanceTache)).getProgress());
					
					Log.e("Nom", nomDeTache);
					Log.e("Description", details);
					Log.e("importance", String.valueOf(importance));
					break;
				
				case R.id.boutonDate:
		            Dialog dateDialog = new DatePickerDialog(AjoutAvanceTache.this, dateListener, 
		            									  calender.get(Calendar.YEAR),
		            									  calender.get(Calendar.MONTH), 
		            									  calender.get(Calendar.DAY_OF_MONTH));
	
		            dateDialog.show();
		            break;
		            
				case R.id.boutonHeure:
					Dialog heureDialog = new TimePickerDialog(AjoutAvanceTache.this, heureListener,
															calender.get(Calendar.HOUR_OF_DAY),
															calender.get(Calendar.MINUTE), 
															DateFormat.is24HourFormat(AjoutAvanceTache.this));
					heureDialog.show();
					break;
				
				case R.id.croixDate:
					dateChoisie.setText("");
					changerVisibilityDateEtHeure();
					break;
				
				case R.id.croixHeure:
					heureChoisie.setText("");
					changerVisibilityDateEtHeure();
					break;
					
			}
			
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
	
	
	private DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener(){

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			jour = dayOfMonth;
			mois = monthOfYear + 1;			//Les mois vont de 0 à 11 (+ 1)
			annee = year;
			dateChoisie.setText("Date choisie : " + jour + "/" + mois + "/" + annee);
            changerVisibilityDateEtHeure();
		}
		
	};
	
	private TimePickerDialog.OnTimeSetListener heureListener = new OnTimeSetListener(){

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			heure = hourOfDay;
			minute = minuteOfHour;
			heureChoisie.setText("Heure choisie : " + heure + "h" + minute);
			changerVisibilityDateEtHeure();
		}
		
		
	};
	
	public void changerVisibilityDateEtHeure(){
		
		if(dateChoisie.getText().equals("")){
			dateChoisie.setVisibility(View.GONE);
			croixDate.setVisibility(View.GONE);
		}	
		else{
			dateChoisie.setVisibility(View.VISIBLE);
			croixDate.setVisibility(View.VISIBLE);
		}
		
		if(heureChoisie.getText().equals("")){
			heureChoisie.setVisibility(View.GONE);
			croixHeure.setVisibility(View.GONE);
		}
			
		else{
			heureChoisie.setVisibility(View.VISIBLE);
			croixHeure.setVisibility(View.VISIBLE);
		}
	}
}
