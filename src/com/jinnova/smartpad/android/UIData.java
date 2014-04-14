package com.jinnova.smartpad.android;

import org.json.JSONException;
import org.json.JSONObject;

public class UIData {

	public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_CAT = 3;
	public static final int TYPE_CATITEM = 4;
	public static final int TYPE_PROMO = 5;
	public static final int TYPE_COMPOUND = 6;
	
	public static final int TYPE_COUNT = 7;
	
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
			return SmartpadViewAdapter.LAYOUTOPT_DEFAULT;
		}
		try {
			return json.getInt("layOpt");
		} catch (JSONException e) {
			return SmartpadViewAdapter.LAYOUTOPT_DEFAULT;
		}
		
	}

	public int getOrder() {
		try {
			return json.getInt(ORD);
		} catch (JSONException e) {
			return 0;
		}
	}
	
	public static String getTypeName(int typeNumber) {
		if (typeNumber == TYPE_POST) {
			return "post";
		} else if (typeNumber == TYPE_BRANCH) {
			return "branch";
		} else if (typeNumber == TYPE_STORE) {
			return "store";
		} else if (typeNumber == TYPE_CAT) {
			return "cat";
		} else if (typeNumber == TYPE_CATITEM) {
			return "citem";
		} else if (typeNumber == TYPE_PROMO) {
			return "pro";
		} else if (typeNumber == TYPE_COMPOUND) {
			return "com";
		} else {
			return null;
		}
	}

	public static int getTypeNumber(String typeName) {
		if ("post".equals(typeName)) {
			return TYPE_POST;
		} else if ("branch".equals(typeName)) {
			return TYPE_BRANCH;
		} else if ("store".equals(typeName)) {
			return TYPE_STORE;
		} else if ("cat".equals(typeName)) {
			return TYPE_CAT;
		} else if ("citem".equals(typeName)) {
			return TYPE_CATITEM;
		} else if ("pro".equals(typeName)) {
			return TYPE_PROMO;
		} else if ("com".equals(typeName)) {
			return TYPE_COMPOUND;
		} else {
			return TYPE_UNKNOWN;
		}
	}
}
