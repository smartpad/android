package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jinnova.smartpad.R;

public class MainActivity extends BaseActivity {

private Fragment mContent;
	
	public MainActivity() {
		super(R.string.feed_list_title);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new FeedFragment();	
		
		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mContent).commit();
		
		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame, new MenuFragment())
		.commit();
		
		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	
	public void showPopup(int itemId){
        View view = findViewById(itemId);
        final PopupMenu popupMenu = new PopupMenu(getSupportActionBar().getThemedContext(), view);
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(android.view.MenuItem item) {
				popupMenu.dismiss();
				return false;
			}
		});
        popupMenu.getMenuInflater().inflate(R.menu.drop_down_menu, popupMenu.getMenu());
        popupMenu.show();
    }
	
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    getSupportMenuInflater().inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
			case android.R.id.home:
				toggle();
				return true;
	        case R.id.action_settings:
	        	showPopup(R.id.action_settings);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}
