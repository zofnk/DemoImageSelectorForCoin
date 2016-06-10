package org.srr.dev.util;

import android.os.Environment;

/**
 * Created by Administrator on 2015/12/14.
 */
public class PathUtils {
    public static String getDownload() {
        return Environment.getExternalStorageDirectory() + "/Download/";
    }
}
