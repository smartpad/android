package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.FIELD_NAME;
import static com.jinnova.smartpad.android.ServerConstants.FIELD_SYSCATNAME;
import static com.jinnova.smartpad.android.ServerConstants.IMG_LOGO_SQUARE;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.android.ImageLoadManager;
import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class BranchViewBuilderDetailSquare extends ViewBuilder<Branch> {
	
	private class BranchUI extends ViewTag {
		TextView syscat;
		TextView branchName;
		ImageView squareLogo;
	}

	public BranchViewBuilderDetailSquare() {
		super(R.layout.branch_details_square);
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.syscat = (TextView) view.findViewById(R.id.syscatName);
		row.branchName = (TextView) view.findViewById(R.id.branchName);
		row.squareLogo = (ImageView) view.findViewById(R.id.branchLogo);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch, SmartpadViewAdapter<UIData> viewAdapter) {
		BranchUI row = (BranchUI) view.getTag();
		viewAdapter.setToViewHtml(branch, row.syscat, FIELD_SYSCATNAME);
		viewAdapter.setToViewHtml(branch, row.branchName, FIELD_NAME);
		ImageLoadManager.instance.registerView(branch.getImagePath(IMG_LOGO_SQUARE), row.squareLogo, null);
	}

}
