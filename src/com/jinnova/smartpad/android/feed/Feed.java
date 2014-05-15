package com.jinnova.smartpad.android.feed;

import java.util.Collection;

import org.json.JSONObject;

import com.jinnova.smartpad.android.GPSLocation;
import com.jinnova.smartpad.android.UIData;

public abstract class Feed extends UIData {
	
	public Feed(JSONObject json) {
		setJson(json);
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

	public String getUrl() {
		return getTypeName() + "/" + getId() + "/drill";
	}
	
	/**
	 * @return id of an quick-taken offer to a member card (no introduction shown) 
	 */
	/*public String getMemberCardIssue() {
		return null;
	}*/

}
