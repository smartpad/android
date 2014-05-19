package com.jinnova.smartpad.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageLoaderTask {

	private HashMap<String, Drawable> imageCache;
	private static Drawable DEFAULT_ICON = null;
	private BaseAdapter adapt;
	private static ImageLoaderTask instance;

	public synchronized static ImageLoaderTask getInstance() {
		if (instance == null) {
			instance = new ImageLoaderTask();
		}
		return instance;
	}

	private ImageLoaderTask() {
		imageCache = new HashMap<String, Drawable>();
	}

	public Drawable loadImage(BaseAdapter adapt, ImageView view) {
		this.adapt = adapt;
		String url = (String) view.getTag();
		if (imageCache.containsKey(url)) {
			return imageCache.get(url);
		} else {
			new Loader().execute(url);
			return DEFAULT_ICON;
		}
	}

	private class Loader extends AsyncTask<String, Void, Drawable> {

		String _url;

		@Override
		protected Drawable doInBackground(String... params) {
			_url = params[0];
			InputStream istr;
			try {
				URL url = new URL(_url);
				istr = url.openStream();
			} catch (MalformedURLException e) {
				return null;
			} catch (IOException e) {
				return null;
			}
			return Drawable.createFromStream(istr, "src");
		}

		@Override
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			synchronized (this) {
				imageCache.put(_url, result);
			}
			adapt.notifyDataSetChanged();
		}

	}

}