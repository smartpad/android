package com.jinnova.smartpad.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewBuilder<F> {
	
	protected abstract int getLayoutTemplateId(); 

	public View createView(ViewGroup parent, LayoutInflater layoutInflator) {
		return layoutInflator.inflate(getLayoutTemplateId(), parent, false);
	}
	
	public abstract ViewTag createTag(View view);
	
	public abstract void loadView(View view, F f, SmartpadContext context);
}
