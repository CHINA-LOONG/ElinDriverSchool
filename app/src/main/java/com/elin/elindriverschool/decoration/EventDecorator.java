package com.elin.elindriverschool.decoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.elin.elindriverschool.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 17535 on 2017/9/8.
 */

public class EventDecorator implements DayViewDecorator {
    private int color;
    private HashSet<CalendarDay> dates;
    Context context;
    private HashMap<CalendarDay,String> hashMap ;
    private boolean decorateEnable;
    public EventDecorator(Context context,int color, Collection<CalendarDay> dates,HashMap<CalendarDay,String> hashMap,boolean decorateEnable) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.context = context;
        this.hashMap = hashMap;
        this.decorateEnable = decorateEnable;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        CalendarDay calendarDay = CalendarDay.from(new Date());
        if(day.getDate().getTime()<=calendarDay.getDate().getTime()||dates.contains(day)){
            return true;
        }
        return false;
//        return dates.contains(day);
    }

    @Override
    public HashMap<CalendarDay, String> getHashMap() {
        return hashMap;
    }

    @Override
    public boolean decorateEnable() {
        return decorateEnable;
    }

//    @Override
//    public HashMap<CalendarDay, String> getHashMap(HashMap<CalendarDay,String> calendarDayStringHashMap) {
//        return hashMap;
//    }

    @Override
    public void decorate(DayViewFacade view) {
        Bitmap bmp = null;
        bmp= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_half_day);
        view.addSpan(new DotSpan(5, color,bmp));
        bmp= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_whole_day);
        view.addSpan(new DotSpan(5, color,bmp));
        bmp= BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_rest_day);
        view.addSpan(new DotSpan(5, color,bmp));


    }
}
