package gestionDesTaches;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BDDTache extends SQLiteOpenHelper {
	public static final String TACHE_NAME = "Nom";
	public static final String TACHE_ID = "ID";
	public static final String TACHE_DATE_JOUR = "Date_Jour";
	public static final String TACHE_DATE_MOIS = "Date_Mois";
	public static final String TACHE_DATE_ANNEE = "Date_Annee";
	public static final String TACHE_HASDATE = "HasDate";
	public static final String TACHE_DATE_HEURE = "Date_Heure";
	public static final String TACHE_DATE_MINUTE = "Date_Minute";
	public static final String TACHE_HASHOUR = "HasHour";
	public static final String TACHE_ETAT = "Etat";
	public static final String TACHE_IMPORTANCE = "Importance";
	public static final String TACHE_DESCRIPTION = "Description";
	public static final String TACHE_LISTETAGS = "Liste_Tags";	
	public static final String TABLE_TACHE_NAME = "Tache";
	
	public static final String TABLE_TACHE_CREATE = "CREATE TABLE " + TABLE_TACHE_NAME + "(" +
														TACHE_NAME + " TEXT NOT NULL, " +
														TACHE_ID + " INTEGER PRIMARY KEY, " +
														TACHE_DATE_JOUR + " INTEGER NOT NULL, " +
														TACHE_DATE_MOIS + " INTEGER NOT NULL, " +
														TACHE_DATE_ANNEE + " INTEGER NOT NULL, " +
														TACHE_HASDATE + " INTEGER NOT NULL, " +
														TACHE_DATE_HEURE + " INTEGER NOT NULL, " +
														TACHE_DATE_MINUTE + " INTEGER NOT NULL, " +
														TACHE_HASHOUR + " INTEGER NOT NULL, " +
														TACHE_ETAT + " INTEGER NOT NULL DEFAULT 0, " +
														TACHE_IMPORTANCE + " INTEGER NOT NULL DEFAULT 1, " +
														TACHE_DESCRIPTION + " TEXT NOT NULL DEFAULT \"Pas de description\", " + 
														TACHE_LISTETAGS + " TEXT NOT NULL)";
	
	public static final String TABLE_TACHE_DROP = "DROP TABLE IF EXISTS " + TABLE_TACHE_NAME + ";";

	public BDDTache(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_TACHE_CREATE);
	}
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TABLE_TACHE_DROP);
		this.onCreate(db);
		
	}
	
	public void ajouter(Tache t){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues valeursTache = new ContentValues();
		valeursTache.put(TACHE_NAME, t.getNom());
		valeursTache.put(TACHE_ID, t.getIdTache());
		valeursTache.put(TACHE_DATE_JOUR, t.getDate().getDate());
		valeursTache.put(TACHE_DATE_MOIS, t.getDate().getMonth());
		valeursTache.put(TACHE_DATE_ANNEE, t.getDate().getYear());
		
		if(t.getHasDate())
			valeursTache.put(TACHE_HASDATE, 1);
		else
			valeursTache.put(TACHE_HASDATE, 0);
		
		valeursTache.put(TACHE_DATE_HEURE, t.getDate().getHours());
		valeursTache.put(TACHE_DATE_MINUTE, t.getDate().getMinutes());
		
		if(t.getHasHour())
			valeursTache.put(TACHE_HASHOUR, 1);
		else
			valeursTache.put(TACHE_HASHOUR, 0);
		
		if(t.getEtat())
			valeursTache.put(TACHE_ETAT, 1);
		else
			valeursTache.put(TACHE_ETAT, 0);

		valeursTache.put(TACHE_IMPORTANCE, t.getImportance());
		valeursTache.put(TACHE_DESCRIPTION, t.getDescription());
		valeursTache.put(TACHE_LISTETAGS, t.getListeTagsString());
		db.insert(TABLE_TACHE_NAME, null, valeursTache);
		this.close();
	}
	
	public void supprimer(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TACHE_NAME, TACHE_ID + " = ?", new String[] {String.valueOf(id)});
		db.close();
	}
	
	public void toutSupprimer(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TACHE_NAME, null);
		while(c.moveToNext()){
			supprimer(c.getInt(c.getColumnIndex(TACHE_ID)));
		}
		c.close();
		db.close();
	}
	
	public void modifier(Tache t){					
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues valeursTache = new ContentValues();
		valeursTache.put(TACHE_NAME, t.getNom());
		valeursTache.put(TACHE_ID, t.getIdTache());
		valeursTache.put(TACHE_DATE_JOUR, t.getDate().getDate());
		valeursTache.put(TACHE_DATE_MOIS, t.getDate().getMonth());
		valeursTache.put(TACHE_DATE_ANNEE, t.getDate().getYear());
		
		if(t.getHasDate())
			valeursTache.put(TACHE_HASDATE, 1);
		else
			valeursTache.put(TACHE_HASDATE, 0);
		
		valeursTache.put(TACHE_DATE_HEURE, t.getDate().getHours());
		valeursTache.put(TACHE_DATE_MINUTE, t.getDate().getMinutes());
		
		if(t.getHasHour())
			valeursTache.put(TACHE_HASHOUR, 1);
		else
			valeursTache.put(TACHE_HASHOUR, 0);
		
		if(t.getEtat())
			valeursTache.put(TACHE_ETAT, 1);
		else
			valeursTache.put(TACHE_ETAT, 0);
		valeursTache.put(TACHE_IMPORTANCE, t.getImportance());
		valeursTache.put(TACHE_DESCRIPTION, t.getDescription());
		valeursTache.put(TACHE_LISTETAGS, t.getListeTagsString());
		
		db.update(TABLE_TACHE_NAME, valeursTache, TACHE_ID + " = ?", new String[] {String.valueOf(t.getIdTache())});
		
		db.close();
	}
	
	public Tache selectionner(int position){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TACHE_NAME, null);
		int i = 0;
		Tache t = new Tache();
		
		if(c.moveToNext()){
			while(i < position && c.moveToNext()){
				i++;
			}
		}
		
		t.setNom(c.getString(c.getColumnIndex(TACHE_NAME)));
		t.setDate(new Date(c.getInt(c.getColumnIndex(TACHE_DATE_ANNEE)), c.getInt(c.getColumnIndex(TACHE_DATE_MOIS)), c.getInt(c.getColumnIndex(TACHE_DATE_JOUR)),
						   c.getInt(c.getColumnIndex(TACHE_DATE_HEURE)), c.getInt(c.getColumnIndex(TACHE_DATE_MINUTE))));
		
		if(c.getInt(c.getColumnIndex(TACHE_HASDATE)) == 1)
			t.setHasDate(true);
		else
			t.setHasDate(false);
		
		if(c.getInt(c.getColumnIndex(TACHE_HASHOUR)) == 1)
			t.setHasHour(true);
		else
			t.setHasHour(false);
		
		t.setDescription(c.getString(c.getColumnIndex(TACHE_DESCRIPTION)));
		if(c.getInt(c.getColumnIndex(TACHE_ETAT)) == 1)
			t.setEtat(true);
		else
			t.setEtat(false);
		t.setIdTache(c.getInt(c.getColumnIndex(TACHE_ID)));
		t.setImportance(c.getInt(c.getColumnIndex(TACHE_IMPORTANCE)));
		t.setListeTagsString(c.getString(c.getColumnIndex(TACHE_LISTETAGS)));
		
		c.close();
		db.close();
		
		return t;
	}
	
	public int getSize(){
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TACHE_NAME, null);
		int size = c.getCount();
		c.close();
		db.close();
		return size;		
	}
	
	

}
