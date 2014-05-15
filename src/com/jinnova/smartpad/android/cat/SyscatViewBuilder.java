package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class SyscatViewBuilder extends ViewBuilder<Syscat> {
	
	private class SyscatUI extends ViewTag {
		TextView upCatName;
		TextView catName;
	}

	public SyscatViewBuilder() {
		super(R.layout.syscat);
	}

	@Override
	public ViewTag createTag(View view) {
		SyscatUI row  = new SyscatUI();
		row.upCatName = (TextView) view.findViewById(R.id.syscatUpName);
		row.catName = (TextView) view.findViewById(R.id.syscatName);
		return row;
	}

	@Override
	public void loadView(View view, Syscat syscat, SmartpadViewAdapter<UIData> viewAdapter) {
		SyscatUI row = (SyscatUI) view.getTag();
		syscat.setToView(row.upCatName, FIELD_UP_NAME);
		viewAdapter.setOnClickListener(row.upCatName, "/" + TYPENAME_SYSCAT + "/" + syscat.getString(FIELD_UP_ID) + "/" + REST_DRILL);
		row.catName.setText(syscat.getName());
		viewAdapter.setOnClickListener(row.catName, "/" + TYPENAME_SYSCAT + "/" + syscat.getString(FIELD_ID) + "/" + REST_DRILL);
	}

}
