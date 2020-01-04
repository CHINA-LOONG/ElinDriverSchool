package com.elin.elindriverschool.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.elin.elindriverschool.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentStudentActivity extends AppCompatActivity {

    @Bind(R.id.et_search)
    EditText etSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_student);
        ButterKnife.bind(this);

        //比如drawleft设置图片大小
        //获取图片
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        //第一个0是距左边距离，第二个0是距上边距离，40分别是长宽
        drawable.setBounds(0, 0, 40, 40);
        //只放左边
        etSearch.setCompoundDrawables(drawable, null, null, null);
    }

    @OnClick({R.id.iv_title_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
        }
    }

}
