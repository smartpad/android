package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class StoreViewBuilder extends ViewBuilder<Store> {
	
	private class StoreUI extends ViewTag {
		TextView name;

		@Override
		public int getFeedType() {
			return FeedManager.TYPE_STORE;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.news_row;
	}

	@Override
	public ViewTag createTag(View view) {
		StoreUI row  = new StoreUI();
		row.name = (TextView) view.findViewById(R.id.decription);
		return row;
	}

	@Override
	public void loadView(View view, Store store) {
		StoreUI row = (StoreUI) view.getTag();
		row.name.setText("Store");
	}

}
