package com.jinnova.smartpad.android.feed;

import static com.jinnova.smartpad.android.ServerConstants.*;

import org.json.JSONObject;

public class Promotion extends Feed {

	public Promotion(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return TYPE_PROMO;
	}

	@Override
	public String getTypeName() {
		return TYPENAME_PROMO;
	}

	@Override
	public String getTarget() {
		return null;
	}
	
}
