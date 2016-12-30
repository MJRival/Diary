package com.example.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageView add;
    private ImageView browse;
    private ListView listView;
    private List<Day> allListDay = new ArrayList<>();
    private List<Day> listYear = new ArrayList<>();
    private List<Day> listDay = new ArrayList<>();
    private Map<String,String> Month = new HashMap<String, String>(){{
        put("1","January");
        put("2","February");
        put("3","March");
        put("4","April");
        put("5","May");
        put("6","June");
        put("7","July");
        put("8","Auguest");
        put("9","September");
        put("10","October");
        put("11","November");
        put("12","December");
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState){
        preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
        if(preferences.getBoolean("firststart", true)){
            editor = preferences.edit();
            editor.putBoolean("firststart", false);
            editor.commit();

            try{
                Initialization initialization = new Initialization();
                AllDay allDay = (AllDay)this.getApplicationContext();
                allDay.setData(initialization.init());
                saveObject("diary",allDay.getData());
                Log.i("检查",allDay.getData().get(365).getDate());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);

        AllDay allDay = (AllDay)getApplication();
        allDay.setData((List<Day>)getObject("diary"));
        allListDay = allDay.getData();

        final Spinner spinner = (Spinner)findViewById(R.id.year);
        spinner.getSelectedItem();
        spinner.setSelection(6);

        final Spinner spinner2 = (Spinner)findViewById(R.id.month);
        spinner2.getSelectedItem();
        spinner2.setSelection(8);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
                String year = MainActivity.this.getResources().getStringArray(R.array.years)[position];
                String selectMonth = spinner2.getSelectedItem().toString();
                refresh(year,selectMonth);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id){
                String month = MainActivity.this.getResources().getStringArray(R.array.weekday)[position];
                String selectYear = spinner.getSelectedItem().toString();
                refresh(selectYear,month);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){
            }
        });

        add = (ImageView) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String time[] = new String[6];
                final Calendar c = Calendar.getInstance();
                c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                time[0] = String.valueOf(c.get(Calendar.YEAR));
                time[1] = Month.get(String.valueOf(c.get(Calendar.MONTH)+1));
                time[2] = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                time[3] = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
                Date date = c.getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String string = simpleDateFormat.format(date);
                String str = "";
                time[4] = string;
                for(int i = 0;i < allListDay.size();i++){
                    if(allListDay.get(i).getDate().equals(string)){
                        str = allListDay.get(i).getDetail();
                        break;
                    }
                }
                if("1".equals(time[3])){
                    time[3] = "Sunday";
                }else if("2".equals(time[3])){
                    time[3] = "Monday";
                }else if("3".equals(time[3])){
                    time[3] = "Tuesday";
                }else if("4".equals(time[3])){
                    time[3] = "Wednesday";
                }else if("5".equals(time[3])){
                    time[3] = "Thursday";
                }else if("6".equals(time[3])){
                    time[3] = "Friday";
                }else if("7".equals(time[3])){
                    time[3] = "Saturday";
                }
                intent.putExtra("sign","add");
                intent.putExtra("Presenttime",time);
                intent.putExtra("detail",str);
                intent.setClass(MainActivity.this, Show.class);
                startActivity(intent);
            }
        });

        browse = (ImageView)findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("allyear",(Serializable)listYear);
                intent.putExtra("string",(Serializable)listDay);
                intent.setClass(MainActivity.this,Browse.class);
                startActivity(intent);
            }
        });
    }

    private void refresh(String year,String month){
        listYear.clear();
        int j=0;
        int num = allListDay.size();
        for(int i = 0;i < num;i++){
            if(allListDay.get(i).getYear().equals(year)){
                listYear.add(allListDay.get(i));
                Log.i("检查1",listYear.get(0).getMonth()+" "+j++);
                if((i<num - 1)&&(!allListDay.get(i+1).getYear().equals(year))){
                    break;
                }
            }
        }
        Log.i("检查",listYear.size()+"");
        listDay.clear();
        int num2 = listYear.size();
        for(int i = 0;i < num2;i++){
            Log.i("检查2",month+"");
            if(listYear.get(i).getMonth().equals(month)){
                listDay.add(listYear.get(i));
                Log.i("检查2",listDay.size()+"");
                if((i < num2-1)&&(!listYear.get(i+1).getMonth().equals(month))){
                    break;
                }
            }
        }
        Log.i("检查",listDay.size()+"");
        try{
            Adapter adapter = new Adapter(this,(ArrayList<Day>)listDay);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("signal","edit");
                    intent.putExtra("data",listDay.get(position));
                    String time[] = new String[6];
                    time[0] = listDay.get(position).getYear();
                    time[1] = listDay.get(position).getMonth();
                    time[2] = listDay.get(position).getDay();
                    time[3] = listDay.get(position).getWeek();
                    time[4] = listDay.get(position).getDate();

                    intent.putExtra("PresentTime",time);
                    intent.setClass(MainActivity.this,Show.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void saveObject(String name,List<Day> days){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try{
            fileOutputStream = this.openFileOutput(name, MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(days);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Object getObject(String name) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            fileInputStream = this.openFileInput(name);
            objectInputStream = new ObjectInputStream(fileInputStream);
            return objectInputStream.readObject();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
