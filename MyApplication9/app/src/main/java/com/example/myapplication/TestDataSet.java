package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<TestData> getData() {
        List<TestData> result = new ArrayList();
        result.add(new TestData("游戏小助手", "刚刚","抖出好游戏"));
        result.add(new TestData("抖音小助手", "昨天","收下我的祝福"));
        result.add(new TestData("系统消息", "12-28","登录提醒"));
        result.add(new TestData("Kamijou Touma", "11-11","转发[视频]"));
        result.add(new TestData("Misaka Mikoto", "12-11","[Hi]"));
        result.add(new TestData("Shirai Kuroko", "1-1","HHHHaaa"));
        result.add(new TestData("陌生人消息", "11-12","哈哈"));
        result.add(new TestData("路人甲", "11-11","转发[视频]"));
        result.add(new TestData("宋兵乙", "11-12","转发[视频]"));
        result.add(new TestData("强盗丙", "11-13","转发[视频]"));
        result.add(new TestData("土匪丁", "11-14","转发[视频]"));
        return result;
    }

}