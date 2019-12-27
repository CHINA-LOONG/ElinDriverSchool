package com.elin.elindriverschool.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.LogoutBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by imac on 16/12/30.
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_my_info_name)
    TextView tvMyInfoName;
    @Bind(R.id.tv_my_info_phone)
    TextView tvMyInfoPhone;
    @Bind(R.id.tv_my_info_id_num)
    TextView tvMyInfoIdNum;

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("个人资料");
//        btnMyInfoLogoOut.setOnClickListener(this);

        initView();
    }
    private void initView(){
        tvMyInfoIdNum.setText(BaseApplication.getInstance().getCoachIdNum());
        tvMyInfoName.setText(BaseApplication.getInstance().getCoachName());
        tvMyInfoPhone.setText(BaseApplication.getInstance().getCoachPhone());
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                MyInfoActivity.this.finish();
                break;
        }
    }
}
