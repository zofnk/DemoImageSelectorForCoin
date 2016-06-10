package org.srr.dev.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import org.srr.dev.R;


public class PagerIndicator extends View{

	private Paint paint;
	private Paint paint2;
	private Paint paint3;
	private float cx;
	private float cy;
	private float radius;
	private int offset;
	private int unselectedColor;
	private int selectedColor;
	private int count=4;

	public PagerIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
		selectedColor=a.getColor(R.styleable.ViewPagerIndicator_selectedColor,Color.GRAY );
		unselectedColor=a.getColor(R.styleable.ViewPagerIndicator_unselectedColor,Color.RED );
		a.getDimension(R.styleable.ViewPagerIndicator_strokeWidth, 20);
		radius=a.getDimension(R.styleable.ViewPagerIndicator_radius, 5);
		a.recycle();
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setTextSize(50);
		paint.setColor(unselectedColor);
		paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint2.setStyle(Style.STROKE);
		paint2.setTextSize(50);
		paint2.setColor(Color.BLACK);
		paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint3.setTextSize(50);
		paint3.setColor(selectedColor);
		
		
	}
	@Override
	protected void onDraw(Canvas canvas) {		
		cx=(float) (getWidth()/2-(count-1)*1.5*radius);
		cy=getHeight()/2;
		
		for (int i = 0; i < count; i++) {
			canvas.drawCircle(cx+radius*3*i, cy, radius, paint);
//			canvas.drawCircle(cx+radius*3*i, cy, radius, paint2);
		}
		canvas.drawCircle(cx+offset, cy, radius, paint3);
	}
	public void move(int position, float arg1) {
		position=position%count;
		if (position==(count-1)&&arg1!=0) {
			return;
		}
		offset=(int) (3*radius*(position+arg1));
		invalidate();
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
