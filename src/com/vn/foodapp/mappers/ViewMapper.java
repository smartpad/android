package com.vn.foodapp.mappers;

import java.util.HashMap;

import android.view.LayoutInflater;

import com.vn.foodapp.data.Feed;
import com.vn.foodapp.data.Post;

public class ViewMapper {
	
	private HashMap<Class<? extends Feed>, ViewBuilder> builderMap;
	
	public ViewMapper(LayoutInflater layoutInflator) {
		builderMap = new HashMap<Class<? extends Feed>, ViewBuilder>();
		builderMap.put(Post.class, new PostViewBuilder(layoutInflator));
	}
	
	public ViewBuilder getBuilder(Feed feed) {
		return builderMap.get(feed.getClass());
	}

}
