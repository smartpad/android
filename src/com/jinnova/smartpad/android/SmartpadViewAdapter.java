package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.feed.Feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class SmartpadViewAdapter<T extends UIData> extends BaseAdapter implements JsonObjectFactory<T> {
	
	//builder map is long/exhausted, because there are multiple layouts / custom layouts for each feed type. 
	private ViewBuilder<?>[][] builderMap;
	
	private int builderCount;

	private SmartpadActivity activity;
	
	private UIDataList<T> feedList;

	protected SmartpadViewAdapter() {
	}
	
	protected abstract ViewBuilder<?>[][] initBuilderMap();
	
	public SmartpadViewAdapter(SmartpadActivity activity) {
		this.activity = activity;
		feedList = new UIDataList<T>(this);
	}
	
	private void initBuilderMapInternal() {
		builderMap = initBuilderMap();
		builderCount = 0;
		for (ViewBuilder<?>[] builders : builderMap) {
			builderCount += builders.length;
		}
	}
	
	public final void loadMore() {
		feedList.loadMore(this);
	}

	@Override
	public final int getCount() {
		return feedList.size();
	}

	/**
	 * @param pos
	 * @return
	 */
	@Override
	public final Object getItem(int pos) {
		return feedList.get(pos);
	}

	@Override
	public final int getViewTypeCount() {
		if (builderMap == null) {
			initBuilderMapInternal();
		}
		return builderCount;
	}
	
	//protected abstract int getItemViewLayout(T item);
	
	//protected abstract int getItemViewType(T item);

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public final int getItemViewType(int position) {
		Feed feed = (Feed) feedList.get(position);
		return feed.getType();
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.SmartpadViewAdapter#getItemViewType(java.lang.Object)
	 */
	/*protected int getItemViewType(Feed feed) {
		return feed.getType();
	}*/

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.SmartpadViewAdapter#getItemViewLayout()
	 */
	/*protected int getItemViewLayout(Feed feed) {
		return feed.getLayoutOption();
	}*/

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public final long getItemId(int position) {
		Feed feed = (Feed) feedList.get(position);
		return feed.getOrder();
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	/**
	 * @param pos
	 * @param view
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if (builderMap == null) {
			initBuilderMapInternal();
		}

		T item = feedList.get(pos);
		@SuppressWarnings("unchecked")
		ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[item.getType()][item.getLayoutOption()];

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
				Log.d("SmartpadViewAdapter", "view reuse failed");
				convertView = createView(viewBuilder, parent);
			}
		} else {
			convertView = createView(viewBuilder, parent);
		}
		viewBuilder.loadView(convertView, item, this.activity);
		return convertView;
	}
	
	public static int viewCreationCount;
	
	private static View createView(ViewBuilder<?> viewBuilder, ViewGroup parent) {
		viewCreationCount++;
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newView = viewBuilder.createView(parent, inflater);
		ViewTag newTag = viewBuilder.createTag(newView);
		newView.setTag(newTag);
		return newView;
	}
	
}
