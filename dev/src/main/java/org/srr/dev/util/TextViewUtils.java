package org.srr.dev.util;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import org.srr.dev.base.BaseApplication;

public class TextViewUtils {

    public static void LoadInTextView(String str, TextView view) {
        if (str == null || "\"\"".equals(str) || "null".equals(str) || str.length() == 0) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(str);
        }
    }

    public static void LoadGoTextView(String str, TextView view) {
        if (str == null || "\"\"".equals(str) || "null".equals(str) || str.length() == 0) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
            view.setText(str);
        }
    }

    public static void setColorTextView(String text, @ColorRes int id, int start, int end, TextView textView) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(BaseApplication.mContext.getResources().getColor(id));
        builder.setSpan(redSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }

    public static void setColorTextViewInt(@StringRes int text, int content, @ColorRes int id, TextView textView) {
        String str = BaseApplication.mContext.getString(text);
        final int i = str.indexOf("%d");
        str = String.format(str, content);
        String content1 = content + "";
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(BaseApplication.mContext.getResources().getColor(id));
        builder.setSpan(redSpan, i, i + content1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(builder);
    }
}
