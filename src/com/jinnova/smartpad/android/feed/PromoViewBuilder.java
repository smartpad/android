package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class PromoViewBuilder extends ViewBuilder<Promotion> {
	
	private class PromotionUI extends ViewTag {
		TextView name;
		Button detailBtnFeed;
	}

	PromoViewBuilder() {
		super(R.layout.feed_row);
	}

	@Override
	public ViewTag createTag(View view) {
		PromotionUI row  = new PromotionUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		row.detailBtnFeed = (Button) view.findViewById(R.id.detailBtnFeed);
		
		return row;
	}

	@Override
	public void loadView(View view, Promotion promo, SmartpadViewAdapter<?> viewAdapter) {
		PromotionUI row = (PromotionUI) view.getTag();
		row.name.setText("Promotion " + promo.getName());
		row.detailBtnFeed.setOnClickListener(createDetailListener(viewAdapter, promo));
		
		/*final String target = promo.getTarget();
		if (target == null) {
			row.detailBtnFeed.setVisibility(View.INVISIBLE);
		} else {
			row.detailBtnFeed.setOnClickListener(new OnClickListener() {
				
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
		}*/
	}

}
