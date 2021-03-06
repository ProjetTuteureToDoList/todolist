package com.Activites;

import java.util.ArrayList;
import java.util.Calendar;

import Adapters.ListeTagAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.todolist.R;

public class AjoutAvanceTache extends Activity {

	String nomDeTache = null;
	String details = null;
	int importance;
	int jour = -1, mois = -1, annee = -1;
	int heure = -1, minute = -1;

	Button boutonCreation = null;
	Button boutonDate = null;
	Button boutonHeure = null;
	TextView importanceText = null;
	String listeTags = null;

	TextView dateChoisie = null;
	TextView heureChoisie = null;
	ImageView croixDate = null;
	ImageView croixHeure = null;
	ImageView boutonRetour = null;
	
	TextView listeTagsView = null;

	ListeTagAdapter lta = null;
	ArrayList<Integer> tabIdTag = new ArrayList<Integer>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout_avance_tache);

		// Initialisation du titre : ouverture du fichier pour la police
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Lifestyle M54.ttf");
		titre.setTypeface(font);

		// Initialisation du bouton Cr�er
		boutonCreation = (Button) findViewById(R.id.Creer);
		boutonCreation.setOnClickListener(click);

		// Initialisation des boutons Date & Heure
		boutonDate = (Button) findViewById(R.id.boutonDate);
		boutonDate.setOnClickListener(click);
		boutonHeure = (Button) findViewById(R.id.boutonHeure);
		boutonHeure.setOnClickListener(click);

		// Initialisation importanceText, qui est le texte qui renvoie
		// l'importance choisie en texte
		importanceText = (TextView) findViewById(R.id.importanceTacheText);
		importance = 0;
		importanceText.setText("Importance de la t�che (Choix : " + importance
				+ ")");
		((RatingBar) findViewById(R.id.importanceTache))
				.setOnRatingBarChangeListener(RatingBarNew);

		// Initialisation des TextView permettant de savoir le choix de
		// l'utilisateur pour la date et l'heure
		dateChoisie = (TextView) findViewById(R.id.dateChoisie);
		heureChoisie = (TextView) findViewById(R.id.heureChoisie);
		dateChoisie.setText("");
		heureChoisie.setText("");

		// Initialisation des croix suppression pour la date et l'heure
		croixDate = (ImageView) findViewById(R.id.croixDate);
		croixHeure = (ImageView) findViewById(R.id.croixHeure);
		croixDate.setOnClickListener(click);
		croixHeure.setOnClickListener(click);
		
		changerVisibilityDateEtHeure();

		// Initialisation Listener sur allScreen
		findViewById(R.id.allScreen).setOnClickListener(click);

		//Initialisation de la liste en String des tags de la t�che
		listeTags = "";
		
		// Initialisation bouton retour
		boutonRetour = (ImageView) findViewById(R.id.retour);
		boutonRetour.setOnTouchListener(touchClick);
		
		//Initialisation bouton modifier tags
		((Button) findViewById(R.id.ListeDesTagsBouton)).setOnClickListener(click);

		if ((getIntent().getBooleanExtra("modif", false))) {
			boutonCreation.setText("Modifier");
			EditText edittext_nom = (EditText) findViewById(R.id.nomDeTache);
			edittext_nom.setText((getIntent().getStringExtra("nom")));
			EditText edittext_details = (EditText) findViewById(R.id.detailTache);
			edittext_details
					.setText((getIntent().getStringExtra("description")));
			if(getIntent().getBooleanExtra("hasDate", false)){
				jour = getIntent().getIntExtra("dateJour", 1);
				mois = getIntent().getIntExtra("dateMois", 1);
				annee = getIntent().getIntExtra("dateAnnee", 1970);
				dateChoisie.setText("Date choisie : " + jour + "/" + mois + "/"
						+ annee);
			}
			if(getIntent().getBooleanExtra("hasHour", false)){
				heure = getIntent().getIntExtra("dateHeure", 0);
				minute = getIntent().getIntExtra("dateMinute", 0);
				heureChoisie.setText("Heure choisie : " + heure + "h" + minute);
			}
			changerVisibilityDateEtHeure();

			RatingBar importancer = (RatingBar) findViewById(R.id.importanceTache);
			importancer.setRating((float) ((getIntent().getIntExtra(
					"importance", 0)) * 0.5));
			
			listeTags = getIntent().getStringExtra("listeTags");
			readTags();
		}
		
		lta = new ListeTagAdapter(this, 1);
		
		//Initialisation du textView de la liste de tags 
		listeTagsView = (TextView) findViewById(R.id.liste_tags_descriptif);
		listeTagsView.setText(lta.getListeTag().getListeNomTag(listeTags));
		
	}

	private OnClickListener click = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Calendar dateActuelle = Calendar.getInstance();

			switch (v.getId()) {
			case R.id.Creer:

				nomDeTache = (((EditText) findViewById(R.id.nomDeTache))
						.getText()).toString();
				details = (((EditText) findViewById(R.id.detailTache))
						.getText()).toString();
				boolean dateEtHeureCorrectes = false, justeHeure = false;
				
				if (nomDeTache.replace(" ", "").length() > 0) {
					if (annee > dateActuelle.get(Calendar.YEAR))
						dateEtHeureCorrectes = true;
					else if (annee == dateActuelle.get(Calendar.YEAR)
							|| annee == -1) {
						if (mois > dateActuelle.get(Calendar.MONTH) + 1)
							dateEtHeureCorrectes = true;
						else if (mois == dateActuelle.get(Calendar.MONTH) + 1
								|| mois == -1) {
							if (jour > dateActuelle.get(Calendar.DAY_OF_MONTH))
								dateEtHeureCorrectes = true;
							else if (jour == dateActuelle
									.get(Calendar.DAY_OF_MONTH) || jour == -1) {
								if(annee == -1 && mois == -1 && jour == -1)
									justeHeure = true;
								if (heure > dateActuelle
										.get(Calendar.HOUR_OF_DAY) || (heure != -1 && justeHeure))
									dateEtHeureCorrectes = true;
								else if (heure == dateActuelle
										.get(Calendar.HOUR_OF_DAY)
										|| heure == -1) {
									if (minute >= dateActuelle
											.get(Calendar.MINUTE)
											|| minute == -1)
										dateEtHeureCorrectes = true;
									else
										Toast.makeText(
												AjoutAvanceTache.this,
												"Vous venez du pass� ? Erreur : minutes",
												Toast.LENGTH_SHORT).show();
								} else
									Toast.makeText(
											AjoutAvanceTache.this,
											"Vous venez du pass� ? Erreur : heures",
											Toast.LENGTH_SHORT).show();
							} else
								Toast.makeText(AjoutAvanceTache.this,
										"Vous venez du pass� ? Erreur : jour",
										Toast.LENGTH_SHORT).show();
						} else
							Toast.makeText(AjoutAvanceTache.this,
									"Vous venez du pass� ? Erreur : mois",
									Toast.LENGTH_SHORT).show();
					} else
						Toast.makeText(AjoutAvanceTache.this,
								"Vous venez du pass� ? Erreur : ann�e",
								Toast.LENGTH_SHORT).show();

				} else
					Toast.makeText(AjoutAvanceTache.this,
							"Votre t�che n'a pas de nom !", Toast.LENGTH_SHORT)
							.show();

				if (dateEtHeureCorrectes) {
					Intent mainActivity = new Intent(AjoutAvanceTache.this,
							MainActivity.class);
					Bundle donneesTache = new Bundle();
					if (getIntent().getBooleanExtra("modif", false) == true) {
						donneesTache.putInt("modification", 1);
						donneesTache.putInt("id",
								getIntent().getIntExtra("descriptif_tache_id", 0));
						donneesTache.putBoolean("etat", getIntent()
								.getBooleanExtra("etat", false));
					} else {
						donneesTache.putInt("ajout_avancee", 1);
					}
					donneesTache.putString("nom", nomDeTache);
					donneesTache.putString("description", details);
					donneesTache.putInt("dateJour", jour);
					donneesTache.putInt("dateMois", mois);
					donneesTache.putInt("dateAnnee", annee);
					donneesTache.putInt("dateHeure", heure);
					donneesTache.putInt("dateMinute", minute);
					donneesTache.putInt("importance", importance);
					donneesTache.putString("listeTags", listeTags);
					mainActivity.putExtras(donneesTache);
					startActivity(mainActivity);
				}
				break;

			case R.id.boutonDate:
				Dialog dateDialog = new DatePickerDialog(AjoutAvanceTache.this,
						dateListener, dateActuelle.get(Calendar.YEAR),
						dateActuelle.get(Calendar.MONTH),
						dateActuelle.get(Calendar.DAY_OF_MONTH));

				dateDialog.show();
				break;

			case R.id.boutonHeure:
				Dialog heureDialog = new TimePickerDialog(
						AjoutAvanceTache.this, heureListener,
						dateActuelle.get(Calendar.HOUR_OF_DAY),
						dateActuelle.get(Calendar.MINUTE),
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
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				if (findViewById(R.id.nomDeTache).isFocused()) {
					findViewById(R.id.nomDeTache).clearFocus();
					inputMethodManager.hideSoftInputFromWindow(
							findViewById(R.id.nomDeTache).getWindowToken(), 0);
				}
				if (findViewById(R.id.detailTache).isFocused()) {
					findViewById(R.id.detailTache).clearFocus();
					inputMethodManager.hideSoftInputFromWindow(
							findViewById(R.id.detailTache).getWindowToken(), 0);
				}
				break;
			
			case R.id.ListeDesTagsBouton:
				ListView lv = new ListView(AjoutAvanceTache.this);
				lv.setAdapter(lta);
				lv.setBackgroundColor(getResources().getColor(R.color.blanc));
				
				for(Integer id : tabIdTag)
					lta.getItem(lta.findPositionWithId(id)).setAfficheSelection(true);
				
				
				lv.setOnItemClickListener(new ListView.OnItemClickListener(){
					@Override
					public void onItemClick(
						AdapterView<?> av, View v, int position, long arg3) {
							if(lta.getItem(position).getAfficheSelection())
								lta.getItem(position).setAfficheSelection(false);
							else
								lta.getItem(position).setAfficheSelection(true);
							lta.notifyDataSetChanged();
					}
				});
				
				AlertDialog.Builder listeTags = new AlertDialog.Builder(AjoutAvanceTache.this);
				listeTags.setTitle("Choisissez le/les tags");
				listeTags.setCancelable(false);
				listeTags.setView(lv);
				
				listeTags.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(
						DialogInterface dialog, int position) {
										
							
							for(int i = 0 ; i < lta.getCount() ; i++){
								//cochage
								if(lta.getItem(i).getAfficheSelection() && 
										!tabIdTag.contains(lta.getItem(i).getId()))
									tabIdTag.add(lta.getItem(i).getId());
									
								
								//d�cochage
								if(!lta.getItem(i).getAfficheSelection() &&
										tabIdTag.contains(lta.getItem(i).getId()))
									tabIdTag.remove(tabIdTag.indexOf(lta.getItem(i).getId()));
									
							}
							
							for(int i = 0 ; i < lta.getCount() ; i++){
								lta.getItem(i).setAfficheSelection(false);
							}
							writeTags();
							actualiserListeTags();
					}
				});
				
				listeTags.setNegativeButton("Annuler", new DialogInterface.OnClickListener(){
					@Override
					public void onClick(
						DialogInterface dialog, int position) {
							dialog.dismiss();
							for(int i = 0 ; i < lta.getCount() ; i++){
								lta.getItem(i).setAfficheSelection(false);
							}
					}
				});
				
				listeTags.create().show();
				break;
			}

		}

	};

	private DatePickerDialog.OnDateSetListener dateListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (view.isShown()) {
				jour = dayOfMonth;
				mois = monthOfYear + 1; // Les mois vont de 0 � 11 (+ 1)
				annee = year;
				dateChoisie.setText("Date choisie : " + jour + "/" + mois + "/"
						+ annee);
				changerVisibilityDateEtHeure();
			}
		}

	};

	private TimePickerDialog.OnTimeSetListener heureListener = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {

			if (view.isShown()) {
				heure = hourOfDay;
				minute = minuteOfHour;
				heureChoisie.setText("Heure choisie : " + heure + "h" + minute);
				changerVisibilityDateEtHeure();
			}
		}

	};

	private RatingBar.OnRatingBarChangeListener RatingBarNew = new OnRatingBarChangeListener() {

		@Override
		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			importance = ratingBar.getProgress();
			importanceText.setText("Importance de la t�che (Choix : "
					+ importance + ")");
		}

	};

	public void changerVisibilityDateEtHeure() {

		if (dateChoisie.getText().equals("")) {
			dateChoisie.setVisibility(View.GONE);
			croixDate.setVisibility(View.GONE);
		} else {
			dateChoisie.setVisibility(View.VISIBLE);
			croixDate.setVisibility(View.VISIBLE);
		}

		if (heureChoisie.getText().equals("")) {
			heureChoisie.setVisibility(View.GONE);
			croixHeure.setVisibility(View.GONE);
		}

		else {
			heureChoisie.setVisibility(View.VISIBLE);
			croixHeure.setVisibility(View.VISIBLE);
		}

	}

	private View.OnTouchListener touchClick = new OnTouchListener() {
		boolean isInside = true;
		float viewLargeur = 0;
		float viewHauteur = 0;

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (v.getId() == R.id.retour) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					viewLargeur = v.getRight() - v.getLeft();
					viewHauteur = v.getBottom() - v.getTop();
					v.setBackgroundResource(R.color.bleuSelection);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.color.bleu);
					if (isInside) {
						switch (v.getId()) {

						case R.id.retour:
							Intent MainActivity = new Intent(
									AjoutAvanceTache.this, MainActivity.class);
							startActivity(MainActivity);
							break;

						}
					}
					break;

				case MotionEvent.ACTION_MOVE:
					if (event.getX() < viewLargeur
							&& event.getY() < viewHauteur && event.getX() > 0
							&& event.getY() > 0)
						isInside = true;
					else
						isInside = false;
					break;
				}

				return true;
			} else
				return false;
		}

	};
	
	public void readTags(){
		int i = 0;
		String tag = "";
		while(i < listeTags.length()){
			if(listeTags.charAt(i) != '/' && listeTags.charAt(i) != '\n')	{
				tag = tag.concat(String.valueOf(listeTags.charAt(i)));
			}
			if(listeTags.charAt(i) == '/'){
				tabIdTag.add(Integer.parseInt(tag));
				tag = "";
			}
			i++;
		}
	}
	
	public void writeTags(){
		listeTags = "";
		if(tabIdTag.size() > 0){
			for(int i = 0 ; i < tabIdTag.size() ; i++)
				listeTags = listeTags.concat(String.valueOf(tabIdTag.get(i)) + "/");
		}
	}
	
	public void actualiserListeTags(){
		listeTagsView.setText(lta.getListeTag().getListeNomTag(listeTags));
	}

}
