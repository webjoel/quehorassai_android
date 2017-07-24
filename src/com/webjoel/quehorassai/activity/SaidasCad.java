package com.webjoel.quehorassai.activity;

import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.*;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Linha;
import com.webjoel.quehorassai.model.Origem;
import com.webjoel.quehorassai.model.Saida;
import com.webjoel.quehorassai.sql.LinhasSQL;
import com.webjoel.quehorassai.sql.OrigensSQL;
import com.webjoel.quehorassai.sql.SaidasSQL;

public class SaidasCad extends AbstractCadActivity {

	private CheckBox domingo;
	private CheckBox feriado;
	private EditText horario;
	private Spinner linha;
	private List<Linha> linhas;
	private Spinner origem;
	private List<Origem> origens;
	private CheckBox sabado;
	private Saida saida;
	private CheckBox semana;
	private SaidasSQL sql;
	private LinhasSQL sqlLinha;
	private OrigensSQL sqlOrigem;

	@Override
	public void delete() {
		if (saida.getCodigo() != null) {
			sql.delete(saida);
		}
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_saidascad;
	}

	@Override
	public void load() {

		linha = (Spinner) findViewById(R.id.linha);
		origem = (Spinner) findViewById(R.id.origem);
		horario = (EditText) findViewById(R.id.horario);
		semana = (CheckBox) findViewById(R.id.semana);
		sabado = (CheckBox) findViewById(R.id.sabado);
		domingo = (CheckBox) findViewById(R.id.domingo);
		feriado = (CheckBox) findViewById(R.id.feriado);

		sqlLinha = new LinhasSQL(this);
		linhas = sqlLinha.retrieveAll();

		sqlOrigem = new OrigensSQL(this);
		origens = sqlOrigem.retrieveAll();

		ArrayAdapter<Linha> linhaAdapter = new ArrayAdapter<Linha>(this, R.layout.list_adapter,
		        R.id.list, linhas);
		ArrayAdapter<Origem> origemAdapter = new ArrayAdapter<Origem>(this, R.layout.list_adapter,
		        R.id.list, origens);

		linha.setAdapter(linhaAdapter);
		origem.setAdapter(origemAdapter);

		sql = new SaidasSQL(this);

		saida = sql.retrieve(getIntent().getIntExtra("key", 0));

		int positionLinha = 0;
		int positionOrigem = 0;

		if (saida != null) {

			Linha linha = sqlLinha.retrieve(saida.getCodigoLinha());
			Origem origem = sqlOrigem.retrieve(saida.getCodigoOrigem());

			for (Linha l : linhas) {
				if (l.getCodigo().equals(linha.getCodigo())) {
					break;
				}

				positionLinha++;
			}

			for (Origem o : origens) {
				if (o.getCodigo().equals(origem.getCodigo())) {
					break;
				}

				positionOrigem++;
			}

			horario.setText(saida.getHorario());
			semana.setChecked(saida.getSemana().equals("S"));
			sabado.setChecked(saida.getSabado().equals("S"));
			domingo.setChecked(saida.getDomingo().equals("S"));
			feriado.setChecked(saida.getFeriado().equals("S"));
		} else {
			saida = new Saida(null, null, null, null, null, null, null, null);
		}

		linha.setSelection(positionLinha);
		origem.setSelection(positionOrigem);
	}

	@Override
	public void save() {

		saida.setCodigoLinha(linhas.get(linha.getSelectedItemPosition()).getCodigo());
		saida.setCodigoOrigem(origens.get(origem.getSelectedItemPosition()).getCodigo());
		saida.setHorario(horario.getText().toString());
		saida.setSemana(semana.isChecked() ? "S" : "N");
		saida.setSabado(sabado.isChecked() ? "S" : "N");
		saida.setDomingo(domingo.isChecked() ? "S" : "N");
		saida.setFeriado(feriado.isChecked() ? "S" : "N");

		if (saida.getHorario().equals("")) {
			showMessage("Horário é obrigatório!");
			return;
		}

		if (!saida.getHorario().matches("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$")) {
			showMessage("Horário inválido!");
			return;
		}

		if (saida.getCodigo() == null) {
			sql.insert(saida);
		} else {
			sql.update(saida);
		}
	}

	public void showTimePickerDialog(View v) {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public static class TimePickerFragment extends DialogFragment implements
	        TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			final Calendar c = Calendar.getInstance();

			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			String horarioStr = getHorario().getText().toString();
			if (!horarioStr.equals("")) {

				String[] horaMinuto = horarioStr.split(":");

				hour = Integer.valueOf(horaMinuto[0]);
				minute = Integer.valueOf(horaMinuto[1]);
			}

			return new TimePickerDialog(getActivity(), this, hour, minute,
			        DateFormat.is24HourFormat(getActivity()));
		}

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

			String hora = hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay);
			String minuto = minute < 10 ? "0" + minute : String.valueOf(minute);

			getHorario().setText(hora + ":" + minuto);
		}

		private EditText getHorario() {
			return (EditText) getActivity().findViewById(R.id.horario);
		}
	}
}