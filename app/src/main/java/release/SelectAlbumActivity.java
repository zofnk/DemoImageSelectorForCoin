package release;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import org.srr.dev.adapter.DividerGridItemDecoration;
import org.srr.dev.adapter.RecyclerViewAdapter;
import org.srr.dev.base.BaseActivity;
import org.srr.dev.util.DimensionsUtil;
import org.srr.dev.util.IML;
import org.srr.dev.util.choosepic.FileManager;
import org.srr.dev.util.choosepic.ImageFloder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sample.binvshe.com.demo_imageselector.R;

public class SelectAlbumActivity extends BaseActivity {


    HashMap<Integer, Boolean> check_list = new HashMap();
    private ImageAdapter img_adapter;
    private final static int SCAN_OK = 1;


    /**
     * 最多选择图片的个数
     */
    public static int MAX_NUM = 9;
    public int check_num = 0;
    public static final int TAKE_PICTURE = 520;
    public static final int PREVIEW = 205;

    public static final String INTENT_MAX_NUM = "intent_max_num";
    public static final String RELEASE_IMGS_NUM = "intent_img_num";
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashMap<String, Integer> tmpDir = new HashMap<>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<>();

    private ContentResolver mContentResolver;

    /**
     * 已选择的图片
     */
    private ArrayList<String> selectedPicture = new ArrayList<String>();
    private String cameraPath = null;
    private ImageFloder imageAll = null;
    private RecyclerView pic_grid;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    img_adapter = new ImageAdapter(imageAll);
                    pic_grid.setAdapter(img_adapter);
                    break;
            }
        }

    };
    private TextView select_num;
    private CheckBox album_toggle;
    private AlbumAdapter album_adapter;
    private Context mContext = this;

    @Override
    protected void initGetIntent() {
        MAX_NUM = getIntent().getIntExtra(INTENT_MAX_NUM, 3);
        mContentResolver = getContentResolver();
        imageAll = new ImageFloder();
        imageAll.setDir("/所有图片");
        check_num = getIntent().getIntExtra(RELEASE_IMGS_NUM, 0);
        getThumbnail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_select_album;
    }

    @Override
    public void initView() {
        select_num = (TextView) findViewById(R.id.ac_select_album_select_num);
        select_num.setText(check_num + "/" + MAX_NUM);
        pic_grid = (RecyclerView) findViewById(R.id.ac_select_album_pic_list);
        album_toggle = (CheckBox) findViewById(R.id.ac_select_album_toggle);
        findViewById(R.id.ac_select_album_title_preview).setOnClickListener(this);
        findViewById(R.id.ac_select_album_cancel).setOnClickListener(this);
        findViewById(R.id.ac_select_album_pic_enter).setOnClickListener(this);
        album_toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPopupWindow(album_toggle);
                    backgroundAlpha(0.5f);
                }
            }
        });
        pic_grid.setLayoutManager(new GridLayoutManager(this, 3));
        pic_grid.setItemAnimator(new DefaultItemAnimator());
        pic_grid.addItemDecoration(new DividerGridItemDecoration(getResources().getDrawable(R.drawable.grid_recyclerview_gray)));
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_select_album_cancel:
                finish();
                break;
            case R.id.ac_select_album_pic_enter:
                Intent intent = getIntent();
                intent.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
                setResult(RESULT_OK, intent);
                break;
            case R.id.ac_select_album_title_preview:
                Intent start_intent = new Intent(SelectAlbumActivity.this, PreviewActivity.class);
                start_intent.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
                startActivityForResult(start_intent, PREVIEW);
                break;
            default:
                break;
        }
    }

    @Override
    public void refreshData() {

    }


    /**
     * 图片列表适配器
     */
    class ImageAdapter extends RecyclerViewAdapter<ImageAdapter.ViewHolder> {
        private List<String> images = new ArrayList<>();

        public ImageAdapter(ImageFloder imageFloder) {
            super();
            images.addAll(imageFloder.images);
            check_list.clear();
            selectedPicture.clear();
            for (int i = 0; i < images.size(); i++) {
                check_list.put(i, false);
            }
//            check_num=0;
//            select_num.setText(check_num + "/" + MAX_NUM);
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_img_album_ac, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.img = (ImageView) v.findViewById(R.id.item_img_ac_album);
            holder.cb = (ImageView) v.findViewById(R.id.item_cb_ac_album);

            return holder;
        }

        public void setImagesList(ImageFloder imageFloder) {
            images.clear();
            check_list.clear();
            selectedPicture.clear();
            images.addAll(imageFloder.images);
            for (int i = 0; i < images.size(); i++) {
                check_list.put(i, false);
            }
            check_num = 0;
            select_num.setText(check_num + "/" + MAX_NUM);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder viewHolder, final int i) {

            final ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) viewHolder;
            View.OnClickListener check_listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_list.get(i)) {
                        check_num--;
                        holder.cb.setImageResource(R.drawable.select_album_album_no);
                        select_num.setText(check_num + "/" + MAX_NUM);
                        check_list.put(i, !check_list.get(i));
                        selectedPicture.remove(images.get(i));
                    } else {
                        if (check_num + 1 > MAX_NUM) {
                            Toast.makeText(SelectAlbumActivity.this, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
                        } else {
                            check_num++;
                            holder.cb.setImageResource(R.drawable.select_album_album_yes);
                            select_num.setText(check_num + "/" + MAX_NUM);
                            check_list.put(i, !check_list.get(i));
                            selectedPicture.add(images.get(i));
                        }
                    }
                }
            };

            if (check_list.get(i)) {
                holder.cb.setImageResource(R.drawable.select_album_album_yes);
            } else {
                holder.cb.setImageResource(R.drawable.select_album_album_no);
            }


            String path = images.get(i);

            IML.load(getBaseContext(), holder.img, "file://" + path);

            holder.img.setOnClickListener(check_listener);

            holder.cb.setOnClickListener(check_listener);


        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img, cb;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    /**
     * 相册列表适配器
     */
    class AlbumAdapter extends RecyclerViewAdapter<AlbumAdapter.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_album_text, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.album = (TextView) v.findViewById(R.id.item_album_text);
            holder.num = (TextView) v.findViewById(R.id.item_album_num);
            return holder;
        }

        @Override
        public int getItemCount() {
            return mDirPaths.size() + 1;
        }

        @Override
        public void onBindHolder(RecyclerView.ViewHolder viewHolder, final int i) {

            final AlbumAdapter.ViewHolder holder = (AlbumAdapter.ViewHolder) viewHolder;
            if (i == 0) {
                holder.album.setText(imageAll.getName());
                holder.num.setText(imageAll.images.size() + "");
            } else {
                holder.album.setText(mDirPaths.get(i - 1).getName());
                holder.num.setText(mDirPaths.get(i - 1).images.size() + "");
            }

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView album, num;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_selectalbum, null);
        RecyclerView selectalbum_list = (RecyclerView) contentView.findViewById(R.id.rc_pop_selectalbum_list);
        selectalbum_list.setLayoutManager(new LinearLayoutManager(this));

        if (album_adapter == null) {
            album_adapter = new AlbumAdapter();
        }
        selectalbum_list.setAdapter(album_adapter);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                DimensionsUtil.dip2px(200, this), DimensionsUtil.dip2px(200, this), true);

        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            public void onDismiss() {
                backgroundAlpha(1);
                album_toggle.setChecked(false);
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.bg_dialog));
        int hight = DimensionsUtil.dip2px(112, this);
        popupWindow.showAtLocation(view, Gravity.TOP, 0, hight);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(view);

        album_adapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View views, int position) {
                if (position == 0) {
                    img_adapter.setImagesList(imageAll);
                } else {
                    img_adapter.setImagesList(mDirPaths.get(position - 1));
                }
                popupWindow.dismiss();
            }

            @Override
            public boolean onItemLongClick(View views, int position) {
                return false;
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 使用相机拍照
     *
     * @version 1.0
     * @author
     */
    protected void goCamare() {
        if (selectedPicture.size() + 1 > MAX_NUM) {
            Toast.makeText(this, "最多选择" + MAX_NUM + "张", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    /**
     * 用于拍照时获取输出的Uri
     *
     * @return
     * @version 1.0
     * @author zyh
     */
    protected Uri getOutputMediaFileUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(FileManager.getImagePath(this) + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (cameraPath != null) {
            selectedPicture.add(cameraPath);
            Intent data2 = new Intent();
            data2.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
            setResult(RESULT_OK, data2);
        }

        if (requestCode == PREVIEW && data != null) {
            Intent intent = getIntent();
            ArrayList<String> imgs = (ArrayList<String>) data.getSerializableExtra(SelectAlbumActivity.INTENT_SELECTED_PICTURE);
            intent.putExtra(INTENT_SELECTED_PICTURE, imgs);
            setResult(RESULT_OK, intent);
        }
    }

    /**
     * 得到缩略图
     */
    private void getThumbnail() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                        MediaStore.MediaColumns.DATE_ADDED + " DESC");
                if (mCursor.moveToFirst()) {
                    int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    do {
                        // 获取图片的路径
                        String path = mCursor.getString(_date);
                        // Log.e("TAG", path);
                        imageAll.images.add(path);
                        if (TextUtils.isEmpty(imageAll.getFirstImagePath())) {
                            imageAll.setFirstImagePath(path);
                            imageAll.setDir("/所有图片");
                        }
                        // 获取该图片的父路径名
                        File parentFile = new File(path).getParentFile();
                        if (parentFile == null) {
                            continue;
                        }
                        ImageFloder imageFloder = null;
                        String dirPath = parentFile.getAbsolutePath();
                        if (!tmpDir.containsKey(dirPath)) {
                            // 初始化imageFloder
                            imageFloder = new ImageFloder();
                            imageFloder.setDir(dirPath);
                            imageFloder.setFirstImagePath(path);
                            mDirPaths.add(imageFloder);
                            tmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                        } else {
                            imageFloder = mDirPaths.get(tmpDir.get(dirPath));
                        }
                        imageFloder.images.add(path);
                    } while (mCursor.moveToNext());
                }
                mCursor.close();
                mHandler.sendEmptyMessage(SCAN_OK);
                tmpDir = null;
            }
        }).start();
    }


}
