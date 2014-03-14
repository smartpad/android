package com.jinnova.smartpad.android.viewmap;

import android.content.Context;
import android.view.LayoutInflater;

import com.jinnova.smartpad.android.feed.Feed;
import com.jinnova.smartpad.android.feed.FeedManager;

public class ViewMapper {
	
	private final ViewBuilder<?>[] builderMap;
	
	public ViewMapper(Context context) {
		LayoutInflater layoutInflator = LayoutInflater.from(context);
		builderMap = new ViewBuilder[2];
		builderMap[FeedManager.TYPE_POST] = new PostViewBuilder(layoutInflator);
		builderMap[FeedManager.TYPE_BRANCH] = new BranchViewBuilder(layoutInflator);
	}
	
	public int getViewTypeCount() {
		return builderMap.length;
	}
	
	@SuppressWarnings("unchecked")
	public <F extends Feed> ViewBuilder<F> getBuilder(int feedType) {
		return (ViewBuilder<F>) builderMap[feedType];
	}

}
