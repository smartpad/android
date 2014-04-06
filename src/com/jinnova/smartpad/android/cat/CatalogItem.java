package com.jinnova.smartpad.android.cat;

import org.json.JSONObject;

import com.jinnova.smartpad.android.feed.Feed;
import com.jinnova.smartpad.android.feed.FeedViewAdapter;

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
