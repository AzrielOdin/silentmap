package com.example.maptest;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyMapService extends Service {

	// NotificationManager gives the user wizard eyes, to see all the good shit
	// in the background
	private NotificationManager mNotificationManager;
	private boolean isServiceStarted = false;

	// a unique Identification Number for the Notificication, using it on
	// Notification start and cancel
	private int NOTIFICATION = R.string.local_service_started;

	// Class for clients to access. Because we know this service always runs in
	// the same process as its clients, we don't need to deal with Inter-process
	// communication.

	public class LocalBinder extends Binder {
		MyMapService getService() {
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
		return START_STICKY;
	}
	
	public boolean isServiceStarted() {
		return isServiceStarted;
	}
	
	

}
