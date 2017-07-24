package com.webjoel.quehorassai.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.adapter.SaidasAdapter;
import com.webjoel.quehorassai.model.Destino;
import com.webjoel.quehorassai.model.Origem;
import com.webjoel.quehorassai.model.Saida;
import com.webjoel.quehorassai.sql.DestinosSQL;
import com.webjoel.quehorassai.sql.OrigensSQL;
import com.webjoel.quehorassai.sql.SaidasSQL;

public class Main extends ListActivity {

	private Integer codigoDestino;
	private Integer codigoOrigem;
	private Spinner destino;
	private List<Destino> destinos;
	private Spinner origem;
	private List<Origem> origens;
	private List<Saida> saidas;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.destinos:
				startActivityForResult(new Intent(this, DestinosList.class), 1);

				return true;
			case R.id.origens:
				startActivityForResult(new Intent(this, OrigensList.class), 1);

				return true;
			case R.id.linhas:
				startActivityForResult(new Intent(this, LinhasList.class), 1);

				return true;
			case R.id.itinerarios:
				startActivityForResult(new Intent(this, ItinerariosList.class), 1);

				return true;
			case R.id.saidas:
				startActivityForResult(new Intent(this, SaidasList.class), 1);

				return true;
			case R.id.sobre:
				Toast.makeText(this, "By Joel da Rosa.", Toast.LENGTH_LONG).show();

				return true;
			case R.id.sair:
				finish();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		attFilters();
		attResult();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		origem = (Spinner) findViewById(R.id.origem);
		destino = (Spinner) findViewById(R.id.destino);

		origem.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				Origem o = (Origem) parent.getSelectedItem();

				codigoOrigem = o.getCodigo();

				attResult();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		destino.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				Destino d = (Destino) parent.getSelectedItem();

				codigoDestino = d.getCodigo();

				attResult();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		attFilters();

		if (origens.size() > 0 && destinos.size() > 0) {

			codigoOrigem = origens.get(0).getCodigo();
			codigoDestino = destinos.get(0).getCodigo();

			attResult();
		}

        // propaganda

        AdView adView = new AdView(this);

        adView.setAdUnitId("ca-app-pub-2688414088011787/7939357758");
        adView.setAdSize(AdSize.BANNER);

        LinearLayout layout = (LinearLayout) findViewById(R.id.main_activity);

        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);

		attResult();
	}

	private void attFilters() {

		origens = new OrigensSQL(this).retrieveAll();
		destinos = new DestinosSQL(this).retrieveAll();

		ArrayAdapter<Origem> origemAdapter = new ArrayAdapter<Origem>(this, R.layout.list_adapter,
		        R.id.list, origens);
		ArrayAdapter<Destino> destinoAdapter = new ArrayAdapter<Destino>(this,
		        R.layout.list_adapter, R.id.list, destinos);

		origem.setAdapter(origemAdapter);
		destino.setAdapter(destinoAdapter);
	}

	private void attResult() {

		saidas = new SaidasSQL(this)
		        .retrieveAllByCriteria(
		                "WHERE codigo_origem = ? "
		                        + "AND codigo_linha IN (SELECT codigo_linha FROM itinerario "
		                        + "WHERE codigo_destino = ?) "
		                        + "AND time(horario) >= time('now','localtime') "
		                        + "AND ((CASE WHEN strftime('%w','now','localtime') IN ('1','2','3','4','5') THEN 'S' END = semana) "
		                        + "OR (CASE WHEN strftime('%w','now','localtime') = '6' THEN 'S' END = sabado) "
		                        + "OR (CASE WHEN strftime('%w','now','localtime') = '0' THEN 'S' END = domingo)) "
		                        + "ORDER BY time(horario) ASC",
		                new String[] { String.valueOf(codigoOrigem), String.valueOf(codigoDestino) });

		setListAdapter(new SaidasAdapter(this, R.layout.list_adapter, saidas,
		        R.layout.activity_main));
	}
}