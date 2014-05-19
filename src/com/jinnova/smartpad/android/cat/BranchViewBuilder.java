package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.text.method.LinkMovementMethod;

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
		TextView branchName;
		ImageView image;
	}

	public BranchViewBuilder() {
		super(R.layout.branch_row);
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.branchName = (TextView) view.findViewById(R.id.opBranchName);
		row.branchName.setMovementMethod(LinkMovementMethod.getInstance());
		row.image = (ImageView) view.findViewById(R.id.branchImage);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch, SmartpadViewAdapter<UIData> viewAdapter) {
		BranchUI row = (BranchUI) view.getTag();
		row.branchName.setText(branch.getName());
		viewAdapter.setToViewHtml(branch, row.branchName, FIELD_NAME);
		ImageLoadManager.instance.registerView(branch.getImagePath(), row.image);
	}

}
