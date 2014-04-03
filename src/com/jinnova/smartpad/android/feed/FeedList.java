package com.jinnova.smartpad.android.feed;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

class FeedList {
	
	private String version;
	
	private int lastOrder = -1;
	
	private ArrayList<Feed> feeds;

	public FeedList(String version, ArrayList<String> feedStrings) {
		this.version = version;
		if (feedStrings != null && !feedStrings.isEmpty()) {
			this.feeds = new ArrayList<Feed>();
			for (String one : feedStrings) {
				try {
					JSONObject oneJson = (JSONObject) new JSONTokener(one).nextValue();
					feeds.add(FeedManager.instance.instantiate(oneJson));
				} catch (JSONException e) {
					Log.e("FeedList", "JSONException in parsing json from database");
				}
			}
			this.lastOrder = feeds.get(feeds.size() - 1).getOrder();
		}
	}

	public FeedList(JSONObject json) {
		if (json == null) {
			feeds = new ArrayList<Feed>();
			return;
		}
		try {
			version = json.getString("ver");
			JSONArray ja = json.getJSONArray("feeds");
			feeds = new ArrayList<Feed>();
			Feed feed = null;
			for (int i = 0; i < ja.length(); i++) {
				JSONObject feedJson = ja.getJSONObject(i);
				feed = FeedManager.instance.instantiate(feedJson);
				if (feed != null) {
					feeds.add(feed);
				}
			}
			if (feed != null) {
				lastOrder = feed.getOrder();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Feed> getBackedList() {
		return feeds;
	}

	public void append(Feed feed) {
		feeds.add(feed);
	}

	public void appendAll(ArrayList<Feed> feeds) {
		feeds.addAll(feeds);
	}
	
	public String getVersion() {
		return version;
	}
	
	public Feed get(int location) {
		return feeds.get(location);
	}

	public int size() {
		if (feeds == null) {
			return 0;
		}
		return feeds.size();
	}
	
	public int getLastOrder() {
		return lastOrder;
	}
}
