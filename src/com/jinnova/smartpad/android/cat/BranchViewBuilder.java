package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.android.ImageLoadManager;
import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class BranchViewBuilder extends ViewBuilder<Branch> {
	
	private class BranchUI extends ViewTag {
		ImageView squareLogo;
		TextView branchName;
	}

	public BranchViewBuilder() {
		super(R.layout.branch_row);
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.squareLogo = (ImageView) view.findViewById(R.id.branchLogo);
		row.branchName = (TextView) view.findViewById(R.id.branchName);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch, SmartpadViewAdapter<UIData> viewAdapter) {
		BranchUI row = (BranchUI) view.getTag();
		viewAdapter.setToViewHtml(branch, row.branchName, FIELD_NAME);
		ImageLoadManager.instance.registerView(branch.getImagePath(IMG_LOGO_SQUARE), row.squareLogo, row.branchName);
		viewAdapter.setOnClickListener(row.squareLogo, branch.getString(FIELD_LINK));
	}

}