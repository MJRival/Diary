package com.example.diary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 柏宏 on 2016/10/27.
 */

class Initialization {
    List<Day> days = new ArrayList<Day>();
    public List<Day> init(){
        Date date;
        Calendar c = Calendar.getInstance();
        String year;
        String month;
        String day;
        String week;
        Map<String,String> mEngMonth = new HashMap<String, String>(){{
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
        String start = "2010-01-01";
        String end = "2020-12-31";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try{
            dBegin = sdf.parse(start);
            dEnd = sdf.parse(end);
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Date> listDate = getDatesBetweenTwoDate(dBegin, dEnd);
        String str;
        for(Iterator<Date> iter = listDate.iterator(); iter.hasNext();){
            date = iter.next();
            str = sdf.format(date);
            week = getWeekOfDate(date);
            c.setTime(date);
            year = c.get(Calendar.YEAR)+"";
            month = (c.get(Calendar.MONTH)+1)+"";
            day=c.get(Calendar.DATE)+"";
            Day temp = new Day();
            temp.setDate(str);
            temp.setYear(year);
            temp.setMonth(mEngMonth.get(month));
            temp.setDay(day);
            temp.setWeek(week);
            temp.setDetail("");
            days.add(temp);
        }
        return days;
    }
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate){
        List<Date> lDate = new ArrayList<>();
        lDate.add(beginDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        boolean bContinue = true;
        while (bContinue){
            cal.add(Calendar.DAY_OF_MONTH,1);
            if(endDate.after(cal.getTime())){
                lDate.add(cal.getTime());
            }else{
                break;
            }
        }
        lDate.add(endDate);
        return lDate;
    }
    public static String getWeekOfDate(Date date){
        String[] weekDaysName = {
                "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"
        };
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
        return weekDaysName[intWeek];
    }
}
