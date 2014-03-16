package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class StoreItemViewBuilder extends ViewBuilder<StoreItem> {
	
	private class StoreItemUI extends ViewTag {
		TextView name;

		@Override
		public int getItemViewType() {
			return FeedManager.TYPE_STOREITEM;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.news_row;
	}

	@Override
	public ViewTag createTag(View view) {
		StoreItemUI row  = new StoreItemUI();
		row.name = (TextView) view.findViewById(R.id.decription);
		return row;
	}

	@Override
	public void loadView(View view, StoreItem storeItem) {
		StoreItemUI row = (StoreItemUI) view.getTag();
		row.name.setText("Store Item");
	}

}
