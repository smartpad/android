package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class StoreViewBuilder extends ViewBuilder<Store> {
	
	private class StoreUI extends ViewTag {
		TextView name;
		Button detailBtnFeed;
	}

	public StoreViewBuilder() {
		super(R.layout.feed_row);
	}

	@Override
	public ViewTag createTag(View view) {
		StoreUI row  = new StoreUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.detailBtnFeed = (Button) view.findViewById(R.id.detailBtnFeed);
		return row;
	}

	@Override
	public void loadView(View view, Store store, SmartpadViewAdapter<UIData> viewAdapter) {
		StoreUI row = (StoreUI) view.getTag();
		row.name.setText("Shop " + store.getName());
		row.detailBtnFeed.setOnClickListener(createDetailListener(view.getContext(), viewAdapter, store));
	}

}
