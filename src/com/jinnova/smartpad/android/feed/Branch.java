package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

public class Branch extends Operation {

	public Branch(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return FeedViewAdapter.TYPE_BRANCH;
	}

	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			return "";
		}
	}
}
