package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.widget.ListView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadActivity;

public class FeedActivity extends SmartpadActivity {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new FeedViewAdapter(this));
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.SmartpadActivity#getParentViewGroupId()
	 */
	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
}
