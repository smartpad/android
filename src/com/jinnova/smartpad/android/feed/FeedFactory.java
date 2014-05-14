package com.jinnova.smartpad.android.feed;

import static com.jinnova.smartpad.android.ServerConstants.*;

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
		instantiators[TYPE_COMPOUND] = new FeedInstantiator<UIData>() {
			
			@Override
			public UIData instantiate(JSONObject json) {
				//return new UICompound(json);
				return new CompoundFeed(json);
			}
		};
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.UIDataFactory#instantiate(org.json.JSONObject)
	 */
	@Override
	public Feed instantiate(JSONObject json) {
		
		try {
			//int feedType = UIData.getTypeNumber(json.getString(FIELD_TYPE));
			int feedType = json.getInt(FIELD_TYPENUM);
			if (feedType == TYPE_UNKNOWN) {
				return null;
			}
			@SuppressWarnings("unchecked")
			FeedInstantiator<Feed> one = instantiators[feedType];
			if (one == null) {
				return null;
			}
			return one.instantiate(json);
		} catch (JSONException e) {
			return null;
		}
	}

}
