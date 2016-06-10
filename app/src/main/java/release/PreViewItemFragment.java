package release;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import org.srr.dev.util.IML;

import Constants.Constants;
import sample.binvshe.com.demo_imageselector.R;

public class PreViewItemFragment extends Fragment {


    private String img_url;
    private View layout;

    public static PreViewItemFragment newInstance(String img_url) {

        Bundle args = new Bundle();
        args.putString(Constants.BUNDLE_KEY.STR1, img_url);
        PreViewItemFragment fragment = new PreViewItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle arg = getArguments();
        if (arg != null) {
            img_url = arg.getString(Constants.BUNDLE_KEY.STR1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (layout == null) {
            layout = inflater.inflate(R.layout.fr_preview_item, container, false);
            ImageView img = (ImageView) layout.findViewById(R.id.iv_preview_item_img);
            IML.load(getContext() ,img, "file://" + img_url);
        }
        return layout;
    }


}
