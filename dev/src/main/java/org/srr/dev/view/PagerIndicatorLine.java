package org.srr.dev.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.srr.dev.R;
import org.srr.dev.util.DimensionsUtil;


public class PagerIndicatorLine extends View {

    private Paint paint;
    private Paint paint3;
    private float cx;
    private float cy;
    private float ly;
    private float linehight;
    private float untextselectedSize;
    private float textselectedSize;
    private int unselectedColor;
    private int selectedColor;

    private int currcount = 0;
    private final float letterWidth;
    private float l;
    private String title[] = new String[]{"最新", "热门", "精华"};
    private OnClickChangeListener listener;

    public void setTextTitleArray(String[] title) {
        this.title = title;
        invalidate();
    }

    public PagerIndicatorLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerIndicatorLine);
        selectedColor = a.getColor(R.styleable.PagerIndicatorLine_lineselectedColor, 0xfff34842);
        unselectedColor = a.getColor(R.styleable.PagerIndicatorLine_unlineselectedColor, 0xaafa9b98);
        untextselectedSize = a.getDimension(R.styleable.PagerIndicatorLine_untextselectedColor, dip2px(14));
        textselectedSize = a.getDimension(R.styleable.PagerIndicatorLine_textselectedColor, dip2px(16));
        l = a.getDimension(R.styleable.PagerIndicatorLine_textInterval, dip2px(65));
        cy = a.getDimension(R.styleable.PagerIndicatorLine_textmarginTop, dip2px(29));
        ly = a.getDimension(R.styleable.PagerIndicatorLine_linemarginTop, dip2px(37.5f));
        linehight = a.getDimension(R.styleable.PagerIndicatorLine_lineHight, dip2px(2.5f));
        textselectedSize = a.getDimension(R.styleable.PagerIndicatorLine_textselectedColor, dip2px(16));
        a.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(untextselectedSize);
        paint.setColor(unselectedColor);
        paint.setTypeface(Typeface.DEFAULT);
        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setTextSize(textselectedSize);
        paint3.setColor(selectedColor);
        paint3.setTypeface(Typeface.DEFAULT);
        String s = "最新";
        letterWidth = paint.measureText(s) / 2;

    }

    private float dip2px(float dip) {
        return DimensionsUtil.dip2px(dip, getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float lx;
        if (title.length % 2 == 1) {
            lx = letterWidth * 2 + l;
        } else {
            lx = letterWidth * 2 + l / 2;
        }
        cx = getWidth() / 2 - lx;
        for (int i = 0; i < title.length; i++) {
            if (i == currcount) {
                canvas.drawText(title[i], cx + lx * i, cy, paint3);
//                canvas.drawRect(cx + lx * i - dip2px(16), ly, cx + dip2px(48) + i * lx,
//                        ly + linehight, paint3);  //画线
            } else {
                canvas.drawText(title[i], cx + lx * i, cy, paint);
            }
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float x2 = event.getX();
        float l;
        if (title.length % 2 == 1) {
            l = this.l+ letterWidth * 2;
        } else {
            l = letterWidth * 2 + this.l / 2;
        }
        if (x2 > (cx - 2 * letterWidth) && x2 < (cx + 4 * letterWidth)) {
            currcount = 0;
        } else if (x2 > (cx + l - 2 * letterWidth) && x2 < (cx + l + 4 * letterWidth)) {
            currcount = 1;
        } else if (x2 > (cx + l * 2 - 2 * letterWidth) && x2 < (cx + l * 2 + 4 * letterWidth)) {
            if (title.length % 2 == 0) {
                return true;
            }
            currcount = 2;
        } else {
            return true;
        }


        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    this.listener.OnClick(currcount);
                }
                break;

            default:
                break;
        }
        invalidate();
        return true;
    }

    public interface OnClickChangeListener {
        void OnClick(int currcount);
    }

    public void setCurrCount(int count) {
        this.currcount = count;
        invalidate();
    }

    public void setOnClickChangeListener(OnClickChangeListener listener) {
        this.listener = listener;
    }
}
