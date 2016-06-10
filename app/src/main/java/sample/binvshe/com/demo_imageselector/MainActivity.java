package sample.binvshe.com.demo_imageselector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.srr.dev.util.TakePhoto;

import release.SelectAlbumActivity;

public class MainActivity extends AppCompatActivity {

    private static final int TAKE_PHOTO_IMAGE = 201;
    private static final int PICK_UP_IMAGE = 202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnStart(View v){
        SelectPicDialogFragment dialog = new SelectPicDialogFragment();
        dialog.setOnClickListener(new SelectPicDialogFragment.OnClickListener() {
            @Override
            public void takePhoto() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = TakePhoto.getInstance().getOutputMediaFileUri();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO_IMAGE);
            }

            @Override
            public void pickPhoto() {
                Intent intent = new Intent(MainActivity.this, SelectAlbumActivity.class);
                intent.putExtra(SelectAlbumActivity.RELEASE_IMGS_NUM, 0);
                intent.putExtra(SelectAlbumActivity.INTENT_MAX_NUM, 9);
                startActivityForResult(intent, PICK_UP_IMAGE);
            }
        });
        dialog.show(getSupportFragmentManager(), "photo_dialog");
    }
}