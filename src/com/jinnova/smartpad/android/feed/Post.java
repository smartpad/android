package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

public class Post extends Feed {
	
	private static final int IMAGE_MISSING = 0;
	
	private JSONObject json;
	
	public Post(JSONObject json) {
		super(json);
		this.json = json;
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return FeedManager.TYPE_POST;
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

}
