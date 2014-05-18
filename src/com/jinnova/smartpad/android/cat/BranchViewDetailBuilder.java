package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class BranchViewDetailBuilder extends ViewBuilder<Branch> {
	
	private class BranchUI extends ViewTag {
		TextView branchName;
	}

	public BranchViewDetailBuilder() {
		super(R.layout.branch_row);
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.branchName = (TextView) view.findViewById(R.id.opBranchName);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch, SmartpadViewAdapter<UIData> viewAdapter) {
		BranchUI row = (BranchUI) view.getTag();
		row.branchName.setText(branch.getName());
	}

}
