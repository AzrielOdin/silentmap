package com.example.maptest.localStorage;

import java.util.ArrayList;

import com.example.maptest.json.Area;

public interface DbControllerInterface {

	public ArrayList<Area> getAreas();

	public void newAreas(ArrayList<Area> areas);

	public void updateAreas(ArrayList<Area> areas);

	public int deleteAreas(ArrayList<String> CircleHashList);

	public void deleteAllAreas();
}
