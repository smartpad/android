package com.jinnova.smartpad.android.feed;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.content.Context;

import com.jinnova.smartpad.android.SmartpadViewAdapter;
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
import com.jinnova.smartpad.android.cat.SyscatViewBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {
	
	public FeedViewAdapter(Context context, UIDataList<Feed> dataList) {
		super(context, dataList);
	}
	
	@Override
	protected ViewBuilder<?>[][] initBuilderMap() {
		ViewBuilder<?>[][] builderMap = new ViewBuilder[TYPE_COUNT][];
		builderMap[TYPE_POST] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_POST][LAYOUTOPT_DEFAULT] = new PostViewBuilder();
		builderMap[TYPE_POST][LAYOUTOPT_DETAIL] = new PostViewDetailBuilder();

		builderMap[TYPE_SYSCAT] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_SYSCAT][LAYOUTOPT_DEFAULT] = new SyscatViewBuilder();
		builderMap[TYPE_SYSCAT][LAYOUTOPT_DETAIL] = new SyscatViewBuilder();

		builderMap[TYPE_BRANCH] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_BRANCH][LAYOUTOPT_DEFAULT] = new BranchViewBuilder();
		builderMap[TYPE_BRANCH][LAYOUTOPT_DETAIL] = new BranchViewDetailBuilder();

		builderMap[TYPE_PROMO] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_PROMO][LAYOUTOPT_DEFAULT] = new PromoViewBuilder();
		builderMap[TYPE_PROMO][LAYOUTOPT_DETAIL] = new PromoViewDetailBuilder();

		builderMap[TYPE_STORE] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_STORE][LAYOUTOPT_DEFAULT] = new StoreViewBuilder();
		builderMap[TYPE_STORE][LAYOUTOPT_DETAIL] = new StoreViewDetailBuilder();

		builderMap[TYPE_CAT] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_CAT][LAYOUTOPT_DEFAULT] = new CatalogViewBuilder();
		builderMap[TYPE_CAT][LAYOUTOPT_DETAIL] = new CatalogViewDetailBuilder();

		builderMap[TYPE_CATITEM] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_CATITEM][LAYOUTOPT_DEFAULT] = new CatalogItemViewBuilder();
		builderMap[TYPE_CATITEM][LAYOUTOPT_DETAIL] = new CatalogItemViewDetailBuilder();
		
		builderMap[TYPE_COMPOUND] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_COMPOUND][LAYOUTOPT_DEFAULT] = new CompoundFeedViewBuilder(builderMap);
		return builderMap;
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}
}
