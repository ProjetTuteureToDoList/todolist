package com.Activites;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.todolist.R;

public class AjoutAvanceTache extends Activity{
	
	String nomDeTache = null;
	String details = null;
	int importance;
	int jour = -1, mois = -1, annee = -1;
	int heure = -1, minute = -1;
	
	Button boutonCreation = null;
	Button boutonDate = null;
	Button boutonHeure = null;
	TextView importanceText = null;
	
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
		
		//Initialisation du bouton Cr�er
		boutonCreation = (Button) findViewById(R.id.Creer);
		boutonCreation.setOnClickListener(click);
		
		//Initialisation des boutons Date & Heure
		boutonDate = (Button) findViewById(R.id.boutonDate);
		boutonDate.setOnClickListener(click);
		boutonHeure = (Button) findViewById(R.id.boutonHeure);
		boutonHeure.setOnClickListener(click);
		
		//Initialisation importanceText, qui est le texte qui renvoie l'importance choisie en texte
		importanceText = (TextView) findViewById(R.id.importanceTacheText);
		importance = 0;
		importanceText.setText("Importance de la t�che (Choix : " + importance + ")");
		((RatingBar) findViewById(R.id.importanceTache)).setOnRatingBarChangeListener(RatingBarNew);
		
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
		
		//Initialisation Listener sur allScreen
		findViewById(R.id.allScreen).setOnClickListener(click);
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {

			Calendar calender = Calendar.getInstance();
			switch(v.getId()){
				case R.id.Creer:
					nomDeTache = (((EditText) findViewById(R.id.nomDeTache)).getText()).toString();
					details = (((EditText) findViewById(R.id.detailTache)).getText()).toString();
					
					if(nomDeTache.replace(" ", "").length() > 0){
						Calendar dateActuelle = Calendar.getInstance();
						Intent mainActivity = new Intent(AjoutAvanceTache.this, MainActivity.class);
						Bundle donneesTache = new Bundle();
						donneesTache.putInt("ajout_avancee", 1);
						donneesTache.putString("nom", nomDeTache);
						donneesTache.putString("description", details);
						donneesTache.putInt("dateJour", jour);
						donneesTache.putInt("dateMois", mois);
						donneesTache.putInt("dateAnnee", annee);
						donneesTache.putInt("dateHeure", heure);
						donneesTache.putInt("dateMinute", minute);
						donneesTache.putInt("importance", importance);
						mainActivity.putExtras(donneesTache);
						startActivity(mainActivity);
					}
					else
						Toast.makeText(AjoutAvanceTache.this, "Votre t�che n'a pas de nom !", Toast.LENGTH_SHORT).show();
					
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
					jour = -1;
					mois = -1;
					annee = -1;
					changerVisibilityDateEtHeure();
					break;
				
				case R.id.croixHeure:
					heureChoisie.setText("");
					heure = -1;
					minute = -1;
					changerVisibilityDateEtHeure();
					break;
				
				case R.id.allScreen:
					InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					
					if(findViewById(R.id.nomDeTache).isFocused()){
						findViewById(R.id.nomDeTache).clearFocus();	
				    	inputMethodManager.hideSoftInputFromWindow(findViewById(R.id.nomDeTache).getWindowToken(), 0);
					}
					if(findViewById(R.id.detailTache).isFocused()){
						findViewById(R.id.detailTache).clearFocus();
				    	inputMethodManager.hideSoftInputFromWindow(findViewById(R.id.detailTache).getWindowToken(), 0);
					}
			    	break;					
			}
					
		}
		
	};
	
	private DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener(){
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if(view.isShown()){
				jour = dayOfMonth;
				mois = monthOfYear + 1;			//Les mois vont de 0 � 11 (+ 1)
				annee = year;
				dateChoisie.setText("Date choisie : " + jour + "/" + mois + "/" + annee);
	            changerVisibilityDateEtHeure();
			}
		}
		
	};
	
	private TimePickerDialog.OnTimeSetListener heureListener = new OnTimeSetListener(){
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
			
			if(view.isShown()){
				heure = hourOfDay;
				minute = minuteOfHour;
				heureChoisie.setText("Heure choisie : " + heure + "h" + minute);
				changerVisibilityDateEtHeure();
			}
		}
		
		
	};
	
	private RatingBar.OnRatingBarChangeListener RatingBarNew = new OnRatingBarChangeListener(){

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
				importance = ratingBar.getProgress();
				importanceText.setText("Importance de la t�che (Choix : " + importance + ")");
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
