package com.jinnova.smartpad.android.cat;

import android.view.View;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UICompound;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CompoundViewBuilder extends ViewBuilder<UICompound> {
	
	private class CatalogUI extends ViewTag {
	}

	public CompoundViewBuilder() {
		super(R.layout.feed_row);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogUI row  = new CatalogUI();
		return row;
	}

	@Override
	public void loadView(View view, UICompound compound, SmartpadViewAdapter<?> viewAdapter) {
		
	}

}
