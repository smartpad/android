package com.vn.foodapp.data;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

public class Post implements Feed {
	
	private static final int IMAGE_MISSING = 0;
	
	private JSONObject json;
	
	public Post(JSONObject json) {
		this.json = json;
	}
	
	public int getImage() {
		try {
			return json.getInt("image");
		} catch (JSONException e) {
			return IMAGE_MISSING;
		}
	}
	public String getDate() {
		try {
			return json.getString("date");
		} catch (JSONException e) {
			return "";
		}
	}
	public String getTitle() {
		try {
			return json.getString("title");
		} catch (JSONException e) {
			return "";
		}
	}
	public String getDecription() {
		try {
			return json.getString("desc");
		} catch (JSONException e) {
			return "";
		}
	}

	/* (non-Javadoc)
	 * @see com.vn.foodapp.data.Feed#getOrder()
	 */
	@Override
	public int getOrder() {
		try {
			return json.getInt("ord");
		} catch (JSONException e) {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see com.vn.foodapp.data.Feed#getTarget()
	 */
	@Override
	public Object getTarget() {
		return new Object();
	}

	/* (non-Javadoc)
	 * @see com.vn.foodapp.data.Feed#getGPSLocations()
	 */
	@Override
	public Collection<GPSLocation> getGPSLocations() {
		return null;
	}

}
