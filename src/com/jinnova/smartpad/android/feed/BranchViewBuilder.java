package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;
import com.jinnova.smartpad.R;

import android.view.View;
import android.widget.TextView;

public class BranchViewBuilder extends ViewBuilder<Branch> {
	
	private class BranchUI extends ViewTag {
		TextView name;

		@Override
		public int getItemViewType() {
			return FeedManager.TYPE_BRANCH;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.news_row;
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.name = (TextView) view.findViewById(R.id.decription);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch) {
		BranchUI row = (BranchUI) view.getTag();
		row.name.setText("Branch: " + branch.getName());
	}

}
