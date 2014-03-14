package com.jinnova.smartpad.android.viewmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.android.feed.FeedManager;
import com.jinnova.smartpad.android.feed.Post;
import com.jinnova.smartpad.R;

public class PostViewBuilder implements ViewBuilder<Post> {
	
	private LayoutInflater layoutInflator;
	
	private class PostUI extends ViewTag {
		ImageView imageView;
		TextView date;
		TextView title;
		TextView decription;
		
		@Override
		public int getFeedType() {
			return FeedManager.TYPE_POST;
		}
	}
	
	public PostViewBuilder(LayoutInflater layoutInflator) {
		this.layoutInflator = layoutInflator;
	}

	@Override
	public View createView(ViewGroup parent) {
		return layoutInflator.inflate(R.layout.news_row, parent, false);
	}

	@Override
	public ViewTag createTag(View view) {
		PostUI tag = new PostUI();
		tag.title = (TextView) view.findViewById(R.id.title);
		tag.imageView = (ImageView) view.findViewById(R.id.newsImage);
		tag.date = (TextView) view.findViewById(R.id.date);
		tag.decription = (TextView) view.findViewById(R.id.decription);
		return tag;
	}

	@Override
	public void loadView(View view, Post post) {
		PostUI row = (PostUI) view.getTag();
		row.date.setText(post.getDate());
		row.decription.setText(post.getDecription());
		row.title.setText(post.getTitle());
		row.imageView.setImageResource(post.getImage());
	}

}
