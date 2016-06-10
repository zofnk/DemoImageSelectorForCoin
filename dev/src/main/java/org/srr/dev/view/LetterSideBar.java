package org.srr.dev.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LetterSideBar extends View{

	private static final int NUM= 27;
	private Paint paint;
	private OnLetterChangedListener listener;
	private float offset=25;
	private boolean isPressed;
	private char selectedChar;
	
	public void setOnLetterChangedListener(
			OnLetterChangedListener listener) {
		this.listener=listener;
		
	}
	
	public interface OnLetterChangedListener {
		void onLetterChanged(String letter);
		void onActionUp();
	}
	
	public LetterSideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(20);
		paint.setColor(Color.RED);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float x2 = event.getX();
		float y2 = event.getY();
		int index=(int) (y2/getHeight()*NUM);
		selectedChar = (char) ('A'+index-1);
		if (index==0) {
			selectedChar='#';
		}
		String letter=String.valueOf(selectedChar);
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isPressed=true;
			this.listener.onLetterChanged(letter);
			break;
		case MotionEvent.ACTION_MOVE:
			this.listener.onLetterChanged(letter);
			break;
		case MotionEvent.ACTION_UP:
			isPressed=false;
			this.listener.onActionUp();
			break;

		default:
			break;
		}
		invalidate();
		return true;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (isPressed) {
			canvas.drawColor(0x40000000);
		}
		char ch='#';
		for (int i = 0; i < NUM; i++) {
			if (i!=0) {
				ch=(char) ('A'+i-1);
			}
			if (ch==selectedChar&&isPressed) {
				paint.setColor(Color.BLUE);
			}else {
				paint.setColor(Color.RED);
			}
			String letter = String.valueOf(ch);
			float letterWidth=paint.measureText(letter);
			float cx=getWidth()/2-letterWidth/2;
			float hight=getHeight()/NUM;
			float cy=hight/2+letterWidth/2;
			canvas.drawText(letter, cx, cy+i*hight, paint);
		}
	}



}
