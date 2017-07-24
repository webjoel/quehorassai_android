package com.webjoel.quehorassai.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Linha;

public class LinhasAdapter extends ArrayAdapter<Linha> {

	private List<Linha> linhas;

	public LinhasAdapter(Context context, int textViewResourceId, List<Linha> objects) {

		super(context, textViewResourceId, objects);

		linhas = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
			        Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.list_adapter, null);
		}

		Linha l = linhas.get(position);

		if (l != null) {

			TextView text = (TextView) convertView.findViewById(R.id.list);

			text.setText(l.getDescricao());
		}

		return convertView;
	}
}