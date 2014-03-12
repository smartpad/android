package com.vn.foodapp.data;

import java.util.Date;

import com.vn.foodapp.R;

public class FeedManager {
	
	public static FeedManager instance;
	
	private int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	
	public static void initialize() {
		instance = new FeedManager();
	}

	public int getFeedCount() {
		return images.length;
	}
	
	public Feed getFeed(int pos) {
		//return images[pos];
		Post p = new Post();
		p.date = new Date().toString();
		p.decription = "Oscar không chỉ là nơi tôn vinh các tài năng điện ảnh mà còn là nơi thể hiện tính cách, văn hóa của các ngôi sao qua những bài phát biểu nhận giải.";
		p.title = "Người đàn bà cuồng dâm' bị cấm chiếu ở Thổ Nhĩ Kỳ";
		p.image = (Integer) images[pos];
		return p;
	}
}
