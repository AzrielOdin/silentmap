package com.example.maptest.json;

import com.google.android.gms.maps.model.Circle;

public class PhoneArea {
	private String user;
	private String circle_hash;

	private Settings settings;
	private Circle circle;

	public PhoneArea(String user, String circle_hash, Settings settings,
			Circle circle) {
		super();
		this.user = user;
		this.circle_hash = circle_hash;
		this.settings = settings;
		this.circle = circle;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCircle_hash() {
		return circle_hash;
	}

	public void setCircle_hash(String circle_hash) {
		this.circle_hash = circle_hash;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}