package com.jinnova.smartpad.android.dummyserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.jinnova.smartpad.android.UIDataList;
import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;

@SuppressWarnings("unused")
public class AppServer {
	
	private static int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	
	public static JSONObject getFeeds(int from, int size, final UIDataList feedList, final BaseAdapter adapter) {
		
		new AsyncTask<String, Void, Object>() {

			@Override
			protected Object doInBackground(String... params) {
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response;
				try {
					response = httpclient.execute(new HttpGet(params[0]));
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						response.getEntity().writeTo(out);
						out.close();
						return out.toString();
					} else {
						// Closes the connection.
						response.getEntity().getContent().close();
						throw new IOException(statusLine.getReasonPhrase());
					}
				} catch (ClientProtocolException e) {
					return e;
				} catch (IOException e) {
					return e;
				}
			}

			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof Throwable) {
					return;
				}
				try {
					feedList.load(new JSONObject((String) result));
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					feedList.load(null);
				}
			}
			
		}.execute("http://10.88.68.236:9090/feeds?size=" + size);
		return null;
		
		/*HttpClient httpclient = new DefaultHttpClient();
	    HttpResponse response = httpclient.execute(new HttpGet("http://10.88.68.236:9090/feeds?size=" + size));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        response.getEntity().writeTo(out);
        out.close();
        String responseString = out.toString();
        System.out.println(responseString);
        try {
			return new JSONObject(responseString);
		} catch (JSONException e) {
			throw new IOException("Server returns malformed json");
		}*/
		
		/*JSONObject json = new JSONObject();
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
		return json;*/
	}
	
	/*private static JSONObject newFeed(int order, int image) {
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
	}*/
}