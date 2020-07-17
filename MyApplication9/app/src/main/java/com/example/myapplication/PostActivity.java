package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.myapplication.api.IMiniDouyinService;
import com.example.myapplication.model.GetVideosResponse;
import com.example.myapplication.model.PostVideoResponse;
import com.example.myapplication.model.Video;
import com.example.myapplication.util.ResourceUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final String TAG = "MainActivity";
    private RecyclerView mRv;
    private List<Video> mVideos = new ArrayList<>();
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    public Button mBtn;
    private Button mBtnRefresh;
    private String name1 = "00000";
    private String ID1 = "00000";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);
        Intent intent = getIntent();
        name1 = intent.getStringExtra("name");
        ID1 = intent.getStringExtra("ID");
        Log.i(TAG, "onCreate:           "+name1+ID1);
        initBtns();
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBtns() {
        mBtn = findViewById(R.id.btn_p);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mBtn.getText().toString();
                if (getString(R.string.select_an_image).equals(s)) {
                    chooseImage();
                } else if (getString(R.string.select_a_video).equals(s)) {
                    chooseVideo();
                } else if (getString(R.string.post_it).equals(s)) {
                    if (mSelectedVideo != null && mSelectedImage != null) {
                        postVideo();
                    } else {
                        throw new IllegalArgumentException("error data uri, mSelectedVideo = "
                                + mSelectedVideo
                                + ", mSelectedImage = "
                                + mSelectedImage);
                    }
                } else if ((getString(R.string.success_try_refresh).equals(s))) {
                    mBtn.setText(R.string.select_an_image);
                }
            }
        });

        mBtnRefresh = findViewById(R.id.btn_refresh);
    }



    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }

    public void chooseVideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() called with: requestCode = ["
                + requestCode
                + "], resultCode = ["
                + resultCode
                + "], data = ["
                + data
                + "]");

        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == PICK_IMAGE) {
                mSelectedImage = data.getData();
                Log.d(TAG, "selectedImage = " + mSelectedImage);
                mBtn.setText(R.string.select_a_video);
            } else if (requestCode == PICK_VIDEO) {
                mSelectedVideo = data.getData();
                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);
                mBtn.setText(R.string.post_it);
            }
        }
    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        File f = new File(ResourceUtils.getRealPath(PostActivity.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    private void postVideo() {
        mBtn.setText("POSTING...");
        mBtn.setEnabled(false);
        MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
        Log.i("tttsss", "postVideo: "+mSelectedImage);
        Log.i("tttsss2", "postVideo: "+mSelectedVideo);
        //MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);

        Log.i("iiiissss", "postVideo: "+ID1+"   "+name1);
        miniDouyinService.postVideo(ID1, name1, coverImagePart, videoPart).enqueue(
                new Callback<PostVideoResponse>() {
                    @Override
                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                        if (response.body() != null) {
                            Toast.makeText(PostActivity.this, "上传成功", Toast.LENGTH_SHORT)
                                    .show();
                        }
                        Log.i("eeerrr222", "onRe: ");
                        mBtn.setText(R.string.select_an_image);
                        mBtn.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {
                        mBtn.setText(R.string.select_an_image);
                        mBtn.setEnabled(true);
                        Toast.makeText(PostActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("eeerrr", "onFailure: "+throwable.getMessage());
                    }
                });
    }

}
