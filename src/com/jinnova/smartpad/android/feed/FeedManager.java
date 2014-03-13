package com.jinnova.smartpad.android.feed;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.dummyserver.AppServer;

public class FeedManager {
	
	public static final String TYPE_POST = "post";
	public static final String TYPE_BRANCH = "branch";
	public static final String TYPE_STORE = "store";
	public static final String TYPE_STOREITEM = "sitem";
	public static final String TYPE_PROMO = "promo";
	
	public static FeedManager instance;
	
	private FeedList feedList;

	private interface FeedInstantiator {
		Feed instantiate(JSONObject json);
	}
	
	private HashMap<String, FeedInstantiator> instantiatorMap;
	
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
	
	public Feed instantiate(JSONObject json) {
		if (instantiatorMap == null) {
			instantiatorMap = new HashMap<String, FeedManager.FeedInstantiator>();
			instantiatorMap.put(TYPE_POST, new FeedInstantiator() {
				
				@Override
				public Feed instantiate(JSONObject json) {
					return new Post(json);
				}
			});
			instantiatorMap.put(TYPE_BRANCH, new FeedInstantiator() {
				
				@Override
				public Feed instantiate(JSONObject json) {
					return new Branch(json);
				}
			});
		}
		
		FeedInstantiator instantiator;
		try {
			instantiator = instantiatorMap.get(json.getString("type"));
		} catch (JSONException e) {
			return null;
		}
		return instantiator.instantiate(json);
	}
}