package com.example.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragmenttwo extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapterMess mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private ImageButton vbutton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = view.findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);MyAdapterMess

        mAdapter = new MyAdapterMess(TestDataSet.getData());
        //mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        //LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mAdapter.setOnItemClickListener(new MyAdapterMess.IOnItemClickListener() {
            @Override
            public void onItemCLick(int position, TestData data) {
                Log.i("ccclllkkk", "onItemCLick: "+data.title);
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongCLick(int position, TestData data) {

            }
        });
        return view;
    }
}
