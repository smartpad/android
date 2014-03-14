package com.jinnova.smartpad.android.feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewMapper;
import com.jinnova.smartpad.android.ViewTag;

public class FeedViewAdapter extends BaseAdapter {

	private ViewMapper viewMapper;
	
	public FeedViewAdapter(ViewMapper viewMapper) {
		super();
		this.viewMapper = viewMapper;
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
		return viewMapper.getViewTypeCount();
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
		ViewBuilder<Feed> viewBuilder = viewMapper.getBuilder(feed.getType());

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag.getFeedType() != feed.getType()) {
				Log.d(FeedViewAdapter.class.getName(), "view reuse failed");
				LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = viewBuilder.createView(parent, inflater);
				ViewTag newTag = viewBuilder.createTag(convertView);
				convertView.setTag(newTag);
			}
		} else {
			LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = viewBuilder.createView(parent, inflater);
			ViewTag newTag = viewBuilder.createTag(convertView);
			convertView.setTag(newTag);
		}
		
		viewBuilder.loadView(convertView, feed);
		return convertView;
	}
}
