package com.Activites;

import gestionDesTaches.Tache;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.Activites.ListeTacheAdapter.CroixAdapterListener;
import com.todolist.R;

public class MainActivity extends Activity implements CroixAdapterListener {
	Button b = null;
	EditText entreeText = null;
	ListeTacheAdapter lta = null;
	
	/*Permet de contr�ler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;			
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Initialisation EditText pour la gestion les �ven�ments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses � faire, initialisation de la ListeTacheAdapter, liaison � une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    
	    //Initialisation d'un LinearLayout repr�sentant la totalit� de l'�cran
	    LinearLayout t = (LinearLayout) findViewById(R.id.allScreen);
	    t.setOnClickListener(ajouterListener);
	    
	    //Initialisation et gestion de l'�v�nement clic du bouton ajouter
	    b = (Button) findViewById(R.id.bouton);
	    b.setOnClickListener(ajouterListener);
	    
	    //Initialisation du Listener de la Croix
	    lta.addListener(this);
	    
	}

	//////EVENEMENTS LISTENERS
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
			    case R.id.allScreen:
			    		for(int i = 0 ; i < lta.getCount() ; i++)
			    			lta.setOptionTacheVisibilite(false, i);
		    
			}
			
			actualiserListe();
			
		}
	};
	
	private AdapterView.OnItemClickListener tacheListener = new AdapterView.OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
			lta.ajoutTacheAdapter(((Tache) lta.getItem(position)).getNom());
			actualiserListe();
        }
	};
	
	private AdapterView.OnItemLongClickListener tacheLongListener = new AdapterView.OnItemLongClickListener() {
		@Override
        public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long arg3) {
           
            for(int i = 0 ; i < lta.getCount() ; i++){
				if(i == position)
					lta.setOptionTacheVisibilite(true, position);
				else
					lta.setOptionTacheVisibilite(false, i);
			}
	    	actualiserListe();
			return true;
        }
	};
	
	@Override
	public void onClickCroix(int position) {
		lta.suppressionTacheAdapter(position);
		actualiserListe();
	}
	//////////////
	
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
