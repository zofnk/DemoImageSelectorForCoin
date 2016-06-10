package org.srr.dev.util;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/1/5.
 */
public class KeyboardUtils {

    public static void collapseKeyboard(FragmentActivity context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
