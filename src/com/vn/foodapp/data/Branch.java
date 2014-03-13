package com.vn.foodapp.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Branch extends Feed {

	public Branch(JSONObject json) {
		super(json);
	}

	public String getName() {
		try {
			return json.getString("name");
		} catch (JSONException e) {
			return "";
		}
	}
}
