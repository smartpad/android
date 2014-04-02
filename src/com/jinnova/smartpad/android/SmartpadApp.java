package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.feed.FeedManager;
import com.jinnova.smartpad.android.localstore.StoreManager;

import android.app.Application;

public class SmartpadApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		FeedManager.initialize();
		StoreManager.initialize();
	}

}
