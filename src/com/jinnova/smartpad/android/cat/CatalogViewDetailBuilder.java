package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CatalogViewDetailBuilder extends ViewBuilder<Catalog> {
	
	private class CatalogUI extends ViewTag {
		TextView name;
	}

	public CatalogViewDetailBuilder() {
		super(R.layout.feed_row_detail);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogUI row  = new CatalogUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		return row;
	}

	@Override
	public void loadView(View view, Catalog cat, SmartpadViewAdapter<UIData> viewAdapter) {
		CatalogUI row = (CatalogUI) view.getTag();
		row.name.setText("Catalog " + cat.getName());
	}
}
