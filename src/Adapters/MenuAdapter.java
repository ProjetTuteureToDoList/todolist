package Adapters;

import java.util.ArrayList;
import java.util.List;

import com.todolist.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	List<String> menuContent; 
	LayoutInflater mInflater; 
	
	public MenuAdapter(Context context){
		menuContent = new ArrayList<String>();
		menuContent.add("Ajouter une tâche..");
		menuContent.add("Afficher par tags..");
		mInflater =LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return menuContent.size();
	}

	@Override
	public Object getItem(int position) {
		return menuContent.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layout = null;
		
		if(convertView != null)
			layout = (LinearLayout) convertView;
		else
			layout = (LinearLayout) mInflater.inflate(R.layout.menu, parent, false);
		
		TextView itemMenu = (TextView) layout.findViewById(R.id.itemMenu);
		itemMenu.setText(menuContent.get(position));
		
		return layout;
	}

}
