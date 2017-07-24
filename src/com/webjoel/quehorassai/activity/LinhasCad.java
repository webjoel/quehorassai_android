package com.webjoel.quehorassai.activity;

import android.widget.EditText;

import com.webjoel.quehorassai.R;
import com.webjoel.quehorassai.model.Linha;
import com.webjoel.quehorassai.sql.LinhasSQL;

public class LinhasCad extends AbstractCadActivity {

	private EditText descricao;
	private Linha linha;
	private LinhasSQL sql;

	@Override
	public void delete() {
		if (linha.getCodigo() != null) {
			sql.delete(linha);
		}
	}

	@Override
	public int getLayoutResID() {
		return R.layout.activity_linhascad;
	}

	@Override
	public void load() {

		sql = new LinhasSQL(this);

		descricao = (EditText) findViewById(R.id.descricaoLinha);
		linha = sql.retrieve(getIntent().getIntExtra("key", 0));

		if (linha != null) {
			descricao.setText(linha.getDescricao());
		} else {
			linha = new Linha(null, null);
		}
	}

	@Override
	public void save() {

		linha.setDescricao(descricao.getText().toString());

		if (linha.getDescricao().equals("")) {
			showMessage("Descrição é obrigatório!");
			return;
		}

		if (linha.getCodigo() == null) {
			sql.insert(linha);
		} else {
			sql.update(linha);
		}
	}
}