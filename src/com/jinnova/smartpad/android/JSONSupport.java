package com.jinnova.smartpad.android;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.widget.TextView;

public class JSONSupport {

	public static String getString(JSONObject json, String name) {
		if (json.has(name)) {
			try {
				return json.getString(name);
			} catch (JSONException e) {
				Log.w("json", e);
				return null;
			}
		} else {
			return null;
		}
	}

	public static void setText(TextView view, JSONObject json, String name) {
		
		try {
			if (json.has(name)) {
				view.setText(json.getString(name));
				return;
			}
		} catch (JSONException e) {
			Log.w("json", e);
		}
		view.setText("");
	}
}
