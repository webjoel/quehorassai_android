package com.webjoel.quehorassai.activity;

import java.util.List;

import android.content.Intent;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.LinhasAdapter;
import com.webjoel.quehorassai.model.Linha;
import com.webjoel.quehorassai.sql.LinhasSQL;

public class LinhasList extends AbstractListActivity {

	private List<Linha> linhas;

	@Override
	public void attList() {

		LinhasSQL sql = new LinhasSQL(this);

		linhas = sql.retrieveAll();

		setListAdapter(new LinhasAdapter(this, R.layout.list_adapter, linhas));
	}

	@Override
	public Intent getIntentCad() {
		return new Intent(this, LinhasCad.class);
	}

	@Override
	public Integer getKey(Integer position) {
		return linhas.get(position).getCodigo();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_linhaslist;
	}
}