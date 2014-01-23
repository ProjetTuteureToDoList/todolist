package com.Activites;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView; 

import com.chiralcode.colorpicker.ColorPickerDialog;
import com.chiralcode.colorpicker.ColorPickerDialog.OnColorSelectedListener;
import com.todolist.R;

public class GererTags extends Activity{
	
	ImageView couleur = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gerer_tags);
		
		//Initialisation du titre : ouverture du fichier pour la police 
		((TextView) findViewById(R.id.titre)).setTypeface(Typeface.createFromAsset(getAssets(), "Lifestyle M54.ttf"));
		
		//Initialisation de l'ImageView représentant la couleur du tag
		couleur = (ImageView) findViewById(R.id.couleur);
		couleur.setOnClickListener(click);
	}
	
	private OnClickListener click = new OnClickListener(){

		@Override
		public void onClick(View v) {
			ColorPickerDialog colorPickerDialog = new ColorPickerDialog(GererTags.this, 0, new OnColorSelectedListener() {

		        @Override
		        public void onColorSelected(int color) {
		            couleur.setBackgroundColor(color);
		        }

		    });
		    colorPickerDialog.show();
			
		}
		
	};
}
