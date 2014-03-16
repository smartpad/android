package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {

	@Override
	protected ViewBuilder<?>[] initBuilderMap() {
		ViewBuilder<?>[] builderMap = new ViewBuilder[FeedManager.TYPE_COUNT];
		builderMap[FeedManager.TYPE_POST] = new PostViewBuilder();
		builderMap[FeedManager.TYPE_BRANCH] = new BranchViewBuilder();
		builderMap[FeedManager.TYPE_PROMO] = new PromoViewBuilder();
		builderMap[FeedManager.TYPE_STORE] = new StoreViewBuilder();
		builderMap[FeedManager.TYPE_STOREITEM] = new StoreItemViewBuilder();
		return builderMap;
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	@Override
	public int getCount() {
		return FeedManager.instance.getFeedCount();
	}

	/**
	 * @param pos
	 * @return
	 */
	@Override
	public Object getItem(int pos) {
		return FeedManager.instance.getFeed(pos);
	}

	@Override
	public int getItemViewType(int position) {
		Feed feed = FeedManager.instance.getFeed(position);
		return feed.getType();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		return 0;
	}
}
