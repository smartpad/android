package com.jinnova.smartpad.android;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.feed.Feed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class UIDataStore<T extends UIData> {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "smartpad";
	
	private static final String COL_ORD = "ord";
	private static final String COL_JSON = "json";
	private static final String COL_EXPIRE = "exp";
	
	private class OpenHelper extends SQLiteOpenHelper {
		
		private final String tableName;

		OpenHelper(Context context, String tableName) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.tableName = tableName;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + tableName + " (ord int primary key, exp datetime, json TEXT);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
		}
		
	}
	
	private OpenHelper openHelper;
	
	UIDataStore(Context context, String tableName) {
		openHelper = new OpenHelper(context, tableName);
	}

	ArrayList<JSONObject> get(int offset, int size) throws JSONException {

		Cursor cursor = null;
		try {
			cursor = openHelper.getReadableDatabase().query(
					false, openHelper.tableName, new String[] {COL_ORD, COL_JSON}, 
					null, null, null, null, COL_ORD, String.valueOf(offset + size));
			
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
	
	String getListVersion() {
		return null;
	}
	
	void append(JSONArray dataArray) throws JSONException {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		for (int i = 0; i < dataArray.length(); i++) {
			values.clear();
			JSONObject json = dataArray.getJSONObject(i);
			values.put(COL_ORD, json.getInt(UIData.ORD));
			values.put(COL_JSON, json.toString());
			db.insert(openHelper.tableName, null, values);
		}
	}
	
	/**
	 * update column feed_version of table smartpad with new version number
	 * delete all from table feeds
	 * insert all to table feeds
	 * 
	 * @param newVersion
	 * @param feeds
	 */
	public void replaceFeedList(String newVersion, List<Feed> feeds) {
		
	}
	
	/**
	 * delete feedId from table feeds
	 * 
	 * @param feedId
	 */
	public void removeFeed(int feedId) {
		
	}

}
