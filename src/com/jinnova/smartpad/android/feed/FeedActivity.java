package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.widget.ListView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.localstore.StoreManager;

public class FeedActivity extends SmartpadActivity {

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
		//TODO: Move To SmartpadActivity
		/*ActionBar ab = getActionBar();
		ab.setIcon(R.drawable.ic_action_storage);
		ab.setDisplayShowTitleEnabled(false);
		ab.setDisplayHomeAsUpEnabled(true);*/
		//ab.setLogo(R.drawable.ic_action_storage);
	}

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
	//TODO: Move To SmartpadActivity
	/*public void showPopup(int itemId) {
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
				case R.id.promotion_menu_item:
					switchContent(new PromotionFragment());
					return true;
				case R.id.store_catalog_menu_item:
					switchContent(new StoreCatalogFragment());
					return true;
				case R.id.favorite_menu_item:
					switchContent(new FavoriteFragment());
					return true;
				case R.id.orders_menu_item:
					switchContent(new OrdersFragment());
					return true;
				case R.id.member_card_menu_item:
					switchContent(new MemberCardFragment());
					return true;
				}
				return false;
			}
		});
		popupMenu.getMenuInflater().inflate(R.menu.right_drop_down_menu,
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
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getFragmentManager().putFragment(outState, "mContent", mContent);
	}
	
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getFragmentManager().beginTransaction().replace(R.id.activity_main, fragment).commit();
	}*/
}
