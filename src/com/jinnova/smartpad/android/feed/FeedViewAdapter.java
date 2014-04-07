package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIDataStore;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.cat.Branch;
import com.jinnova.smartpad.android.cat.BranchViewBuilder;
import com.jinnova.smartpad.android.cat.Catalog;
import com.jinnova.smartpad.android.cat.CatalogItem;
import com.jinnova.smartpad.android.cat.CatalogItemViewBuilder;
import com.jinnova.smartpad.android.cat.CatalogViewBuilder;
import com.jinnova.smartpad.android.cat.Store;
import com.jinnova.smartpad.android.cat.StoreViewBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_CAT = 3;
	public static final int TYPE_CATITEM = 4;
	public static final int TYPE_PROMO = 5;
	
	public static final int TYPE_COUNT = 6;

	private interface FeedInstantiator<T> {
		T instantiate(JSONObject json);
	}
	
	@SuppressWarnings("rawtypes")
	private FeedInstantiator[] instantiators;
	
	public FeedViewAdapter(SmartpadActivity context) {
		super(context, UIDataStore.TABLE_FEEDS, "feeds");
		instantiators = new FeedInstantiator[TYPE_COUNT];
		instantiators[TYPE_POST] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Post(json);
			}
		};
		instantiators[TYPE_BRANCH] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Branch(json);
			}
		};
		instantiators[TYPE_PROMO] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Promotion(json);
			}
		};
		instantiators[TYPE_STORE] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Store(json);
			}
		};
		instantiators[TYPE_CAT] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Catalog(json);
			}
		};
		instantiators[TYPE_CATITEM] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new CatalogItem(json);
			}
		};
	}
	
	@Override
	protected ViewBuilder<?>[][] initBuilderMap() {
		ViewBuilder<?>[][] builderMap = new ViewBuilder[TYPE_COUNT][];
		builderMap[TYPE_POST] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_POST][LAYOUTOPT_DEFAULT] = new PostViewBuilder();
		builderMap[TYPE_POST][LAYOUTOPT_DETAIL] = new PostViewBuilder();

		builderMap[TYPE_BRANCH] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_BRANCH][LAYOUTOPT_DEFAULT] = new BranchViewBuilder();
		builderMap[TYPE_BRANCH][LAYOUTOPT_DETAIL] = new BranchViewBuilder();

		builderMap[TYPE_PROMO] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_PROMO][LAYOUTOPT_DEFAULT] = new PromoViewBuilder();
		builderMap[TYPE_PROMO][LAYOUTOPT_DETAIL] = new PromoViewBuilder();

		builderMap[TYPE_STORE] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_STORE][LAYOUTOPT_DEFAULT] = new StoreViewBuilder();
		builderMap[TYPE_STORE][LAYOUTOPT_DETAIL] = new StoreViewBuilder();

		builderMap[TYPE_CAT] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_CAT][LAYOUTOPT_DEFAULT] = new CatalogViewBuilder();
		builderMap[TYPE_CAT][LAYOUTOPT_DETAIL] = new CatalogViewBuilder();

		builderMap[TYPE_CATITEM] = new ViewBuilder<?>[LAYOUTOPT_COUNT];
		builderMap[TYPE_CATITEM][LAYOUTOPT_DEFAULT] = new CatalogItemViewBuilder();
		builderMap[TYPE_CATITEM][LAYOUTOPT_DETAIL] = new CatalogItemViewBuilder();
		return builderMap;
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	@Override
	public Feed instantiate(JSONObject json) {
		
		try {
			int feedType = convertFeedType(json.getString("type"));
			if (feedType == TYPE_UNKNOWN) {
				return null;
			}
			@SuppressWarnings("unchecked")
			FeedInstantiator<Feed> one = instantiators[feedType];
			return one.instantiate(json);
		} catch (JSONException e) {
			return null;
		}
	}
	
	private static int convertFeedType(String type) {
		if ("post".equals(type)) {
			return TYPE_POST;
		} else if ("branch".equals(type)) {
			return TYPE_BRANCH;
		} else if ("store".equals(type)) {
			return TYPE_STORE;
		} else if ("cat".equals(type)) {
			return TYPE_CAT;
		} else if ("citem".equals(type)) {
			return TYPE_CATITEM;
		} else if ("pro".equals(type)) {
			return TYPE_PROMO;
		} else {
			return TYPE_UNKNOWN;
		}
	}
}
