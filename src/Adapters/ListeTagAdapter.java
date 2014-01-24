package Adapters;

import gestionDesTaches.BDDTache;
import gestionDesTaches.ListeTaches;
import gestionDesTags.ListeTags;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListeTagAdapter extends BaseAdapter{
	LayoutInflater mInflater;
	ListeTags lt = null;
	Context listeContexte;
	
	public ListeTagAdapter(Context context){
		this.listeContexte = context;
		//BDDTache db = new BDDTag(context, "Tache", null, 1);
		//this.lt = new ListeTags(db);
		this.mInflater = LayoutInflater.from(this.listeContexte);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
