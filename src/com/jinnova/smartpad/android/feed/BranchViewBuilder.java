package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;
import com.jinnova.smartpad.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BranchViewBuilder implements ViewBuilder<Branch> {
	
	private LayoutInflater layoutInflator;
	
	private class BranchUI extends ViewTag {
		TextView name;

		@Override
		public int getFeedType() {
			return FeedManager.TYPE_BRANCH;
		}
	}
	
	public BranchViewBuilder(LayoutInflater layoutInflator) {
		this.layoutInflator = layoutInflator;
	}

	@Override
	public View createView(ViewGroup parent) {
		return layoutInflator.inflate(R.layout.news_row, parent, false);
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
