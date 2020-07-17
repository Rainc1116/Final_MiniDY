package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib3.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    public ArrayList<String> url;
    public ArrayList<String> imurl;
    //private int[] imgs = {R.raw.img_video_1, R.raw.img_video_2, R.raw.img_video_3, R.raw.img_video_4, R.raw.img_video_5, R.raw.img_video_6, R.raw.img_video_7, R.raw.img_video_8};
    //private int[] videos = {R.raw.video_1, R.raw.video_2, R.raw.video_3, R.raw.video_4, R.raw.video_5, R.raw.video_6, R.raw.video_7, R.raw.video_8};
    private Context mContext;
    private int mCount = 8;//TODO 根据输入修改 此处为测试数据  8
    public  static int index = 0;

    public ViewAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAdapter.ViewHolder holder, int position) {
        // TODO 根据视频获取的格式修改
        Log.i("iinnnddd", "onBindViewHolder: "+index);
        Log.i("cccc", "onBindViewHolder: "+imurl.get(index));
        ImageHelper.displayWebImage(imurl.get(index), holder.img_thumb);
        holder.videoView.setVideoURI(Uri.parse(url.get(index)));
        index++;
    }

    @Override
    public int getItemCount() {
        return url.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_thumb;
        VideoView videoView;
        ImageView img_play;
        RelativeLayout rootView;

        public ViewHolder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view);
            img_play = itemView.findViewById(R.id.img_play);
            rootView = itemView.findViewById(R.id.root_view);
        }
    }
}
