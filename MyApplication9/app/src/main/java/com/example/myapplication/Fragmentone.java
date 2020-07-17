package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lib3.ImageHelper;
import com.example.myapplication.api.IMiniDouyinService;
import com.example.myapplication.model.GetVideosResponse;
import com.example.myapplication.model.Video;

import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragmentone extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Video> mVideos = new ArrayList<>();
    final ArrayList<String> url = new ArrayList<>();
    final ArrayList<String> imurl = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private ImageButton vbutton;
    private Button my;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
    int index;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new MyAdapter(TestDataSet.getData());
        recyclerView.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(
                        LayoutInflater.from(getActivity())
                                .inflate(R.layout.recycle_item, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
                final Video video = mVideos.get(i);
                viewHolder.bind(getActivity(), video);
            }

            @Override
            public int getItemCount() {
                return mVideos.size();
            }
        });
        //LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
//        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(new MyAdapter.IOnItemClickListener() {
            @Override
            public void onItemCLick(int position, TestData data) {
                Log.i("ccclllkkk", "onItemCLick: "+data.title);
            }

            @Override
            public void onItemLongCLick(int position, TestData data) {

            }
        });
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                if (response.body() != null && response.body().videos != null) {
                    mVideos = response.body().videos;
                    for(int i = 0; i < mVideos.size();i++){
                        url.add(mVideos.get(i).videoUrl);
                    }
                    for(int i = 0; i < mVideos.size();i++){
                        imurl.add(mVideos.get(i).imageUrl);
                    }
                    Log.i("aaaa", "onResponse: "+"ccc"+url);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView tx;
        public TextView tm;
        public TextView tn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_head);
            tx = itemView.findViewById(R.id.tv_title);
            tm = itemView.findViewById(R.id.tv_hot);
            tn = itemView.findViewById(R.id.tv_info);
        }

        public void bind(final Activity activity, final Video video) {
            ImageHelper.displayWebImage(video.imageUrl, img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index = url.indexOf(video.videoUrl);
                    VideoPlayActivity.launch(activity, url,imurl,index);
                }
            });
        }
    }

}
