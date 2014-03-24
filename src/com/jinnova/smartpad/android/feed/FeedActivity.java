package com.jinnova.smartpad.android.feed;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.SmartpadContext;
import com.jinnova.smartpad.android.localstore.StoreManager;

public class FeedActivity extends Activity implements SmartpadContext {

	private ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO move to application onCreate()?
		FeedManager.initialize();
		StoreManager.initialize();

		setContentView(R.layout.activity_main);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(new FeedViewAdapter(this));
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.ic_action_storage);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayHomeAsUpEnabled(true);
		//ab.setLogo(R.drawable.ic_action_storage);
	}

	@Override
	public FragmentManager getCurrFragmentManager() {
		return this.getFragmentManager();
	}

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}

	public void showPopup(int itemId) {
		View view = findViewById(itemId);
		final PopupMenu popupMenu = new PopupMenu(getActionBar()
				.getThemedContext(), view);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(android.view.MenuItem item) {
				popupMenu.dismiss();
				return false;
			}
		});
		popupMenu.getMenuInflater().inflate(R.menu.drop_down_menu,
				popupMenu.getMenu());
		popupMenu.show();
	}

	public void showRightPopup(int itemId) {
		View view = findViewById(itemId);
		final PopupMenu popupMenu = new PopupMenu(getActionBar()
				.getThemedContext(), view);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.feed_list_menu_item:
					//toggle();
					return true;
				}
				//popupMenu.dismiss();
				return false;
			}
		});
		popupMenu.getMenuInflater().inflate(R.menu.drop_right_down_menu,
				popupMenu.getMenu());
		popupMenu.show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case android.R.id.home:
			showRightPopup(android.R.id.home);
			return true;
		case R.id.action_settings:
			showPopup(R.id.action_settings);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
