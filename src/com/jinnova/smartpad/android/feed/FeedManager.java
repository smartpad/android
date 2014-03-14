package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.dummyserver.AppServer;

public class FeedManager {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_STOREITEM = 3;
	public static final int TYPE_PROMO = 4;
	
	public static FeedManager instance;
	
	private FeedList feedList;
	
	//private final HashMap<String, Integer> feedTypeConverter;

	private interface FeedInstantiator {
		Feed instantiate(JSONObject json);
	}
	
	private FeedInstantiator[] instantiators;
	
	public static void initialize() {
		instance = new FeedManager();
	}
	
	private FeedManager() {
		instantiators = new FeedInstantiator[5];
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