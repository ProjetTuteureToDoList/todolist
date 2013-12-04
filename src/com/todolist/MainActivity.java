package com.todolist;

import java.util.ArrayList;
import java.util.List;

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
	int compteur = 0;
	Animation anim = null;
	ListeTaches lt = new ListeTaches();
	ListView liste = null;
	List<String> listeNomTaches = new ArrayList<String>();
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire
	    liste = (ListView) findViewById(R.id.listview);
	    liste.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listeNomTaches));
	    
	    
	    //Gestion d'évènements du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(envoyerListener);
	    
	    //Initialisation de croix_rouge.png
	    //image = (ImageView) findViewById(R.id.imageSuppression);
	   
	    
	    //Initialisation de l'animation
	    anim = AnimationUtils.loadAnimation(getApplication(), R.anim.tanslate);
	    
	    
	    
	}
	
	
	private OnClickListener envoyerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
			    		compteur++;
			    		//image.setVisibility(image.VISIBLE);
			    		lt.ajoutTache(new Tache(entreeText.getText().toString()));
				    	listeNomTaches.add(entreeText.getText().toString());
				    	actualiserListe();
				    	entreeText.setText(null);
				    	//liste.getChildAt(listeNomTaches.size()).startAnimation(anim);
				    	//image.startAnimation(anim);
			    	}
			    	break;
			     
		    
			}
			
		}
	};
	
	public void actualiserListe(){
		liste.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listeNomTaches));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
