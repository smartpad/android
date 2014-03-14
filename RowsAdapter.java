package com.vn.foodapp;

import java.util.Date;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RowsAdapter extends BaseAdapter {

	private LayoutInflater layoutInflator;
	private int[] images = {R.drawable.anh1, R.drawable.anh2, R.drawable.anh3, R.drawable.anh4
			, R.drawable.anh5, R.drawable.anh6, R.drawable.anh7, R.drawable.anh8
			, R.drawable.anh9, R.drawable.anh10};
	public RowsAdapter(LayoutInflater layoutInflator) {
		super();
		this.layoutInflator = layoutInflator;
		
	}
	
	@Override
	public boolean areAllItemsEnabled () {
	  	return true;
	}

	@Override
	public int getCount() {
		return images.length;
	}

	@Override
	public Object getItem(int pos) {
		return images[pos];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		Row row;
		if (view == null) {
			view = layoutInflator.inflate(R.layout.news_row, parent, false);
			row  = new Row();
			row.title = (TextView) view.findViewById(R.id.title);
			row.imageView = (ImageView) view.findViewById(R.id.newsImage);
			row.date = (TextView) view.findViewById(R.id.date);
			row.decription = (TextView) view.findViewById(R.id.decription);
			view.setTag(row);
		} else {
			row = (Row) view.getTag();
		}
		row.date.setText(new Date().toString());
		row.decription.setText("Oscar không chỉ là nơi tôn vinh các tài năng điện ảnh mà còn là nơi thể hiện tính cách, văn hóa của các ngôi sao qua những bài phát biểu nhận giải.");
		row.title.setText("Người đàn bà cuồng dâm' bị cấm chiếu ở Thổ Nhĩ Kỳ");
		row.imageView.setImageResource((Integer) getItem(pos));
		return view;
	}
	
	private class Row {
		ImageView imageView;
		TextView date;
		TextView title;
		TextView decription;
	}
}
