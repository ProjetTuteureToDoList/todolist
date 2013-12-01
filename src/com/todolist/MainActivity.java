package com.todolist;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.widget.*;

public class MainActivity extends Activity {
	RelativeLayout layout = null;
	TextView text = null;
	Button b = null;
	EditText entreeText = null;
	TextView titreListe = null;
	TextView listeChoseAFaire = null;
	String ChoseAFaire = "\tRien";
	int compteur = 0;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.texteUn);
		text.setText("ToDoList v1.0");
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    entreeText.setHint((CharSequence) "Une tâche à effectuer ?");
	    titreListe = (TextView) findViewById(R.id.texteDeux);
	    titreListe.setText("\nListe des choses à faire\n__________________________");
	    listeChoseAFaire = (TextView) findViewById(R.id.ChoseAFaire);
	    listeChoseAFaire.setText(ChoseAFaire);
	    b = (Button) findViewById(R.id.bouton);
		b.setText("Ajouter !");
	    b.setOnClickListener(envoyerListener);
	}
	
	
	private OnClickListener envoyerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString() != ""){
				    	if(compteur == 0){
				    		ChoseAFaire = "\t- " + entreeText.getText().toString();
				    		compteur++;
				    	}
				    	else
				    		ChoseAFaire = ChoseAFaire + "\n\t- " + entreeText.getText().toString();
				    		listeChoseAFaire.setText(ChoseAFaire);
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
