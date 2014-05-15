package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class PromoViewDetailBuilder extends ViewBuilder<Promotion> {
	
	private class PromotionUI extends ViewTag {
		TextView name;
	}

	PromoViewDetailBuilder() {
		super(R.layout.feed_row_detail);
	}

	@Override
	public ViewTag createTag(View view) {
		PromotionUI row  = new PromotionUI();
		row.name = (TextView) view.findViewById(R.id.decriptionFeed);
		
		return row;
	}

	@Override
	public void loadView(View view, Promotion promo, SmartpadViewAdapter<UIData> viewAdapter) {
		PromotionUI row = (PromotionUI) view.getTag();
		row.name.setText("Promotion Detail: " + promo.getName());
	}

}
