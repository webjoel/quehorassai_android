package com.webjoel.quehorassai.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.webjoel.quehorassai.R;

public abstract class AbstractCadActivity extends AbstractActivity {

	public abstract void delete();

	public abstract int getLayoutResID();

	public abstract void load();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.cad, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.gravar:

				save();

				setResult(Activity.RESULT_OK);

				finish();

				return true;
			case R.id.excluir:

				delete();

				setResult(Activity.RESULT_OK);

				finish();

				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public abstract void save();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(getLayoutResID());

		load();
	}
}