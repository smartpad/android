package com.vn.foodapp;

import com.vn.foodapp.data.FeedManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

public class MainActivity  extends Activity {

	private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FeedManager.initialize();
		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new RowsAdapter(LayoutInflater.from(this)));
	}
	

}
