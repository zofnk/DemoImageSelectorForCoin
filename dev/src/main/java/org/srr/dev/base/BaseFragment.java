package org.srr.dev.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import org.srr.dev.R;

public abstract class BaseFragment extends Fragment implements OnClickListener {

    private static final String TAG = BaseFragment.class.getName();
    private ProgressDialog mLoadingDialog;
    protected View mContentView = null;
    private LayoutInflater inflater;

    public BaseFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mContentView == null) {
            this.inflater = inflater;
            mContentView = LayoutInflater.from(getActivity()).inflate(getLayoutId(), null);
            this.initBaseView();
            initView(mContentView);
            initData();
        }
        return mContentView;
    }

    private void initBaseView() {
        this.mLoadingDialog = new ProgressDialog(this.getActivity());
        this.mLoadingDialog.setMessage(getString(R.string.loading));
    }


    protected void showLoadingDialog() {
        this.mLoadingDialog.show();
    }

    protected void dismissLoadingDialog() {
        this.mLoadingDialog.dismiss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
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
    protected abstract void initView(View contentView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 刷新数据（重新联网后调用）
     */
    public abstract void doAfterReConnectNewWork();

    @Override
    public void onClick(View view) {
    }

    protected <T extends View> T findView(int viewId) {
        return (T) mContentView.findViewById(viewId);
    }

    public LayoutInflater getInflater() {
        return inflater;
    }
}
