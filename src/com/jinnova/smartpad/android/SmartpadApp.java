package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.db.FeedDBManager;

import android.app.Application;

public class SmartpadApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		FeedDBManager.initialize();
	}

}
