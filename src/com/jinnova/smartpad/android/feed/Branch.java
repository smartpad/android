package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

public class Branch extends Feed {

	public Branch(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public String getType() {
		return FeedManager.TYPE_BRANCH;
	}

	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			return "";
		}
	}
}
