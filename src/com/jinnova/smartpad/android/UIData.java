package com.jinnova.smartpad.android;

import org.json.JSONException;
import org.json.JSONObject;

public class UIData {
	
	static final String ORD = "ord";
	
	protected JSONObject json;
	
	private int overridenLayoutOpt = SmartpadViewAdapter.LAYOUTOPT_UNINITIALIZED;
	
	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	public String getJsonString() {
		return json.toString();
	}
	
	public int getType() {
		throw new UnsupportedOperationException("Subclasses must override getType()");
	}

	public String getId() {
		try {
			return json.getString("id");
		} catch (JSONException e) {
			return null;
		}
	}
	
	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			return null;
		}
	}
	
	void setOverridenLayoutOpt(int opt) {
		this.overridenLayoutOpt = opt;
	}
	
	public int getLayoutOption() {
		if (overridenLayoutOpt !=  SmartpadViewAdapter.LAYOUTOPT_UNINITIALIZED) {
			return overridenLayoutOpt;
		}
		if (!json.has("layOpt")) {
			return 0;
		}
		try {
			return json.getInt("layOpt");
		} catch (JSONException e) {
			return 0;
		}
		
	}

	public int getOrder() {
		try {
			return json.getInt(ORD);
		} catch (JSONException e) {
			return 0;
		}
	}

}
