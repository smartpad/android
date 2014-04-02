package com.jinnova.smartpad.android.member;

import android.os.Bundle;
import android.widget.TextView;

import com.jinnova.smartpad.android.SmartpadActivity;

public class MemberCardActivity extends SmartpadActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello MemberCardActivity");
		setContentView(tv);
	}

	@Override
	public int getParentViewGroupId() {
		return 0;
	}
}
