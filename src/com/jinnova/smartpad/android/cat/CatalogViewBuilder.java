package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CatalogViewBuilder extends ViewBuilder<Catalog> {
	
	private class CatalogUI extends ViewTag {
		TextView name;
		Button detailBtnFeed;
	}

	public CatalogViewBuilder() {
		super(R.layout.feed_row);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogUI row  = new CatalogUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.detailBtnFeed = (Button) view.findViewById(R.id.detailBtnFeed);
		return row;
	}

	@Override
	public void loadView(View view, Catalog cat, SmartpadViewAdapter<?> viewAdapter) {
		CatalogUI row = (CatalogUI) view.getTag();
		row.name.setText("Catalog " + cat.getName());
		row.detailBtnFeed.setOnClickListener(createDetailListener(view.getContext(), viewAdapter, cat));
	}
}