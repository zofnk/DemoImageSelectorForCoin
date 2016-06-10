package org.srr.dev.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.srr.dev.base.BaseApplication;
import org.srr.dev.R;

public class IML {

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void load(Context context , ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.iml_ic_stub)
                .into(imageView);
    }

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadIcon(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(BaseApplication.mContext)
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.iml_ic_stub)
                .into(imageView);
    }
    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadBanner(ImageView imageView, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (imageView == null) {
            return;
        }
        Glide.with(BaseApplication.mContext)
                .load(url)
                .dontAnimate()
                .placeholder(R.drawable.iml_ic_stub_banner)
                .into(imageView);
    }


    public static void clear() {

    }

}
