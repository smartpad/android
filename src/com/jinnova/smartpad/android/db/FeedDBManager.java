package com.jinnova.smartpad.android.db;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.jinnova.smartpad.android.UIDataStore;
import com.jinnova.smartpad.android.feed.Feed;

/**
 * definition of table feeds:
 * 		id:int|value:string
 *
 */
public class FeedDBManager implements UIDataStore<Feed> {
	
	public FeedDBManager() {
		
	}
	
	/**
	 * @return value in table smartpad, column feed_version 
	 */
	@Override
	public String getListVersion() {
		return "a";
	}
	
	/**
	 * select * from table feeds
	 * 
	 * @param from
	 * @param size
	 * @return
	 */
	@Override
	public ArrayList<String> get(int from, int size) {
		return null;
	}
	
	/**
	 * insert to table feeds
	 * @param feeds
	 */
	@Override
	public void append(JSONArray dataArray) {
		
	}
	
	/**
	 * update column feed_version of table smartpad with new version number
	 * delete all from table feeds
	 * insert all to table feeds
	 * 
	 * @param newVersion
	 * @param feeds
	 */
	public void replaceFeedList(String newVersion, List<Feed> feeds) {
		
	}
	
	/**
	 * delete feedId from table feeds
	 * 
	 * @param feedId
	 */
	public void removeFeed(int feedId) {
		
	}

}
