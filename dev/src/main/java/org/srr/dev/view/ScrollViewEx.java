package org.srr.dev.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

public class ScrollViewEx extends ScrollView{
	OnScrollChangedListener listener;
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (listener!=null) {
			listener.onChanged(t);
		}
	}

	public ScrollViewEx(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public ScrollViewEx(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}

	public ScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
	}


	public void setOnScrollChangedListener(OnScrollChangedListener listener) {
		this.listener=listener;
	}
	public interface OnScrollChangedListener{
		void onChanged(int y);
	}


	private int downX;
	private int downY;
	private int mTouchSlop;



	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int action = e.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				downX = (int) e.getRawX();
				downY = (int) e.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int moveY = (int) e.getRawY();
				if (Math.abs(moveY - downY) > mTouchSlop) {
					return true;
				}
		}
		return super.onInterceptTouchEvent(e);
	}

}
