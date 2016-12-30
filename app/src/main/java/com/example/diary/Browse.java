package com.example.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 柏宏 on 2016/12/7.
 */

public class Browse extends AppCompatActivity {
    List<Day> listDay = new ArrayList<>();
    List<Day> listYear = new ArrayList<>();
    private List<List<Day>> Year = new ArrayList<>();
    String string = "";
    Day tempDay;
    TextView textView;
    ImageView imageView;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse);

        textView = (TextView)findViewById(R.id.browse_string);
        listYear = (List<Day>)getIntent().getSerializableExtra("allyear");
        List<Day> temp1 = new ArrayList<>();
        List<Day> temp2 = new ArrayList<>();
        List<Day> temp3 = new ArrayList<>();
        List<Day> temp4 = new ArrayList<>();
        List<Day> temp5 = new ArrayList<>();
        List<Day> temp6 = new ArrayList<>();
        List<Day> temp7 = new ArrayList<>();
        List<Day> temp8 = new ArrayList<>();
        List<Day> temp9 = new ArrayList<>();
        List<Day> temp10 = new ArrayList<>();
        List<Day> temp11 = new ArrayList<>();
        List<Day> temp12 = new ArrayList<>();
        for(int i=0;i<listYear.size();i++){
            if(listYear.get(i).getMonth().equals("January")){
                temp1.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("February")){
                temp2.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("March")){
                temp3.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("April")){
                temp4.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("May")){
                temp5.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("June")){
                temp6.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("July")){
                temp7.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("August")){
                temp8.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("September")){
                temp9.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("October")){
                temp10.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("November")){
                temp11.add(listYear.get(i));
            }
            else if(listYear.get(i).getMonth().equals("December")){
                temp12.add(listYear.get(i));
            }
        }
        Year.add(temp1);
        Year.add(temp2);
        Year.add(temp3);
        Year.add(temp4);
        Year.add(temp5);
        Year.add(temp6);
        Year.add(temp7);
        Year.add(temp8);
        Year.add(temp9);
        Year.add(temp10);
        Year.add(temp11);
        Year.add(temp12);
        listDay = (List<Day>)getIntent().getSerializableExtra("string");

        imageView = (ImageView)findViewById(R.id.browse_2);
        imageView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Browse.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }));

        gridView = (GridView)findViewById(R.id.horizontal_list);
        refresh();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position, long id){
                listDay.clear();
                ((ArrayList<Day>) listDay).addAll(Year.get(position));
                refresh();
            }
        });
    }
    public void refresh(){
        string = "";
        for(int i = 0;i < listDay.size();i++){
            String str = listDay.get(i).getDetail();
            if(!str.equals("")){
                tempDay = listDay.get(i);
                string += tempDay.getDay()+" "+tempDay.getWeek()+" / "+tempDay.getDetail()+"\n\n";
            }
        }
        textView.setText(string);

        int large = Year.size();
        int length = 60;
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        float density = display.density;
        int gridviewWidth = (int) (large * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridviewWidth,LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(itemWidth);
        gridView.setHorizontalSpacing(5);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(large);

        ChoosingAdapter adapter = new ChoosingAdapter(this,(ArrayList<List<Day>>)Year,(ArrayList<Day>)listDay);
        gridView.setAdapter(adapter);
    }
}
