package com.jinnova.smartpad.android;

import static com.jinnova.smartpad.android.ServerConstants.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public abstract class UIData {

	/*public static final int TYPE_UNKNOWN = -1;
	public static final int TYPE_POST = 0;
	public static final int TYPE_BRANCH = 1;
	public static final int TYPE_STORE = 2;
	public static final int TYPE_CAT = 3;
	public static final int TYPE_CATITEM = 4;
	public static final int TYPE_PROMO = 5;
	public static final int TYPE_COMPOUND = 6;
	
	public static final int TYPE_COUNT = 7;*/
	
	//static final String ORD = "ord";
	
	protected JSONObject json;
	
	private int overridenLayoutOpt = SmartpadViewAdapter.LAYOUTOPT_UNINITIALIZED;
	
	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	public String getJsonString() {
		return json.toString();
	}
	
	public int getType() {
		throw new UnsupportedOperationException("Subclasses must override getType()");
	}
	
	public abstract String getTypeName();

	public String getId() {
		try {
			return json.getString(FIELD_ID);
		} catch (JSONException e) {
			Log.w("smartpad.json", e);
			return null;
		}
	}
	
	public String getName() {
		try {
			return json.getString(FIELD_NAME);
		} catch (JSONException e) {
			Log.w("smartpad.json", e);
			return null;
		}
	}
	
	public String getCatItemTypeName() {
		return JSONSupport.getString(json, FIELD_SYSCATID);
	}
	
	void setOverridenLayoutOpt(int opt) {
		this.overridenLayoutOpt = opt;
	}
	
	public int getLayoutOption() {
		if (overridenLayoutOpt !=  SmartpadViewAdapter.LAYOUTOPT_UNINITIALIZED) {
			return overridenLayoutOpt;
		}
		if (!json.has("layOpt")) {
			return SmartpadViewAdapter.LAYOUTOPT_DEFAULT;
		}
		try {
			return json.getInt("layOpt");
		} catch (JSONException e) {
			Log.w("smartpad.json", e);
			return SmartpadViewAdapter.LAYOUTOPT_DEFAULT;
		}
		
	}

	public int getOrder() {
		/*try {
			return json.getInt(ORD);
		} catch (JSONException e) {
			return 0;
		}*/
		return 1;
	}
	
	public String getString(String name) {
		if (json.has(name)) {
			try {
				return json.getString(name);
			} catch (JSONException e) {
				Log.w("json", e);
				return null;
			}
		} else {
			return null;
		}
	}

	public void setToView(TextView view, String name) {
		
		try {
			if (json.has(name)) {
				view.setText(json.getString(name));
				return;
			}
		} catch (JSONException e) {
			Log.w("json", e);
		}

		view.setText("");
		view.setVisibility(View.GONE);
	}

	public void setToViewHtml(TextView view, String name, SmartpadViewAdapter<UIData> viewAdapter) {
		
		try {
			if (json.has(name)) {
				String html = json.getString(name);
				if (html != null) {
					//view.setText(Html.fromHtml(source));
					setTextViewHTML(view, html, viewAdapter);
					return;
				}
			}
		} catch (JSONException e) {
			Log.w("json", e);
		}

		view.setText("");
		view.setVisibility(View.GONE);
	}
	
	//http://stackoverflow.com/questions/12418279/android-textview-with-clickable-links-how-to-capture-clicks
	private void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span,
			final SmartpadViewAdapter<UIData> viewAdapter) {
		int start = strBuilder.getSpanStart(span);
		int end = strBuilder.getSpanEnd(span);
		int flags = strBuilder.getSpanFlags(span);
		ClickableSpan clickable = new ClickableSpan() {
			public void onClick(View view) {
				// Do something with span.getURL() to handle the link click...
				//System.out.println(span.getURL());
				viewAdapter.setServicePath(span.getURL().substring("smartpad://".length()));
			}
		};
		strBuilder.setSpan(clickable, start, end, flags);
		strBuilder.removeSpan(span);
	}

	private void setTextViewHTML(TextView text, String html, SmartpadViewAdapter<UIData> viewAdapter) {
		CharSequence sequence = Html.fromHtml(html);
		SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
		URLSpan[] urls = strBuilder.getSpans(0, sequence.length(),
				URLSpan.class);
		for (URLSpan span : urls) {
			makeLinkClickable(strBuilder, span, viewAdapter);
		}
		text.setText(strBuilder);
	}
	
	/*public static String getTypeName(int typeNumber) {
		if (typeNumber == TYPE_POST) {
			return "post";
		} else if (typeNumber == TYPE_BRANCH) {
			return "branch";
		} else if (typeNumber == TYPE_STORE) {
			return "store";
		} else if (typeNumber == TYPE_CAT) {
			return "cat";
		} else if (typeNumber == TYPE_CATITEM) {
			return "citem";
		} else if (typeNumber == TYPE_PROMO) {
			return "pro";
		} else if (typeNumber == TYPE_COMPOUND) {
			return "com";
		} else {
			return null;
		}
	}

	public static int getTypeNumber(String typeName) {
		if ("post".equals(typeName)) {
			return TYPE_POST;
		} else if ("branch".equals(typeName)) {
			return TYPE_BRANCH;
		} else if ("store".equals(typeName)) {
			return TYPE_STORE;
		} else if ("cat".equals(typeName)) {
			return TYPE_CAT;
		} else if ("citem".equals(typeName)) {
			return TYPE_CATITEM;
		} else if ("pro".equals(typeName)) {
			return TYPE_PROMO;
		} else {
			return TYPE_UNKNOWN;
		}
	}*/
}
