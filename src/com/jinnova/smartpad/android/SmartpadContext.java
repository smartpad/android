package com.jinnova.smartpad.android;

import android.support.v4.app.FragmentManager;

public interface SmartpadContext {

	FragmentManager getCurrFragmentManager();

	int getParentViewGroupId();
}
