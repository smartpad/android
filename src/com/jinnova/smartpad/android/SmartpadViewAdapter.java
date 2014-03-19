package com.jinnova.smartpad.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public abstract class SmartpadViewAdapter<T> extends BaseAdapter implements SmartpadContext {
	
	//builder map is long/exhausted, because there are multiple layouts / custom layouts for each feed type. 
	private ViewBuilder<?>[][] builderMap;
	
	private int builderCount;

	private Activity activity;

	protected SmartpadViewAdapter() {
	}
	
	protected abstract ViewBuilder<?>[][] initBuilderMap();
	
	protected abstract int getItemViewLayout(T item);
	
	protected abstract int getItemViewType(T item);
	
	public SmartpadViewAdapter(Activity activity) {
		this.activity = activity;
	}
	
	protected Activity getActivity() {
		return this.activity;
	}
	
	private void initBuilderMapInternal() {
		builderMap = initBuilderMap();
		builderCount = 0;
		for (ViewBuilder<?>[] builders : builderMap) {
			builderCount += builders.length;
		}
	}

	@Override
	public int getViewTypeCount() {
		if (builderMap == null) {
			initBuilderMapInternal();
		}
		return builderCount;
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

		@SuppressWarnings("unchecked")
		T item = (T) getItem(pos);
		@SuppressWarnings("unchecked")
		ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[getItemViewType(item)][getItemViewLayout(item)];

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
		viewBuilder.loadView(convertView, item, this);
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
	
	@Override
	public FragmentManager getCurrFragmentManager() {
		if (this.activity == null) {
			return null;
		}
		return this.activity.getFragmentManager();
	}
}
