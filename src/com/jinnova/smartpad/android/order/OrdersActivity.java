package com.jinnova.smartpad.android.order;

import android.os.Bundle;
import android.widget.TextView;

import com.jinnova.smartpad.android.SmartpadActivity;

public class OrdersActivity extends SmartpadActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Hello OrdersActivity");
		setContentView(tv);
	}
}
