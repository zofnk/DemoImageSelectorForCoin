package org.srr.dev.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoUtils {


    public static Bitmap rotateBitmapByDegree(Bitmap bm, String path) {
        Bitmap returnBm = null;
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (degree == 0) {
            return bm;
        }
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            Log.e("PhotoUtils","OutOfMemoryError");
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    public static void rotateBitmapByDegree(String path) {
        Bitmap returnBm = null;
        int degree = 0;
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (degree == 0) {
            return;
        }
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }

        BufferedOutputStream bos = null;
        File file = new File(path);//将要保存图片的路径
        if (file.exists()) {
            file.delete();
        }
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            returnBm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把相册或相机拍下照片的本地路径传入，即可压缩并且返回压缩后的路径
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    public static List<String> saveBitmapFileList(List<String> path, String files, int width, int height) {
        setDownloadPath(files);
        List<String> paths = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            Bitmap bitmap = decodeSampledBitmapFromPath(path.get(i), width, height);
            String save_path = files + "img" + i + ".jpg";
            paths.add(save_path);
            File file = new File(save_path);//将要保存图片的路径
            bitmap = rotateBitmapByDegree(bitmap, path.get(i));  //对图片进行旋转
            BufferedOutputStream bos = null;
            try {

                bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (bos != null) {
                    try {
                        bos.flush();
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paths;
    }

    /**
     * 把相册或相机拍下照片的本地路径传入，即可压缩并且返回压缩后的路径
     * @param path   图片路径
     * @param files  存储文件夹
     * @param width  压缩宽
     * @param height 高
     * @return
     */
    public static String saveBitmapFile(String path, String files, int width, int height) {
        setDownloadPath(files);
        Bitmap bitmap = decodeSampledBitmapFromPath(path, width, height);
        String save_path = files + "img_" + System.currentTimeMillis() + ".jpg";
        File file = new File(save_path);//将要保存图片的路径
        bitmap = rotateBitmapByDegree(bitmap, path);  //对图片进行旋转
        BufferedOutputStream bos = null;
        try {

            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return save_path;
    }

    /**
     * 判断是否存在这个路径，不存在的话创建
     */
    public static void setDownloadPath(String files) {
        File file = new File(files);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 压缩图片  长宽选最大的按比例缩放
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {
        // 获得图片宽高，并不把图片加载到内存中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);

        //再次解析图片
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 得到缩放的大小
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int hiegh = options.outHeight;

        int inSampleSize = 1;

        if (width > reqWidth || hiegh > reqHeight) {
            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(hiegh * 1.0f / reqHeight);
            inSampleSize = Math.max(widthRadio, heightRadio);
        }

        return inSampleSize;
    }

}
