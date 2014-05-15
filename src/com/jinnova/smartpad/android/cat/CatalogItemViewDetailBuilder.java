package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CatalogItemViewDetailBuilder extends ViewBuilder<CatalogItem> {
	
	private class CatalogItemUI extends ViewTag {
		TextView name;
	}

	public CatalogItemViewDetailBuilder() {
		super(R.layout.feed_row_detail);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogItemUI row  = new CatalogItemUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		return row;
	}

	@Override
	public void loadView(View view, CatalogItem catItem, SmartpadViewAdapter<UIData> viewAdapter) {
		CatalogItemUI row = (CatalogItemUI) view.getTag();
		row.name.setText("Catalog Item " + catItem.getName());
	}

}
