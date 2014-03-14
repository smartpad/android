package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class FeedActivity extends Activity {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FeedManager.initialize();
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new FeedViewAdapter());
	}

}
