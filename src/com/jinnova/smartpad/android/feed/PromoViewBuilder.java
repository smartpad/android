package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.TextView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class PromoViewBuilder extends ViewBuilder<Promotion> {
	
	private class PromotionUI extends ViewTag {
		TextView name;

		@Override
		public int getItemViewType() {
			return FeedManager.TYPE_PROMO;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.news_row;
	}

	@Override
	public ViewTag createTag(View view) {
		PromotionUI row  = new PromotionUI();
		row.name = (TextView) view.findViewById(R.id.decription);
		return row;
	}

	@Override
	public void loadView(View view, Promotion promo) {
		PromotionUI row = (PromotionUI) view.getTag();
		row.name.setText("Promotion");
	}

}
