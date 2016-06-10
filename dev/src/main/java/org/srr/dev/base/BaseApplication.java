package org.srr.dev.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import org.srr.dev.nuwa.Nuwa;
import org.srr.dev.util.HelperUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseApplication extends MultiDexApplication {

    public static Context mContext ;
    public static DisplayMetrics dm;
    private NetWorkChangeReceiver netWorkChangeReceiver = null;
    private AudioManager audioManager = null;
    private static Map<String, Activity> hmActivity = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //是否有补丁
        File file = new File(Constants.ERROR_FIX + "patch.jar");
        if (file.exists()) {
            Nuwa.init(this);
            Nuwa.loadPatch(this, Constants.ERROR_FIX + "patch.jar");
        }

        HelperUtils.checkNetworkStatus(mContext);
        ExceptionHandler exhandler = ExceptionHandler.getInstance();
        exhandler.init(mContext);
        this.getDisplayMetrics();
        this.registerNetWorkChangeReceiver();// 注册网络状态及返回键监听
    }

    /***
     * 注册网络监听者
     */
    private void registerNetWorkChangeReceiver() {
        this.netWorkChangeReceiver = new NetWorkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetWorkChangeReceiver.intentFilter);
        this.registerReceiver(netWorkChangeReceiver, filter);
    }

    /***
     * 开启系统触摸提示音
     */
    @SuppressWarnings("static-access")
    private void startHintVoice() {
        Settings.System.putInt(mContext.getContentResolver(),
                Settings.System.SOUND_EFFECTS_ENABLED, 1);
        audioManager = (AudioManager) mContext
                .getSystemService(mContext.AUDIO_SERVICE);
        audioManager.loadSoundEffects();
    }

    /***
     * 获取屏幕度量
     */
    private void getDisplayMetrics() {
        dm = new DisplayMetrics();
        ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
    }

    /***
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */

    public static void addDestoryActivity(Activity activity, String activityName) {
        hmActivity.put(activityName, activity);
    }

    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {

        Set<String> keySet = hmActivity.keySet();
        for (String key : keySet) {
            if (key.contains(activityName)) {
                final Activity activity = hmActivity.get(key);
                hmActivity.remove(key);
                activity.finish();
                break;
            }
        }
    }

    /**
     * 销毁类名多个Activity
     */
    public static void destoryActivitys(String activityName) {

        Set<String> keySet = hmActivity.keySet();
        List<String> acName = new ArrayList<>();
        for (String key : keySet) {
            if (key.contains(activityName)) {
                acName.add(key);
            }
        }

        for (String name : acName) {
            final Activity activity = hmActivity.get(name);
            hmActivity.remove(name);
            activity.finish();
        }
        acName.clear();
    }

    /**
     * 去除指定Activity的引用
     */
    public static void removeActivity(String activityName) {
        Set<String> keySet = hmActivity.keySet();
        for (String key : keySet) {
            if (key.contains(activityName)) {
                hmActivity.remove(key);
                break;
            }
        }
    }

    /**
     * 销毁全部activity
     */
    public static void destoryAllActivity() {
        Set<String> keySet = hmActivity.keySet();
        List<String> acName = new ArrayList<>();
        String navi=null;
        for (String key : keySet) {
            if (key.contains("NaviActivity")){
                navi=key;
            }else{
                acName.add(key);
            }
        }
        for (String name : acName) {
            final Activity activity = hmActivity.get(name);
            hmActivity.remove(name);
            activity.finish();
        }

        final Activity naviActivity = hmActivity.get(navi);
        hmActivity.remove(navi);
        naviActivity.finish();

        hmActivity.clear();
        acName.clear();
//        android.os.Process.killProcess(android.os.Process.myPid());   //自杀进程
    }

    /**
     * 判断当前应用是否处于前台
     */
    public static boolean isRunningForeground() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (currentPackageName != null && currentPackageName.equals(mContext.getPackageName())) {
            return true;
        }
        return false;
    }

    /**
     * 更新通知
     */
    public static void notifyMessage() {

    }

//    public String inject(String libPath) {
//
//        boolean hasBaseDexClassLoader = true;
//
//        try {
//
//            Class.forName("dalvik.system.BaseDexClassLoader");
//
//        } catch (ClassNotFoundException e) {
//
//            hasBaseDexClassLoader = false;
//
//        }
//
//        if (hasBaseDexClassLoader) {
//
//            PathClassLoader pathClassLoader = (PathClassLoader) this.getClassLoader();
//
//            DexClassLoader dexClassLoader = new DexClassLoader(libPath, this.getDir("dex", 0).getAbsolutePath(), libPath, this.getClassLoader());
//
//            try {
//
//                Object dexElements = combineArray(getDexElements(getPathList(pathClassLoader)), getDexElements(getPathList(dexClassLoader)));
//
//                Object pathList = getPathList(pathClassLoader);
//
//                setField(pathList, pathList.getClass(), "dexElements", dexElements);
//
//                return "SUCCESS";
//
//            } catch (Throwable e) {
//
//                e.printStackTrace();
//
//                return android.util.Log.getStackTraceString(e);
//
//            }
//
//        }
//
//        return "SUCCESS";
//
//    }
}
