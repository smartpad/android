package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

public class StoreItem extends Feed {

	public StoreItem(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return FeedViewAdapter.TYPE_STOREITEM;
	}

	@Override
	public String getTarget() {
		return null;
	}
}
