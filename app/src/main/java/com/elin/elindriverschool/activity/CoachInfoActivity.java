package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoachInfoActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.et_coach_info_name)
    EditText etCoachInfoName;
    @Bind(R.id.et_coach_info_phone)
    EditText etCoachInfoPhone;
    @Bind(R.id.et_coach_info_wx)
    EditText etCoachInfoWx;
    @Bind(R.id.btn_coach_info_save)
    Button btnCoachInfoSave;
    @Bind(R.id.et_coach_info_suggest)
    EditText etCoachInfoSuggest;

    private MyProgressDialog myProgressDialog;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private Bundle extras;
    String innerId, coachPhone, coachWx, coachName, coachInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        tvCusTitleName.setText("教练员信息");
        extras = getIntent().getExtras();
        innerId = extras.getString("inner_id");
        coachPhone = extras.getString("coach_phone");
        coachWx = extras.getString("coach_wx");
        coachName = extras.getString("coach_name");
        coachInfor = extras.getString("coach_infor");
        if (!TextUtils.isEmpty(coachName)) {
            etCoachInfoName.setText(coachName);
            etCoachInfoName.setSelection(coachName.length());
        }
        if (!TextUtils.isEmpty(coachPhone)) {
            etCoachInfoPhone.setText(coachPhone);
            etCoachInfoPhone.setSelection(coachPhone.length());
        }
        if (!TextUtils.isEmpty(coachWx)) {
            etCoachInfoWx.setText(coachWx);
            etCoachInfoWx.setSelection(coachWx.length());
        }
        if (!TextUtils.isEmpty(coachInfor)) {
            etCoachInfoSuggest.setText(coachInfor);
            etCoachInfoSuggest.setSelection(coachInfor.length());
        }
    }

    @OnClick({R.id.imv_cus_title_back, R.id.btn_coach_info_save})
    public void onClick(View view) {
        coachName = etCoachInfoName.getText().toString().trim();
        coachPhone = etCoachInfoPhone.getText().toString().trim();
        coachWx = etCoachInfoWx.getText().toString().trim();
        coachInfor = etCoachInfoSuggest.getText().toString().trim();

        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.btn_coach_info_save:
                if (!TextUtils.isEmpty(coachName) && !TextUtils.isEmpty(coachPhone)
                        && !TextUtils.isEmpty(coachWx)) {
                    saveData();
                } else {
                    ToastUtils.ToastMessage(this, "请补全您的个人信息");
                }

                break;
        }
    }

    private void saveData() {
        myProgressDialog.show();
        parmasMap.put("promotion_id", innerId);
        parmasMap.put("coach_id", BaseApplication.getInstance().getCoachIdNum());
        parmasMap.put("coach_name", coachName);
        parmasMap.put("coach_phone", coachPhone);
        parmasMap.put("coach_wx", coachWx);
        parmasMap.put("coach_infor", coachInfor);
        parmasMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.UPDATE_INFO)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (myProgressDialog.isShowing()) {
                        myProgressDialog.dismiss();
                    }
                    responseJson = String.valueOf(response.body().string());
                    Log.e("请求成功-->", response.toString());
                    mHandler.sendEmptyMessage(0);
                    call.cancel();
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent();
                    intent.putExtra("coach_name", coachName);
                    intent.putExtra("coach_phone", coachPhone);
                    intent.putExtra("coach_wx", coachWx);
                    intent.putExtra("coach_infor", coachInfor);
                    setResult(RESULT_OK, intent);
                    ToastUtils.ToastMessage(CoachInfoActivity.this, "保存成功");
                    Intent intent1 = new Intent("advertisingDataFresh");
                    intent1.setPackage(getPackageName());
                    sendBroadcast(intent1);
                    finish();
                    break;
            }
        }
    };
}
