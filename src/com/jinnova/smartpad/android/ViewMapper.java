package com.jinnova.smartpad.android;

import android.content.Context;

import com.jinnova.smartpad.android.feed.BranchViewBuilder;
import com.jinnova.smartpad.android.feed.Feed;
import com.jinnova.smartpad.android.feed.FeedManager;
import com.jinnova.smartpad.android.feed.PostViewBuilder;

public class ViewMapper {
	
	private final ViewBuilder<?>[] builderMap;
	
	public ViewMapper(Context context) {
		builderMap = new ViewBuilder[2];
		builderMap[FeedManager.TYPE_POST] = new PostViewBuilder();
		builderMap[FeedManager.TYPE_BRANCH] = new BranchViewBuilder();
	}
	
	public int getViewTypeCount() {
		return builderMap.length;
	}
	
	@SuppressWarnings("unchecked")
	public <F extends Feed> ViewBuilder<F> getBuilder(int feedType) {
		return (ViewBuilder<F>) builderMap[feedType];
	}

}
