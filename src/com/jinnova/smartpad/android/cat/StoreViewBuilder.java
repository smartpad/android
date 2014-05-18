package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.text.method.LinkMovementMethod;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class StoreViewBuilder extends ViewBuilder<Store> {
	
	private class StoreUI extends ViewTag {
		TextView storeName;
	}

	public StoreViewBuilder() {
		super(R.layout.store_layout);
	}

	@Override
	public ViewTag createTag(View view) {
		StoreUI row  = new StoreUI();
		row.storeName = (TextView) view.findViewById(R.id.opStoreName);
		row.storeName.setMovementMethod(LinkMovementMethod.getInstance());
		return row;
	}

	@Override
	public void loadView(View view, Store store, SmartpadViewAdapter<UIData> viewAdapter) {
		StoreUI row = (StoreUI) view.getTag();
		row.storeName.setText(store.getName());
		viewAdapter.setToViewHtml(store, row.storeName, FIELD_NAME);
	}

}
