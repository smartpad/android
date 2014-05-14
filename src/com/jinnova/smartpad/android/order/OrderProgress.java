package com.jinnova.smartpad.android.order;

import org.json.JSONObject;

import com.jinnova.smartpad.android.feed.Feed;

public class OrderProgress extends Feed {

	public OrderProgress(JSONObject json) {
		super(json);
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public String getTypeName() {
		return "undefined";
	}

}
