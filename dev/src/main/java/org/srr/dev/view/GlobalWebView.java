package org.srr.dev.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;

/**
 * WebView控件
 * @author yangwq
 * contact :57890940@qq.com
 * @date 2014-10-15
 */
public class GlobalWebView extends WebView {
	private WebSettings webSettings;

	public GlobalWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		this.setVerticalScrollBarEnabled(false);
		initWebViewParameter();
	}
	
	
	
	public GlobalWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		this.setVerticalScrollBarEnabled(false);
		initWebViewParameter();
	}



	public GlobalWebView(Context context) {
		super(context);
		this.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		this.setVerticalScrollBarEnabled(false);
		initWebViewParameter();
	}



	public void initWebViewParameter()
	{
		webSettings = this.getSettings();
		webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSupportZoom(true); //可以缩放
        webSettings.setDefaultZoom(ZoomDensity.MEDIUM);//默认缩放模式
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL); //适应屏幕
        webSettings.setBlockNetworkImage(false);//设置为有图模�?
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
	}
	
	public void changeUserAgentStr(String uaStr)
	{
		String str = this.getSettings().getUserAgentString();
		webSettings.setUserAgentString(str+uaStr);	
	}
	
	public void chageLoadimgMode(boolean isLoadimg)
	{
		webSettings.setBlockNetworkImage(isLoadimg);
	}
	
	public void release()
	{
		webSettings = null;
		this.removeAllViews();
	}

}
