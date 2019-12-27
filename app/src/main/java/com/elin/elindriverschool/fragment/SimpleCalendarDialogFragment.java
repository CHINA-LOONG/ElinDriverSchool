package com.elin.elindriverschool.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

/**
 * Created by imac on 17/1/2.
 */

public class SimpleCalendarDialogFragment extends AppCompatDialogFragment implements OnDateSelectedListener {
    private TextView textView;
    private String dateSelect;



    public interface OnDateSelectedCompleteListener{
        public void getSelectedDate(String dateSelect);
    }
    OnDateSelectedCompleteListener onDateSelectedCompleteListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDateSelectedCompleteListener = (OnDateSelectedCompleteListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //inflate custom layout and get views
        //pass null as parent view because will be in dialog layout
        View view = inflater.inflate(R.layout.dialog_basic, null);

        textView = (TextView) view.findViewById(R.id.textView);

        MaterialCalendarView widget = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        widget.setOnDateChangedListener(this);

        return new AlertDialog.Builder(getActivity())
                .setTitle("选择日期")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                          dateSelected =  textView.getText().toString();
                    }
                })
                .create();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//        textView.setText(FORMATTER.format(date.getDate()));
//            dateSelect = FORMATTER.format();
        if (date.getMonth()<10){//月份小于10
            if (date.getDay()<10){ //天小于10
                dateSelect = date.getYear()+"-"+"0"+(date.getMonth()+1)+"-"+"0"+date.getDay();

            }else {
                dateSelect = date.getYear()+"-"+"0"+(date.getMonth()+1)+"-"+date.getDay();
            }
        }else {
            if (date.getDay()<10){
                dateSelect = date.getYear()+"-"+(date.getMonth()+1)+"-"+"0"+date.getDay();
            }else {
                dateSelect = date.getYear()+"-"+(date.getMonth()+1)+"-"+date.getDay();
            }
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onDateSelectedCompleteListener.getSelectedDate(dateSelect);
    }
}
