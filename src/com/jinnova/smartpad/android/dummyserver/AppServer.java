package com.jinnova.smartpad.android.dummyserver;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jinnova.smartpad.android.R;

public class AppServer {
	
	private static int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	
	public static JSONObject getFeeds(int from, int size) {
		JSONObject json = new JSONObject();
		try {
			json.accumulate("ver", "a");
			JSONArray ja = new JSONArray();
			for (int i = 0; i < size - 4; ) {
				ja.put(newFeed(from + i, i % images.length));
				i++;
				ja.put(newJson("branch", from + i, "Lotteria"));
				i++;
				ja.put(newJson("store", from + i, "Shop & Go"));
				i++;
				ja.put(newJson("sitem", from + i, "Cheese Burger"));
				i++;
				ja.put(newJson("promo", from + i, "Buy one get one free"));
				i++;
			}
			json.accumulate("feeds", ja);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private static JSONObject newFeed(int order, int image) {
		JSONObject json = new JSONObject();
		try {
			json.accumulate("type", "post");
			json.accumulate("ord", order);
			json.accumulate("date", new Date().toString());
			json.accumulate("desc", "Oscar không chỉ là nơi tôn vinh các tài năng điện ảnh mà còn " +
					"là nơi thể hiện tính cách, văn hóa của các ngôi sao qua những bài phát biểu nhận giải.");
			json.accumulate("title", "Người đàn bà cuồng dâm' bị cấm chiếu ở Thổ Nhĩ Kỳ");
			json.accumulate("image", images[image]);
			System.out.println(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	private static JSONObject newJson(String type, int order, String name) {
		JSONObject json = new JSONObject();
		try {
			json.accumulate("type", type);
			json.accumulate("ord", order);
			json.accumulate("name", name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
