package com.example.myapplication;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MyAdapter.IOnItemClickListener{

    private static final int WAITTIME = 500;
    private ArrayList<String> permission=new ArrayList<String>();
    private ArrayList<String> askPermissionList = new ArrayList<String>();
    private static final String TAG = "TAG";
    private RadioButton mFirstLayout, mSecondLayout,mThirdLayout,mFourthLayout;
    public String name = "aba";
    public String ID = "3180100000";
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private ImageButton vbutton;

    private void requestPermissions(ArrayList<String> needAskPermisson, int requestCode) {
        String[] needpermisson = new String[needAskPermisson.size()];
        needpermisson = needAskPermisson.toArray(needpermisson);
        ActivityCompat.requestPermissions(this,needpermisson,1);

    }


    private void checkPermission(ArrayList<String> permissions, ArrayList<String> askpermissionsList) {
        for(int i=0;i<permissions.size();i++) {
            if(ContextCompat.checkSelfPermission(MainActivity.this,permissions.get(i))!= PackageManager.PERMISSION_GRANTED){
                askpermissionsList.add(permissions.get(i));
            }
        }

    }

    private  boolean verifyPermisson(int[] grantResults) {
        if(grantResults.length<1) {
            return false;
        }
        for(int result:grantResults) {
            if(result!=PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permission.add(Manifest.permission.READ_PHONE_STATE);
        permission.add(Manifest.permission.CAMERA);
        permission.add(Manifest.permission.RECORD_AUDIO);
        checkPermission(permission,askPermissionList);
        if(askPermissionList.size() >0) {
            requestPermissions(askPermissionList,1);

        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, WAITTIME);

        }
        Log.i(TAG, "RecyclerViewActivity onCreate");
        vbutton = findViewById(R.id.imageButton);
        mFirstLayout = findViewById(R.id.main_rbtnChannel);
        mSecondLayout = findViewById(R.id.main_rbtnMessage);
        mThirdLayout = findViewById(R.id.main_rbtnMy);
        mFourthLayout = findViewById(R.id.main_rbtnMore);
        mFirstLayout.setTextColor(Color.BLUE);
        Intent intent = getIntent();
        if(intent.getStringExtra("cname")!=null && intent.getStringExtra("cid")!= null){
            name = intent.getStringExtra("cname");
            ID = intent.getStringExtra("cid");
        }
        //initView();
        FragmentManager fm = getFragmentManager();

        FragmentTransaction MfragmentTransactions  = fm.beginTransaction();

        Fragmentone fff = new Fragmentone();

        MfragmentTransactions .replace(R.id.ly_content,fff);
        MfragmentTransactions .commit();

        setview();
        vbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, VedioActivity.class);
                if(name == null){
                    name = "S";
                }
                if(ID == null){
                    ID = "000000";
                }
                intent.putExtra("name",name);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });
    }

    private void setview() {
        mFirstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirstLayout.setTextColor(Color.BLUE);
                mSecondLayout.setTextColor(Color.BLACK);
                mThirdLayout.setTextColor(Color.BLACK);
                mFourthLayout.setTextColor(Color.BLACK);
                FragmentManager fm = getFragmentManager();

                FragmentTransaction MfragmentTransactions  = fm.beginTransaction();

                Fragmentone fff = new Fragmentone();

                MfragmentTransactions .replace(R.id.ly_content,fff);
                MfragmentTransactions .commit();
            }
        });

        mSecondLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirstLayout.setTextColor(Color.BLACK);
                mSecondLayout.setTextColor(Color.BLUE);
                mThirdLayout.setTextColor(Color.BLACK);
                mFourthLayout.setTextColor(Color.BLACK);

                FragmentManager fm = getFragmentManager();

                FragmentTransaction MfragmentTransactions  = fm.beginTransaction();

                Fragmenttwo fff = new Fragmenttwo();

                MfragmentTransactions .replace(R.id.ly_content,fff);
                MfragmentTransactions .commit();
            }
        });

        mThirdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirstLayout.setTextColor(Color.BLACK);
                mSecondLayout.setTextColor(Color.BLACK);
                mThirdLayout.setTextColor(Color.BLUE);
                mFourthLayout.setTextColor(Color.BLACK);
                FragmentManager fm = getFragmentManager();

                FragmentTransaction MfragmentTransactions  = fm.beginTransaction();

                Fragmentthree fff = new Fragmentthree();
                fff.id = ID;
                fff.n = name;

                MfragmentTransactions .replace(R.id.ly_content,fff);
                MfragmentTransactions .commit();
            }
        });

        mFourthLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirstLayout.setTextColor(Color.BLACK);
                mSecondLayout.setTextColor(Color.BLACK);
                mThirdLayout.setTextColor(Color.BLACK);
                mFourthLayout.setTextColor(Color.BLUE);
                FragmentManager fm = getFragmentManager();

                FragmentTransaction MfragmentTransactions  = fm.beginTransaction();

                Fragmentfour fff = new Fragmentfour();

                MfragmentTransactions .replace(R.id.ly_content,fff);
                MfragmentTransactions .commit();
            }
        });


    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(TestDataSet.getData());
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        //LinearItemDecoration itemDecoration = new LinearItemDecoration(Color.BLUE);
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // DefaultItemAnimator animator = new DefaultItemAnimator();
//        animator.setAddDuration(3000);
        // recyclerView.setItemAnimator(animator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "RecyclerViewActivity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "RecyclerViewActivity onResume");
    }


    @Override
    public void onItemCLick(int position, TestData data) {
        Toast.makeText(MainActivity.this, "点击了第" + position + "条", Toast.LENGTH_SHORT).show();
        mAdapter.addData(position + 1, new TestData("新增头条", "0w","aaa"));
    }

    @Override
    public void onItemLongCLick(int position, TestData data) {
        Toast.makeText(MainActivity.this, "长按了第" + position + "条", Toast.LENGTH_SHORT).show();
        mAdapter.removeData(position);
    }
}
