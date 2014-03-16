package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

public class OrderProgress extends Feed {

	public OrderProgress(JSONObject json) {
		super(json);
	}

	@Override
	public int getType() {
		return 0;
	}

}
