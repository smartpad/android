package com.jinnova.smartpad.android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public abstract class SmartpadViewAdapter<T> extends BaseAdapter {
	
	private ViewBuilder<T>[] builderMap;

	@SuppressWarnings("unchecked")
	protected SmartpadViewAdapter() {
		builderMap = (ViewBuilder<T>[]) initBuilderMap();
	}
	
	protected abstract ViewBuilder<?>[] initBuilderMap();

	@Override
	public int getViewTypeCount() {
		return builderMap.length;
	}

	/**
	 * @param pos
	 * @param view
	 * @param parent
	 * @return
	 */
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		
		ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[getItemViewType(pos)];

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
				Log.d(SmartpadViewAdapter.class.getName(), "view reuse failed");
				convertView = createView(viewBuilder, parent);
			}
		} else {
			convertView = createView(viewBuilder, parent);
		}

		@SuppressWarnings("unchecked")
		T item = (T) getItem(pos);
		viewBuilder.loadView(convertView, item);
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
