package gestionDesTags;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class BDDTag extends SQLiteOpenHelper  {

		
		public static final String TAG_NAME = "Nom";
		public static final String TAG_ID = "ID";
		public static final String TAG_COULEUR = "Couleur";
		public static final String TABLE_TAG_NAME = "Tag";
		
		
		public static final String TABLE_TAG_CREATE = "CREATE TAG" + TABLE_TAG_NAME + "(" +
															TAG_NAME + "TEXT NOT NULL, " + 
															TAG_ID + "INTEGER NOT NULL, " +
															TAG_COULEUR + "TEXT NOT NULL );";
		
		public static final String TABLE_TAG_DROP = "DROP TABLE IF EXISTS " + TABLE_TAG_NAME + ";";
		
		
		public BDDTag(Context context, String name, CursorFactory factory,	int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_TAG_CREATE);
		}
		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(TABLE_TAG_DROP);
			this.onCreate(db);
			
		}
		
		public void ajouter(Tag t){
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues valeursTag = new ContentValues();
			
			valeursTag.put(TAG_NAME, t.getNom());
			valeursTag.put(TAG_COULEUR, t.getCoul());
			valeursTag.put(TAG_ID, t.getId());
			db.insert(TABLE_TAG_NAME, null, valeursTag);
			this.close();
		}
		
		public void supprimer(int id){
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_TAG_NAME, TAG_ID + " = ?", new String[] {String.valueOf(id)});
			db.close();
		}
		
		public void toutSupprimer(){
			int tailleBDD = this.getSize();
			for(int i = 0 ; i < tailleBDD ; i++)
				supprimer(i);
		}
		
		public void modifier(Tag t){					
			
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues valeursTag = new ContentValues();
			
			valeursTag.put(TAG_NAME, t.getNom());
			valeursTag.put(TAG_ID, t.getId());
			valeursTag.put(TAG_COULEUR, t.getCoul());
			db.update(TABLE_TAG_NAME, valeursTag, TAG_ID + " = ?", new String[] {String.valueOf(t.getId())});
			db.close();
		}
		
		public Tag selectionner(int id){
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TAG_NAME + " WHERE " + TAG_ID + " = ?", new String[]{String.valueOf(id)});
			Tag t = new Tag();
			
			if(c.moveToNext()){
				t.setNom(c.getString(c.getColumnIndex(TAG_NAME)));
				t.setId(c.getInt(c.getColumnIndex(TAG_ID)));
				t.setCoul(c.getString(c.getColumnIndex(TAG_COULEUR)));
			}
			
			c.close();
			db.close();
			
			return t;
		}
		
		public int getSize(){
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor c = db.rawQuery("SELECT * FROM " + TABLE_TAG_NAME, null);
			int size = c.getCount();
			c.close();
			db.close();
			return size;		
		}
		

}
