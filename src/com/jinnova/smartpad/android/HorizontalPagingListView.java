package com.jinnova.smartpad.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class HorizontalPagingListView extends ListView {
	
	private float xDistance, yDistance, lastX, lastY;

	public HorizontalPagingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public HorizontalPagingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalPagingListView(Context context) {
		super(context);
	}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            xDistance = yDistance = 0f;
            lastX = ev.getX();
            lastY = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float curX = ev.getX();
            final float curY = ev.getY();
            xDistance += Math.abs(curX - lastX);
            yDistance += Math.abs(curY - lastY);
            lastX = curX;
            lastY = curY;
            if (xDistance > yDistance)
                return false;
        }

        return super.onInterceptTouchEvent(ev);

    }

}
