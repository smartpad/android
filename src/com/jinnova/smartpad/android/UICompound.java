package com.jinnova.smartpad.android;

import org.json.JSONObject;

public class UICompound extends UIData {
	
	private JSONObject json;
	
	public UICompound(JSONObject json) {
		this.json = json;
	}

	@Override
	public int getType() {
		return TYPE_COMPOUND;
	}

	@Override
	public int getLayoutOption() {
		return SmartpadViewAdapter.LAYOUTOPT_DEFAULT;
	}

	@Override
	public String getTypeName() {
		return TYPENAME_COMPOUND;
	}

}
