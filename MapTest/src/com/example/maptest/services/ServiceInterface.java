package com.example.maptest.services;

import java.util.ArrayList;

import com.example.maptest.json.Area;
import com.google.android.gms.maps.model.LatLng;

public interface ServiceInterface {

	public void setCircles();

	public ArrayList<Area> getCircles();

	public void setUpdateRate(int minutes);

	public int getUpdateRate();

	public LatLng checkCurrentPosition(); // Returns current position and
											// updates setting accordingly

	public LatLng getPosition();// Only returns current position

}
