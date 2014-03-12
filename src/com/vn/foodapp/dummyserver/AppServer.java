package com.vn.foodapp.dummyserver;

import java.util.Date;

import com.vn.foodapp.R;
import com.vn.foodapp.data.Feed;
import com.vn.foodapp.data.Post;

public class AppServer {
	
	private static int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	
	public static Feed[] getFeeds(int from, int size) {
		Feed[] feeds = new Feed[size];
		for (int i = 0; i < feeds.length; i++) {
			feeds[i] = newFeed(i % images.length);
		}
		return feeds;
	}
	
	private static Feed newFeed(int i) {
		Post p = new Post();
		p.date = new Date().toString();
		p.decription = "Oscar khÃ´ng chá»‰ lÃ  nÆ¡i tÃ´n vinh cÃ¡c tÃ i nÄƒng Ä‘iá»‡n áº£nh mÃ  cÃ²n lÃ  nÆ¡i thá»ƒ hiá»‡n tÃ­nh cÃ¡ch, vÄƒn hÃ³a cá»§a cÃ¡c ngÃ´i sao qua nhá»¯ng bÃ i phÃ¡t biá»ƒu nháº­n giáº£i.";
		p.title = "NgÆ°á»�i Ä‘Ã n bÃ  cuá»“ng dÃ¢m' bá»‹ cáº¥m chiáº¿u á»Ÿ Thá»• NhÄ© Ká»³";
		p.image = (Integer) images[i];
		return p;
	}
}
