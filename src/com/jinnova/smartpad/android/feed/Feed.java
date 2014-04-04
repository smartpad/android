package com.jinnova.smartpad.android.feed;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.UIData;

public abstract class Feed implements UIData {
	
	protected final JSONObject json;
	
	public Feed(JSONObject json) {
		this.json = json;
	}

	@Override
	public String getId() {
		try {
			return json.getString("id");
		} catch (JSONException e) {
			return null;
		}
	}
	
	public int getLayoutOption() {
		if (!json.has("layOpt")) {
			return 0;
		}
		try {
			return json.getInt("layOpt");
		} catch (JSONException e) {
			return 0;
		}
		
	}

	public int getOrder() {
		try {
			return json.getInt("ord");
		} catch (JSONException e) {
			return 0;
		}
	}

	/**
	 * @return id of another feed (or this feed itself)
	 */
	public String getTarget() {
		return "Hello world";
	}

	public Collection<GPSLocation> getGPSLocations() {
		return null;
	}
	
	/**
	 * @return id of an offer to a member card 
	 */
	public String getMemberCardOffer() {
		return null;
	}
	
	/**
	 * @return id of an quick-taken offer to a member card (no introduction shown) 
	 */
	/*public String getMemberCardIssue() {
		return null;
	}*/

}
