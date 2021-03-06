package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import org.json.JSONObject;

import com.jinnova.smartpad.android.feed.Feed;

public class Branch extends Feed {

	public Branch(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return TYPE_BRANCH;
	}

	@Override
	public String getTypeName() {
		return TYPENAME_BRANCH;
	}
}
