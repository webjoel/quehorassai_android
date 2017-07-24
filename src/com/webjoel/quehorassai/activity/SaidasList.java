package com.webjoel.quehorassai.activity;

import java.util.List;

import android.content.Intent;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.SaidasAdapter;
import com.webjoel.quehorassai.model.Saida;
import com.webjoel.quehorassai.sql.SaidasSQL;

public class SaidasList extends AbstractListActivity {

	private List<Saida> saidas;

	@Override
	public void attList() {

		SaidasSQL sql = new SaidasSQL(this);

		saidas = sql.retrieveAll();

		setListAdapter(new SaidasAdapter(this, R.layout.list_adapter, saidas, getLayoutResID()));
	}

	@Override
	public Intent getIntentCad() {
		return new Intent(this, SaidasCad.class);
	}

	@Override
	public Integer getKey(Integer position) {
		return saidas.get(position).getCodigo();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_saidaslist;
	}
}