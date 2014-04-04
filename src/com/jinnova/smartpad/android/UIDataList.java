package com.jinnova.smartpad.android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.jinnova.smartpad.android.db.FeedDBManager;
import com.jinnova.smartpad.android.dummyserver.AppServer;

import android.util.Log;
import android.widget.BaseAdapter;

public class UIDataList<T extends UIData> {
	
	private static final int DEFAULT_PAGESIZE = 10;
	
	private String version = "unloaded";
	
	private int lastOrder = -1;
	
	private final ArrayList<T> backedList = new ArrayList<T>();
	
	private final JsonObjectFactory<T> factory;
	
	public UIDataList(JsonObjectFactory<T> factory) {
		this.factory = factory;
	}
	
	public T get(int location) {
		return backedList.get(location);
	}

	public int size() {
		return backedList.size();
	}
	
	public int getLastOrder() {
		return lastOrder;
	}

	public void load(JSONObject json) {
		backedList.clear();
		if (json == null) {
			return;
		}
		try {
			version = json.getString("ver");
			JSONArray ja = json.getJSONArray("feeds");
			T t = null;
			for (int i = 0; i < ja.length(); i++) {
				JSONObject itemJson = ja.getJSONObject(i);
				t = factory.instantiate(itemJson);
				if (t != null) {
					backedList.add(t);
				}
			}
			if (t != null) {
				lastOrder = t.getOrder();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loadMore(BaseAdapter adapter) {
		
		int currentCount = backedList.size();
		ArrayList<String> moreData = FeedDBManager.instance.getFeeds(currentCount, currentCount + DEFAULT_PAGESIZE);
		if (moreData != null && !moreData.isEmpty()) {
			//found more data from db, keep loading from db only
			for (String oneString : moreData) {
				try {
					JSONObject oneJson = (JSONObject) new JSONTokener(oneString).nextValue();
					backedList.add(factory.instantiate(oneJson));
				} catch (JSONException e) {
					Log.e("UIDataList", "JSONException from database");
				}
			}
			return true;
		}
		
		//load more from server
		UIDataList<T> remoteLoadeds = new UIDataList<T>(this.factory);
		remoteLoadeds.load(AppServer.getFeeds(currentCount, currentCount + DEFAULT_PAGESIZE, this, adapter));
		if (remoteLoadeds != null && remoteLoadeds.version.equals(version)) {
			ArrayList<T> justLoadedList = remoteLoadeds.backedList;
			if (!justLoadedList.isEmpty()) {
				backedList.addAll(remoteLoadeds.backedList);
				FeedDBManager.instance.appendFeeds(remoteLoadeds.backedList);
				return true;
			} else {
				return false;
			}
		} else {
			//StoreManager.instance.replaceFeedList(remoteLoadedFeeds.getVersion(), remoteLoadedFeeds.getBackedList());
			//TODO ask user?
			return true;
		}
	}
}
