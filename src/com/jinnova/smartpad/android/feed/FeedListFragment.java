package com.jinnova.smartpad.android.feed;

import static com.jinnova.smartpad.android.ServerConstants.*;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.UIDataList;
import com.jinnova.smartpad.android.UIDataStore;

public class FeedListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_feed_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		/*ListView list = (ListView) view.findViewById(R.id.feed_list);
		FeedViewAdapter adapter = (FeedViewAdapter) list.getAdapter();*/
		
		UIDataList<Feed> dataList = new UIDataList<Feed>(this.getActivity(), new FeedFactory(), UIDataStore.TABLE_FEEDS, REST_FEEDS);
		final FeedViewAdapter adapter = new FeedViewAdapter(this.getActivity(), dataList);
		
		ListView list = (ListView) view.findViewById(R.id.feed_list);
		list.setAdapter(adapter);
		list.setOnScrollListener(new OnScrollListener() {
			Boolean isLoadMore = true;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				isLoadMore = (firstVisibleItem + visibleItemCount >= totalItemCount) && isLoadMore;
				if (isLoadMore) {
					adapter.loadMore();
					isLoadMore = false;
				} else {
					isLoadMore = firstVisibleItem + visibleItemCount != totalItemCount;
				}
			}
		});
		adapter.loadMore();
	}

	/*private FeedViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		ListView list = (ListView) findViewById(R.id.list);
		adapter = new FeedViewAdapter(this);
		list.setAdapter(adapter);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		adapter.loadMore();
	}

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}

	@Override
	public SmartpadViewAdapter<?> getViewAdapter() {
		return adapter;
	}*/
}
