package com.jinnova.smartpad.android.cat;

import android.view.View;
import android.widget.ImageView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class SyscatViewBuilder extends ViewBuilder<Syscat> {
	
	private class SyscatUI extends ViewTag {
		ImageView image;
	}

	public SyscatViewBuilder() {
		super(R.layout.branch_row);
	}

	@Override
	public ViewTag createTag(View view) {
		SyscatUI row  = new SyscatUI();
		row.image = (ImageView) view.findViewById(R.id.imageBranch);
		return row;
	}

	@Override
	public void loadView(View view, Syscat syscat, SmartpadViewAdapter<?> viewAdapter) {
		SyscatUI row = (SyscatUI) view.getTag();
		row.image.setImageResource(R.drawable.lotteria_logo);
	}

}
