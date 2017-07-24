package com.webjoel.quehorassai.activity;

import android.widget.EditText;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Destino;
import com.webjoel.quehorassai.sql.DestinosSQL;

public class DestinosCad extends AbstractCadActivity {

	private EditText descricao;
	private Destino destino;
	private DestinosSQL sql;

	@Override
	public void delete() {
		if (destino.getCodigo() != null) {
			sql.delete(destino);
		}
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_destinoscad;
	}

	@Override
	public void load() {

		sql = new DestinosSQL(this);

		descricao = (EditText) findViewById(R.id.descricaoDestino);
		destino = sql.retrieve(getIntent().getIntExtra("key", 0));

		if (destino != null) {
			descricao.setText(destino.getDescricao());
		} else {
			destino = new Destino(null, null);
		}
	}

	@Override
	public void save() {

		destino.setDescricao(descricao.getText().toString());

		if (destino.getDescricao().equals("")) {
			showMessage("Descrição é obrigatório!");
			return;
		}

		if (destino.getCodigo() == null) {
			sql.insert(destino);
		} else {
			sql.update(destino);
		}
	}
}