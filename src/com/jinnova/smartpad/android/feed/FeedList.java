package com.jinnova.smartpad.android.feed;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FeedList {
	
	private String version;
	
	private int lastOrder;
	
	private LinkedList<Feed> feeds;

	public FeedList(JSONObject json) {
		try {
			version = json.getString("ver");
			JSONArray ja = json.getJSONArray("feeds");
			feeds = new LinkedList<Feed>();
			Feed feed = null;
			for (int i = 0; i < ja.length(); i++) {
				JSONObject feedJson = ja.getJSONObject(i);
				feed = FeedManager.instance.instantiate(feedJson);
				feeds.add(feed);
			}
			if (feed != null) {
				lastOrder = feed.getOrder();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getVersion() {
		return version;
	}
	
	public Feed get(int location) {
		return feeds.get(location);
	}

	public int size() {
		return feeds.size();
	}
	
	public int getLastOrder() {
		return lastOrder;
	}
}
