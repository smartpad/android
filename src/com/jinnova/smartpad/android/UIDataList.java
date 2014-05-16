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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UIDataList<T extends UIData> {
	
	@SuppressWarnings("unused")
	private static final int DEFAULT_PAGESIZE = 10;
	
	//private String version;
	final int tableId;
	
	//private int lastOrder = 0; //order number starts from 1
	
	final ArrayList<T> backedList = new ArrayList<T>(50);
	boolean backedListClear = false;
	
	private String servicePath = "/" + REST_FEEDS;
	//private Feed feed;
	
	final UIDataStore<T> persistStore;
	
	final UIDataFactory<T> factory;
	
	final boolean[] newListLock = new boolean[] {false};
	//private boolean newListLoading = false;
	
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
	
	String getServicePath() {
		return this.servicePath;
	}

	/*public void setDetail(T detailed) {
		backedList.clear();
		//backedList.add(detailed);	
		// lastOrder = 0;
		//detailed.setOverridenLayoutOpt(SmartpadViewAdapter.LAYOUTOPT_DETAIL);
		//servicePath = "similar/" + UIData.getTypeName(detailed.getType()) + "/" + detailed.getId();
		//servicePath = "similar/" + detailed.getTypeName() + "/" + detailed.getId();
		//feed = (Feed) detailed;
		servicePath = ((Feed) detailed).getUrl();
	}*/

	public void setServicePath(String p) {
		backedListClear = true;
		servicePath = p;
	}

	void setServicePathInternal(String p) {
		servicePath = p;
	}
	
	T get(int location) {
		return backedList.get(location);
	}

	int size() {
		return backedList.size();
	}

	void switchToLatest(SmartpadViewAdapter<? extends UIData> loadingCallback) {
		backedListClear = true;
		if (persistStore != null) {
			persistStore.switchToLatest(tableId);
		}
		loadMore(loadingCallback);
	}
	
	void loadMore(final SmartpadViewAdapter<? extends UIData> loadingCallback) {
		
		synchronized (newListLock) {
			if (newListLock[0] || servicePath == null) {
				return;
			}
			newListLock[0] = true;
		}
		
		new LoadTask<T>(this, loadingCallback).execute();
	}

}

class LoadTask<T extends UIData> extends AsyncTask<String, Void, Object> {
	
	private final UIDataList<T> dataList;
	
	private ArrayList<T> newList = null;
	private boolean newVersionLoaded = false;
	SmartpadViewAdapter<? extends UIData> loadingCallback;
	
	LoadTask(UIDataList<T> dataList, SmartpadViewAdapter<? extends UIData> loadingCallback) {
		this.dataList = dataList;
		this.loadingCallback = loadingCallback;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Object doInBackground(String... params) {
		try {
			return doInBackground();
		} finally {
			synchronized (dataList.newListLock) {
				dataList.newListLock[0] = false;
			}
		}
	}
	
	private Object doInBackground() {
		//first attempt with database
		//TODO temporarily ignore loading from local store
		/*ArrayList<JSONObject> moreData;
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
		}*/
		
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
				if (dataList.persistStore != null) {
					JSONObject newData = result.getJSONObject(VERSIONING_NEW);
					dataList.persistStore.addNewVersion(dataList.tableId, result.getString("v"), result.getString("exp"), newData);
				}
				newVersionLoaded = true;
			}

			//check for data in target version
			if (result.has(VERSIONING_TARGET)) {
				JSONObject targetData = result.getJSONObject(VERSIONING_TARGET);
				if (dataList.persistStore != null) {
					dataList.persistStore.insert(dataList.tableId, targetData, true /*toTableInUse*/);
				}
				
				JSONArray targetArray = targetData.getJSONArray(FIELD_ARRAY);
				newList = buildList(targetArray);
				String nextUrl = JSONSupport.getString(targetData, FIELD_ACTION_LOADNEXT);
				//next url is null, when no more data available
				if (nextUrl != null) {
					nextUrl = nextUrl.substring(REST_SCHEME.length());
				}
				dataList.setServicePathInternal(nextUrl);
			}
			return null;
		} catch (JSONException e) {
			return e;
		}
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Object caughtError) {
		if (caughtError == null) {
			if (newList != null && !newList.isEmpty()) {
				Log.d("UIDataList", "Loaded items: " + newList.size());
				if (dataList.backedListClear) {
					dataList.backedList.clear();
				}
				dataList.backedList.addAll(newList);
				loadingCallback.notifyDataSetChanged();
			}
			if (newVersionLoaded) {
				loadingCallback.newVersionLoaded();
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
		
		String feedUrl = dataList.getServicePath();
		/*if (feedUrl == null) {
			feedUrl = "/" + REST_FEEDS;
		}*/
		
		String serviceUrl = "http://10.88.106.11:9090" + feedUrl;
		//String serviceUrl = "http://192.168.1.8:9090" + ServerConstants.REST_FEEDS + feedUrl;
		
		HttpClient httpclient = new DefaultHttpClient();
		ByteArrayOutputStream tempStream= null;
		try {
			Log.d("UIDataList", "Connect: " + serviceUrl);
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
	
	@SuppressWarnings("unused")
	private ArrayList<T> buildList(ArrayList<JSONObject> dataArray) {
		ArrayList<T> newList = new ArrayList<T>();
		for (JSONObject itemJson : dataArray) {
			T t = dataList.factory.instantiate(itemJson);
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
			T t = dataList.factory.instantiate(itemJson);
			if (t != null) {
				newList.add(t);
			}
		}
		return newList;
	}
}