package com.jinnova.smartpad.android.feed;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Feed {
	
	protected final JSONObject json;
	
	public Feed(JSONObject json) {
		this.json = json;
	}
	
	public abstract int getType();
	
	public String getId() {
		return null;
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
		return null;
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
	public String getMemberCardIssue() {
		return null;
	}

}
