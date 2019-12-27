package com.elin.elindriverschool.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.elin.elindriverschool.R;


public class MyProgressDialog extends Dialog {

	TextView tView;

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		tView = (TextView) findViewById(R.id.id_tv_loadingmsg);
		tView.setText("Loading...");
	}
//	public void setMessage(String msg){
//		gettView().setText(msg);
//	}


}
