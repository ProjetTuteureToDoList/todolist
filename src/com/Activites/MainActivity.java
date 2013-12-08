package com.Activites;

import gestionDesTaches.Tache;

import com.todolist.R;

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
	Animation anim = null;
	ListeTacheAdapter lta = null;
	
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
	    checkList.setOnItemClickListener(tacheListener);
	    
	    
	    //Initialisation et gestion de l'évènement clic du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(ajouterListener);
	   
	    
	    //Initialisation de l'animation
	    anim = AnimationUtils.loadAnimation(getApplication(), R.anim.tanslate);	  
	    
	}
	
	
	private OnClickListener ajouterListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			switch(v.getId()) {
			    case R.id.bouton:
			    	if(entreeText.getText().toString().length() > 0){
			    		lta.ajoutTacheAdapter(entreeText.getText().toString());
				    	actualiserListe();
				    	//lta.getView(lta.getCount() - 1, findViewById(R.id.tache), null).startAnimation(anim); NE MARCHE PAS
				    	entreeText.setText(null);
			    	}
			    	break;
		    
			}
			
		}
	};
	
	private AdapterView.OnItemClickListener tacheListener = new AdapterView.OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
            lta.ajoutTacheAdapter(((Tache) lta.getItem(position)).getNom());
	    	actualiserListe();
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
