package com.webjoel.quehorassai.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Origem;

public class OrigensAdapter extends ArrayAdapter<Origem> {

	private List<Origem> origens;

	public OrigensAdapter(Context context, int textViewResourceId, List<Origem> objects) {

		super(context, textViewResourceId, objects);

		origens = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
			        Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_adapter, null);
		}

		Origem o = origens.get(position);

		if (o != null) {

			TextView text = (TextView) convertView.findViewById(R.id.list);

			text.setText(o.getDescricao());
		}

		return convertView;
	}
}