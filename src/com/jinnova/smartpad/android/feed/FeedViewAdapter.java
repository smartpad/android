package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.SmartpadActivity;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_STOREITEM = 3;
	public static final int TYPE_PROMO = 4;
	
	public static final int TYPE_COUNT = 5;
	
	private static final int LAYOUTOPT_POST_DEFAULT = 0;
	private static final int LAYOUTOPT_POST_COUNT = 1;
	
	private static final int LAYOUTOPT_BRANCH_DEFAULT = 0;
	private static final int LAYOUTOPT_BRANCH_COUNT = 1;
	
	private static final int LAYOUTOPT_STORE_DEFAULT = 0;
	private static final int LAYOUTOPT_STORE_COUNT = 1;
	
	private static final int LAYOUTOPT_STOREITEM_DEFAULT = 0;
	private static final int LAYOUTOPT_STOREITEM_COUNT = 1;
	
	private static final int LAYOUTOPT_PROMO_DEFAULT = 0;
	private static final int LAYOUTOPT_PROMO_COUNT = 1;

	private interface FeedInstantiator<T> {
		T instantiate(JSONObject json);
	}
	
	@SuppressWarnings("rawtypes")
	private FeedInstantiator[] instantiators;
	
	public FeedViewAdapter(SmartpadActivity context) {
		super(context, "feeds", "feeds");
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
		instantiators[TYPE_STOREITEM] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new StoreItem(json);
			}
		};
	}
	
	@Override
	protected ViewBuilder<?>[][] initBuilderMap() {
		ViewBuilder<?>[][] builderMap = new ViewBuilder[TYPE_COUNT][];
		builderMap[TYPE_POST] = new ViewBuilder<?>[LAYOUTOPT_POST_COUNT];
		builderMap[TYPE_POST][LAYOUTOPT_POST_DEFAULT] = new PostViewBuilder();

		builderMap[TYPE_BRANCH] = new ViewBuilder<?>[LAYOUTOPT_BRANCH_COUNT];
		builderMap[TYPE_BRANCH][LAYOUTOPT_BRANCH_DEFAULT] = new BranchViewBuilder();

		builderMap[TYPE_PROMO] = new ViewBuilder<?>[LAYOUTOPT_PROMO_COUNT];
		builderMap[TYPE_PROMO][LAYOUTOPT_PROMO_DEFAULT] = new PromoViewBuilder();

		builderMap[TYPE_STORE] = new ViewBuilder<?>[LAYOUTOPT_STORE_COUNT];
		builderMap[TYPE_STORE][LAYOUTOPT_STORE_DEFAULT] = new StoreViewBuilder();

		builderMap[TYPE_STOREITEM] = new ViewBuilder<?>[LAYOUTOPT_STOREITEM_COUNT];
		builderMap[TYPE_STOREITEM][LAYOUTOPT_STOREITEM_DEFAULT] = new StoreItemViewBuilder();
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
		} else if ("sitem".equals(type)) {
			return TYPE_STOREITEM;
		} else if ("promo".equals(type)) {
			return TYPE_PROMO;
		} else {
			return TYPE_UNKNOWN;
		}
	}
}
