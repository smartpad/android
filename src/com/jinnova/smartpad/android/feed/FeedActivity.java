package com.jinnova.smartpad.android.feed;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ListView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.SmartpadContext;
import com.jinnova.smartpad.android.localstore.StoreManager;

public class FeedActivity extends Activity implements SmartpadContext {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//TODO move to application onCreate()?
		FeedManager.initialize();
		StoreManager.initialize();
		
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new FeedViewAdapter(this));
	}
	
	@Override
	public FragmentManager getCurrFragmentManager() {
		return this.getFragmentManager();
	}

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
}
