package com.jinnova.smartpad.android.feed;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinnova.smartpad.android.R;
import com.jinnova.smartpad.android.SmartpadViewAdapter;
import com.jinnova.smartpad.android.UIData;
import com.jinnova.smartpad.android.ViewBuilder;
import com.jinnova.smartpad.android.ViewTag;

public class CompoundFeedViewBuilder extends ViewBuilder<CompoundFeed> {
	
	boolean isView;
	private class CompoundFeedUI extends ViewTag {
		ViewPager viewPager;
	}

	private ViewBuilder<?>[][] builderMap;

	public CompoundFeedViewBuilder(ViewBuilder<?>[][] builderMap) {
		super(R.layout.viewpager_feed);
		this.builderMap = builderMap;
	}

	@Override
	public ViewTag createTag(View view) {
		CompoundFeedUI tag = new CompoundFeedUI();
		tag.viewPager = (ViewPager) view.findViewById(R.id.viewpager_feed);
		tag.viewPager.setPageMargin(-10);
		tag.viewPager.setHorizontalFadingEdgeEnabled(true);
		tag.viewPager.setFadingEdgeLength(5);
		return tag;
	}

	@Override
	public void loadView(final View view, final CompoundFeed compoundFeed, final SmartpadViewAdapter<UIData> viewAdapter) {
		CompoundFeedUI row = (CompoundFeedUI) view.getTag();
		row.viewPager.setAdapter(new ViewPagerAdapter(view.getContext(), viewAdapter, compoundFeed));
		
	}
	
	private class ViewPagerAdapter extends PagerAdapter {
		
		private CompoundFeed compoundFeed;
		private SmartpadViewAdapter<UIData> viewAdapter;
		private LayoutInflater inflater;
		
		public ViewPagerAdapter(Context context, SmartpadViewAdapter<UIData> viewAdapter, CompoundFeed compoundFeed) {
			this.viewAdapter = viewAdapter;
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.compoundFeed = compoundFeed;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == ((View) object);
		}
		
		@Override
		public int getCount() {
			return compoundFeed.size();
		}
		
		/* (non-Javadoc)
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup, int)
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Feed item = compoundFeed.getfeed(position);
			
			//there may be new/unknown feed type from server, causing null item
			if (item == null) {
				return null;
			}
			
			@SuppressWarnings("unchecked")
			ViewBuilder<Feed> viewBuilder = (ViewBuilder<Feed>) builderMap[item.getType()][item.getLayoutOption()];
			View convertView = viewBuilder.createView(container, inflater);
			ViewTag newTag = viewBuilder.createTag(convertView);
			convertView.setTag(newTag);
			
			loadView(viewBuilder, convertView, item);
			container.addView(convertView);
			return convertView;
		}

		protected void loadView(ViewBuilder<Feed> viewBuilder, View convertView, Feed feed) {
			viewBuilder.loadView(convertView, feed, viewAdapter);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}
	}
}
