package com.Activites;

import gestionDesTaches.Tache;

import com.todolist.R;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.app.Activity;
import android.widget.*;

public class MainActivity extends Activity {
	Button b = null;
	EditText entreeText = null;
	ListeTacheAdapter lta = null;
	int temp;
	
	/*Permet de contrôler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;			
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    checkList.setOnItemClickListener(tacheListener);
	    
	    
	    //Initialisation et gestion de l'évènement clic du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(ajouterListener);
	    
	}
	
	
	private OnClickListener ajouterListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
			    		lta.ajoutTacheAdapter(entreeText.getText().toString());
				    	entreeText.setText(null);
			    	}
			    	break;
			    	
			    case R.drawable.croix_suppr : //NE MARCHE PAS POUR L'INSTANT
            		lta.suppressionTacheAdapter(temp);
            		break;
		    
			}
			
			actualiserListe();
			
		}
	};
	
	private AdapterView.OnItemClickListener tacheListener = new AdapterView.OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
			lta.suppressionTacheAdapter(position);
			actualiserListe();
        }
	};
	
	private AdapterView.OnItemLongClickListener tacheLongListener = new AdapterView.OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
			temp = position;
            lta.ajoutTacheAdapter(((Tache) lta.getItem(position)).getNom());
	    	actualiserListe();
			return true;
        }
	};
	
	public void actualiserListe(){
		checkList.setAdapter(lta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
