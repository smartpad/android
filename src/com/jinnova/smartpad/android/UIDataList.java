package com.jinnova.smartpad.android;

import static com.jinnova.smartpad.android.ServerConstants.*;

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

import com.jinnova.smartpad.android.feed.Feed;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UIDataList<T extends UIData> {
	
	private static final int DEFAULT_PAGESIZE = 10;
	
	//private String version;
	private final int tableId;
	
	//private int lastOrder = 0; //order number starts from 1
	
	private final ArrayList<T> backedList = new ArrayList<T>();
	
	//private String servicePath;
	private Feed feed;
	
	private final UIDataStore<T> persistStore;
	
	private final UIDataFactory<T> factory;
	
	public UIDataList(Context context, UIDataFactory<T> factory, int table, String servicePath) {
		//this.servicePath = servicePath;
		this.tableId = table;
		if (table != UIDataStore.TABLE_NOTABLE) {
			this.persistStore = new UIDataStore<T>(context);
		} else {
			this.persistStore = null;
		}
		this.factory = factory;
	}

	public void setDetail(T detailed) {
		backedList.clear();
		backedList.add(detailed);
		//TODO lastOrder = 0;
		detailed.setOverridenLayoutOpt(SmartpadViewAdapter.LAYOUTOPT_DETAIL);
		//servicePath = "similar/" + UIData.getTypeName(detailed.getType()) + "/" + detailed.getId();
		//servicePath = "similar/" + detailed.getTypeName() + "/" + detailed.getId();
		feed = (Feed) detailed;
	}
	
	T get(int location) {
		return backedList.get(location);
	}

	int size() {
		return backedList.size();
	}

	void switchToLatest(SmartpadViewAdapter<? extends UIData> adapter) {
		backedList.clear();
		if (persistStore != null) {
			persistStore.switchToLatest(tableId);
		}
		loadMore(adapter);
	}
	
	void loadMore(final SmartpadViewAdapter<? extends UIData> adapter) {
		
		new AsyncTask<String, Void, Object>() {
			
			private ArrayList<T> newList = null;
			private boolean newVersionLoaded = false;
		
			@Override
			protected Object doInBackground(String... params) {
				
				//first attempt with database
				ArrayList<JSONObject> moreData;
				if (persistStore != null) {
					try {
						//moreData = persistStore.get(tableId, lastOrder, lastOrder + DEFAULT_PAGESIZE);
						int size = backedList.size();
						moreData = persistStore.get(tableId, size, size + DEFAULT_PAGESIZE);
					} catch (JSONException e) {
						Log.w("UIDataList", "json format error in stored data");
						return e;
					}
				} else {
					moreData = null;
				}
				
				if (moreData != null && !moreData.isEmpty()) {
					//found more data from db, keep loading from db only
					newList = buildList(moreData);
					return null;
				}
				
				//nothing in database, go to server
				String[] data = new String[1];
				Object caughtError = connect(data);
				if (caughtError != null) {
					return caughtError;
				}
				try {
					
					//check for data in new version
					JSONObject result = new JSONObject(data[0]);
					if (result.has(VERSIONING_NEW)) {
						//got new data version
						if (persistStore != null) {
							JSONObject newData = result.getJSONObject(VERSIONING_NEW);
							persistStore.addNewVersion(tableId, result.getString("v"), result.getString("exp"), newData);
						}
						newVersionLoaded = true;
					}

					//check for data in target version
					if (result.has(VERSIONING_TARGET)) {
						JSONObject targetData = result.getJSONObject(VERSIONING_TARGET);
						if (persistStore != null) {
							persistStore.insert(tableId, targetData, true /*toTableInUse*/);
						}
						
						JSONArray targetArray = targetData.getJSONArray(FIELD_ARRAY);
						newList = buildList(targetArray);
					}
					return null;
				} catch (JSONException e) {
					return e;
				}
			}			
			@Override
			protected void onPostExecute(Object caughtError) {
				if (caughtError == null) {
					if (newList != null) {
						backedList.addAll(newList);
						adapter.notifyDataSetChanged();
					}
					if (newVersionLoaded) {
						adapter.newVersionLoaded();
					}
					return;
				}
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
			}
			
			private Object connect(String[] data) {
				/*String versionParams;
				if (persistStore != null) {
					versionParams= "verTarget=" + persistStore.getVersionInUse(tableId) +
							"&verLatest=" + persistStore.getVersionLatest(tableId);
				} else {
					versionParams = "";
				}
				String serviceUrl = "http://10.88.106.11:9090/" + servicePath + "?" + versionParams +
						"&offset=" + backedList.size() + "&size=" + DEFAULT_PAGESIZE;*/
				
				String feedUrl;
				if (feed != null) {
					feedUrl = feed.getUrl();
				} else {
					feedUrl = "";
				}
				String serviceUrl = "http://10.88.106.11:9090/" + ServerConstants.REST_FEEDS + "/" + feedUrl;
				HttpClient httpclient = new DefaultHttpClient();
				ByteArrayOutputStream tempStream= null;
				try {
					HttpResponse response = httpclient.execute(new HttpGet(serviceUrl));
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
						return statusLine;
					}
					tempStream = new ByteArrayOutputStream();
					response.getEntity().writeTo(tempStream);
					data[0] = tempStream.toString();
					return null;
				} catch (ClientProtocolException e) {
					return e;
				} catch (IOException e) {
					return e;
				} finally {
					try {
						if (tempStream != null) {
							tempStream.close();
						}
						/*if (response != null) {
							response.getEntity().getContent().close();
						}*/
					} catch (IOException e) {
						//ignore
						Log.w("UIDataList", "IOException in closing temp stream: " + e.getMessage());
					}
				}
			}
			
			private ArrayList<T> buildList(ArrayList<JSONObject> dataArray) {
				ArrayList<T> newList = new ArrayList<T>();
				for (JSONObject itemJson : dataArray) {
					T t = factory.instantiate(itemJson);
					if (t != null) {
						newList.add(t);
					}
				}
				return newList;
			}
			
			private ArrayList<T> buildList(JSONArray dataArray) throws JSONException {
				ArrayList<T> newList = new ArrayList<T>();
				for (int i = 0; i < dataArray.length(); i++) {
					JSONObject itemJson = dataArray.getJSONObject(i);
					T t = factory.instantiate(itemJson);
					if (t != null) {
						newList.add(t);
					}
				}
				return newList;
			}
		}.execute((String) null);
	}

}

