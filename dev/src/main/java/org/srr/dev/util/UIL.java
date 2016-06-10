

package org.srr.dev.util;

public class UIL {
//
//    private static DisplayImageOptions options;
//
//    //    private static PauseOnScrollListener mPauseOnScrollListener;  ListView 图片加载优化
//    private static PauseOnRVScrollListener mPauseOnRVScrollListener; //Recyclerview图片加载优化
//
//    public static void init(Context context) {
//        initImageLoader(context);
//        options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisk(true).considerExifParams(true)
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                // .showImageOnLoading(R.drawable.app_icon)
//                .showImageOnLoading(R.mipmap.uil_ic_stub)
//                .showImageForEmptyUri(R.mipmap.uil_ic_empty)
//                .showImageOnFail(R.mipmap.uil_ic_error).cacheInMemory(true)
//                .cacheOnDisk(true).considerExifParams(true)
//                .build();
//        ImageLoader imageLoader = ImageLoader.getInstance();
//
////        mPauseOnScrollListener = new PauseOnScrollListener(imageLoader, true,
////                true);
//        mPauseOnRVScrollListener = new PauseOnRVScrollListener(imageLoader, true,
//                true);
//
//    }
//
//    public static void initImageLoader(Context context) {
//        // This configuration tuning is custom. You can tune every option, you
//        // may tune some of them,
//        // or you can create default configuration by
//        // ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .diskCacheSize(50 * 1024 * 1024)
//                // 50 Mb
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .writeDebugLogs() // Remove for release app
//                .build();
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config);
//    }
//
//    /**
//     * 加载图片
//     *
//     * @param imageView
//     * @param defaultId
//     * @param url
//     */
//    public static void load(ImageView imageView, int defaultId, String url) {
//        if (android.text.TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (imageView == null) {
//            return;
//        }
//        ImageLoader.getInstance().displayImage(url, imageView, options);
//    }
//
//    /**
//     * 加载图片
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void load(ImageView imageView, String url) {
//        if (android.text.TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (imageView == null) {
//            return;
//        }
//        ImageLoader.getInstance().displayImage(url, imageView, options);
//    }
//
//    public static void load(ImageView imageView, String url, int type) {
//        if (android.text.TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (imageView == null) {
//            return;
//        }
//        // DisplayImageOptions options =
//        // ImageOptionFactory.createImageOption(type);
//        ImageLoader.getInstance().displayImage(url, imageView, options);
//    }
//
//    /**
//     * 加载图片
//     *
//     * @param imageView
//     * @param defaultId
//     * @param url
//     */
//    public static void load(ImageView imageView, int defaultId, String url,
//                            Object object) {
//        if (android.text.TextUtils.isEmpty(url)) {
//            return;
//        }
//        if (imageView == null) {
//            return;
//        }
//        ImageLoader.getInstance().displayImage(url, imageView, options);
//    }
//
//    /**
//     * 同步加载图片
//     *
//     * @param url
//     * @return 位图资源
//     */
//    public static Bitmap loadSync(String url) {
//        if (android.text.TextUtils.isEmpty(url)) {
//            return null;
//        }
//        Bitmap bm = ImageLoader.getInstance().loadImageSync(url, options);
//        return bm;
//    }
//
//    // /**
//    // * 自定义图片监听回调的加载图片
//    // * @param defaultId
//    // * @param url
//    // * @param imageListener
//    // */
//    // public static void load(int defaultId, String url,ImageListener
//    // imageListener){
//    // HttpRequestManager.getInstance().getImageLoader().get(url,
//    // imageListener);
//    // }
//
//    /**
//     * 是否已有图片缓存
//     *
//     * @param key 图片缓存的键，一般为url
//     * @return
//     */
//    public static boolean hasImageCache(String key) {
//        return ImageLoader.getInstance().getMemoryCache().keys().contains(key);
//    }
//
//    /**
//     * 获取缓存对象
//     *
//     * @return
//     */
//    public static MemoryCache getMemoryCache() {
//        return ImageLoader.getInstance().getMemoryCache();
//    }
//
//    public static PauseOnRVScrollListener getScrollListener() {
//        return mPauseOnRVScrollListener;
//    }
//
//    public static DisplayImageOptions getOptions() {
//        return options;
//    }
//
//    public static void setOptions(DisplayImageOptions options) {
//        UIL.options = options;
//    }
//
//    public static void clear() {
//
//    }
//
//
//    //Recyclerview 的图片加载优化
//    public static class PauseOnRVScrollListener extends RecyclerView.OnScrollListener {
//        private ImageLoader imageLoader;
//        private final boolean pauseOnScroll;
//        private final boolean pauseOnFling;
//        private final RecyclerView.OnScrollListener externalListener;
//
//        public PauseOnRVScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling) {
//            this(imageLoader, pauseOnScroll, pauseOnFling, (RecyclerView.OnScrollListener) null);
//        }
//
//        public PauseOnRVScrollListener(ImageLoader imageLoader, boolean pauseOnScroll, boolean pauseOnFling, RecyclerView.OnScrollListener customListener) {
//            this.imageLoader = imageLoader;
//            this.pauseOnScroll = pauseOnScroll;
//            this.pauseOnFling = pauseOnFling;
//            this.externalListener = customListener;
//        }
//
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            switch (newState) {
//                case 0:
//                    this.imageLoader.resume();
//                    break;
//                case 1:
//                    if (this.pauseOnScroll) {
//                        this.imageLoader.pause();
//                    }
//                    break;
//                case 2:
//                    if (this.pauseOnFling) {
//                        this.imageLoader.pause();
//                    }
//            }
//
//            if (this.externalListener != null) {
//                this.externalListener.onScrollStateChanged(recyclerView, newState);
//            }
//
//        }
//
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            if (this.externalListener != null) {
//                this.externalListener.onScrolled(recyclerView, dx, dy);
//            }
//
//        }
//    }
}
