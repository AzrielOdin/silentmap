package com.example.maptest.json;

import java.security.InvalidParameterException;

public class Area {
	private String circle_hash;
	private double latitude;
	private double longitude;
	private int radius;
	private Settings settings;
	private int synchStatus;

	// 0 - area is from database
	// 1 - area is from database but has been modified in app
	// 2 - area has been created in app but not saved in db yet

	public Area(double latitude, double longitude, int radius,
			String circle_hash, Settings settings, int synchStatus) {
		super();
		if ((synchStatus > 2) || (synchStatus < 0)) {
			throw new InvalidParameterException();
		}
		this.circle_hash = circle_hash;
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.settings = settings;
		this.synchStatus = synchStatus;
	}

	public String getCircle_hash() {
		return circle_hash;
	}

	public void setCircle_hash(String circle_hash) {
		this.circle_hash = circle_hash;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public int getSynchStatus() {
		return synchStatus;
	}

	public void setSynchStatus(int synchStatus) {
		if ((synchStatus > 2) || (synchStatus < 0)) {
			throw new InvalidParameterException();
		}
		this.synchStatus = synchStatus;
	}

}