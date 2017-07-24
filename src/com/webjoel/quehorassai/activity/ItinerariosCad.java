package com.webjoel.quehorassai.activity;

import java.util.List;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Destino;
import com.webjoel.quehorassai.model.Itinerario;
import com.webjoel.quehorassai.model.Linha;
import com.webjoel.quehorassai.sql.DestinosSQL;
import com.webjoel.quehorassai.sql.ItinerariosSQL;
import com.webjoel.quehorassai.sql.LinhasSQL;

public class ItinerariosCad extends AbstractCadActivity {

	private Spinner destino;
	private List<Destino> destinos;
	private Itinerario itinerario;
	private Spinner linha;
	private List<Linha> linhas;
	private ItinerariosSQL sql;
	private DestinosSQL sqlDestino;
	private LinhasSQL sqlLinha;

	@Override
	public void delete() {
		if (itinerario.getCodigo() != null) {
			sql.delete(itinerario);
		}
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_itinerarioscad;
	}

	@Override
	public void load() {

		sql = new ItinerariosSQL(this);

		linha = (Spinner) findViewById(R.id.linha);
		destino = (Spinner) findViewById(R.id.destino);

		sqlLinha = new LinhasSQL(this);
		linhas = sqlLinha.retrieveAll();

		sqlDestino = new DestinosSQL(this);
		destinos = sqlDestino.retrieveAll();

		ArrayAdapter<Linha> linhaAdapter = new ArrayAdapter<Linha>(this, R.layout.list_adapter,
		        R.id.list, linhas);
		ArrayAdapter<Destino> destinoAdapter = new ArrayAdapter<Destino>(this,
		        R.layout.list_adapter, R.id.list, destinos);

		linha.setAdapter(linhaAdapter);
		destino.setAdapter(destinoAdapter);

		itinerario = sql.retrieve(getIntent().getIntExtra("key", 0));

		int positionLinha = 0;
		int positionDestino = 0;

		if (itinerario != null) {

			Linha linha = sqlLinha.retrieve(itinerario.getCodigoLinha());
			Destino destino = sqlDestino.retrieve(itinerario.getCodigoDestino());

			for (Linha l : linhas) {
				if (l.getCodigo().equals(linha.getCodigo())) {
					break;
				}

				positionLinha++;
			}

			for (Destino d : destinos) {
				if (d.getCodigo().equals(destino.getCodigo())) {
					break;
				}

				positionDestino++;
			}
		} else {
			itinerario = new Itinerario(null, null, null);
		}

		linha.setSelection(positionLinha);
		destino.setSelection(positionDestino);
	}

	@Override
	public void save() {

		itinerario.setCodigoLinha(linhas.get(linha.getSelectedItemPosition()).getCodigo());
		itinerario.setCodigoDestino(destinos.get(destino.getSelectedItemPosition()).getCodigo());

		if (itinerario.getCodigo() == null) {
			sql.insert(itinerario);
		} else {
			sql.update(itinerario);
		}
	}
}