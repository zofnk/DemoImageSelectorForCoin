package org.srr.dev.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.srr.dev.util.HelperUtils;


/**
 * Function: 网络变化监听者
 * 
 * @author Xiang DateTime 2011-12-14 下午04:56:59
 */

public class NetWorkChangeReceiver extends BroadcastReceiver {

	public static final String intentFilter = "android.net.conn.CONNECTIVITY_CHANGE";

	@Override
	public void onReceive(Context context, Intent intent) {

		HelperUtils.checkNetworkStatus(context);

	}



}
