package com.jinnova.smartpad.android.feed;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

import com.jinnova.smartpad.android.db.FeedDBManager;
import com.jinnova.smartpad.android.dummyserver.AppServer;

public class FeedManager {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_STOREITEM = 3;
	public static final int TYPE_PROMO = 4;
	
	public static final int TYPE_COUNT = 5;
	
	private static final int FEED_PAGESIZE = 10;
	
	public static FeedManager instance;
	
	private FeedList feedList;

	private interface FeedInstantiator {
		Feed instantiate(JSONObject json);
	}
	
	private FeedInstantiator[] instantiators;
	
	public static void initialize() {
		instance = new FeedManager();
	}
	
	private FeedManager() {
		instantiators = new FeedInstantiator[TYPE_COUNT];
		instantiators[TYPE_POST] = new FeedInstantiator() {
			
			@Override
			public Feed instantiate(JSONObject json) {
				return new Post(json);
			}
		};
		instantiators[TYPE_BRANCH] = new FeedInstantiator() {
			
			@Override
			public Feed instantiate(JSONObject json) {
				return new Branch(json);
			}
		};
		instantiators[TYPE_PROMO] = new FeedInstantiator() {
			
			@Override
			public Feed instantiate(JSONObject json) {
				return new Promotion(json);
			}
		};
		instantiators[TYPE_STORE] = new FeedInstantiator() {
			
			@Override
			public Feed instantiate(JSONObject json) {
				return new Store(json);
			}
		};
		instantiators[TYPE_STOREITEM] = new FeedInstantiator() {
			
			@Override
			public Feed instantiate(JSONObject json) {
				return new StoreItem(json);
			}
		};
	}
	
	private static int convertFeedType(String type) {
		if ("post".equals(type)) {
			return TYPE_POST;
		} else if ("branch".equals(type)) {
			return TYPE_BRANCH;
		} else if ("store".equals(type)) {
			return TYPE_STORE;
		} else if ("sitem".equals(type)) {
			return TYPE_STOREITEM;
		} else if ("promo".equals(type)) {
			return TYPE_PROMO;
		} else {
			return TYPE_UNKNOWN;
		}
	}

	public int getFeedCount() {
		if (feedList == null) {
			initialLoadFeeds();
		}
		return feedList.size();
	}
	
	private void initialLoadFeeds() {
		ArrayList<String> feedStrings = FeedDBManager.instance.getFeeds(0, FEED_PAGESIZE);
		if (feedStrings != null && !feedStrings.isEmpty()) {
			//found data from local db, load from there
			feedList = new FeedList(FeedDBManager.instance.getFeedListVersion(), feedStrings);
		} else {
			//no saved data, load from server
			try {
				feedList = new FeedList(AppServer.getFeeds(0, FEED_PAGESIZE));
				FeedDBManager.instance.replaceFeedList(feedList.getVersion(), feedList.getBackedList());
			} catch (IOException e) {
				feedList = new FeedList(null);
			}
		}
	}
	
	public boolean getMore() {
		
		if (feedList == null) {
			initialLoadFeeds();
			return true;
		}
		
		int currentCount = feedList.size();
		ArrayList<String> moreFeeds = FeedDBManager.instance.getFeeds(currentCount, currentCount + FEED_PAGESIZE);
		if (moreFeeds != null && !moreFeeds.isEmpty()) {
			//found more data from db, keep loading from db only
			for (String feedString : moreFeeds) {
				try {
					JSONObject feedJson = (JSONObject) new JSONTokener(feedString).nextValue();
					feedList.append(instantiate(feedJson));
				} catch (JSONException e) {
					Log.e("FeedManager", "JSONException from database");
				}
			}
			return true;
		}
		
		//load more from server
		try {
			FeedList remoteLoadedFeeds  = new FeedList(AppServer.getFeeds(currentCount, currentCount + FEED_PAGESIZE));
			if (remoteLoadedFeeds != null && remoteLoadedFeeds.getVersion().equals(feedList.getVersion())) {
				ArrayList<Feed> justLoadedList = remoteLoadedFeeds.getBackedList();
				if (!justLoadedList.isEmpty()) {
					feedList.appendAll(remoteLoadedFeeds.getBackedList());
					FeedDBManager.instance.appendFeeds(remoteLoadedFeeds.getBackedList());
					return true;
				} else {
					return false;
				}
			} else {
				//StoreManager.instance.replaceFeedList(remoteLoadedFeeds.getVersion(), remoteLoadedFeeds.getBackedList());
				//TODO ask user?
				return true;
			}
		} catch (IOException e) {
			return false;
		}
	}
	
	public Feed getFeed(int pos) {
		return feedList.get(pos);
	}
	
	public Feed instantiate(JSONObject json) {
		
		try {
			int feedType = convertFeedType(json.getString("type"));
			if (feedType == TYPE_UNKNOWN) {
				return null;
			}
			FeedInstantiator one = instantiators[feedType];
			return one.instantiate(json);
		} catch (JSONException e) {
			return null;
		}
	}
}