package org.srr.dev.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.Build;

import org.srr.dev.base.Constants;


public class HelperUtils {
	
	/**
	 * 返回网络状态
	 * 
	 * @return 1为成功WiFi已连接，2为cmnet，3为cmwap，4为ctwap， -1为网络未连接
	 */
	public static int checkNetworkStatus(Context context) {
		ConnectivityManager mConnectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);// 上下文连接服务
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();// 给定网络接口的状态类型
		if (info == null || !info.isAvailable()) {// 无网络判断
			return (Constants.CURRENT_NETWORK_STATE_TYPE = Constants.NETWORK_STATE_IDLE);
		}
		String typeName = info.getTypeName();
		if (typeName.equals("WIFI")) {
			return (Constants.CURRENT_NETWORK_STATE_TYPE = Constants.NETWORK_STATE_WIFI);
		}
		String extraName = info.getExtraInfo();
		if (extraName == null || extraName.trim().length() == 0) {
			String proxyHost = null;
			if (Build.VERSION.SDK_INT >= 13) {
				proxyHost = System.getProperties()
						.getProperty("http.proxyHost");
			} else {
				proxyHost = Proxy.getHost(context);
			}
			return (Constants.CURRENT_NETWORK_STATE_TYPE = (proxyHost == null ? Constants.NETWORK_STATE_CMNET
					: Constants.NETWORK_STATE_CMWAP));
		}

		if (extraName.equals("cmnet") || extraName.equals("3gnet")
				|| extraName.equals("uninet") || extraName.equals("ctnet")
				|| extraName.equals("ctnet:CDMA") || extraName.equals("CTC")) {
			return (Constants.CURRENT_NETWORK_STATE_TYPE = Constants.NETWORK_STATE_CMNET);
		} else if (extraName.equals("cmwap") || extraName.equals("3gwap")
				|| extraName.equals("uniwap")) {
			return (Constants.CURRENT_NETWORK_STATE_TYPE = Constants.NETWORK_STATE_CMWAP);
		} else if ("ctwap:CDMA".equals(extraName) || extraName.equals("ctwap")
				|| extraName.equals("#777")) {
			return (Constants.CURRENT_NETWORK_STATE_TYPE = Constants.NETWORK_STATE_CTWAP);
		}

		String proxyHost = null;
		if (Build.VERSION.SDK_INT >= 13) {
			proxyHost = System.getProperties().getProperty("http.proxyHost");
		} else {
			proxyHost = Proxy.getHost(context);
		}
		return (Constants.CURRENT_NETWORK_STATE_TYPE = (proxyHost == null ? Constants.NETWORK_STATE_CMNET
				: Constants.NETWORK_STATE_CMWAP));
	}

}
