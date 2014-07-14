package com.example.maptest;

import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maptest.AreaCreateDialog.EditAreaDialogListener;
import com.example.maptest.RegisterDialog.EditNameDialogListener;
import com.example.maptest.json.Area;
import com.example.maptest.json.Auth;
import com.example.maptest.json.Data;
import com.example.maptest.json.GeneralRequest;
import com.example.maptest.json.Settings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity implements EditNameDialogListener,
		EditAreaDialogListener {

	private final String TAG = "Map Project";
	private GPSTracker gps;
	public static GoogleMap map;
	private Button btnShowLocation;
	private Button silentButton;
	private Marker locationMarker;
	private AudioManager audio;
	private ConnectivityManager connManager;
	private NetworkInfo mWifi;
	private Runnable r;
	private Circle circle;
	private CircleOptions circleOptions;
	private LatLng location;
	long meterValue;
	float[] distance = new float[2];
	ArrayList<Circle> circleList = new ArrayList<Circle>();
	TextView txt = null;
	SendPostRequest request = new SendPostRequest();
	String userId = "";
	String areaSize = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);

		silentButton = (Button) findViewById(R.id.silentButton);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		// turns phone silent if wifi is on
		connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//		if (mWifi.isConnected()) {
//			audio.setRingerMode(0);
//		}

		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {

				FragmentManager areaCreateFragment = getFragmentManager();
				AreaCreateDialog testAreaDialog = new AreaCreateDialog();
				testAreaDialog.setRetainInstance(true);
				testAreaDialog.show(areaCreateFragment, "fragment_name");

				circleOptions = new CircleOptions().center(point);

			}
		});

		// show location button click event
		btnShowLocation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// create class object
				gps = new GPSTracker(MainActivity.this);

				// check if GPS enabled
				if (gps.canGetLocation()) {

					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();

					location = new LatLng(latitude, longitude);
					if (locationMarker != null) {
						locationMarker.remove();
					}
					locationMarker = map.addMarker(new MarkerOptions()
							.position(location).title("Current Location"));

					map.moveCamera(CameraUpdateFactory.newLatLngZoom(location,
							15));
					map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000,
							null);
					
					if (circle != null) {
						for (int i = 0; i <= circleList.size() - 1; i++) {
							Location.distanceBetween(
									locationMarker.getPosition().latitude,
									locationMarker.getPosition().longitude,
									circleList.get(i).getCenter().latitude,
									circleList.get(i).getCenter().longitude,
									distance);

							if (distance[0] > circleList.get(i).getRadius()) {
								audio.setRingerMode(2);
								// Toast.makeText(getBaseContext(),
								// "Outside index " + i,
								// Toast.LENGTH_SHORT).show();
							} else {
								audio.setRingerMode(0);
								// Toast.makeText(getBaseContext(),
								// "Inside index " + i, Toast.LENGTH_SHORT)
								// .show();
							}
						}
					}

				} else {
					gps.showSettingsAlert();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {

		// Single menu item is selected do something
		// Ex: launching new activity/screen or show alert message
		case R.id.menu_layer:

			Intent mapLayerIntent = new Intent(getApplicationContext(),
					MapLayerActivity.class);

			startActivity(mapLayerIntent);
			break;

		case R.id.register:
			FragmentManager dialogFragment = getFragmentManager();
			RegisterDialog testRegisterDialog = new RegisterDialog();
			testRegisterDialog.setRetainInstance(true);
			testRegisterDialog.show(dialogFragment, "fragment_name");

			break;

		default:
			return super.onOptionsItemSelected(item);
		}

		return false;

	}

	public Area[] circlesToArea() {
		Area temp;
		Area[] results = new Area[circleList.size()];
		Settings dummy = new Settings(true, true);
		for (int i = 0; i <= circleList.size() - 1; i++) {

			temp = new Area(0, circleList.get(i).getCenter().latitude,
					circleList.get(i).getCenter().longitude, (int) circleList
							.get(i).getRadius(), dummy);

			results[i] = temp;
		}

		return results;

	}

	public GeneralRequest generateSavePayload(Area[] areas, String id) {

		Auth myAuth = new Auth(id);
		Data myData = new Data("save", areas);
		GeneralRequest myGeneralRequest = new GeneralRequest(myAuth, myData);

		return myGeneralRequest;
	}

	public GeneralRequest generateSavePayload(String id) {

		Area[] emptyArea = new Area[0];
		Auth myAuth = new Auth(id);
		Data myData = new Data("delete", emptyArea);
		GeneralRequest myGeneralRequest = new GeneralRequest(myAuth, myData);

		return myGeneralRequest;
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i(TAG, "The activity is visible and about to be started.");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.i(TAG, "The activity is visible and about to be restarted.");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
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
		Log.i(TAG, "The activity is no longer visible (it is now \"stopped\")");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		Log.i(TAG, "The activity is about to be destroyed.");
	}

	@Override
	public void onFinishEditDialog(String inputText) {

		Auth auth = new Auth(inputText);

		request.sendMessage(auth, "register");

		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG)
				.show();

	}

	@Override
	public void onFinishAreaDialog(double inputText) {
		circleOptions.radius(inputText);
		circle = map.addCircle(circleOptions);
		circleList.add(circle);
		Toast.makeText(getApplicationContext(), "Circle Added",
				Toast.LENGTH_LONG).show();

	}

}
