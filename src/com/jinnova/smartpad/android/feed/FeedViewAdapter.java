package com.jinnova.smartpad.android.feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class FeedViewAdapter extends BaseAdapter {
	
	private final ViewBuilder<?>[] builderMap;
	
	public FeedViewAdapter() {
		builderMap = new ViewBuilder[2];
		builderMap[FeedManager.TYPE_POST] = new PostViewBuilder();
		builderMap[FeedManager.TYPE_BRANCH] = new BranchViewBuilder();
		builderMap[FeedManager.TYPE_PROMO] = new PromoViewBuilder();
		builderMap[FeedManager.TYPE_STORE] = new StoreViewBuilder();
		builderMap[FeedManager.TYPE_STOREITEM] = new StoreItemViewBuilder();
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	@Override
	public int getCount() {
		return FeedManager.instance.getFeedCount();
	}

	/**
	 * @param pos
	 * @return
	 */
	@Override
	public Object getItem(int pos) {
		return FeedManager.instance.getFeed(pos);
	}

	@Override
	public int getItemViewType(int position) {
		Feed feed = FeedManager.instance.getFeed(position);
		return feed.getType();
	}

	@Override
	public int getViewTypeCount() {
		return builderMap.length;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * @param pos
	 * @param view
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		Feed feed = FeedManager.instance.getFeed(pos);
		@SuppressWarnings("unchecked")
		ViewBuilder<Feed> viewBuilder = (ViewBuilder<Feed>) builderMap[feed.getType()];

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag == null || tag.getFeedType() != feed.getType()) {
				Log.d(FeedViewAdapter.class.getName(), "view reuse failed");
				convertView = createView(viewBuilder, parent);
			}
		} else {
			convertView = createView(viewBuilder, parent);
		}
		
		viewBuilder.loadView(convertView, feed);
		return convertView;
	}
	
	public static int viewCreationCount;
	
	private static View createView(ViewBuilder<Feed> viewBuilder, ViewGroup parent) {
		viewCreationCount++;
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newView = viewBuilder.createView(parent, inflater);
		ViewTag newTag = viewBuilder.createTag(newView);
		newView.setTag(newTag);
		return newView;
	}
}
