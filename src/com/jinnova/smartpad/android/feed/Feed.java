package com.jinnova.smartpad.android.feed;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class Feed {
	
	protected final JSONObject json;
	
	public Feed(JSONObject json) {
		this.json = json;
	}

	public int getOrder() {
		try {
			return json.getInt("ord");
		} catch (JSONException e) {
			return 0;
		}
	}

	public Object getTarget() {
		return new Object();
	}

	public Collection<GPSLocation> getGPSLocations() {
		return null;
	}

}
