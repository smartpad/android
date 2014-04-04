package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.widget.ListView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadActivity;

public class FeedActivity extends SmartpadActivity {

	private FeedViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		ListView list = (ListView) findViewById(R.id.list);
		adapter = new FeedViewAdapter(this);
		list.setAdapter(adapter);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		adapter.loadMore();
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.SmartpadActivity#getParentViewGroupId()
	 */
	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
}
