package com.vn.foodapp;

import com.vn.foodapp.data.FeedManager;
import com.vn.foodapp.mappers.ViewMapper;

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
		ViewMapper viewMapper = new ViewMapper(LayoutInflater.from(this));
		list.setAdapter(new RowsAdapter(viewMapper));
	}

}
