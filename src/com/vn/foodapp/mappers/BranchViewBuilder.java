package com.vn.foodapp.mappers;

import com.vn.foodapp.R;
import com.vn.foodapp.data.Branch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BranchViewBuilder implements ViewBuilder<Branch> {
	
	private LayoutInflater layoutInflator;
	
	public BranchViewBuilder(LayoutInflater layoutInflator) {
		this.layoutInflator = layoutInflator;
	}

	@Override
	public View createView(ViewGroup parent) {
		View view = layoutInflator.inflate(R.layout.news_row, parent, false);
		BranchUI row  = new BranchUI();
		row.name = (TextView) view.findViewById(R.id.decription);
		view.setTag(row);
		return view;
	}
	
	private class BranchUI {
		TextView name;
	}

	@Override
	public void loadView(View view, Branch branch) {
		BranchUI row = (BranchUI) view.getTag();
		row.name.setText("Branch: " + branch.getName());
	}

}
