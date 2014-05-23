package com.jinnova.smartpad.android;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageLoadManager {
	
	private static final String SERVER_MEDIA = "http://10.88.106.11:8080/imaging";
	
	private static final int THREAD_MAXCOUNT = 3;
	
	//private static final String imageServer = "http://totallystockholm.se";

	private Bitmap imageNotFound;
	
	private Bitmap imageIOError;
	
	public static ImageLoadManager instance;
	
	//SortedSet sorts paths by path value, but we need sorting by adding order
	//private SortedSet<String> pathQueue = new TreeSet<String>();
	private LinkedList<String> pathQueue = new LinkedList<String>();
	
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
			if (pathQueue.contains(path)) {
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
		
		if (path == null) {
			return;
		}
		
		synchronized (lock) {
			
			if (loadedImages.containsKey(path)) {
				view.setImageBitmap(loadedImages.get(path));
				return;
			}
			
			if (!pathQueue.contains(path)) {
				//load may be done too fast, or the load was not registered at all
				Log.d("image loading", "fastload?");
				registerLoad(path);
			} else {
				//prioritize up this image
				pathQueue.remove(path);
				pathQueue.addFirst(path);
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
					path = pathQueue.getFirst();
					pathQueue.remove(path);
				}
				
				Bitmap image = null;
				try {
					int index = path.lastIndexOf("/");
					File cacheDir = new File(context.getFilesDir() + path.substring(0, index));
					String imageFileName = path.substring(index + 1);
	
					String[] etag = new String[1];
					etag[0] = cacheGetETag(cacheDir, imageFileName);
					image = httpGetImage(path, etag);
					
					if (image == null) {
						//not changed, cached image to be used 
						image = cacheGetImage(cacheDir, imageFileName);
						
						//by somehow, we may cannot load image from cache
						if (image == null) {
							image = httpGetImage(path, null);
						}
					} else {
						cacheStoreImage(cacheDir, imageFileName, image, etag[0]);
					}
					
				} catch (MalformedURLException e) {
					image = imageIOError;
					Log.d("ImageLoadManager", "Error", e);
				} catch (IOException e) {
					image = imageIOError;
					Log.d("ImageLoadManager", "Error", e);
				}
				
				if (image == null) {
					image = imageNotFound;
				}
				synchronized (lock) {
					updateQueue.put(path, image);
					loadedImages.put(path, image);
				}
				
				new ImageUpdateViewTask().execute((String) null);
			}
		}
	}
	
	private static Bitmap cacheGetImage(File cacheDir, String imageFileName) {
		
		File imageFile = new File(cacheDir, imageFileName);
		if (!imageFile.exists()) {
			return null;
		}
		Log.d("ImageLoadManager", "Loading image from file: " + imageFile.getAbsolutePath());
		return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
	}
	
	private static String cacheGetETag(File cacheDir, String imageFileName) {
		
		File etagFile = new File(cacheDir, imageFileName + ".etag");
		if (!etagFile.exists()) {
			Log.d("ImageLoadManager", "No etag found at " + etagFile.getAbsolutePath());
			return null;
		}
		
		try {
			FileReader fr = new FileReader(etagFile);
			BufferedReader reader = new BufferedReader(fr);
			String etag = reader.readLine();
			Log.d("ImageLoadManager", "Got etag from " + etagFile.getAbsolutePath() + ": " + etag);
			return etag;
		} catch (IOException e) {
			Log.d("ImageLoadManager", "Can't read etag", e);
			return null;
		}
	}
	
	private static void deleteDir(File target) {
		if (target.isFile()) {
			target.delete();
			return;
		}
		for (File f : target.listFiles()) {
			/*if (f.isDirectory()) {
				deleteDir(f);
			} else {
				f.delete();
			}*/
			deleteDir(f);
		}
		target.delete();
	}
	
	private static File prepareFileForWriting(File folder, String fileName) throws IOException {
		
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		File f = new File(folder, fileName);
		if (f.exists() /*&& imageFile.isDirectory()*/) {
			deleteDir(f);
		}

		f.createNewFile();
		/*if (!imageFile.exists()) {
			imageFile.createNewFile();
		}*/
		return f;
	}
	
	private static void cacheStoreImage(File cacheDir, String imageFileName, Bitmap image, String etag) throws IOException {
		
		File imageFile = prepareFileForWriting(cacheDir, imageFileName);
		FileOutputStream outStream = null;
		try {
			outStream = new FileOutputStream(imageFile);
			image.compress(CompressFormat.PNG, 100, outStream);
			Log.d("ImageLoadManager", "Saved image to file: " + imageFile.getAbsolutePath());
		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}
		
		File etagFile = prepareFileForWriting(cacheDir, imageFileName + ".etag");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(etagFile));
			writer.write(etag);
			Log.d("ImageLoadManager", "Saved etag to file: " + etagFile.getAbsolutePath());
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	/**
	 * http://stackoverflow.com/questions/4457492/how-do-i-use-the-simple-http-client-in-android
	 * 
	 * @return null if and only if not changed
	 */
	private Bitmap httpGetImage(String path, String[] etag) throws ClientProtocolException, IOException {
		
		String url = SERVER_MEDIA + path;
		Log.d("ImageLoadManager", "Loading image from: " + url);
		
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		if (etag != null && etag.length > 0 && etag[0] != null) {
			httpget.setHeader("If-None-Match", etag[0]);
		}

		// Execute the request
		InputStream instream = null;
		try {
			HttpResponse response = httpclient.execute(httpget);
			Log.d("ImageLoadManager", path + ": " + response.getStatusLine().toString());
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_NOT_FOUND) {
				throw new IOException("Not found");
			}
			if (statusCode == HttpStatus.SC_NOT_MODIFIED) {
				Log.d("ImageLoadManager", path + ": Not modified");
				return null;
			}
			
			Header[] etagHeader = response.getHeaders("ETag");
			if (etagHeader != null && etagHeader.length > 0) {
				etag[0] = etagHeader[0].getValue();
			}
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity == null) {
				throw new IOException("No entity found in response");
			}

			// A Simple JSON Response Read
			instream = entity.getContent();
			Bitmap image = BitmapFactory.decodeStream(instream);
			if (image == null) {
				throw new IOException("Can't decode image stream");
			}
			return image;
		} finally {
			if (instream != null) {
				instream.close();
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