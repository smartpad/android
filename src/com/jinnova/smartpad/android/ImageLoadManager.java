package com.jinnova.smartpad.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoadManager {
	
	private static final int THREAD_MAXCOUNT = 3;
	
	//private static final String imageServer = "http://totallystockholm.se";

	private Bitmap imageNotFound;
	
	private Bitmap imageIOError;
	
	public static ImageLoadManager instance;
	
	private SortedSet<String> pathQueue = new TreeSet<String>();
	
	private HashMap<ImageView, String> viewQueue = new HashMap<ImageView, String>();
	
	private HashMap<String, Bitmap> updateQueue = new HashMap<String, Bitmap>();
	
	private HashMap<String, Bitmap> loadedImages = new HashMap<String, Bitmap>();
	
	private LinkedList<Thread> loadingThreads = new LinkedList<Thread>();
	
	private final Object lock = new Object();
	
	private Context context;
	
	public static void initialize(Context context) {
		instance = new ImageLoadManager();
		instance.imageNotFound = BitmapFactory.decodeResource(context.getResources(), R.drawable.anh1);
		instance.imageIOError = BitmapFactory.decodeResource(context.getResources(), R.drawable.anh2);
		instance.context = context;
	}

	private ImageLoadManager() {
		
	}
	
	public void registerLoad(String path) {
		
		if (path == null) {
			return;
		}
		
		synchronized (lock) {
			
			if (loadedImages.containsKey(path)) {
				return;
			}
			
			pathQueue.add(path);
			if (loadingThreads.size() < THREAD_MAXCOUNT) {
				ImageLoadingThread t = new ImageLoadingThread();
				t.setName("ImageLoadingThread " + new Date());
				loadingThreads.add(t);
				t.start();
			}
		}
	}
	
	public void registerView(String path, ImageView view) {
		synchronized (lock) {
			
			if (loadedImages.containsKey(path)) {
				view.setImageBitmap(loadedImages.get(path));
				return;
			}
			
			if (!pathQueue.contains(path)) {
				//load may be done too fast, or the load was not registered at all
				Log.d("image loading", "fastload?");
				registerLoad(path);
			}
			viewQueue.put(view, path);
		}
	}
	
	private class ImageLoadingThread extends Thread {
		
		@Override
		public void run() {
			
			while (true) {
				String path;
				synchronized (lock) {
					if (pathQueue.isEmpty()) {
						loadingThreads.remove(this);
						return;
					}
					path = pathQueue.first();
					pathQueue.remove(path);
				}
				
				Bitmap bm;
				try {
					
					File dir = new File(context.getFilesDir() + path);
					/*if (dir.exists()) {
						//TODO check if image changed
						File file = new File(dir, "image.png");
						bm = BitmapFactory.decodeFile(file.getAbsolutePath());
						Log.i("image loader", "Loaded image from file");
					} else*/ {
					
						URL url = new URL(UIDataList.SERVER + "/images" + path);
						Log.i("image loader", "Loading image from: " + url);
						bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
						dir.mkdirs();
						File file = new File(dir, "image.png");
						
						FileOutputStream outStream = new FileOutputStream(file);
						bm.compress(CompressFormat.PNG, 100, outStream);
						Log.i("image loader", "Saved image to file");
					}
				} catch (MalformedURLException e) {
					bm = imageNotFound;
				} catch (IOException e) {
					bm = imageIOError;
				}
				
				synchronized (lock) {
					updateQueue.put(path, bm);
					loadedImages.put(path, bm);
				}
				
				new ImageUpdateViewTask().execute((String) null);
			}
		}
	}

	private class ImageUpdateViewTask extends AsyncTask<String, Void, Object> {


		@Override
		protected Object doInBackground(String... params) {
			return null;
		}

		@Override
		protected void onPostExecute(Object o) {

			synchronized (lock) {
				for (Entry<String, Bitmap> oneLoaded : updateQueue.entrySet()) {
					
					String path = oneLoaded.getKey();
					Bitmap bm = oneLoaded.getValue();
					for (Entry<ImageView, String> viewEntry : viewQueue.entrySet()) {
						if (!path.equals(viewEntry.getValue())) {
							continue;
						}
						//check if view is unlaid out?
						//if (view.isLaidOut())
						ImageView view = viewEntry.getKey();
						/*Bitmap bm = inUsedImages.get(view);
						if (bm != null) {
							bm.recycle();
						}*/
						view.setImageBitmap(bm);
					}
				}
				viewQueue.clear();
				updateQueue.clear();
			}
		}

	}

}