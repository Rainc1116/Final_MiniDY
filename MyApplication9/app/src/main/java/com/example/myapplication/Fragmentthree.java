package com.example.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fragmentthree extends Fragment {
    public String id;
    public String n;
    TextView name;
    TextView ID;
    Button button;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_three,container,false);
        button = view.findViewById(R.id.button3);
        name = view.findViewById(R.id.textView2);
        ID = view.findViewById(R.id.textView);
        name.setText("Name: "+n);
        ID.setText("ID: "+id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ChangeActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
