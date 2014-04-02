package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.ImageView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class BranchViewBuilder extends ViewBuilder<Branch> {
	
	private class BranchUI extends ViewTag {
		ImageView image;

		@Override
		public int getItemViewType() {
			return FeedManager.TYPE_BRANCH;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.branch_row;
	}

	@Override
	public ViewTag createTag(View view) {
		BranchUI row  = new BranchUI();
		row.image = (ImageView) view.findViewById(R.id.imageBranch);
		return row;
	}

	@Override
	public void loadView(View view, Branch branch, SmartpadActivity context) {
		BranchUI row = (BranchUI) view.getTag();
		row.image.setImageResource(R.drawable.lotteria_logo);
	}

}
