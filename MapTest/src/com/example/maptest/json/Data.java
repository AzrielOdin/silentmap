package com.example.maptest.json;

import java.util.ArrayList;

public class Data {
	private String action;
	private ArrayList<Area> areas;

	public Data(String action, ArrayList<Area> areas) {
		super();
		this.action = action;
		this.areas = areas;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public ArrayList<Area> getAreas() {
		return areas;
	}

	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
}