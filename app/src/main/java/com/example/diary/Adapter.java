package com.example.diary;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.ViewDragHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柏宏 on 2016/12/6.
 */

public class Adapter extends BaseAdapter {
    private static final int type_1 = 0;
    private static final int type_2 = 1;
    private static final int type_3 = 2;

    private Context context;

    private List<Day> days = new ArrayList<>();

    public Adapter(Context context, ArrayList<Day> as) {
        this.context = context;
        days.addAll(as);
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (!days.get(position).getDetail().equals("")) {
            result = type_1;
        } else if (days.get(position).getDetail().equals("")) {
            if (days.get(position).getWeek().equals("Sunday")) {
                result = type_3;
            } else {
                result = type_2;
            }
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getCount(){
        return days.size();
    }

    @Override
    public Day getItem(int position){
        return days.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderA holderA = null;
        ViewHolderB holderB = null;
        ViewHolderC holderC = null;

        int TYPE = getItemViewType(position);
        if(convertView == null){
            holderA = new ViewHolderA();
            holderB = new ViewHolderB();
            holderC = new ViewHolderC();

            switch (TYPE){
                case type_1:
                    convertView = View.inflate(context, R.layout.table, null);
                    AbsListView.LayoutParams params = new AbsListView.LayoutParams(-1,130);
                    convertView.setLayoutParams(params);
                    holderA.week = (TextView) convertView.findViewById(R.id.week);
                    holderA.day = (TextView) convertView.findViewById(R.id.day);
                    holderA.detail = (TextView) convertView.findViewById(R.id.detail);
                    convertView.setTag(R.id.tag_first,holderA);
                    break;
                case type_2:
                    convertView = View.inflate(context, R.layout.black,null);
                    AbsListView.LayoutParams params1 = new AbsListView.LayoutParams(-1,100);
                    convertView.setLayoutParams(params1);
                    holderB.black = (ImageView) convertView.findViewById(R.id.black);
                    convertView.setTag(R.id.tag_second,holderB);
                    break;
                case type_3:
                    convertView = View.inflate(context,R.layout.red, null);
                    AbsListView.LayoutParams params2 = new AbsListView.LayoutParams(-1,100);
                    convertView.setLayoutParams(params2);
                    holderC.red = (ImageView) convertView.findViewById(R.id.red);
                    convertView.setTag(R.id.tag_third, holderC);
                    break;
            }
        }else{
            switch (TYPE){
                case type_1:
                    holderA = (ViewHolderA) convertView.getTag(R.id.tag_first);
                    break;
                case type_2:
                    holderB = (ViewHolderB) convertView.getTag(R.id.tag_second);
                    break;
                case type_3:
                    holderC = (ViewHolderC) convertView.getTag(R.id.tag_third);
                    break;
            }
        }

        Day pos = days.get(position);

        switch (TYPE){
            case type_1:
                Day one = pos;
                holderA.week.setTextColor(Color.BLACK);
                holderA.week.setText(one.getWeek().substring(0,3));
                if(one.getWeek().equals("Sunday")){
                    holderA.day.setTextColor(Color.RED);
                    holderA.day.setText(one.getDay());
                }else if(one.getWeek().equals("Saturday")){
                    holderA.day.setTextColor(Color.RED);
                    holderA.day.setText(one.getDay());
            }else{
                    holderA.day.setTextColor(Color.BLACK);
                    holderA.day.setText(one.getDay());
                }
                holderA.detail.setTextColor(Color.BLACK);
                holderA.detail.setText(one.getDetail());
                break;
            case type_2:
                holderB.black.setImageResource(R.drawable.notable_black);
                break;
            case type_3:
                holderC.red.setImageResource(R.drawable.notable_red);
                break;
        }
        return convertView;
    }

    private static class ViewHolderA{
        TextView week;
        TextView day;
        TextView detail;
    }

    private  static class ViewHolderB{
        ImageView black;
    }

    private  static class ViewHolderC{
        ImageView red;
    }
}
