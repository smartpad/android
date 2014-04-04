package com.jinnova.smartpad.android;

import org.json.JSONObject;

public interface JsonObjectFactory<T> {
	
	T instantiate(JSONObject json);
}
