package sample.binvshe.com.demo_imageselector;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SelectPicDialogFragment extends DialogFragment implements View.OnClickListener {


    private View layout;
    private AppCompatActivity mActivity;
    private OnClickListener l;

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.AnimBottom); //设置窗口弹出动画
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        layout = inflater.inflate(R.layout.menu_pop_window_layout, container, false);
        mActivity = (AppCompatActivity) getActivity();
        initView();
        return layout;
    }

    private void initView() {
        layout.findViewById(R.id.menu_pop_window_layout).setOnClickListener(this);
        layout.findViewById(R.id.btn_take_photo).setOnClickListener(this);
        layout.findViewById(R.id.btn_pick_photo).setOnClickListener(this);
        layout.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                l.takePhoto();
                break;
            case R.id.btn_pick_photo:
                l.pickPhoto();
                break;
            case R.id.btn_cancel:
                break;
            case R.id.menu_pop_window_layout:
                break;
            default:
                break;
        }
        dismiss();
    }

    public interface OnClickListener {
        void takePhoto();

        void pickPhoto();
    }

    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }
}
