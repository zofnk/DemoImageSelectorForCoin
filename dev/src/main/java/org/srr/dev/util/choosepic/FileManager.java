package org.srr.dev.util.choosepic;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 管理app文件路径以及文件操作的类
 *
 * @author Veivei Peng
 */
public class FileManager {

    /**
     * app 的sdcard上的主目录
     */
    public static final String DIR_MAIN = "Zanplusapp";// FileManager.class.getPackage().getName();
    private static final String DIR_IMAGE = "images";
    private static final String DIR_VEDIO = "vedio";
    private static final String DIR_RECORDER = "recorder";
    private static final String DIR_TEMP_FILE = "temp_file";
    private static final String DIR_THUMBNAIL = "thumbnail";
    private static final String DIR_LOG = "log";
    public static final String FILE_CONFIG = "config.properties";


    private static String getRootSdPath() throws FileNotFoundException {
        if (!isSdcardAvalible()) {
            throw new FileNotFoundException("无效Sd卡");
        }
        String path = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

        }
        if (TextUtils.isEmpty(path)) {
            throw new FileNotFoundException("无效Sd卡");
        }
        return path;
    }


    /**
     * 获取app sdcard主目录 e.g sdcard/myapp/
     *
     * @return 主目录名，包括分隔符 “/”
     * @throws FileNotFoundException
     */
    public static String getMainPathInSd() throws FileNotFoundException {
        String rootPath = getRootSdPath();
        String path = rootPath + DIR_MAIN + File.separator;
        checkOrMkdirs(path);
        createNomediaFile(path);
        return path;
    }


    public static String getMainPathInternal(Context context) {
        File internalFile = context.getFilesDir();
        return internalFile.getAbsolutePath() + File.separator;

    }


    /**
     * mounted and writable
     *
     * @return
     */
    public static boolean isSdcardAvalible() {
        boolean avalible = false;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            boolean writable = Environment.getExternalStorageDirectory().canWrite();
            avalible = writable;
        } else {
            avalible = false;
        }
        return avalible;
    }


    /**
     * 图片存放的路径
     * 如果sdcard无效，则放到手机内部存储
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getImagePath(Context context) {
        return getPathInMain(context, DIR_IMAGE);
    }


    /**
     * 录音存放的路径
     * 如果sdcard无效，则放到手机内部存储
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getRecorderPath(Context context) {
        return getPathInMain(context, DIR_RECORDER);
    }

    public static String getTempFilePath(Context context) {
        return getPathInMain(context, DIR_TEMP_FILE);
    }


    /**
     * 视频存放的路径
     * 如果sdcard无效，则放到手机内部存储
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getVedioPath(Context context) {
        return getPathInMain(context, DIR_VEDIO);
    }


    /**
     * 缩略图
     * 如果sdcard无效，则放到手机内部存储
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getThumbnailPath(Context context) {
        return getPathInMain(context, DIR_THUMBNAIL);
    }


    /**
     * log存放的路径
     * 如果sdcard无效，则放到手机内部存储
     *
     * @return
     * @throws FileNotFoundException
     */
    public static String getLogPath(Context context) {
        return getPathInMain(context, DIR_LOG);
    }

    /**
     * 一级目录
     *
     * @param context
     * @return
     */
    private static String getPathInMain(Context context, String firstClsPathName) {
        String path = null;
        try {
            path = getMainPathInSd() + firstClsPathName + File.separator;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            path = getMainPathInternal(context) + firstClsPathName + File.separator;
        }
        checkOrMkdirs(path);
        return path;
    }

    private static void createNomediaFile(String path) {
        File file = new File(path, ".nomedia");
        if (file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void checkOrMkdirs(String path) {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("FileManager:  invalid file path");

        } else {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

    }


    /**
     * app配置文件
     *
     * @param context
     * @return
     */
    public static File getConfigFile(Context context) {
        String mainPath = null;
        try {
            mainPath = getMainPathInSd();
        } catch (FileNotFoundException e1) {
            mainPath = null;
            e1.printStackTrace();
        }
        File retFile = null;
        if (TextUtils.isEmpty(mainPath)) {
            retFile = new File(context.getFilesDir(), FILE_CONFIG);
        } else {
            retFile = new File(mainPath, FILE_CONFIG);
        }
        if (retFile != null && !retFile.exists()) {
            try {
                retFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retFile;
    }


}
