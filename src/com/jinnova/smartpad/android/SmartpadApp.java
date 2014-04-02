package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.db.FeedDBManager;
import com.jinnova.smartpad.android.feed.FeedManager;

import android.app.Application;

public class SmartpadApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		FeedManager.initialize();
		FeedDBManager.initialize();
	}

}
