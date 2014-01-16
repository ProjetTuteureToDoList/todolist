package com.Activites;

import Adapters.ListeTacheAdapter;
import Adapters.MenuAdapter;
import Autres.OnSwipeTouchListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.todolist.R;

public class DescriptifTache extends Activity{
	
	ImageView retour = null;
	ImageView modification = null;
	ImageView suppr = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tache_descriptif);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView titre = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		titre.setTypeface(font);
		
		retour = (ImageView) findViewById(R.id.retour);
		modification = (ImageView) findViewById(R.id.modification);
		suppr = (ImageView) findViewById(R.id.corbeille);
		
		retour.setOnTouchListener(touchClick);
		modification.setOnTouchListener(touchClick);
		suppr.setOnTouchListener(touchClick);
		
		TextView tache = (TextView) findViewById(R.id.tache);
		tache.setText("Id"+getIntent().getIntExtra("id", 1) + "/nNom: " + getIntent().getStringExtra("nom") + 
					  "\nDescription : " + getIntent().getStringExtra("description") +
					  "\nDate : " + getIntent().getIntExtra("date", 1) + 
					  "/" + getIntent().getIntExtra("dateMois", 1) + 
					  "/" + getIntent().getIntExtra("dateAnnee", 1970) +
					  "\nPriorité : " + getIntent().getIntExtra("importance", 1) + 
					  "\nEtat : ");
		if(getIntent().getBooleanExtra("etat", true))
			tache.setText(tache.getText() + "Fait");
		else
			tache.setText(tache.getText() + "Non fait");
		
	}
	
	private View.OnTouchListener touchClick = new OnTouchListener(){
		boolean isInside = true;
		float viewLargeur = 0;
		float viewHauteur = 0;
	
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		if(v.getId() == R.id.corbeille || v.getId() == R.id.retour ||v.getId() == R.id.modification){
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					viewLargeur = v.getRight() - v.getLeft();
					viewHauteur = v.getBottom() - v.getTop();
					v.setBackgroundResource(R.color.bleuSelection);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundResource(R.color.bleu);
					if(isInside){
						switch(v.getId()){
								
							case R.id.corbeille:
								Intent MainActivity = new Intent(DescriptifTache.this, MainActivity.class);
								Bundle Idsuppr = new Bundle();
								Idsuppr.putInt("id" , getIntent().getIntExtra("id", 1));
								MainActivity.putExtras(Idsuppr);
								startActivity(MainActivity);
								/*Builder confirmationSuppr = new AlertDialog.Builder(DescriptifTache.this);
									confirmationSuppr.setTitle("Êtes-vous sûr de supprimer cette tâche ?");
							
								confirmationSuppr.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
									public void onClick(DialogInterface dialog, int id){
								    		if(lta.getItem(id).getAfficheOption()){
								    			lta.supprimer(this);
								    		}
								    	}
									
									}
								);
								
								confirmationSuppr.setNegativeButton("Non", new DialogInterface.OnClickListener(){
									public void onClick(DialogInterface dialog, int id){
									}
								});
								confirmationSuppr.show();
						    	break;*/
						    	
							case R.id.retour:
						    	break;	
						    
							case R.id.icone_tags:
								break;
						}
					}
					break;
					
				case MotionEvent.ACTION_MOVE:
					if(event.getX() < viewLargeur && event.getY() < viewHauteur && event.getX() > 0 && event.getY() > 0)
						isInside = true;
					else
						isInside = false;
					break;
			}
			
			return true;
		}
		else
			return false;
	}
	
};

}