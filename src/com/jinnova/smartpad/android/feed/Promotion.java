package com.jinnova.smartpad.android.feed;

import org.json.JSONObject;

import com.jinnova.smartpad.android.UIData;

public class Promotion extends Feed {

	public Promotion(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return UIData.TYPE_PROMO;
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
