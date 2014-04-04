package com.jinnova.smartpad.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

class UIDataList<T extends UIData> {
	
	private static final int DEFAULT_PAGESIZE = 10;
	
	private String version;
	
	private int lastOrder = 0; //order number starts from 1
	
	private final ArrayList<T> backedList = new ArrayList<T>();
	
	private final String servicePath;
	
	private final UIDataStore<T> persistStore;
	
	private final UIDataFactory<T> factory;

	class UIDataTaskServer extends AsyncTask<String, Void, Object> {
	
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
					return new IOException(statusLine.getReasonPhrase());
				}
			} catch (ClientProtocolException e) {
				return e;
			} catch (IOException e) {
				return e;
			}
		}
		
	};
	
	UIDataList(UIDataStore<T> persistStore,  UIDataFactory<T> factory, String servicePath) {
		this.servicePath = servicePath;
		this.persistStore = persistStore;
		this.factory = factory;
	}
	
	T get(int location) {
		return backedList.get(location);
	}

	int size() {
		return backedList.size();
	}
	
	private void append(JSONArray dataArray) {
		try {
			T t = null;
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject itemJson = dataArray.getJSONObject(i);
				t = factory.instantiate(itemJson);
				if (t != null) {
					backedList.add(t);
				}
			}
			if (t != null) {
				lastOrder = t.getOrder();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void append(ArrayList<String> moreData) {
		for (String oneString : moreData) {
			try {
				JSONObject oneJson = (JSONObject) new JSONTokener(oneString).nextValue();
				backedList.add(factory.instantiate(oneJson));
			} catch (JSONException e) {
				Log.e("UIDataList", "JSONException from database");
			}
		}
	}
	
	void loadMore(final BaseAdapter adapter) {
		
		ArrayList<String> moreData = persistStore.get(lastOrder, lastOrder + DEFAULT_PAGESIZE);
		if (moreData != null && !moreData.isEmpty()) {
			//found more data from db, keep loading from db only
			version = persistStore.getListVersion();
			append(moreData);
			adapter.notifyDataSetChanged();
			return;
		}
		
		new UIDataTaskServer() {			
			@Override
			protected void onPostExecute(Object result) {
				if (result instanceof Throwable) {
					return;
				}
				try {
					JSONObject json = new JSONObject((String) result);
					String newVer = json.getString("ud_v");
					JSONArray dataArray = json.getJSONArray("ud_l");
					if (version == null || version.equals(newVer)) {
						if (version == null) {
							version = newVer;
						}
						persistStore.append(dataArray);
						append(dataArray);
					} else {
						//got new data version
					}
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					//feedList.load(null);
				}
			}
		}.execute("http://10.88.68.236:9090/" + servicePath + "?offset=" + lastOrder + "&size=" + DEFAULT_PAGESIZE);
	}
}
