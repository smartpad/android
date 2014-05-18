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

public class CatalogViewBuilder extends ViewBuilder<Catalog> {
	
	private class CatalogUI extends ViewTag {
		TextView branchName;
		TextView name;
	}

	public CatalogViewBuilder() {
		super(R.layout.privatecat);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogUI row  = new CatalogUI();
		row.branchName = (TextView) view.findViewById(R.id.pcatBranchName);
		row.branchName.setMovementMethod(LinkMovementMethod.getInstance());
		row.name = (TextView) view.findViewById(R.id.pcatName);
		row.name.setMovementMethod(LinkMovementMethod.getInstance());
		return row;
	}

	@Override
	public void loadView(View view, Catalog cat, SmartpadViewAdapter<UIData> viewAdapter) {
		CatalogUI row = (CatalogUI) view.getTag();
		viewAdapter.setToViewHtml(cat, row.branchName, FIELD_BRANCHNAME);
		viewAdapter.setToViewHtml(cat, row.name, FIELD_NAME);
	}
}