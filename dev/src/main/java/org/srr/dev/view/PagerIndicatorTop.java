package org.srr.dev.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import org.srr.dev.R;


public class PagerIndicatorTop extends View {

    private Paint paint;
    private Paint paint3;
    private float cx;
    private float cy;
    private int unselectedColor;
    private int selectedColor;
    private int currcount = 0;
    private final float letterWidth;
    private String title[]=new String []{"达人榜","土豪榜"};
    private OnClickChangeListener listener;

    public PagerIndicatorTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
        selectedColor = a.getColor(R.styleable.PagerIndicatorLine_lineselectedColor, 0xfff34842);
        unselectedColor = a.getColor(R.styleable.PagerIndicatorLine_unlineselectedColor, 0xaaf34842);
        a.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30);
        paint.setColor(unselectedColor);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setTextSize(33);
        paint3.setColor(selectedColor);
        String s = "最新";
        letterWidth = paint.measureText(s) / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {

        cx = (float) (getWidth() / 2 - ((title.length - 1) * 3 + 1) * letterWidth);
        cy = getHeight() / 2;

        for (int i = 0; i < title.length; i++) {
            if (i == currcount) {
                canvas.drawText(title[i], cx + letterWidth * 6 * i, cy-5, paint3);
                canvas.drawRect(cx + letterWidth * 6 * i - letterWidth, cy + letterWidth / 2, cx + letterWidth * 6 * i + 3 * letterWidth+10,
                        cy + letterWidth / 2 + 5, paint3);
            } else {
                canvas.drawText(title[i], cx + letterWidth * 6 * i, cy, paint);
            }
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x2 = event.getX();

        if (x2>(cx-2*letterWidth)&&x2<(cx+4*letterWidth)){
            currcount=0;
        }else if(x2>(cx+4*letterWidth)&&x2<(cx+10*letterWidth)){
            currcount=1;
        }else if(x2>(cx+10*letterWidth)&&x2<(cx+16*letterWidth)){
            currcount=2;
        }else{
            return true;
        }


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                this.listener.OnClick(currcount);
                break;

            default:
                break;
        }
        invalidate();
        return true;
    }

    public interface OnClickChangeListener{
        void OnClick(int currcount);
    }
    public void setOnClickChangeListener(OnClickChangeListener listener){
        this.listener=listener;
    }
}
