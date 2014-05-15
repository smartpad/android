package com.jinnova.smartpad.android.feed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class PostViewDetailBuilder extends ViewBuilder<Post> {
	
	private class PostUI extends ViewTag {
		ImageView imageView;
		TextView date;
		TextView title;
		TextView decription;
	}

	PostViewDetailBuilder() {
		super(R.layout.feed_row_detail);
	}

	@Override
	public ViewTag createTag(View view) {
		PostUI tag = new PostUI();
		tag.title = (TextView) view.findViewById(R.id.titleFeed);
		tag.imageView = (ImageView) view.findViewById(R.id.imageFeed);
		tag.date = (TextView) view.findViewById(R.id.dateFeed);
		tag.decription = (TextView) view.findViewById(R.id.decriptionFeed);
		return tag;
	}

	@Override
	public void loadView(final View view, final Post post, final SmartpadViewAdapter<UIData> viewAdapter) {
		PostUI row = (PostUI) view.getTag();
		row.date.setText(post.getDate() + " (views: " + FeedViewAdapter.viewCreationCount + ")");
		row.decription.setText(post.getDecription());
		row.title.setText(post.getTitle());
		row.imageView.setImageResource(post.getImage());
	}

}
