package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.UICompound;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.UIDataFactory;
import com.jinnova.smartpad.android.cat.Branch;
import com.jinnova.smartpad.android.cat.Catalog;
import com.jinnova.smartpad.android.cat.CatalogItem;
import com.jinnova.smartpad.android.cat.Store;

public class FeedFactory implements UIDataFactory<Feed> {

	private interface FeedInstantiator<T> {
		T instantiate(JSONObject json);
	}
	
	@SuppressWarnings("rawtypes")
	private FeedInstantiator[] instantiators;
	
	public FeedFactory() {

		instantiators = new FeedInstantiator[UIData.TYPE_COUNT];
		instantiators[UIData.TYPE_POST] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Post(json);
			}
		};
		instantiators[UIData.TYPE_BRANCH] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Branch(json);
			}
		};
		instantiators[UIData.TYPE_PROMO] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Promotion(json);
			}
		};
		instantiators[UIData.TYPE_STORE] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Store(json);
			}
		};
		instantiators[UIData.TYPE_CAT] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new Catalog(json);
			}
		};
		instantiators[UIData.TYPE_CATITEM] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new CatalogItem(json);
			}
		};
		instantiators[UIData.TYPE_COMPOUND] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				return new UICompound(json);
			}
		};
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.UIDataFactory#instantiate(org.json.JSONObject)
	 */
	@Override
	public Feed instantiate(JSONObject json) {
		
		try {
			int feedType = UIData.getTypeNumber(json.getString("type"));
			if (feedType == UIData.TYPE_UNKNOWN) {
				return null;
			}
			@SuppressWarnings("unchecked")
			FeedInstantiator<Feed> one = instantiators[feedType];
			return one.instantiate(json);
		} catch (JSONException e) {
			return null;
		}
	}

}
