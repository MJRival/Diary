package com.example.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by 柏宏 on 2016/12/6.
 */

public class Show extends AppCompatActivity {
    private TextView time;
    private ImageView back;
    private Button fin;
    private EditText write;
    private Day data = new Day();
    private List<Day> allListDay = null;
    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        final AllDay allDay = (AllDay)getApplication();
        allListDay = allDay.getData();

        write = (EditText)findViewById(R.id.diary);

        write.setFocusable(false);
        write.setFocusableInTouchMode(false);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                write.setFocusableInTouchMode(true);
                write.setFocusable(true);
            }
        });

        String sign = (String)this.getIntent().getExtras().get("sign");
        String detail = "";
        if(sign.equals("add")){
            detail = (String)this.getIntent().getExtras().get("detail");
        }

        time = (TextView)findViewById(R.id.date);
        String Presenttime[] = (String[]) this.getIntent().getExtras().get("Presenttime");
        data.setYear(Presenttime[0]);
        data.setMonth(Presenttime[1]);
        data.setDay(Presenttime[2]);
        data.setWeek(Presenttime[3]);
        data.setDate(Presenttime[4]);
        data.setDetail(detail);
        write.setText(data.getDetail());

        if(Presenttime[3].equals("Saturday")){
            Presenttime[3] = "<font color='#FF0000'>" + Presenttime[3] + "</font>";
        }
        if(Presenttime[3].equals("Sunday")){
            Presenttime[3] = "<font color='#FF0000'>" + Presenttime[3] + "</font>";
        }
        time.setText(Html.fromHtml(Presenttime[3]));
        time.append("/"+Presenttime[0]+" "+Presenttime[1]+"/"+Presenttime[2]);
        if(sign.equals("write")){
            data = (Day)this.getIntent().getExtras().get("data");
            write.setText(data.getDetail());
        }

        fin = (Button)findViewById(R.id.done);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setDetail(write.getText().toString());
                for(int i = 0;i < allListDay.size();i++){
                    if(allListDay.get(i).getDate().equals(data.getDate())){
                        allListDay.get(i).setDetail(data.getDetail());
                        allDay.setData(allListDay);
                        saveDiary("diary",allDay.getData());
                        break;
                    }
                }
                Intent intent = new Intent();
                intent.setClass(Show.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back = (ImageView)findViewById(R.id.home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Show.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveDiary(String name,List<Day> days){
        FileOutputStream file = null;
        ObjectOutputStream object = null;
        try{
            file = this.openFileOutput(name, MODE_PRIVATE);
            object = new ObjectOutputStream(file);
            object.writeObject(data);
        }catch (Exception except){
            except.printStackTrace();
        }
    }
}
