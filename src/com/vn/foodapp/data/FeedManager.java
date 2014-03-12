package com.vn.foodapp.data;

import com.vn.foodapp.dummyserver.AppServer;

public class FeedManager {
	
	public static FeedManager instance;
	
	private Feed[] feeds;
	
	public static void initialize() {
		instance = new FeedManager();
	}

	public int getFeedCount() {
		if (feeds == null) {
			feeds = AppServer.getFeeds(0, 50);
		}
		return feeds.length;
	}
	
	public Feed getFeed(int pos) {
		if (feeds == null) {
			feeds = AppServer.getFeeds(0, 50);
		}
		return feeds[pos];
	}
}
