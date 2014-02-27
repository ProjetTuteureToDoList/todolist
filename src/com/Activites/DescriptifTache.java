package com.Activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.todolist.R;

public class DescriptifTache extends Activity {

	Boolean modif = false;
	TextView nom = null;
	TextView description = null;
	TextView descriptionr = null;
	TextView date = null;
	TextView dater = null;
	TextView etat = null;
	TextView etatr = null;
	TextView importancetext = null;
	RatingBar importance = null;
	ImageView retour = null;
	ImageView modification = null;
	ImageView suppr = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tache_descriptif);

		// Initialisation du titre : ouverture du fichier pour la police
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"Lifestyle M54.ttf");
		titre.setTypeface(font);

		retour = (ImageView) findViewById(R.id.retour);
		modification = (ImageView) findViewById(R.id.modification);
		suppr = (ImageView) findViewById(R.id.corbeille);

		retour.setOnTouchListener(touchClick);
		modification.setOnTouchListener(touchClick);
		suppr.setOnTouchListener(touchClick);
		
		String datecomplte;
		
		if(!getIntent().getBooleanExtra("hasDate", true))
			datecomplte = "Tous les jours";
		else
			datecomplte = (String.valueOf(getIntent().getIntExtra(
					"dateJour", 1)))
					+ "/"
					+ (String.valueOf(getIntent().getIntExtra("dateMois", 1)))
					+ "/"
					+ (String.valueOf(getIntent().getIntExtra("dateAnnee", 1970)));		
		
		if(getIntent().getBooleanExtra("hasHour", true))
			datecomplte = datecomplte.concat(" à  " + (String.valueOf(getIntent().getIntExtra("dateHeure", 0)))
					+ " : "	+ (String.valueOf(getIntent().getIntExtra("dateMinute", 0))));
		
		
		nom = (TextView) findViewById(R.id.nom);
		nom.setText(getIntent().getStringExtra("nom"));
		description = (TextView) findViewById(R.id.description);
		description.setText("Description: ");
		descriptionr = (TextView) findViewById(R.id.descriptionr);
		descriptionr.setText(getIntent().getStringExtra("description"));
		date = (TextView) findViewById(R.id.date);
		date.setText("Date");
		dater = (TextView) findViewById(R.id.dater);
		dater.setText(datecomplte);
		importancetext = (TextView) findViewById(R.id.importancetext);
		importancetext.setText("Importance de la tâche");
		importance = (RatingBar) findViewById(R.id.importance);
		importance
				.setRating((float) ((getIntent().getIntExtra("importance", 0)) * 0.5));
		etat = (TextView) findViewById(R.id.etat);
		etat.setText("Etat: ");
		etatr = (TextView) findViewById(R.id.etatr);
		if (getIntent().getBooleanExtra("etat", true))
			etatr.setText("Fait");
		else
			etatr.setText("Non fait");

	}

	private View.OnTouchListener touchClick = new OnTouchListener() {
		boolean isInside = true;
		float viewLargeur = 0;
		float viewHauteur = 0;

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			if (v.getId() == R.id.corbeille || v.getId() == R.id.retour
					|| v.getId() == R.id.modification) {
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
						case R.id.modification:
							modif = true;
							Intent ajoutAvanceTache = new Intent(
									DescriptifTache.this,
									AjoutAvanceTache.class);
							Bundle modification = new Bundle();
							modification.putBoolean("modif", true);
							modification.putInt("descriptif_tache_id",
									getIntent().getIntExtra("id", 1));
							modification.putString("nom", getIntent()
									.getStringExtra("nom"));
							modification.putString("description", getIntent()
									.getStringExtra("description"));
							modification.putInt("dateJour", getIntent()
									.getIntExtra("dateJour", 1));
							modification.putInt("dateMois", getIntent()
									.getIntExtra("dateMois", 1));
							modification.putInt("dateAnnee", getIntent()
									.getIntExtra("dateAnnee", 1));
							modification.putInt("dateHeure", getIntent()
									.getIntExtra("dateHeure", 1));
							modification.putInt("dateMinute", getIntent()
									.getIntExtra("dateMinute", 1));
							modification.putInt("importance",
									(int) ((getIntent().getIntExtra(
											"importance", 0))));
							modification.putBoolean("etat", getIntent()
									.getBooleanExtra("etat", true));
							modification.putString("listeTags", getIntent()
									.getStringExtra("listeTags"));
							ajoutAvanceTache.putExtras(modification);
							startActivity(ajoutAvanceTache);

							break;
						case R.id.corbeille:

							Builder confirmationSuppr = new AlertDialog.Builder(
									DescriptifTache.this);
							confirmationSuppr
									.setTitle("Êtes-vous sûr de vouloir supprimer cette tâche ?");

							confirmationSuppr.setPositiveButton("Oui",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Intent MainActivity = new Intent(
													DescriptifTache.this,
													MainActivity.class);
											Bundle Idsuppr = new Bundle();
											Idsuppr.putInt(
													"descriptif_tache_id",
													getIntent().getIntExtra(
															"id", 1));
											MainActivity.putExtras(Idsuppr);
											startActivity(MainActivity);
										}
									}

							);

							confirmationSuppr.setNegativeButton("Non",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
										}
									});
							confirmationSuppr.show();
							break;

						case R.id.retour:
							Intent MainActivity = new Intent(
									DescriptifTache.this, MainActivity.class);
							startActivity(MainActivity);
							break;

						case R.id.icone_tags:
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

}