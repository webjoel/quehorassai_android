package com.webjoel.quehorassai.activity;

import java.util.List;

import android.content.Intent;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.OrigensAdapter;
import com.webjoel.quehorassai.model.Origem;
import com.webjoel.quehorassai.sql.OrigensSQL;

public class OrigensList extends AbstractListActivity {

	private List<Origem> origens;

	@Override
	public void attList() {

		OrigensSQL sql = new OrigensSQL(this);

		origens = sql.retrieveAll();

		setListAdapter(new OrigensAdapter(this, R.layout.list_adapter, origens));
	}

	@Override
	public Intent getIntentCad() {
		return new Intent(this, OrigensCad.class);
	}

	@Override
	public Integer getKey(Integer position) {
		return origens.get(position).getCodigo();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_origenslist;
	}
}