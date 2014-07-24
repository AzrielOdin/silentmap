package com.example.maptest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
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
import com.example.maptest.security.Encription;
import com.example.maptest.services.MyMapService;
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
	private boolean service_bound = false;
	private MyMapService service;
	long meterValue;
	float[] distance = new float[2];
	ArrayList<Circle> circleList = new ArrayList<Circle>();
	TextView txt = null;
	SendPostRequest request = new SendPostRequest();
	String userId = "";
	String userPassword = "";
	String areaSize = "";
	private Encription enc = Encription.getInstance();

	/**
	 * Method that creates the ServiceConnection and sets its onServiceConnected
	 * and onServiceDisconnected listeners.
	 */
	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceDisconnected(ComponentName className) {
		}

		public void onServiceConnected(ComponentName className, IBinder binder) {
			// Set the binder.
			MyMapService.LocalBinder b = (MyMapService.LocalBinder) binder;
			// Retrieve the service using the binder.
			service = b.getService();
			service_bound = true;
		}

	};

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

		// if (mWifi.isConnected()) {
		// audio.setRingerMode(0);
		// }

		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {

				FragmentManager areaCreateFragment = getFragmentManager();
				AreaCreateDialog testAreaDialog = new AreaCreateDialog();
				testAreaDialog.setRetainInstance(true);
				testAreaDialog.show(areaCreateFragment, "fragment_name");

				circleOptions = new CircleOptions().center(point)
						.strokeColor(Color.BLUE).fillColor(Color.parseColor("#500084d3"));

			}
		});

		map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng point) {

				if (circle != null) {
					for (int i = 0; i <= circleList.size() - 1; i++) {

						circleList.get(i).remove(); //just to test behavior, do not want
						
					}
				}
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

	public ArrayList<Area> circlesToArea() {
		Area temp;
		ArrayList<Area> results = new ArrayList<Area>();
		Settings dummy = new Settings(true, true);
		String circleHash = null;

		for (int i = 0; i <= circleList.size() - 1; i++) {

			try {
				circleHash = enc.encode(String.valueOf(circleList.get(i)
						.getCenter().latitude), (String.valueOf(circleList.get(
						i).getCenter().longitude)));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			temp = new Area(circleList.get(i).getCenter().latitude, circleList
					.get(i).getCenter().longitude, (int) circleList.get(i)
					.getRadius(), circleHash, dummy);

			results.add(temp);
		}

		return results;

	}

	public GeneralRequest generateSavePayload(ArrayList<Area> areas, String id,
			String password) {

		Auth myAuth = new Auth(id, password);
		Data myData = new Data("save", areas);
		GeneralRequest myGeneralRequest = new GeneralRequest(myAuth, myData);

		return myGeneralRequest;
	}

	public GeneralRequest generateSavePayload(String id, String password) {

		ArrayList<Area> emptyArea = new ArrayList<Area>();
		Auth myAuth = new Auth(id, password);
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
	public void onFinishEditDialog(String inputTextId, String inputTextPassword) {

		Auth auth = new Auth(inputTextId, inputTextPassword);

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
