package com.elin.elindriverschool.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.elin.elindriverschool.R;

import butterknife.ButterKnife;

public class AppointmentStudentActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_student);
        ButterKnife.bind(this);

        //比如drawleft设置图片大小
        //获取图片
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        //第一个0是距左边距离，第二个0是距上边距离，40分别是长宽
        drawable.setBounds(0, 0, 30, 30);
        //只放左边
//        searchBox.setCompoundDrawables(drawable, null, null, null);
    }
}
