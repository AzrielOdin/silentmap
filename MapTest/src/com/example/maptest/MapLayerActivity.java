package com.example.maptest;

//dis sheet changes teh map flavo

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import com.google.android.gms.maps.GoogleMap;

public class MapLayerActivity extends Activity {

	private final String TAG = "Map Project";

	private Button confirm;
	private Button cancel;
	private RadioButton normalRadio;
	private RadioButton terrainRadio;
	private RadioButton satteliteRadio;
	private RadioButton hybridRadio;
	private GoogleMap optionsMap = MainActivity.map; // lebadprogrammingpractice
														// face, but it works ;v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map_layer);

		confirm = (Button) findViewById(R.id.confirmButton);
		cancel = (Button) findViewById(R.id.cancelButton);
		normalRadio = (RadioButton) findViewById(R.id.normalRadio);
		satteliteRadio = (RadioButton) findViewById(R.id.satelliteRadio);
		hybridRadio = (RadioButton) findViewById(R.id.hybridRadio);
		terrainRadio = (RadioButton) findViewById(R.id.terrainRadio);

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (normalRadio.isChecked()) {
					optionsMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					finish();
				} else if (satteliteRadio.isChecked()) {
					optionsMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
					finish();
				} else if (hybridRadio.isChecked()) {
					optionsMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					finish();
				} else if (terrainRadio.isChecked()) {
					optionsMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
					finish();
				} else {
					finish();
				}
			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "The dialog activity is visible and about to be started.");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "The dialog activity is visible and about to be restarted.");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG,
				"The dialog activity is and has focus (it is now \"resumed\")");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.i(TAG,
				"Another activity is taking focus (this activity is about to be \"paused\")");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i(TAG,
				"The dialog activity is no longer visible (it is now \"stopped\")");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(TAG, "The dialog activity is about to be destroyed.");
	}

	@Override
	public void onBackPressed() {
		Log.i(TAG, "The dialog activity has been backed upupup");
		finish();
	}

}
