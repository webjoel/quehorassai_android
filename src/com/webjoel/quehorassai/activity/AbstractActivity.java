package com.webjoel.quehorassai.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

public class AbstractActivity extends FragmentActivity {

	protected final Activity getActivity() {
		return this;
	}

	protected final String getConnectUrl(String url) {

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()) {
			return url;
		}

		return null;
	}

	protected final String getData(String key) {
		return getPreferences(Context.MODE_PRIVATE).getString(key, "");
	}

	protected final void putData(String key, String value) {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(key, value);
		editor.commit();
	}

	protected final void showMessage(String msg) {
		showMessage(msg, Toast.LENGTH_SHORT);
	}

	protected final void showMessage(String msg, int length) {
		Toast.makeText(this, msg, length).show();
	}
}