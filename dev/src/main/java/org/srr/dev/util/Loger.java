package org.srr.dev.util;

import android.util.Log;

/**
 * 打印日志控制
 * @author WeiQi
 * @email 57890940@qq.com
 * @date 2014-9-20
 */

public class Loger {

	private final static int J = 6;

	public final static int VERBOSE = 6;

	public final static int DEBUG = 5;

	public final static int INFO = 4;

	public final static int WARN = 3;

	public final static int ERROR = 2;

	public final static int ASSERT = 1;

	public static int v(String tag, String msg) {
		if (J >= VERBOSE) {
			return Log.v(tag, msg);
		}
		return 0;
	}

	public static int d(String tag, String msg) {
		if (J >= DEBUG) {
			return Log.d(tag, msg);
		}
		return 0;
	}

	public static int i(String tag, String msg) {
		if (J >= INFO) {
			return Log.i(tag, msg);
		}
		return 0;
	}

	public static int w(String tag, String msg) {
		if (J >= WARN) {
			return Log.w(tag, msg);
		}
		return 0;
	}

	public static int e(String tag, String msg) {
		if (J >= ERROR) {
			return Log.e(tag, msg);
		}
		return 0;
	}

	public static int e(String tag, String msg, Throwable tr) {
		if (J >= ERROR) {
			return Log.e(tag, msg, tr);
		}
		return 0;
	}
}
