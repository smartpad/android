package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.jinnova.smartpad.android.feed.Feed;

public class CatalogItem extends Feed {

	public CatalogItem(JSONObject json) {
		super(json);
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.feed.Feed#getType()
	 */
	@Override
	public int getType() {
		return TYPE_CATITEM;
	}

	@Override
	public String getTypeName() {
		return TYPENAME_CATITEM;
	}

	@Override
	public String getTarget() {
		return null;
	}

	public String getUrl() {
		try {
			return "/" + TYPENAME_CATITEM + "/" + json.getString(FIELD_SYSCATID) + "/" + getId() + "/drill";
		} catch (JSONException e) {
			Log.w("smartpad.json", e);
			return null;
		}
	}
}
