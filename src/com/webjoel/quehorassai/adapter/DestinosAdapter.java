package com.webjoel.quehorassai.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Destino;

public class DestinosAdapter extends ArrayAdapter<Destino> {

	private List<Destino> destinos;

	public DestinosAdapter(Context context, int textViewResourceId, List<Destino> objects) {

		super(context, textViewResourceId, objects);

		destinos = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
			        Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_adapter, null);
		}

		Destino d = destinos.get(position);

		if (d != null) {

			TextView text = (TextView) convertView.findViewById(R.id.list);

			text.setText(d.getDescricao());
		}

		return convertView;
	}
}