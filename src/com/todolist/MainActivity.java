package com.todolist;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class MainActivity extends Activity {
	Button b = null;
	EditText entreeText = null;
	TextView listeChoseAFaire = null;
	String ChoseAFaire = "\tRien";
	int compteur = 0;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire
	    listeChoseAFaire = (TextView) findViewById(R.id.ChoseAFaire);
	    listeChoseAFaire.setText(ChoseAFaire);
	    
	    //Gestion d'évènements du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(envoyerListener);
	}
	
	
	private OnClickListener envoyerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
				    	if(compteur == 0){
				    		ChoseAFaire = "\t- " + entreeText.getText().toString();
				    		compteur++;
				    	}
				    	else
				    		ChoseAFaire = ChoseAFaire + "\n\t- " + entreeText.getText().toString();
				    		listeChoseAFaire.setText(ChoseAFaire);
				    		entreeText.setText(null);
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
