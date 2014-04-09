package com.jinnova.smartpad.android.feed;

import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FeedDetailFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_feed_detail, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		/*ListView list = (ListView) view.findViewById(R.id.feed_list);
		FeedViewAdapter adapter = (FeedViewAdapter) list.getAdapter();*/

		FeedViewAdapter adapter = new FeedViewAdapter(this.getActivity());
		String jsonString = getActivity().getIntent().getStringExtra("feed");
		try {
			adapter.setDetail(adapter.instantiate(new JSONObject(jsonString)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView list = (ListView) view.findViewById(R.id.feed_detail);
		list.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
	}

}
