package sample.binvshe.com.demo_imageselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.srr.dev.adapter.RecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;

import Constants.Constants;

public class ShowResultActivity extends AppCompatActivity {

    private RecyclerView resultRecyclerView;
    private ImageView singleImageView;

    private ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        initView();
    }

    private void initView() {
        images = (ArrayList<String>) getIntent().getSerializableExtra(Constants.IntentExtra.INTENT_SELECTED_PICTURE);
        singleImageView = (ImageView) findViewById(R.id.single_image);

        resultRecyclerView = (RecyclerView) findViewById(R.id.result_recycler);
        resultRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        if (images.size() == 1) {
            resultRecyclerView.setVisibility(View.GONE);
            Glide.with(ShowResultActivity.this)
                    .load(new File(images.get(0)))
                    .into(singleImageView);
        } else {
            singleImageView.setVisibility(View.GONE);
            resultRecyclerView.setAdapter(new ImageAdapter());
        }
    }

    public class ImageAdapter extends RecyclerViewAdapter<ImageAdapter.ViewHolder> {

        @Override
        public void onBindHolder(RecyclerView.ViewHolder viewHolder, int position) {
            final ImageAdapter.ViewHolder holder = (ImageAdapter.ViewHolder) viewHolder;
            Glide.with(ShowResultActivity.this)
                    .load(new File(images.get(position)))
                    .centerCrop()
                    .into(holder.imageView);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public int getItemCount() {
            return images.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }
}
