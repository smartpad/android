package com.jinnova.smartpad.android;

import org.json.JSONObject;

public interface UIDataFactory<T> {
	
	T instantiate(JSONObject json);
}
