package com.jinnova.smartpad.android.cat;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class SyscatViewBuilder extends ViewBuilder<Syscat> {
	
	private class SyscatUI extends ViewTag {
		TextView catName;
		TextView segments;
	}

	public SyscatViewBuilder() {
		super(R.layout.syscat);
	}

	@Override
	public ViewTag createTag(View view) {
		SyscatUI row  = new SyscatUI();
		row.catName = (TextView) view.findViewById(R.id.syscatName);
		row.catName.setMovementMethod(LinkMovementMethod.getInstance());
		row.segments = (TextView) view.findViewById(R.id.syscatSegments);
		row.segments.setMovementMethod(LinkMovementMethod.getInstance());
		return row;
	}

	@Override
	public void loadView(View view, Syscat syscat, SmartpadViewAdapter<UIData> viewAdapter) {
		SyscatUI row = (SyscatUI) view.getTag();
		viewAdapter.setToViewHtml(syscat, row.catName, FIELD_NAME);
		viewAdapter.setToViewHtml(syscat, row.segments, FIELD_SEGMENT);
	}

}
