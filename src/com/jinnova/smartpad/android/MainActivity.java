package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.feed.FeedManager;
import com.jinnova.smartpad.android.viewmap.ViewMapper;
import com.jinnova.smartpad.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FeedManager.initialize();
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		ViewMapper viewMapper = new ViewMapper(this);
		list.setAdapter(new RowsAdapter(viewMapper));
	}

}
