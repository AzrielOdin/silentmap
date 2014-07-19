package com.example.maptest.security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

import com.example.maptest.json.Area;

public class Validation {
	private static Encription enc = Encription.getInstance();
	private static Validation instance = null;

	private Validation() {

	}

	public static Validation getInstance() {
		if (instance == null) {
			instance = new Validation();
		}

		return instance;
	}

	public synchronized ArrayList<Area> validateAreas(ArrayList<Area> areas) {
		for (Area a : areas) {
			String computed = enc.areaToHash(a);
			if (computed != a.getCircle_hash()) {
				areas.remove(a);
				Log.i("Validation", "Hash mismatch for circle:\nLatitude: "
						+ a.getLatitude() + "\nLongitude: " + a.getLongitude()
						+ "\nRadius: " + a.getRadius() + "\nExpected: "
						+ a.getCircle_hash() + "\nComputed: " + computed);
			}
		}
		return areas;
	}
}