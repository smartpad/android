package com.jinnova.smartpad.android.viewmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.android.feed.Post;
import com.vn.foodapp.R;

public class PostViewBuilder implements ViewBuilder<Post> {
	
	private LayoutInflater layoutInflator;
	
	public PostViewBuilder(LayoutInflater layoutInflator) {
		this.layoutInflator = layoutInflator;
	}

	@Override
	public View createView(ViewGroup parent) {
		View view = layoutInflator.inflate(R.layout.news_row, parent, false);
		PostUI row  = new PostUI();
		row.title = (TextView) view.findViewById(R.id.title);
		row.imageView = (ImageView) view.findViewById(R.id.newsImage);
		row.date = (TextView) view.findViewById(R.id.date);
		row.decription = (TextView) view.findViewById(R.id.decription);
		view.setTag(row);
		return view;
	}
	
	private class PostUI {
		ImageView imageView;
		TextView date;
		TextView title;
		TextView decription;
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
