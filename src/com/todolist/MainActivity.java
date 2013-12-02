package com.todolist;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class MainActivity extends Activity {
	Button b = null;
	ImageView image = null;
	EditText entreeText = null;
	TextView listeChoseAFaire = null;
	String choseAFaire = null;
	int compteur = 0;
	Animation anim = null;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire
	    listeChoseAFaire = (TextView) findViewById(R.id.ChoseAFaire);
	    
	    //Gestion d'évènements du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(envoyerListener);
	    
	    //Initialisation de croix_rouge.png
	    image = (ImageView) findViewById(R.id.imageSuppression);
	   
	    //Initialisation de la liste vide avec la croix effacée
	    if(compteur == 0){
	    	choseAFaire = "\n\tRien";
	    	image.setVisibility(image.GONE);
	    }
	    listeChoseAFaire.setText(choseAFaire);
	    
	    //Initialisation de l'animation
	    anim = AnimationUtils.loadAnimation(getApplication(), R.anim.tanslate);
	}
	
	
	private OnClickListener envoyerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
				    	if(compteur == 0){
				    		choseAFaire = "\n\t- " + entreeText.getText().toString();
				    		compteur++;
				    		image.setVisibility(image.VISIBLE);
				    	}
				    	else
				    		choseAFaire = choseAFaire + "\n\t- " + entreeText.getText().toString();
				    	
				    	listeChoseAFaire.setText(choseAFaire);
				    	entreeText.setText(null);
				    	listeChoseAFaire.startAnimation(anim);
				    	image.startAnimation(anim);
			    	}
			    	break;
			     
		    
			}
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
