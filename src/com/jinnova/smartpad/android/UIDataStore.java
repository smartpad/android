package com.jinnova.smartpad.android;

import java.util.ArrayList;

import org.json.JSONArray;

public interface UIDataStore<T extends UIData> {

	ArrayList<String> get(int from, int i);
	
	String getListVersion();
	
	void append(JSONArray dataArray);

}
