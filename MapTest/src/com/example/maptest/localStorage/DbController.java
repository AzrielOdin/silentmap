package com.example.maptest.localStorage;

import java.util.ArrayList;

import com.example.maptest.json.Area;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbController extends SQLiteOpenHelper implements
		DbControllerInterface {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "mobile";

	// tables
	private static final String TABLE_USERS = "users";
	private static final String TABLE_AREAS = "areas";

	// Areas Table Columns names
	private static final String KEY_ID_A = "id";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_RADIUS = "radius";
	private static final String KEY_CIRCLE_HASH = "circle_hash";
	private static final String KEY_SILENT = "silent";
	private static final String KEY_VIBRATE = "vibrate";

	public DbController(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_AREAS_TABLE = "CREATE TABLE " + TABLE_AREAS + "("
				+ KEY_ID_A + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL,"
				+ KEY_RADIUS + " INTEGER," + KEY_CIRCLE_HASH + " TEXT,"
				+ KEY_SILENT + " INTEGER," + KEY_VIBRATE + " INTEGER" + ")";
		db.execSQL(CREATE_AREAS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREAS);

		// Create tables again
		onCreate(db);
	}

	@Override
	public void onConfigure(SQLiteDatabase db) {

		db.setForeignKeyConstraintsEnabled(true);

	}

	@Override
	public ArrayList<Area> getAreas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newAreas(ArrayList<Area> areas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAreas(ArrayList<Area> areas) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAreas(ArrayList<String> CircleHashList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllAreas() {
		// TODO Auto-generated method stub

	}
}
