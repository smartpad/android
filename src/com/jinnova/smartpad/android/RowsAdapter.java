package com.jinnova.smartpad.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.feed.Feed;
import com.jinnova.smartpad.android.feed.FeedManager;
import com.jinnova.smartpad.android.viewmap.ViewBuilder;
import com.jinnova.smartpad.android.viewmap.ViewMapper;

public class RowsAdapter extends BaseAdapter {

	private ViewMapper viewMapper;
	
	public RowsAdapter(ViewMapper viewMapper) {
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
	public View getView(int pos, View view, ViewGroup parent) {
		
		Feed feed = FeedManager.instance.getFeed(pos);
		ViewBuilder<Feed> viewBuilder = viewMapper.getBuilder(feed);
		if (view == null) {
			view = viewBuilder.createView(parent);
		}
		viewBuilder.loadView(view, feed);
		return view;
	}
}