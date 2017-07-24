package com.webjoel.quehorassai.activity;

import java.util.List;

import android.content.Intent;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.ItinerariosAdapter;
import com.webjoel.quehorassai.model.Itinerario;
import com.webjoel.quehorassai.sql.ItinerariosSQL;

public class ItinerariosList extends AbstractListActivity {

	private List<Itinerario> itinerarios;

	@Override
	public void attList() {

		ItinerariosSQL sql = new ItinerariosSQL(this);

		itinerarios = sql.retrieveAll();

		setListAdapter(new ItinerariosAdapter(this, R.layout.list_adapter, itinerarios));
	}

	@Override
	public Intent getIntentCad() {
		return new Intent(this, ItinerariosCad.class);
	}

	@Override
	public Integer getKey(Integer position) {
		return itinerarios.get(position).getCodigo();
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_itinerarioslist;
	}
}