package com.jinnova.smartpad.android.db;

import java.util.ArrayList;
import java.util.List;

import com.jinnova.smartpad.android.feed.Feed;

/**
 * definition of table feeds:
 * 		id:int|value:string
 *
 */
public class FeedDBManager {
	
	public static FeedDBManager instance;
	
	private FeedDBManager() {
		
	}
	public static void initialize() {
		instance = new FeedDBManager();
	}
	
	/**
	 * @return value in table smartpad, column feed_version 
	 */
	public String getFeedListVersion() {
		return "a";
	}
	
	/**
	 * select * from table feeds
	 * 
	 * @param from
	 * @param size
	 * @return
	 */
	public ArrayList<String> getFeeds(int from, int size) {
		return null;
	}
	
	/**
	 * insert to table feeds
	 * @param feeds
	 */
	public void appendFeeds(List<?> feeds) {
		
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
