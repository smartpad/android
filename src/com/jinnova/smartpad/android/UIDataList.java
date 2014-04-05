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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

class UIDataList<T extends UIData> {
	
	private static final int DEFAULT_PAGESIZE = 10;
	
	//private String version;
	private final int tableId;
	
	private int lastOrder = 0; //order number starts from 1
	
	private final ArrayList<T> backedList = new ArrayList<T>();
	
	private final String servicePath;
	
	private final UIDataStore<T> persistStore;
	
	private final UIDataFactory<T> factory;

	class UIDataTaskServer extends AsyncTask<String, Void, JSONObject> {
		
		Object caughtError;
	
		@Override
		protected JSONObject doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response;
			try {
				response = httpclient.execute(new HttpGet(params[0]));
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					out.close();
					try {
						JSONObject result = new JSONObject(out.toString());
						String newVer = result.getString("v");
						JSONArray dataArray = result.getJSONArray("l");
						String version = persistStore.getVersionInUse(tableId);
						if (version == null || version.equals(newVer)) {
							//got more data for current version
							persistStore.insert(tableId, dataArray, true /*toTableInUse*/);
						} else {
							//got new data version
							persistStore.addNewVersion(tableId, newVer, result.getString("exp"), dataArray);
						}
						return result;
					} catch (JSONException e) {
						caughtError = e;
						return null;
					}
				} else {
					// Closes the connection.
					response.getEntity().getContent().close();
					caughtError = statusLine;
					return null;
				}
			} catch (ClientProtocolException e) {
				caughtError = e;
				return null;
			} catch (IOException e) {
				caughtError = e;
				return null;
			}
		}
		
	};
	
	UIDataList(Context context, UIDataFactory<T> factory, int table, String servicePath) {
		this.servicePath = servicePath;
		this.tableId = table;
		this.persistStore = new UIDataStore<T>(context);
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

	void switchToLatest(BaseAdapter adapter) {
		backedList.clear();
		persistStore.switchToLatest(tableId);
		adapter.notifyDataSetChanged();
	}
	
	void loadMore(final BaseAdapter adapter) {
		
		try {
			ArrayList<JSONObject> moreData = persistStore.get(tableId, lastOrder, lastOrder + DEFAULT_PAGESIZE);
			if (moreData != null && !moreData.isEmpty()) {
				//found more data from db, keep loading from db only
				//version = persistStore.getVersionInUse();
				for (JSONObject one : moreData) {
					backedList.add(factory.instantiate(one));
				}
				adapter.notifyDataSetChanged();
				return;
			}
		} catch (JSONException e1) {
			Log.w("UIDataList", "json format error in stored data");
		}
		
		new UIDataTaskServer() {			
			@Override
			protected void onPostExecute(JSONObject result) {
				if (caughtError != null) {
					String msg;
					if (caughtError instanceof Throwable) {
						msg = caughtError.getClass().getSimpleName() + ": " + ((Throwable) caughtError).getMessage();
					} else if (caughtError instanceof StatusLine) {
						StatusLine sl = (StatusLine) caughtError;
						msg = "StatusLine: " + sl.getStatusCode() + ": " + sl.getReasonPhrase();
					} else {
						msg = String.valueOf(caughtError);
					}
					Log.w("UIDataList", msg);
					return;
				}
				try {
					String newVer = result.getString("v");
					JSONArray dataArray = result.getJSONArray("l");
					String version = persistStore.getVersionInUse(tableId);
					if (version == null || version.equals(newVer)) {
						if (version == null) {
							version = newVer;
						}
						append(dataArray);
						adapter.notifyDataSetChanged();
					} else {
						//got new data version
						//TODO inform user
					}
				} catch (JSONException e) {
					//feedList.load(null);
				}
			}
		//}.execute("http://10.88.68.236:9090/" + servicePath + "?offset=" + lastOrder + "&size=" + DEFAULT_PAGESIZE);
		}.execute("http://192.168.0.123:9090/" + servicePath + "?offset=" + lastOrder + "&size=" + DEFAULT_PAGESIZE);
	}
}
