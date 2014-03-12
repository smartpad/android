package com.vn.foodapp.data;

import com.vn.foodapp.R;
import com.vn.foodapp.dummyserver.AppServer;

public class FeedManager {
	
	public static FeedManager instance;
	
	private int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	
	private Feed[] feeds;
	
	public static void initialize() {
		instance = new FeedManager();
	}

	public int getFeedCount() {
		return images.length;
	}
	
	public Feed getFeed(int pos) {
		if (feeds == null) {
			feeds = AppServer.getFeeds(0, 50);
		}
		return feeds[pos];
	}
}
