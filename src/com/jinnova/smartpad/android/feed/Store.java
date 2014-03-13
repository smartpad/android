package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

public class Store extends Feed {

	public Store(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public String getType() {
		return FeedManager.TYPE_STORE;
	}

}
