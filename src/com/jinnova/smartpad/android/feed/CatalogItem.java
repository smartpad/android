package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

public class CatalogItem extends Feed {

	public CatalogItem(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return FeedViewAdapter.TYPE_CATITEM;
	}

	@Override
	public String getTarget() {
		return null;
	}
}
