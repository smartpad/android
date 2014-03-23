package com.jinnova.smartpad.android.feed;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jinnova.smartpad.R;

public class MenuFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list, null);
		final ListView list = (ListView) view.findViewById(R.id.feed_menu);
		String[] menuString = {"Feeds", "Favorites", "Member cards", "Orders history"};
		list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, menuString));
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				onListItemClick(list, v, position, id);
			}
		});
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	//@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		switch (position) {
		case 0:
			newContent = new FeedFragment();
			break;
		case 1:
			newContent = new FeedFragment();
			break;
		case 2:
			newContent = new FeedFragment();
			break;
		case 3:
			newContent = new FeedFragment();
			break;
		}
		if (newContent != null)
			switchFragment(newContent);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		if(getActivity() instanceof MainActivity) {
			MainActivity fca = (MainActivity) getActivity();
			fca.switchContent(fragment);
		}
	}

}
