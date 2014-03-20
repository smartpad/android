package com.jinnova.smartpad.android.feed;

import com.jinnova.smartpad.android.SmartpadContext;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.ViewBuilder;

public class FeedViewAdapter extends SmartpadViewAdapter<Feed> {
	
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
	
	public FeedViewAdapter(SmartpadContext context) {
		super(context);
	}
	
	@Override
	protected ViewBuilder<?>[][] initBuilderMap() {
		ViewBuilder<?>[][] builderMap = new ViewBuilder[FeedManager.TYPE_COUNT][];
		builderMap[FeedManager.TYPE_POST] = new ViewBuilder<?>[LAYOUTOPT_POST_COUNT];
		builderMap[FeedManager.TYPE_POST][LAYOUTOPT_POST_DEFAULT] = new PostViewBuilder();

		builderMap[FeedManager.TYPE_BRANCH] = new ViewBuilder<?>[LAYOUTOPT_BRANCH_COUNT];
		builderMap[FeedManager.TYPE_BRANCH][LAYOUTOPT_BRANCH_DEFAULT] = new BranchViewBuilder();

		builderMap[FeedManager.TYPE_PROMO] = new ViewBuilder<?>[LAYOUTOPT_PROMO_COUNT];
		builderMap[FeedManager.TYPE_PROMO][LAYOUTOPT_PROMO_DEFAULT] = new PromoViewBuilder();

		builderMap[FeedManager.TYPE_STORE] = new ViewBuilder<?>[LAYOUTOPT_STORE_COUNT];
		builderMap[FeedManager.TYPE_STORE][LAYOUTOPT_STORE_DEFAULT] = new StoreViewBuilder();

		builderMap[FeedManager.TYPE_STOREITEM] = new ViewBuilder<?>[LAYOUTOPT_STOREITEM_COUNT];
		builderMap[FeedManager.TYPE_STOREITEM][LAYOUTOPT_STOREITEM_DEFAULT] = new StoreItemViewBuilder();
		return builderMap;
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
	 * @see com.jinnova.smartpad.android.SmartpadViewAdapter#getItemViewType(java.lang.Object)
	 */
	@Override
	protected int getItemViewType(Feed feed) {
		return feed.getType();
	}

	/* (non-Javadoc)
	 * @see com.jinnova.smartpad.android.SmartpadViewAdapter#getItemViewLayout()
	 */
	@Override
	protected int getItemViewLayout(Feed feed) {
		return feed.getLayoutOption();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		Feed feed = FeedManager.instance.getFeed(position);
		return feed.getOrder();
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}
}
