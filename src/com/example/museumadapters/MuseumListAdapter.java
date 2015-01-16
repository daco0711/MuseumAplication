package com.example.museumadapters;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.museumaplication.MuseumActivity;
import com.example.museumaplication.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class MuseumListAdapter extends BaseAdapter implements ListAdapter {
	private MuseumActivity activity;
	private ArrayList list;

	public MuseumListAdapter(MuseumActivity activity, ArrayList list) {
		super();
		this.activity = activity;
		this.list = list;

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View museumView =  convertView;
		if (convertView == null)
			museumView = activity.getLayoutInflater().inflate(R.layout.textfieldmuseum, null);
		
		final HashMap<String, String> item = (HashMap<String, String>) list
				.get(position);

		TextView txtMuseumName = (TextView) museumView
				.findViewById(R.id.museumName);
		txtMuseumName.setText(item.get(activity.Museum_name));

		TextView txtMuseumAddress = (TextView) museumView
				.findViewById(R.id.museumAddress);
		txtMuseumAddress.setText(item.get(activity.Museum_address));

		TextView txtEstablished = (TextView) museumView
				.findViewById(R.id.established);
		txtEstablished.setText(item.get(activity.Established));

		museumView.setLongClickable(true);
		return museumView;

	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int arg0) {
		return true;
	}

}
