package com.webjoel.quehorassai.activity;

import java.util.List;

import android.content.Intent;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.DestinosAdapter;
import com.webjoel.quehorassai.model.Destino;
import com.webjoel.quehorassai.sql.DestinosSQL;

public class DestinosList extends AbstractListActivity {

	private List<Destino> destinos;

	@Override
	public void attList() {

		DestinosSQL sql = new DestinosSQL(this);

		destinos = sql.retrieveAll();

		setListAdapter(new DestinosAdapter(this, R.layout.list_adapter, destinos));
	}

	@Override
	public Intent getIntentCad() {
		return new Intent(this, DestinosCad.class);
	}

	@Override
	public Integer getKey(Integer position) {
		return destinos.get(position).getCodigo();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_destinoslist;
	}
}