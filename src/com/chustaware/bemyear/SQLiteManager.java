package com.chustaware.bemyear;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "BeMyEar";

	// Table Names
	private static final String TABLE_SAMPLE = "samples";
	private static final String TABLE_DATE = "dates";

	public SQLiteManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Change type of pattern
		String createTableSample = "CREATE TABLE " + TABLE_SAMPLE + "(" +
				"id INTEGER PRIMARY KEY NOT NULL, " +
				"name TEXT, " +
				"icon TEXT, " +
				"pattern TEXT," +
				"enabled BOOLEAN)";

		String createTableDate = "CREATE TABLE " + TABLE_DATE + "(" +
				"id INTEGER, " +
				"date INTEGER)";

		// Create tables
		db.execSQL(createTableSample);
		db.execSQL(createTableDate);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public long insertSample(Sample sample) {
		SQLiteDatabase db = this.getWritableDatabase();

		long inserted = -1;
		if (db != null) {
			ContentValues values = new ContentValues();
			values.put("name", sample.getName());
			values.put("icon", sample.getIcon());
			values.put("pattern", sample.getPatternAsString());
			values.put("enabled", sample.isEnabled() ? 1 : 0);

			inserted = db.insert(TABLE_SAMPLE, null, values);
		}

		db.close();
		return inserted;

	}

	public long insertDate(Sample sample, SampleDate date) {
		SQLiteDatabase db = this.getWritableDatabase();

		long inserted = -1;
		if (db != null) {
			ContentValues values = new ContentValues();
			values.put("id", sample.getId());
			values.put("date", date.getDate().toString());

			inserted = db.insert(TABLE_DATE, null, values);
		}

		db.close();
		return inserted;
	}

	private Sample getSample(Cursor c) {
		return new Sample(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), (c.getInt(4) == 1) ? true
				: false);
	}

	public ArrayList<Sample> getSamples() {
		ArrayList<Sample> samples = null;
		SQLiteDatabase dbRead = this.getReadableDatabase();

		if (dbRead != null) {
			Cursor c = dbRead.rawQuery("SELECT * FROM " + TABLE_SAMPLE, null);
			if (c.moveToFirst()) {
				samples = new ArrayList<Sample>();
				samples.add(getSample(c));
				while (c.moveToNext())
					samples.add(getSample(c));
			}
			c.close();
			dbRead.close();
		}

		return samples;
	}

	public Sample getSample(int id) {
		SQLiteDatabase dbRead = this.getReadableDatabase();
		if (dbRead != null) {
			Cursor c = dbRead.rawQuery("SELECT * FROM " + TABLE_SAMPLE + " WHERE id=" + id, null);
			if (c.moveToFirst()) {
				return getSample(c);
			}
			c.close();
			dbRead.close();
		}

		return null;
	}

	public void updateEnabled(Sample sample) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValue = new ContentValues();
		newValue.put("enabled", sample.isEnabled());
		db.update(TABLE_SAMPLE, newValue, "id=" + sample.getId(), null);
		db.close();
	}

	public void updateSample(Sample sample) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues newValues = new ContentValues();
		newValues.put("name", sample.getName());
		newValues.put("icon", sample.getIcon());
		newValues.put("pattern", sample.getPatternAsString());
		newValues.put("enabled", sample.isEnabled() ? 1 : 0);
		db.update(TABLE_SAMPLE, newValues, "id=" + sample.getId(), null);
		db.close();
	}

	private void deleteTable(String tabla) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabla, null, null);
		db.close();
	}

	public void deleteSampleTable() {
		deleteTable(TABLE_SAMPLE);
	}

	public void deleteDateTable() {
		deleteTable(TABLE_DATE);
	}

	public boolean deleteSample(Sample sample) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (db != null) {
			db.delete(TABLE_SAMPLE, "id=" + sample.getId(), null);
			db.close();
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		ArrayList<Sample> samples = getSamples();
		if (samples == null) {
			return "Error SQLITE";
		}
		String out = "";
		for (int i = 0; i < samples.size(); i++) {
			out += samples.get(i) + ", ";
		}
		return out;
	}
}
