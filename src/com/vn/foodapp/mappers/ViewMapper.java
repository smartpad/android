package com.vn.foodapp.mappers;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;

import com.vn.foodapp.data.Branch;
import com.vn.foodapp.data.Feed;
import com.vn.foodapp.data.Post;

public class ViewMapper {
	
	private HashMap<Class<? extends Feed>, ViewBuilder<?>> builderMap;
	
	public ViewMapper(Context context) {
		LayoutInflater layoutInflator = LayoutInflater.from(context);
		builderMap = new HashMap<Class<? extends Feed>, ViewBuilder<?>>();
		builderMap.put(Post.class, new PostViewBuilder(layoutInflator));
		builderMap.put(Branch.class, new BranchViewBuilder(layoutInflator));
	}
	
	@SuppressWarnings("unchecked")
	public <F extends Feed> ViewBuilder<F> getBuilder(F feed) {
		return (ViewBuilder<F>) builderMap.get(feed.getClass());
	}

}
