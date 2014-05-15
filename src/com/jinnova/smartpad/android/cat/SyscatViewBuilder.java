package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.ServerConstants;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class SyscatViewBuilder extends ViewBuilder<Syscat> {
	
	private class SyscatUI extends ViewTag {
		TextView branchName;
		TextView upCatName;
		TextView catName;
		Button detailBtn;
	}

	public SyscatViewBuilder() {
		super(R.layout.syscat);
	}

	@Override
	public ViewTag createTag(View view) {
		SyscatUI row  = new SyscatUI();
		row.branchName = (TextView) view.findViewById(R.id.branchName);
		row.upCatName = (TextView) view.findViewById(R.id.upCatName);
		row.catName = (TextView) view.findViewById(R.id.catName);
		row.detailBtn = (Button) view.findViewById(R.id.detailBtn);
		return row;
	}

	@Override
	public void loadView(View view, Syscat syscat, SmartpadViewAdapter<UIData> viewAdapter) {
		SyscatUI row = (SyscatUI) view.getTag();
		syscat.setToView(row.branchName, FIELD_BRANCHNAME);
		syscat.setToView(row.upCatName, FIELD_UP_NAME);
		//row.upCatName.setText(Html.fromHtml("<a href='google.com'>go</a>"));
		row.catName.setText(syscat.getName());
		row.detailBtn.setOnClickListener(createDetailListener(view.getContext(), viewAdapter, syscat));
		viewAdapter.setOnClickListener(row.upCatName, TYPENAME_SYSCAT + "/" + syscat.getString(FIELD_UP_ID) + "/" + ServerConstants.REST_DRILL);
	}

}
