package com.vn.foodapp.mappers;

import com.vn.foodapp.data.Feed;

import android.view.View;
import android.view.ViewGroup;

public interface ViewBuilder<F extends Feed> {
	
	void loadView(View view, F f);

	View createView(ViewGroup parent);
}
