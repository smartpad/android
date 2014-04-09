package com.jinnova.smartpad.android;

import com.jinnova.smartpad.android.feed.FeedDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public abstract class ViewBuilder<T extends UIData> {
	
	private final int layoutTemplateId;
	
	protected ViewBuilder(int layoutTemplateId) {
		this.layoutTemplateId = layoutTemplateId;
	}
	
	protected int getLayoutTemplateId() {
		return layoutTemplateId;
	}
	
	protected OnClickListener createDetailListener(final Context context, final SmartpadViewAdapter<?> adapter, final UIData uidata) {

		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				//adapter.setDetail(uidata);

				Intent intent = new Intent(context, FeedDetailActivity.class);
				intent.putExtra("feed", uidata.getJsonString());
				context.startActivity(intent);
			}
		};
	}

	public View createView(ViewGroup parent, LayoutInflater layoutInflator) {
		return layoutInflator.inflate(getLayoutTemplateId(), parent, false);
	}
	
	public abstract ViewTag createTag(View view);
	
	public abstract void loadView(View view, T uidata, SmartpadViewAdapter<?> viewAdapter);
}
