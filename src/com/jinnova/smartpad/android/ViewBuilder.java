package com.jinnova.smartpad.android;

import android.content.Context;
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
	
	protected OnClickListener createDetailListener(final Context context, final SmartpadViewAdapter<UIData> adapter, final UIData uidata) {

		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				adapter.setDetail(uidata);

				//this also works well, and beside, this produces proper backstack entry,
				//but feeling unsure if startActivity() in this way is ok, according to android api?
				//Intent intent = new Intent(context, FeedListActivity.class);
				//intent.putExtra("feed", uidata.getJsonString());
				//context.startActivity(intent);
			}
		};
	}

	public View createView(ViewGroup parent, LayoutInflater layoutInflator) {
		return layoutInflator.inflate(getLayoutTemplateId(), parent, false);
	}
	
	public abstract ViewTag createTag(View view);
	
	public abstract void loadView(View view, T uidata, SmartpadViewAdapter<UIData> viewAdapter);
}
