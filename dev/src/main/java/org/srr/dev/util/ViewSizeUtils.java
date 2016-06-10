package org.srr.dev.util;

import android.view.View;
import android.view.ViewGroup;

public class ViewSizeUtils {

    /**
     * @param v 控件
     * @param x 宽  x小于0时 不改变
     * @param y 高  y小于0时 不改变
     */

    public static void setSize(View v, int x, int y) {
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        if (x > 0) {
            lp.width = x;
        }
        if (y > 0) {
            lp.height = y;
        }
        v.setLayoutParams(lp);
    }
}
