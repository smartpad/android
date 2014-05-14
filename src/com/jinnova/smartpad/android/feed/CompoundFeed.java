package com.jinnova.smartpad.android.feed;

import java.util.ArrayList;

import org.json.JSONObject;

import com.jinnova.smartpad.android.UIData;

public class CompoundFeed extends Feed {

	private ArrayList<Feed> feedList;
	
	public CompoundFeed(JSONObject json) {
		super(json);
		feedList = new ArrayList<Feed>();
	}
	@Override
	public int getType() {
		return UIData.TYPE_COMPOUND;
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
		return feedList.size();
	}
	
	public void addfeed(Feed feed) {
		feedList.add(feed);
	}
	
	public Feed getfeed(int position) {
		return feedList.get(position);
	}
}
