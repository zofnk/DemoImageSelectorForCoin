package release;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import org.srr.dev.base.BaseActivity;

import java.util.ArrayList;

import sample.binvshe.com.demo_imageselector.R;

public class PreviewActivity extends BaseActivity {

    private ArrayList<String> imgs = new ArrayList<>();
    private ArrayList<Boolean> imgs_check = new ArrayList<>();
    private ViewPager pager;

    @Override
    protected void initGetIntent() {
        Intent intent = getIntent();
        ArrayList<String> imgs = (ArrayList<String>) intent.getSerializableExtra(SelectAlbumActivity.INTENT_SELECTED_PICTURE);
        this.imgs.addAll(imgs);
        for (int i = 0; i < this.imgs.size(); i++) {
            imgs_check.add(true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.ac_preview;
    }

    @Override
    public void initView() {
        findViewById(R.id.ac_preview_title_back).setOnClickListener(this);
        findViewById(R.id.ac_preview_bottom_enter).setOnClickListener(this);
        final TextView title_img_num = (TextView) findViewById(R.id.ac_preview_title_text);
        title_img_num.setText(1 + "/" + imgs.size());
        final CheckBox ischeck = (CheckBox) findViewById(R.id.ac_preview_bottom_check);
        pager = (ViewPager) findViewById(R.id.ac_preview_title_pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PreViewItemFragment.newInstance(imgs.get(position));
            }

            @Override
            public int getCount() {
                return imgs.size();
            }
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title_img_num.setText(position + 1 + "/" + imgs.size());
                ischeck.setChecked(imgs_check.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ischeck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                imgs_check.set(pager.getCurrentItem(), isChecked);
            }
        });
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ac_preview_title_back:
                finish();
                break;
            case R.id.ac_preview_bottom_enter:
                for (int i = 0; i < imgs_check.size(); i++) {
                    if (!imgs_check.get(i)) {
                        imgs.set(i, null);
                    }
                }
                imgs.remove(null);
                Intent intent = getIntent();
                intent.putExtra(SelectAlbumActivity.INTENT_SELECTED_PICTURE, imgs);
                setResult(RESULT_OK, intent);
                break;
            default:
                break;

        }
    }


    @Override
    public void refreshData() {

    }
}
