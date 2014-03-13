package com.vn.foodapp.data;

import com.vn.foodapp.dummyserver.AppServer;

public class FeedManager {
	
	public static FeedManager instance;
	
	private FeedList feedList;
	
	public static void initialize() {
		instance = new FeedManager();
	}

	public int getFeedCount() {
		if (feedList == null) {
			feedList = new FeedList(AppServer.getFeeds(0, 50));
		}
		return feedList.size();
	}
	
	public Feed getFeed(int pos) {
		if (feedList == null) {
			feedList = new FeedList(AppServer.getFeeds(0, 50));
		}
		return feedList.get(pos);
	}
}
