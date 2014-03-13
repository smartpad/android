package com.jinnova.smartpad.android.viewmap;

import com.jinnova.smartpad.android.feed.Feed;

import android.view.View;
import android.view.ViewGroup;

public interface ViewBuilder<F extends Feed> {
	
	void loadView(View view, F f);

	View createView(ViewGroup parent);
}
