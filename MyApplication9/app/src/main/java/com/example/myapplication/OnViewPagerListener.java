package com.example.myapplication;

public interface OnViewPagerListener {

    void onInitComplete();

    void onPageRelease(boolean isNext, int position);

    void onPageSelected(int position, boolean isBottom);


}
