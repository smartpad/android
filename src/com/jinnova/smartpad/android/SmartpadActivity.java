package com.jinnova.smartpad.android;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.fav.FavoriteActivity;
import com.jinnova.smartpad.android.feed.FeedListActivity;
import com.jinnova.smartpad.android.member.MemberCardActivity;
import com.jinnova.smartpad.android.order.OrdersActivity;

public abstract class SmartpadActivity extends Activity {

	protected Fragment mContent;
	//public abstract int getParentViewGroupId();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.ic_action_storage);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayHomeAsUpEnabled(true);
	}
	
	public void showPopup(int itemId) {
		View view = findViewById(itemId);
		final PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(), view);
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

	public void showRightPopup(int itemId) {
		View view = findViewById(itemId);
		final PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(), view);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.feed_list_menu_item:
					switchActivity(FeedListActivity.class);
					return true;
				/*case R.id.store_catalog_menu_item:
					switchActivity(ShoppingActivity.class);
					return true;*/
				case R.id.favorite_menu_item:
					switchActivity(FavoriteActivity.class);
					return true;
				case R.id.orders_menu_item:
					switchActivity(OrdersActivity.class);
					return true;
				case R.id.member_card_menu_item:
					switchActivity(MemberCardActivity.class);
					return true;
				}
				return false;
			}
		});
		popupMenu.getMenuInflater().inflate(R.menu.right_drop_down_menu, popupMenu.getMenu());
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
	
	/*@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getFragmentManager().putFragment(outState, "mContent", mContent);
	}*/
	
	/*public void switchFragment(Fragment fragment) {
		mContent = fragment;
		getFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
	}*/
	
	public void switchActivity(Class<?> name) {
		Intent intent = new Intent(this, name);
		startActivity(intent);
	}
}
