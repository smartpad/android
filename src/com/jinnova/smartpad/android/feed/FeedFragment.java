package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.SmartpadContext;
import com.jinnova.smartpad.android.localstore.StoreManager;

public class FeedFragment extends Fragment implements SmartpadContext {
	private ListView list;
	
	public FeedFragment() {
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FeedManager.initialize();
		StoreManager.initialize();
		View view = inflater.inflate(R.layout.activity_main, null);
		list = (ListView) view.findViewById(R.id.list);
		list.setAdapter(new FeedViewAdapter(this));
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		/*super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);*/
	}

	@Override
	public FragmentManager getCurrFragmentManager() {
		return this.getFragmentManager();
	}

	@Override
	public int getParentViewGroupId() {
		return R.id.activity_main;
	}
}