package com.example.maptest.localStorage;

import java.util.ArrayList;

import com.example.maptest.json.Area;
import com.example.maptest.json.Settings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_AREAS);

		// Create tables again
		onCreate(db);
	}

	@Override
	public ArrayList<Area> getAreas() {
		ArrayList<Area> areas = new ArrayList<Area>();

		String query = "Select latitude,longitude,radius,circle_hash,silent,vibrate FROM areas";

		Cursor cursor = getReadableDatabase().rawQuery(query, new String[] {});

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Area temp = new Area(cursor.getDouble(0), cursor.getDouble(1),
					cursor.getInt(2), cursor.getString(3), new Settings(
							cursor.getInt(4), cursor.getInt(5)), 0);

			areas.add(temp);
			cursor.moveToNext();
		}
		cursor.close();

		return areas;
	}

	@Override
	public void newAreas(ArrayList<Area> areas) {
		int index = 0;
		SQLiteDatabase db = getReadableDatabase();
		db.beginTransaction();
		try {
			for (int i = index; i < areas.size(); i++) {
				Area curArea = areas.get(i);
				ContentValues values = new ContentValues();
				values.put(KEY_LATITUDE, curArea.getLatitude());
				values.put(KEY_LONGITUDE, curArea.getLongitude());
				values.put(KEY_LATITUDE, curArea.getRadius());
				values.put(KEY_CIRCLE_HASH, curArea.getCircle_hash());
				values.put(KEY_SILENT, curArea.getSettings().isSilent());
				values.put(KEY_VIBRATE, curArea.getSettings().isVibrate());
				db.insert(TABLE_AREAS, null, values);
				index++;
				// In case you do larger updates
				db.yieldIfContendedSafely();
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

	@Override
	public void updateAreas(ArrayList<Area> areas) {
		int index = 0;

		// ToDo check if circles have hashes, update the ones that do and create
		// a hash for ones that don't

		SQLiteDatabase db = getReadableDatabase();
		db.beginTransaction();
		try {
			for (int i = index; i < areas.size(); i++) {
				if (areas.get(i).getSynchStatus() == 1) {
					Area curArea = areas.get(i);
					ContentValues values = new ContentValues();
					values.put(KEY_RADIUS, curArea.getRadius());
					values.put(KEY_SILENT, curArea.getSettings().isSilent());
					values.put(KEY_VIBRATE, curArea.getSettings().isVibrate());
					db.update(TABLE_AREAS, values, "WHERE circle_hash ="
							+ curArea.getCircle_hash(), null);
				} else if (areas.get(i).getSynchStatus() == 2) {
					Area curArea = areas.get(i);
					ContentValues values = new ContentValues();
					values.put(KEY_LATITUDE, curArea.getLatitude());
					values.put(KEY_LONGITUDE, curArea.getLongitude());
					values.put(KEY_CIRCLE_HASH, curArea.getCircle_hash());
					values.put(KEY_RADIUS, curArea.getRadius());
					values.put(KEY_SILENT, curArea.getSettings().isSilent());
					values.put(KEY_VIBRATE, curArea.getSettings().isVibrate());
					db.insert(TABLE_AREAS, null, values);
				}
				index++;
				// In case you do larger updates
				db.yieldIfContendedSafely();
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}

	}

	@Override
	public int deleteAreas(ArrayList<Area> CircleHashList) {
		int result = 0;
		SQLiteDatabase db = getReadableDatabase();
		db.beginTransaction();
		try {
			for (int i = 0; i < CircleHashList.size(); i++) {
				result += db
						.delete(TABLE_AREAS, KEY_CIRCLE_HASH + " = ?",
								new String[] { CircleHashList.get(i)
										.getCircle_hash() });

			}

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		return result;
	}

	@Override
	public void deleteAllAreas() {
		String query = "DELETE FROM " + TABLE_AREAS;
		getReadableDatabase().rawQuery(query, new String[] {});

	}
}
