package com.jinnova.smartpad.android;

import android.app.Application;

public class SmartpadApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ImageLoadManager.initialize(this);
	}

}
