package com.jinnova.smartpad.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UIDataStore<T extends UIData> extends SQLiteOpenHelper {
	
	private static final String DCOL_ORD = "ord";
	private static final String DCOL_JSON = "json";
	
	private static final int DATABASE_VERSION = 15;
	private static final String DATABASE_NAME = "smartpad";

	public static int TABLE_FEEDS = 0;
	private static final String[] NAMES = new String[] {"feeds"};
	
	private final String[] versLast = new String[NAMES.length];
	private final String[] versPrev = new String[NAMES.length];
	//which table_postfix is for latest version: 0 or 1
	private final int[] postfixLast = new int[NAMES.length];
	private final String[] exps = new String[NAMES.length];
	
	private boolean latestInUsed[] = new boolean[NAMES.length]; //true if latest version is being used, false otherwise

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("UIDataOpenHelper", "Creating database version " + DATABASE_VERSION);
		//tid: tableId
		//v_last: latest version
		//v_prev: previous version
		//t_last: postfix of table containing latest version
		db.execSQL("CREATE TABLE table_vers (tid int primary key, v_last TEXT, v_prev TEXT, t_last int, exp TEXT);");
		for (String tableName : NAMES) {
			db.execSQL("CREATE TABLE " + tableName + "0 (ord int primary key, json TEXT);");
			db.execSQL("CREATE TABLE " + tableName + "1 (ord int primary key, json TEXT);");
		}
		
		ContentValues values = new ContentValues();
		for (int i = 0; i < NAMES.length; i++) {
			values.put("tid", i);
			values.put("t_last", 0);
			db.insert("table_vers", null, values);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//temporarily for dev purpose only
		db.execSQL("drop table table_vers");
		db.execSQL("drop table feeds0");
		db.execSQL("drop table feeds1");
		onCreate(db);
	}

	@Override
	public void onOpen(SQLiteDatabase db) {

		Log.i("UIDataOpenHelper", "Loading versioning data (db version: " + DATABASE_VERSION + ")");
		Cursor cursor = null;
		try {
			cursor = db.query(false, "table_vers", 
					new String[] {"tid", "v_last", "v_prev", "t_last", "exp"}, null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				int tableNumber = cursor.getInt(0);
				versLast[tableNumber] = cursor.getString(1);
				versPrev[tableNumber] = cursor.getString(2);
				postfixLast[tableNumber] = cursor.getInt(3);
				exps[tableNumber] = cursor.getString(4);
				
				latestInUsed[tableNumber] = true;
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	UIDataStore(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	String getVersionInUse(int tableId) {
		if (latestInUsed[tableId]) {
			return versLast[tableId];
		} else {
			return versPrev[tableId];
		}
	}

	String getVersionLatest(int tableId) {
		return versLast[tableId];
	}

	void switchToLatest(int tableId) {
		latestInUsed[tableId] = true;
	}

	private int getTablePostfix(int tableId, boolean ofTableInUse) {
		
		int postfix;
		//post of the table being used
		if (latestInUsed[tableId]) {
			postfix = postfixLast[tableId];
		} else {
			postfix = 1 - postfixLast[tableId];
		}
		
		//toggle number to get postfix of table not used
		if (!ofTableInUse) {
			postfix = 1 - postfix;
		}
		return postfix;
	}

	ArrayList<JSONObject> get(int tableId, int offset, int size) throws JSONException {

		Cursor cursor = null;
		try {
			//this must be before calling to getTablePostfix, so that onOpen() get called
			//and we have versioning data pre-populated.
			SQLiteDatabase db = getWritableDatabase();
			
			String tableName = NAMES[tableId] + getTablePostfix(tableId, true);
			cursor = db.query(
					false, tableName, new String[] {DCOL_ORD, DCOL_JSON}, 
					null, null, null, null, DCOL_ORD, String.valueOf(offset + size));
			
			if (offset > 0 && !cursor.move(offset)) {
				return null;
			}
		
			ArrayList<JSONObject> result = new ArrayList<JSONObject>();
			while (cursor.moveToNext()) {
				String json = cursor.getString(1);
				result.add(new JSONObject(json));
			}
			return result;
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
	}
	
	void insert(int tableId, JSONArray dataArray, boolean toTableInUse) throws JSONException {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 0; i < dataArray.length(); i++) {
			values.clear();
			JSONObject json = dataArray.getJSONObject(i);
			values.put(DCOL_ORD, json.getInt(UIData.ORD));
			values.put(DCOL_JSON, json.toString());
			db.insert(NAMES[tableId] + getTablePostfix(tableId, toTableInUse), null, values);
		}
		
	}
	
	void addNewVersion(int tableId, String newVersion, String expiration, JSONArray dataArray) throws JSONException {
		
		//delete all in unused table
		SQLiteDatabase db = getWritableDatabase();
		int tablePostfix = getTablePostfix(tableId, false);
		db.delete(NAMES[tableId] + tablePostfix, null, null);

		//insert data to table not being used
		insert(tableId, dataArray, false);

		//update version number, expiration
		ContentValues values = new ContentValues();
		//"tid", "v_last", "v_prev", "t_last", "exp"
		values.put("v_last", newVersion);
		values.put("v_prev", versLast[tableId]);
		values.put("t_last", tablePostfix);
		values.put("exp", expiration);
		getWritableDatabase().update("table_vers", values, "tid=" + tableId, null);
		versPrev[tableId] = versLast[tableId];
		versLast[tableId] = newVersion;
		postfixLast[tableId] = tablePostfix;
		latestInUsed[tableId] = false;
	}

}
