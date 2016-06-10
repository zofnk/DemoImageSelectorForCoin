package org.srr.dev.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import org.srr.dev.R;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements
        OnClickListener {

    protected boolean isondesk = true;
    protected ProgressDialog mLoadingDialog;
    private AnimationDrawable animationDrawable;
    protected BaseActivity _this = BaseActivity.this;

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            mLoadingDialog = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }//强制设置为竖屏

        //取toString作为hashmap的key
        BaseApplication.addDestoryActivity(this, toString());

        refreshContentView();
    }

    protected void refreshContentView() {
        int layoutId = getLayoutId();
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setMessage(getString(R.string.loading));
        }
        setContentView(layoutId);
        ButterKnife.bind(this);
        initGetIntent();
        initView();
        initData();
    }

    protected void setLoadingDialogMessage(String msg) {
        mLoadingDialog.setMessage(msg);
    }

    /**
     * 初始化intent数据
     */
    protected abstract void initGetIntent();


    /**
     * 显示加载中对话框
     */
    public void showLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (!mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
//					animationDrawable.start();
                }

            }
        });

    }

    /**
     * 隐藏加载对话框
     */
    public void dismissLoadingDialog() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                    mLoadingDialog.dismiss();
//					animationDrawable.stop();
                }
            }
        });

    }

    /**
     * 返回内容View布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();


    @Override
    public void onClick(View v) {
    }

    /**
     * 刷新数据（重新联网后调用）
     */
    public abstract void refreshData();

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }

    @Override
    public void finish() {
        BaseApplication.removeActivity(toString());
        super.finish();
    }

}
