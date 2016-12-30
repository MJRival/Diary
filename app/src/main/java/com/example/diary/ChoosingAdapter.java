package com.example.diary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柏宏 on 2016/12/1.
 */

public class ChoosingAdapter extends BaseAdapter {
    private  static final int type_1 = 0;
    private  static final int type_2 = 1;

    private Context context;

    private List<List<Day>> year = new ArrayList<>();
    private List<Day> month = new ArrayList<>();

    public ChoosingAdapter(Context context,ArrayList<List<Day>> a,ArrayList<Day> b){
        this.context = context;
        year.addAll(a);
        month.addAll(b);
    }

    @Override
    public int getItemViewType(int position){
        int comeout = 0;
        if(year.get(position).get(0).getMonth().equals(month.get(0).getMonth())){
            comeout = type_1;
        }else{
            comeout = type_2;
        }
        return comeout;
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getCount(){
        return year.size();
    }

    @Override
    public List<Day> getItem(int position){
        return year.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolderA holderA = null;
        ViewHolderB holderB = null;
        int Type = getItemViewType(position);
        if(convertView == null){
            holderA = new ViewHolderA();
            holderB = new ViewHolderB();
            switch (Type){
                case type_1:
                    convertView = View.inflate(context, R.layout.month,null);
                    holderA.month = (TextView)convertView.findViewById(R.id.month);
                    convertView.setTag(R.id.tag_chosen,holderA);
                    break;
                case type_2:
                    convertView = View.inflate(context,R.layout.unmonth,null);
                    holderB.month = (TextView)convertView.findViewById(R.id.month);
                    convertView.setTag(R.id.tag_not_chosen,holderB);
                    break;
            }
        }else {
            switch (Type){
                case type_1:
                    holderA=(ViewHolderA) convertView.getTag(R.id.tag_chosen);
                    break;
                case type_2:
                    holderB=(ViewHolderB) convertView.getTag(R.id.tag_not_chosen);
                    break;
            }
        }

        List<Day> o = year.get(position);

        switch (Type){
            case type_1:
                String A = o.get(0).getMonth().substring(0,3).toUpperCase();
                holderA.month.setText(A);
                break;

            case type_2:
                String B = o.get(0).getMonth().substring(0,3).toUpperCase();
                holderB.month.setText(B);
                break;
        }
        return convertView;
    }

    private static class ViewHolderA{
        TextView month;
    }

    private static class ViewHolderB{
        TextView month;
    }
}
