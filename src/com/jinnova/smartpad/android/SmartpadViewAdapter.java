package com.jinnova.smartpad.android;

import static com.jinnova.smartpad.android.ServerConstants.*;

import com.jinnova.smartpad.android.feed.Feed;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public abstract class SmartpadViewAdapter<T extends UIData> extends BaseAdapter {
	
	public static final int LAYOUTOPT_UNINITIALIZED = -1;
	public static final int LAYOUTOPT_DEFAULT = 0;
	public static final int LAYOUTOPT_DETAIL = 1;
	public static final int LAYOUTOPT_COUNT = 2;
	
	//builder map is long/exhausted, because there are multiple layouts / custom layouts for each feed type. 
	private ViewBuilder<?>[][] builderMap;
	
	private int[][] viewTypeNumbers;
	private int builderCount;

	//private Context context;
	
	private UIDataList<T> feedList;
	//private CompoundFeed compoundFeed;
	
	protected SmartpadViewAdapter() {
	}
	
	protected abstract ViewBuilder<?>[][] initBuilderMap();
	
	public SmartpadViewAdapter(Context context, UIDataList<T> dataList) {
		//this.context = context;
		feedList = dataList;
	}
	
	private void initBuilderMapInternal() {
		builderMap = initBuilderMap();
		builderCount = 0;
		viewTypeNumbers = new int[builderMap.length][];
		for (int i = 0; i < builderMap.length; i++) {
			ViewBuilder<?>[] builders = builderMap[i];
			if (builders != null) {
				viewTypeNumbers[i] = new int[builders.length];
				for (int j = 0; j < builders.length; j++) {
					viewTypeNumbers[i][j] = builderCount + j;
				}
				builderCount += builders.length;
			}
		}
	}
	
	public final void loadMore() {
		feedList.loadMore(this);
	}
	
	public void setDetail(T item) {
		//feedList.setDetail(item);
		feedList.setServicePath(((Feed) item).getUrl());
		//notifyDataSetChanged();
		feedList.loadMore(this);
	}
	
	public void setServicePath(String servicePath) {
		feedList.setServicePath(servicePath);
		//notifyDataSetChanged();
		feedList.loadMore(this);
	}

	@Override
	public final int getCount() {
		return feedList.size();
	}

	/**
	 * @param pos
	 * @return
	 */
	@Override
	public final Object getItem(int pos) {
		return feedList.get(pos);
	}

	@Override
	public final int getViewTypeCount() {
		if (builderMap == null) {
			initBuilderMapInternal();
		}
		return builderCount;
	}

	/* (non-Javadoc)
	 * @see android.widget.BaseAdapter#getItemViewType(int)
	 */
	@Override
	public final int getItemViewType(int position) {
		Feed feed = (Feed) feedList.get(position);
		//return feed.getType();
		return viewTypeNumbers[feed.getType()][feed.getLayoutOption()];
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public final long getItemId(int position) {
		Feed feed = (Feed) feedList.get(position);
		return feed.getOrder();
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	/**
	 * @param pos
	 * @param view
	 * @param parent
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if (builderMap == null) {
			initBuilderMapInternal();
		}
		/*if (pos == 0) {
			if (compoundFeed == null) {
				compoundFeed = new CompoundFeed();
				compoundFeed.addfeed((Feed) feedList.get(0));
				compoundFeed.addfeed((Feed) feedList.get(1));
				compoundFeed.addfeed((Feed) feedList.get(2));
			}
			ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[compoundFeed.getType()][compoundFeed.getLayoutOption()];
			if (convertView != null) {
				ViewTag tag = (ViewTag) convertView.getTag();
				if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
					Log.d("SmartpadViewAdapter", "view reuse failed");
					convertView = createView(viewBuilder, parent);
					((ViewTag) convertView.getTag()).setItemViewType(compoundFeed.getType());
				}
			} else {
				convertView = createView(viewBuilder, parent);
				((ViewTag) convertView.getTag()).setItemViewType(compoundFeed.getType());
			}
			viewBuilder.loadView(convertView, (T) compoundFeed, this);
			return convertView;
		}*/
		T item = feedList.get(pos);
		if (item.getType() >= builderMap.length) {
			//unknown item
			return null;
		}
		if (item.getLayoutOption() >= builderMap[item.getType()].length) {
			//unknown layout option
			return null;
		}
		ViewBuilder<T> viewBuilder = (ViewBuilder<T>) builderMap[item.getType()][item.getLayoutOption()];
		if (viewBuilder == null) {
			//unsupported layout option
			return null;
		}

		if (convertView != null) {
			//Heterogeneous lists can specify their number of view types, so that this View is always of the right type
			//we do safe check anyway
			ViewTag tag = (ViewTag) convertView.getTag();
			if (tag == null || tag.getItemViewType() != getItemViewType(pos)) {
				if (tag.getItemViewType() != getItemViewType(pos)) {
					Log.d("SmartpadViewAdapter", "view reuse failed");
				}
				convertView = createView(viewBuilder, parent);
				((ViewTag) convertView.getTag()).setItemViewType(item.getType());
			}
		} else {
			convertView = createView(viewBuilder, parent);
			((ViewTag) convertView.getTag()).setItemViewType(viewTypeNumbers[item.getType()][item.getLayoutOption()]);
		}
		viewBuilder.loadView(convertView, item, (SmartpadViewAdapter<UIData>) this);
		return convertView;
	}
	
	public static int viewCreationCount;
	
	private static View createView(ViewBuilder<?> viewBuilder, ViewGroup parent) {
		viewCreationCount++;
		LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View newView = viewBuilder.createView(parent, inflater);
		ViewTag newTag = viewBuilder.createTag(newView);
		newView.setTag(newTag);
		return newView;
	}

	/**
	 * callback from UIDataList.loadMore()
	 */
	public void newVersionLoaded() {
		// ask user to refresh with latest data list
		
	}
	
	public void setOnClickListener(TextView view, final String servicePath) {
		
		view.setTextColor(Color.BLUE);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				setServicePath(servicePath);
			}
		});
	}
	
	public void setOnClickListener(TextView view, Feed feed, final String typeName, final String fieldId) {
		
		view.setTextColor(Color.BLUE);
		final String fieldValue = feed.getString(FIELD_UP_ID);
		if (fieldValue == null) {
			return;
		}
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String servicePath = "/" + REST_FEEDS + "/" + TYPENAME_SYSCAT + "/" + fieldValue + "/" + REST_DRILL;
				setServicePath(servicePath);
			}
		});
	}

	public void setToView(UIData data, TextView view, String fieldId) {

		String s = data.getString(fieldId);
		if (s != null) {
			view.setText(s);
			view.setVisibility(View.VISIBLE);
			return;
		}

		view.setText("");
		view.setVisibility(View.GONE);
	}

	public void setToViewHtml(UIData data, TextView view, String fieldId) {

		String html = data.getString(fieldId);
		if (html != null /*&& !"".equals(html.trim())*/) {
			//view.setText(Html.fromHtml(source));
			setTextViewHTML(view, html);
			view.setVisibility(View.VISIBLE);
			return;
		}

		view.setText("");
		view.setVisibility(View.GONE);
	}

	//http://stackoverflow.com/questions/12418279/android-textview-with-clickable-links-how-to-capture-clicks
	private void setTextViewHTML(TextView text, String html) {
		CharSequence sequence = Html.fromHtml(html);
		SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
		URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
		for (URLSpan span : urls) {
			makeLinkClickable(strBuilder, span);
		}
		text.setText(strBuilder);
	}
	
	private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span) {
		int start = strBuilder.getSpanStart(span);
		int end = strBuilder.getSpanEnd(span);
		int flags = strBuilder.getSpanFlags(span);
		ClickableSpan clickable = new ClickableSpan() {
			public void onClick(View view) {
				// Do something with span.getURL() to handle the link click...
				//System.out.println(span.getURL());
				SmartpadViewAdapter.this.setServicePath(span.getURL().substring(REST_SCHEME.length()));
			}
		};
		strBuilder.setSpan(clickable, start, end, flags);
		strBuilder.removeSpan(span);
	}
	
}
