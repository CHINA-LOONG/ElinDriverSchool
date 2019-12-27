package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.view.PickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DialogPickerView extends BaseActivity implements View.OnClickListener{
	PickerView picker_hour,picker_minut;
	TextView text_cancel,text_submit;
	String date_hour;
	String date_minut;
	private Bundle bundle;
	private String flag;
	List<String> hourList = new ArrayList<String>();
	List<String> minutList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_pickerview);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		text_cancel = (TextView) findViewById(R.id.picker_cancel);
		text_cancel.setOnClickListener(this);
		text_submit = (TextView) findViewById(R.id.picker_submit);
		text_submit.setOnClickListener(this);
		picker_hour = (PickerView) findViewById(R.id.picker_hour);
		picker_minut = (PickerView) findViewById(R.id.picker_minut);

		//获取时间
		getDate();
		if(current_hour<10){
			date_hour = "0"+current_hour+"";
		}else {
			date_hour = current_hour+"";
		}

		date_minut = (current_minut/10)*10+"";

		for (int i = 0; i < 24; i++) {
			hourList.add(i < 10 ? "0" + i: "" + i);
		}
		for (int i = 0; i < 60; i+=10) {
			minutList.add(i < 10 ? "0" + i : "" + i );
		}
		picker_minut.setData(minutList);
		picker_hour.setData(hourList);

		picker_hour.setOnSelectListener(new PickerView.onSelectListener() {
			@Override
			public void onSelect(String text) {
				date_hour = text;
			}
		});
		picker_minut.setOnSelectListener(new PickerView.onSelectListener() {
			@Override
			public void onSelect(String text) {
				date_minut = text;
			}
		});
		picker_hour.setSelected(current_hour);
		picker_minut.setSelected(current_minut/10);
	}

	@Override
	public void onClick(View v) {
		Intent data = new Intent();
		switch(v.getId()){
		case R.id.picker_cancel:
			finish();
			break;
		case R.id.picker_submit:

			data.putExtra("hour",date_hour);
			if(TextUtils.equals(date_minut,"0")){
				data.putExtra("minute",date_minut+"0");
			}else {
				data.putExtra("minute",date_minut);
			}

			setResult(RESULT_OK,data);
			finish();
			break;
		}
	}
	//获取时间
	int current_hour,current_minut;
	private Calendar mCalendar;
	public void getDate(){
		mCalendar = Calendar.getInstance();
		current_hour = mCalendar.get(Calendar.HOUR_OF_DAY);
		current_minut = mCalendar.get(Calendar.MINUTE);
	}
}
