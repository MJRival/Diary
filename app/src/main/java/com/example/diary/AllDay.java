package com.example.diary;

import android.app.Application;

import java.util.List;

/**
 * Created by 柏宏 on 2016/12/8.
 */

public class AllDay extends Application{
    private List<Day> days;

    public List<Day> getData(){
        return this.days;
    }
    public void setData(List<Day> c){
        this.days = c;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

}
