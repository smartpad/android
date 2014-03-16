package com.jinnova.smartpad.android.feed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinnova.smartpad.R;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class PostViewBuilder extends ViewBuilder<Post> {
	
	private class PostUI extends ViewTag {
		ImageView imageView;
		TextView date;
		TextView title;
		TextView decription;
		Button goBtn;
		@Override
		public int getItemViewType() {
			return FeedManager.TYPE_POST;
		}
	}

	@Override
	protected int getLayoutTemplateId() {
		return R.layout.news_row;
	}

	@Override
	public ViewTag createTag(View view) {
		PostUI tag = new PostUI();
		tag.title = (TextView) view.findViewById(R.id.title);
		tag.imageView = (ImageView) view.findViewById(R.id.newsImage);
		tag.date = (TextView) view.findViewById(R.id.date);
		tag.decription = (TextView) view.findViewById(R.id.decription);
		tag.goBtn = (Button) view.findViewById(R.id.goBtn);
		return tag;
	}

	@Override
	public void loadView(View view, Post post) {
		PostUI row = (PostUI) view.getTag();
		row.date.setText(post.getDate() + " (views: " + FeedViewAdapter.viewCreationCount + ")");
		row.decription.setText(post.getDecription());
		row.title.setText(post.getTitle());
		row.imageView.setImageResource(post.getImage());
		final String target = post.getTarget();
		if (target == null) {
			row.goBtn.setVisibility(View.INVISIBLE);
		} else {
			row.goBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
					alertDialog.setTitle("Alert");
					alertDialog.setMessage(target);
					alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					// here you can add functions
					}
					});
					alertDialog.show();
				
				}
			});
		}
	}

}
