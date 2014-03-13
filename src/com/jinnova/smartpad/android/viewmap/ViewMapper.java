package com.jinnova.smartpad.android.viewmap;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;

import com.jinnova.smartpad.android.feed.Feed;
import com.jinnova.smartpad.android.feed.FeedManager;

public class ViewMapper {
	
	private HashMap<String, ViewBuilder<?>> builderMap;
	
	public ViewMapper(Context context) {
		LayoutInflater layoutInflator = LayoutInflater.from(context);
		builderMap = new HashMap<String, ViewBuilder<?>>();
		builderMap.put(FeedManager.TYPE_POST, new PostViewBuilder(layoutInflator));
		builderMap.put(FeedManager.TYPE_BRANCH, new BranchViewBuilder(layoutInflator));
	}
	
	@SuppressWarnings("unchecked")
	public <F extends Feed> ViewBuilder<F> getBuilder(F feed) {
		return (ViewBuilder<F>) builderMap.get(feed.getType());
	}

}
