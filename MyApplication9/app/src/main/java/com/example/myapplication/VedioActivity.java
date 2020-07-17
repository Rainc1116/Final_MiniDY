package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class VedioActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    Camera mCamera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    ImageView imageView;
    VideoView videoView;
    Button button;
    Button vbutton;
    MediaRecorder mediaRecorder;
    String mp4Path;
    String name1;
    String ID1;
    boolean isRecording = false;



    //动态申请sd卡读写权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedio_activity);
        verifyStoragePermissions(this);
        Intent intent = getIntent();
        name1 = intent.getStringExtra("name");
        ID1 = intent.getStringExtra("ID");
        vbutton = findViewById(R.id.button2);
        surfaceView = findViewById(R.id.surfaceView);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView2);
        videoView = findViewById(R.id.videoView);
        initCamera();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        vbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record(videoView);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VedioActivity.this,PostActivity.class);
                intent.putExtra("name",name1);
                intent.putExtra("ID",ID1);
                startActivity(intent);
            }
        });
    }


    private void initCamera(){
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.set("orientation","portrait");
        parameters.set("rotation",0);
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        try{
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        if(holder.getSurface() == null){
            return;
        }
        mCamera.stopPreview();
        try{
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    //@Override
    /*protected void onResume(){
        super.onResume();
        if(mCamera == null){
            initCamera();
        }
        mCamera.startPreview();
    }*/

    @Override
    protected void onPause(){
        super.onPause();
        mCamera.stopPreview();
    }

    private boolean prepareVideoRecord(){
        mediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mp4Path = getOutputFile();
        mediaRecorder.setOutputFile(mp4Path);
        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
        mediaRecorder.setOrientationHint(90);
        try{
            mediaRecorder.prepare();
        }catch (Exception e){
            Log.i("wwwtttt", "record: "+e);
            mediaRecorder.release();
            return false;
        }
        return true;
    }

    private String getOutputFile() {
        String filepath = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath()+ File.separator+"2.mp4";
        Log.i("ppp", "getOutputFile: "+filepath);
        return filepath;
    }

    public void record(View view){
        if(isRecording){
            vbutton.setText("录制");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder=null;
            mCamera.lock();

            videoView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            videoView.setVideoPath(mp4Path);
            videoView.start();
        }
        else{
            if(prepareVideoRecord()){
                vbutton.setText("暂停");
                mediaRecorder.start();
            }
        }
        isRecording=!isRecording;
    }
}
