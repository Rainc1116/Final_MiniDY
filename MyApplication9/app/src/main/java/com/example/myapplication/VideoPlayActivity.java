package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    private ViewAdapter mAdapter;
    private ImageButton mBtnLike, mBtnBack;
    private ImageView mImageLike, mImageStar, mImageHeart;
    private MyLayoutManager myLayoutManager;
    int index;
    ArrayList<String> url;
    ArrayList<String> imurl;

    public static void launch(Activity activity, ArrayList<String> url, ArrayList<String> imurl, int i) {
        Intent intent = new Intent(activity, VideoPlayActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("imurl", imurl);
        intent.putExtra("index", i);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        url = new ArrayList<String>(getIntent().getStringArrayListExtra("url"));
        imurl = new ArrayList<String>(getIntent().getStringArrayListExtra("imurl"));
        index = getIntent().getIntExtra("index",0);
        Log.i("uuurrrsssi", "onCreate: index"+index);
        Log.i("uuurrrsssi", "onCreate: index"+url.size());
        mRecyclerView = findViewById(R.id.recycler);
        myLayoutManager = new MyLayoutManager(this, OrientationHelper.VERTICAL, false);
        mAdapter = new ViewAdapter(this);
        mAdapter.url = new ArrayList<String>(url);
        mAdapter.imurl = new ArrayList<String>(imurl);
        mAdapter.index = index;
        Log.i("uuurrrsss", "onCreate: "+url);
        Log.i("uuurrrsss", "onCreate: "+imurl);

        mRecyclerView.setLayoutManager(myLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mBtnLike = findViewById(R.id.btn_like);
        mBtnBack = findViewById(R.id.btn_back);

        mImageLike = findViewById(R.id.iv_like);
        mImageStar = findViewById(R.id.iv_star);
        mImageHeart = findViewById(R.id.iv_heart);

        mBtnLike.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);

        myLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {

            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                mImageLike.setVisibility(View.INVISIBLE);
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position, boolean bottom) {
                playVideo(0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Animation likeAnimation, zoomAnimation, rotateAnimation,fadeOutAnimation;
        switch (view.getId()) {
            case R.id.btn_like:
                likeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
                zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom);
                rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotation);
                fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);

                mImageLike.setVisibility(View.VISIBLE);
                mImageHeart.setVisibility(View.VISIBLE);
                mImageStar.setVisibility(View.VISIBLE);

                mBtnLike.setAnimation(fadeOutAnimation);
                mImageLike.setAnimation(likeAnimation);

                mImageHeart.setAnimation(zoomAnimation);
                mImageStar.setAnimation(zoomAnimation);
                mImageHeart.setAnimation(rotateAnimation);
                mImageStar.setAnimation(rotateAnimation);

                mImageLike.setVisibility(View.VISIBLE);
                mImageHeart.setVisibility(View.INVISIBLE);
                mImageStar.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_back:
                startActivity(new Intent(VideoPlayActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private void releaseVideo(int index) {
        View itemView = mRecyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(int position) {
        View itemView = mRecyclerView.getChildAt(position);
        final FullWindowVideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final RelativeLayout rootView = itemView.findViewById(R.id.root_view);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });

        videoView.start();

        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;

            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    imgPlay.animate().alpha(0.7f).start();
                    videoView.pause();
                    isPlaying = false;
                } else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }
}

