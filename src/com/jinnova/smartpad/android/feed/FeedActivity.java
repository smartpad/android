package com.jinnova.smartpad.android.feed;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.localstore.StoreManager;

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
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        /*case R.id.action_search:
	            //openSearch();
	            return true;*/
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
}
