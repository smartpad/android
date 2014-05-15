package com.jinnova.smartpad.android.feed;

import static com.jinnova.smartpad.android.ServerConstants.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.jinnova.smartpad.android.ServerConstants;

public class CompoundFeed extends Feed {

	//private ArrayList<Feed> feedList;
	private JSONArray feedList;
	
	private FeedFactory factory = new FeedFactory();
	
	public CompoundFeed(JSONObject json) {
		super(json);
		try {
			feedList = json.getJSONArray(ServerConstants.FIELD_ARRAY);
		} catch (JSONException e) {
			feedList = null;
			Log.w("CompoundFeed", e);
		}
	}
	@Override
	public int getType() {
		return TYPE_COMPOUND;
	}

	@Override
	public String getTypeName() {
		return TYPENAME_COMPOUND;
	}
	
	@Override
	public int getLayoutOption() {
		return 0;
	}
	
	public int size() {
		return feedList.length();
	}
	
	/*public void addfeed(Feed feed) {
		feedList.add(feed);
	}*/
	
	public Feed getfeed(int position) {
		JSONObject itemJson;
		try {
			itemJson = feedList.getJSONObject(position);
			return factory.instantiate(itemJson);
		} catch (JSONException e) {
			Log.w("CompoundFeed", e);
			return null;
		}
	}
}
