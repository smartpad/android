package com.jinnova.smartpad.android;

public abstract class ViewTag {
	
	private int viewType;
	
	void setItemViewType(int viewType) {
		this.viewType = viewType;
	}

	int getItemViewType() {
		return viewType;
	}
}
