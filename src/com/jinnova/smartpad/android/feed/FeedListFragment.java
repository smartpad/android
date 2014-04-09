package com.jinnova.smartpad.android.feed;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jinnova.smartpad.android.R;

public class FeedListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_feed_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		/*ListView list = (ListView) view.findViewById(R.id.feed_list);
		FeedViewAdapter adapter = (FeedViewAdapter) list.getAdapter();*/
		ListView list = (ListView) view.findViewById(R.id.feed_list);
		FeedViewAdapter adapter = new FeedViewAdapter(this.getActivity());
		list.setAdapter(adapter);
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
