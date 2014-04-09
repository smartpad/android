package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class StoreViewDetailBuilder extends ViewBuilder<Store> {
	
	private class StoreUI extends ViewTag {
		TextView name;
	}

	public StoreViewDetailBuilder() {
		super(R.layout.feed_row_detail);
	}

	@Override
	public ViewTag createTag(View view) {
		StoreUI row  = new StoreUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		return row;
	}

	@Override
	public void loadView(View view, Store store, SmartpadViewAdapter<?> viewAdapter) {
		StoreUI row = (StoreUI) view.getTag();
		row.name.setText("Shop " + store.getName());
	}

}
