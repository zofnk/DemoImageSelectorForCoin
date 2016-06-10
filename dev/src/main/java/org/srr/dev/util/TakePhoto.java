package org.srr.dev.util;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import junit.framework.Assert;

import org.srr.dev.base.BaseApplication;
import org.srr.dev.util.choosepic.FileManager;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/2/18.
 */
public class TakePhoto {

    private static final String KEY_FILE_URI = "com.getlua.lua.camera_file_uri";
    private static final String TAG = TakePhoto.class.getName();
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static TakePhoto mInstance;
    private Uri mFileUri;
    private String cameraPath;


    private TakePhoto() {

    }

    private static synchronized void initSync() {
        if (mInstance == null) {
            mInstance = new TakePhoto();
        }
    }

    public static TakePhoto getInstance() {
        if (mInstance == null) {
            initSync();
        }
        return mInstance;
    }

    public void initTakePhoto(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_FILE_URI)) {
                mFileUri = Uri
                        .parse(savedInstanceState.getString(KEY_FILE_URI));
            } else {
                mFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a
            }
        } else {
            mFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file
        }
    }


    /**
     * 用于拍照时获取输出的Uri
     */
    public Uri getOutputMediaFileUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(FileManager.getImagePath(BaseApplication.mContext) + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    public String getCameraPath() {
        return cameraPath;
    }

    public void clear() {
        cameraPath = null;
    }


    /**
     * return a file based on the mFileUri that always has the format
     * file://xyz/xyz
     */
    @SuppressWarnings("unused")
    private File getFileFromUri() {
        if (mFileUri != null) {
            try {
                URI uri;
                if (mFileUri.toString().startsWith("file://")) {
                    // normal path
                    uri = URI.create(mFileUri.toString());
                } else {
                    // support path
                    uri = URI.create("file://" + mFileUri.toString());
                }
                File file = new File(uri);
                if (file != null) {
                    if (file.canRead()) {
                        return file;
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri(int type) {
        File file = getOutputMediaFile(type);
        Assert.assertNotNull("getOutputMediaFileUri file", file);
        return Uri.fromFile(file);
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        String state = Environment.getExternalStorageState();
        Assert.assertTrue("external media is mounted",
                TextUtils.equals(state, Environment.MEDIA_MOUNTED));

        File mediaStorageDir;
        if (Build.VERSION.SDK_INT > 8) {
            mediaStorageDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else {
            mediaStorageDir = new File(
                    Environment.getExternalStorageDirectory(), "Pictures");
        }

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdirs() || mediaStorageDir.isDirectory()) {
                Log.v(TAG, "directory is ok");
            } else {
                Assert.fail("failed to create directory");
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            Assert.fail("Invalid media type");
            return null;
        }
        Assert.assertNotNull("media file is not null", mediaFile);
        Log.v(TAG, "will store file at " + mediaFile.toString());
        return mediaFile;
    }

}
