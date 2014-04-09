package com.jinnova.smartpad.android;

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
	
	protected OnClickListener createDetailListener(final SmartpadViewAdapter<?> adapter, final UIData uidata) {

		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.setDetail(uidata);
			}
		};
	}

	public View createView(ViewGroup parent, LayoutInflater layoutInflator) {
		return layoutInflator.inflate(getLayoutTemplateId(), parent, false);
	}
	
	public abstract ViewTag createTag(View view);
	
	public abstract void loadView(View view, T uidata, SmartpadViewAdapter<?> viewAdapter);
}
