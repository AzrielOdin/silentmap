package com.example.maptest.json;

import java.util.ArrayList;

public class Data {
	private String action;
	private Area[] areas;

	public Data(String action, Area[] areas) {
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

	public Area[] getAreas() {
		return areas;
	}

	public void setAreas(Area[] areas) {
		this.areas = areas;
	}
}
