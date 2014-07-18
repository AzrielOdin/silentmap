package com.example.maptest.services;

import java.util.ArrayList;

import com.example.maptest.GPSTracker;
import com.example.maptest.MainActivity;
import com.example.maptest.R;
import com.example.maptest.R.string;
import com.example.maptest.json.Area;
import com.google.android.gms.maps.model.LatLng;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyMapService extends Service implements ServiceInterface {

	private boolean isServiceStarted = false;
	

	// Class for clients to access. Because we know this service always runs in
	// the same process as its clients, we don't need to deal with Inter-process
	// communication.

	public class LocalBinder extends Binder {
		public MyMapService getService() {
			// Return the current service.
			return MyMapService.this;
		}
	}

	private final IBinder binder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//codul care porneste threadul runnable, si ii da bice
		
		
		return START_STICKY;
	}

	public boolean isServiceStarted() {
		return isServiceStarted;
	}

	@Override
	public void setCircles() {
		
	}

	@Override
	public ArrayList<Area> getCircles() {
		
		return null;
	}

	@Override
	public void setUpdateRate(int minutes) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getUpdateRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LatLng checkCurrentPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LatLng getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
