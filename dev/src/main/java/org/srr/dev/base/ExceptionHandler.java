package org.srr.dev.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;

import org.srr.dev.util.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ExceptionHandler implements UncaughtExceptionHandler {

    private String TAG = "ExceptionHandler";
    private Context context;
    private UncaughtExceptionHandler mDefaultHandler;
    private static ExceptionHandler instance = new ExceptionHandler();
    private String errorPath = Constants.ERROR_PATH;
    private Map<String, String> infos = new HashMap<String, String>();
    //格式化日期,作为错误日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    /**
     * 保证只有一个实例
     */
    private ExceptionHandler() {
    }

    public static ExceptionHandler getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex)) {
            uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error:", e);
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        /**使用Toast显示异常*/
        new Thread() {
            public void run() {
                Looper.prepare();
                Log.e(TAG, "", ex);
//				ToastUtil.show_long(context, "很抱歉,程序出现异常,即将退出");
                Looper.loop();
            }

            ;
        }.start();
        /**收集设备信息*/
        collectDeviceInfo();
        /**保存错误日志到文件*/
        saveErrorInfotoFile(ex);
        return true;
    }

    private void collectDeviceInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                infos.put("versionName", pi.versionName == null ? "null" : pi.versionName);
                infos.put("versionCode", "" + pi.versionCode);
            }
            infos.put("sdk_int", "" + Build.VERSION.SDK_INT);
            infos.put("productor", "" + Build.MANUFACTURER);
            infos.put("model", "" + Build.MODEL);
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect packinfo", e);
        }
    }

    private String saveErrorInfotoFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        sb.append("..." + "\n");
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String time = formatter.format(new Date());
            String fileName = "excption_" + time + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory() + errorPath;
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                } else {
                    File[] files = dir.listFiles();
                    for (File f : files) {
//                		f.delete();
                    }
                }
                FileOutputStream fos = new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }

    public void setErrorPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            errorPath = path;
        }
    }
}
