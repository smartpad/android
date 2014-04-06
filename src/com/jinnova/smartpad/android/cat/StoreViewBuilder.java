package com.jinnova.smartpad.android.cat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;
import com.jinnova.smartpad.android.feed.FeedViewAdapter;

public class StoreViewBuilder extends ViewBuilder<Store> {
	
	private class StoreUI extends ViewTag {
		TextView name;
		Button goBtn;
		@Override
		public int getItemViewType() {
			return FeedViewAdapter.TYPE_STORE;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.feed_row;
	}

	@Override
	public ViewTag createTag(View view) {
		StoreUI row  = new StoreUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.goBtn = (Button) view.findViewById(R.id.goBtnFeed);
		return row;
	}

	@Override
	public void loadView(View view, Store store, SmartpadActivity context) {
		StoreUI row = (StoreUI) view.getTag();
		row.name.setText("Shop " + store.getName());
		final String target = store.getTarget();
		if (target == null) {
			row.goBtn.setVisibility(View.INVISIBLE);
		} else {
			row.goBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
					alertDialog.setTitle("Alert");
					alertDialog.setMessage(target);
					alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						}
					});
					alertDialog.show();
				
				}
			});
		}
	}

}
