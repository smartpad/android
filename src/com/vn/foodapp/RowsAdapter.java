package com.vn.foodapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vn.foodapp.data.Feed;
import com.vn.foodapp.data.FeedManager;
import com.vn.foodapp.mappers.ViewBuilder;
import com.vn.foodapp.mappers.ViewMapper;

public class RowsAdapter extends BaseAdapter {

	private ViewMapper viewMapper;
	
	public RowsAdapter(LayoutInflater layoutInflator) {
		super();
		this.viewMapper = new ViewMapper(layoutInflator);
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
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param pos
	 * @param view
	 * @param parent
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
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
