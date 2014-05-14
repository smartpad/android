package com.jinnova.smartpad.android.feed;

import android.content.Context;

import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.UIDataList;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.cat.BranchViewBuilder;
import com.jinnova.smartpad.android.cat.BranchViewDetailBuilder;
import com.jinnova.smartpad.android.cat.CatalogItemViewBuilder;
import com.jinnova.smartpad.android.cat.CatalogItemViewDetailBuilder;
import com.jinnova.smartpad.android.cat.CatalogViewBuilder;
import com.jinnova.smartpad.android.cat.CatalogViewDetailBuilder;
import com.jinnova.smartpad.android.cat.StoreViewBuilder;
import com.jinnova.smartpad.android.cat.StoreViewDetailBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {
	
	public FeedViewAdapter(Context context, UIDataList<Feed> dataList) {
		super(context, dataList);
	}
	
	@Override
	protected ViewBuilder<?>[][] initBuilderMap() {
		ViewBuilder<?>[][] builderMap = new ViewBuilder[UIData.TYPE_COUNT][];
		builderMap[UIData.TYPE_POST] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_POST][LAYOUTOPT_DEFAULT] = new PostViewBuilder();
		builderMap[UIData.TYPE_POST][LAYOUTOPT_DETAIL] = new PostViewDetailBuilder();

		builderMap[UIData.TYPE_BRANCH] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_BRANCH][LAYOUTOPT_DEFAULT] = new BranchViewBuilder();
		builderMap[UIData.TYPE_BRANCH][LAYOUTOPT_DETAIL] = new BranchViewDetailBuilder();

		builderMap[UIData.TYPE_PROMO] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_PROMO][LAYOUTOPT_DEFAULT] = new PromoViewBuilder();
		builderMap[UIData.TYPE_PROMO][LAYOUTOPT_DETAIL] = new PromoViewDetailBuilder();

		builderMap[UIData.TYPE_STORE] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_STORE][LAYOUTOPT_DEFAULT] = new StoreViewBuilder();
		builderMap[UIData.TYPE_STORE][LAYOUTOPT_DETAIL] = new StoreViewDetailBuilder();

		builderMap[UIData.TYPE_CAT] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_CAT][LAYOUTOPT_DEFAULT] = new CatalogViewBuilder();
		builderMap[UIData.TYPE_CAT][LAYOUTOPT_DETAIL] = new CatalogViewDetailBuilder();

		builderMap[UIData.TYPE_CATITEM] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_CATITEM][LAYOUTOPT_DEFAULT] = new CatalogItemViewBuilder();
		builderMap[UIData.TYPE_CATITEM][LAYOUTOPT_DETAIL] = new CatalogItemViewDetailBuilder();
		
		builderMap[UIData.TYPE_COMPOUND] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[UIData.TYPE_COMPOUND][LAYOUTOPT_DEFAULT] = new CompoundFeedViewBuilder(builderMap);
		return builderMap;
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}
}
