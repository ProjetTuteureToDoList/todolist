package com.Activites;

import gestionDesTaches.Tache;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.Activites.ListeTacheAdapter.CroixAdapterListener;
import com.todolist.R;

public class MainActivity extends Activity implements CroixAdapterListener {
	Button boutonAjouter = null;		//la variable du bouton	"Ajouter !"		
	EditText entreeText = null;			//enntrée de texte pour le rajout de tâche
	ListeTacheAdapter lta = null;		//représente la listView adapté pour la ListeTache
	LinearLayout allScreen = null;		//représente le layout englobant tout l'écran
	
	/*Permet de contrôler la ListView d'activity_main contenant
	 * toutes les vues de tache_page_principale.xml
	 */
	ListView checkList = null;			//Permet de faire le lien entre la lta et une ListView donné dans activity_main.xml
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Temporaire : masquage du TextView "Retour"
		TextView retour = (TextView) findViewById(R.id.retour);
		retour.setVisibility(8);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		TextView texteView = (TextView) findViewById(R.id.titre);
		Typeface font = Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf");
		texteView.setTypeface(font);
		
		//Initialisation EditText pour la gestion les évenèments
	    entreeText = (EditText) findViewById(R.id.entreeTexte);
	    
	    //Liste des choses à faire, initialisation de la ListeTacheAdapter, liaison à une ListView
	    lta = new ListeTacheAdapter(this);
	    checkList = (ListView) findViewById(R.id.listview);
	    checkList.setAdapter(lta);
	    checkList.setOnItemClickListener(tacheListener);
	    checkList.setOnItemLongClickListener(tacheLongListener);
	    
	    //Initialisation d'un LinearLayout représentant la totalité de l'écran
	    allScreen = (LinearLayout) findViewById(R.id.allScreen);
	    allScreen.setOnClickListener(ajouterListener);
	    
	    //Initialisation et gestion de l'évènement clic du bouton ajouter
	    boutonAjouter = (Button) findViewById(R.id.bouton);
	    boutonAjouter.setOnClickListener(ajouterListener);
	    
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
				    	entreeText.clearFocus();
				    	InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				    	inputMethodManager .hideSoftInputFromWindow(entreeText.getWindowToken(), 0);
			    	}
			    	break;
			    case R.id.allScreen:
			    		for(int i = 0 ; i < lta.getCount() ; i++)
			    			lta.setOptionTacheVisibilite(false, i);
			    		entreeText.clearFocus();
		    
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
