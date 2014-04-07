package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CatalogItemViewBuilder extends ViewBuilder<CatalogItem> {
	
	private class CatalogItemUI extends ViewTag {
		TextView name;
		Button detailBtnFeed;
	}

	public CatalogItemViewBuilder() {
		super(R.layout.feed_row);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogItemUI row  = new CatalogItemUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.detailBtnFeed = (Button) view.findViewById(R.id.detailBtnFeed);
		return row;
	}

	@Override
	public void loadView(View view, CatalogItem catItem, SmartpadActivity context) {
		CatalogItemUI row = (CatalogItemUI) view.getTag();
		row.name.setText("Catalog Item " + catItem.getName());
		row.detailBtnFeed.setOnClickListener(createDetailListener(context.getViewAdapter(), catItem));
	}

}
