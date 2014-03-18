package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

public class Operation extends Feed {

	public Operation(JSONObject json) {
		super(json);
	}

	@Override
	public int getType() {
		return FeedManager.TYPE_UNKNOWN;
	}
	
	public MemberOffer getMemberOffer() {
		return null;
	}

}
