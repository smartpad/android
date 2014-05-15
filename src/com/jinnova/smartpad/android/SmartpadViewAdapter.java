package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.feed.Feed;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class SmartpadViewAdapter<T extends UIData> extends BaseAdapter {
	
	public static final int LAYOUTOPT_UNINITIALIZED = -1;
	public static final int LAYOUTOPT_DEFAULT = 0;
	public static final int LAYOUTOPT_DETAIL = 1;
	public static final int LAYOUTOPT_COUNT = 2;
	
	//builder map is long/exhausted, because there are multiple layouts / custom layouts for each feed type. 
	private ViewBuilder<?>[][] builderMap;
	
	private int[][] viewTypeNumbers;
	private int builderCount;

	//private Context context;
	
	private UIDataList<T> feedList;
	//private CompoundFeed compoundFeed;
	
	protected SmartpadViewAdapter() {
	}
	
	protected abstract ViewBuilder<?>[][] initBuilderMap();
	
	public SmartpadViewAdapter(Context context, UIDataList<T> dataList) {
		//this.context = context;
		feedList = dataList;
	}
	
	private void initBuilderMapInternal() {
		builderMap = initBuilderMap();
		builderCount = 0;
		viewTypeNumbers = new int[builderMap.length][];
		for (int i = 0; i < builderMap.length; i++) {
			ViewBuilder<?>[] builders = builderMap[i];
			if (builders != null) {
				viewTypeNumbers[i] = new int[builders.length];
				for (int j = 0; j < builders.length; j++) {
					viewTypeNumbers[i][j] = builderCount + j;
				}
				builderCount += builders.length;
			}
		}
	}
	
	public final void loadMore() {
		feedList.loadMore(this);
	}
	
	public void setDetail(T item) {
		feedList.setDetail(item);
		//notifyDataSetChanged();
		feedList.loadMore(this);
	}
	
	public void setServicePath(String servicePath) {
		feedList.setServicePath(servicePath);
		//notifyDataSetChanged();
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

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public final int getItemViewType(int position) {
		Feed feed = (Feed) feedList.get(position);
		//return feed.getType();
		return viewTypeNumbers[feed.getType()][feed.getLayoutOption()];
	}

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
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if (builderMap == null) {
			initBuilderMapInternal();
		}
		/*if (pos == 0) {
			if (compoundFeed == null) {
				compoundFeed = new CompoundFeed();
				compoundFeed.addfeed((Feed) feedList.get(0));
				compoundFeed.addfeed((Feed) feedList.get(1));
				compoundFeed.addfeed((Feed) feedList.get(2));
			}
			ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[compoundFeed.getType()][compoundFeed.getLayoutOption()];
			if (convertView != null) {
				ViewTag tag = (ViewTag) convertView.getTag();
				if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
					Log.d("SmartpadViewAdapter", "view reuse failed");
					convertView = createView(viewBuilder, parent);
					((ViewTag) convertView.getTag()).setItemViewType(compoundFeed.getType());
				}
			} else {
				convertView = createView(viewBuilder, parent);
				((ViewTag) convertView.getTag()).setItemViewType(compoundFeed.getType());
			}
			viewBuilder.loadView(convertView, (T) compoundFeed, this);
			return convertView;
		}*/
		T item = feedList.get(pos);
		if (item.getType() >= builderMap.length) {
			//unknown item
			return null;
		}
		if (item.getLayoutOption() >= builderMap[item.getType()].length) {
			//unknown layout option
			return null;
		}
		ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[item.getType()][item.getLayoutOption()];
		if (viewBuilder == null) {
			//unsupported layout option
			return null;
		}

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
				Log.d("SmartpadViewAdapter", "view reuse failed");
				convertView = createView(viewBuilder, parent);
				((ViewTag) convertView.getTag()).setItemViewType(item.getType());
			}
		} else {
			convertView = createView(viewBuilder, parent);
			((ViewTag) convertView.getTag()).setItemViewType(item.getType());
		}
		viewBuilder.loadView(convertView, item, (SmartpadViewAdapter<UIData>) this);
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

	/**
	 * callback from UIDataList.loadMore()
	 */
	public void newVersionLoaded() {
		// ask user to refresh with latest data list
		
	}

	public void setOnClickListener(TextView view, final String servicePath) {
		
		view.setTextColor(Color.BLUE);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				setServicePath(servicePath);
			}
		});
	}
	
}
