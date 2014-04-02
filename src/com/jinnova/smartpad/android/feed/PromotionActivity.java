package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.android.SmartpadActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PromotionActivity extends SmartpadActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello PromotionActivity");
		setContentView(tv);
	}

	@Override
	public int getParentViewGroupId() {
		return 0;
	}
}
