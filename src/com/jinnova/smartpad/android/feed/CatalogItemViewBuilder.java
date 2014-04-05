package com.jinnova.smartpad.android.feed;

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

public class CatalogItemViewBuilder extends ViewBuilder<CatalogItem> {
	
	private class CatalogItemUI extends ViewTag {
		TextView name;
		Button goBtn;
		@Override
		public int getItemViewType() {
			return FeedViewAdapter.TYPE_CATITEM;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.feed_row;
	}

	@Override
	public ViewTag createTag(View view) {
		CatalogItemUI row  = new CatalogItemUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.goBtn = (Button) view.findViewById(R.id.goBtnFeed);
		return row;
	}

	@Override
	public void loadView(View view, CatalogItem catItem, SmartpadActivity context) {
		CatalogItemUI row = (CatalogItemUI) view.getTag();
		row.name.setText("Catalog Item " + catItem.getName());
		final String target = catItem.getTarget();
		if (target == null) {
			row.goBtn.setVisibility(View.INVISIBLE);
		} else {
			row.goBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
					alertDialog.setTitle("Alert");
					alertDialog.setMessage(target);
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
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
