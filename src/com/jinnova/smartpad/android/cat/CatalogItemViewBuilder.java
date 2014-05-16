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

public class CatalogItemViewBuilder extends ViewBuilder<CatalogItem> {
	
	private class CatalogItemUI extends ViewTag {
		TextView branchName;
		TextView name;
		TextView details;
	}

	public CatalogItemViewBuilder() {
		super(R.layout.catitem);
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogItemUI row  = new CatalogItemUI();
		row.branchName = (TextView) view.findViewById(R.id.catitemBranchName);
		row.name = (TextView) view.findViewById(R.id.catitemName);
		row.name.setMovementMethod(LinkMovementMethod.getInstance());
		//row.name.setLinksClickable(true);
		row.details = (TextView) view.findViewById(R.id.catitemDetails);
		row.details.setMovementMethod(LinkMovementMethod.getInstance());
		//row.details.setLinksClickable(true);
		return row;
	}

	@Override
	public void loadView(View view, CatalogItem catItem, SmartpadViewAdapter<UIData> viewAdapter) {
		CatalogItemUI row = (CatalogItemUI) view.getTag();
		row.branchName.setText(catItem.getString(FIELD_BRANCHNAME));
		viewAdapter.setToViewHtml(catItem, row.name, FIELD_NAME);
		viewAdapter.setToViewHtml(catItem, row.details, FIELD_DISP);
	}

}
