package com.jinnova.smartpad.android.feed;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.viewmap.ViewBuilder;
import com.jinnova.smartpad.android.viewmap.ViewMapper;
import com.jinnova.smartpad.android.viewmap.ViewTag;

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
				Log.d(RowsAdapter.class.getName(), "view reuse failed");
				convertView = viewBuilder.createView(parent);
				ViewTag newTag = viewBuilder.createTag(convertView);
				convertView.setTag(newTag);
			}
		} else {
			convertView = viewBuilder.createView(parent);
			ViewTag newTag = viewBuilder.createTag(convertView);
			convertView.setTag(newTag);
		}
		
		viewBuilder.loadView(convertView, feed);
		return convertView;
	}
}
