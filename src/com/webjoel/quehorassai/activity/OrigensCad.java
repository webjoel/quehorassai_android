package com.webjoel.quehorassai.activity;

import android.widget.EditText;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Origem;
import com.webjoel.quehorassai.sql.OrigensSQL;

public class OrigensCad extends AbstractCadActivity {

	private EditText descricao;
	private Origem origem;
	private OrigensSQL sql;

	@Override
	public void delete() {
		if (origem.getCodigo() != null) {
			sql.delete(origem);
		}
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_origenscad;
	}

	@Override
	public void load() {

		sql = new OrigensSQL(this);

		descricao = (EditText) findViewById(R.id.descricaoOrigem);
		origem = sql.retrieve(getIntent().getIntExtra("key", 0));

		if (origem != null) {
			descricao.setText(origem.getDescricao());
		} else {
			origem = new Origem(null, null);
		}
	}

	@Override
	public void save() {

		origem.setDescricao(descricao.getText().toString());

		if (origem.getDescricao().equals("")) {
			showMessage("Descrição é obrigatório!");
			return;
		}

		if (origem.getCodigo() == null) {
			sql.insert(origem);
		} else {
			sql.update(origem);
		}
	}
}