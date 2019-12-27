package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.LoginApp;
import com.elin.elindriverschool.model.RegisterBean;
import com.elin.elindriverschool.util.DES;
import com.elin.elindriverschool.util.MD5Util;
import com.elin.elindriverschool.util.MyDateUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * Created by imac on 17/1/7.
 */

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.edt_change_pwd_phone)
    EditText edtChangePwdPhone;
    @Bind(R.id.edt_change_pwd_name)
    EditText edtChangePwdName;
    @Bind(R.id.edt_change_pwd_id_num)
    EditText edtChangePwdIdNum;
    @Bind(R.id.edt_change_pwd_new_pwd)
    EditText edtChangePwdNewPwd;
    @Bind(R.id.edt_change_pwd_confirm)
    EditText edtChangePwdConfirm;
    @Bind(R.id.btn_change_pwd_confirm)
    Button btnChangePwdConfirm;
//    @Bind(R.id.register_select_school)
//    Spinner registerSelectSchool;

    private MyProgressDialog progressDialog;
    private String paramsJson, bodyJson;
    private Gson gson = new Gson();
    private Map<String, String> parmasMap = new HashMap<>();
    private Map<String, String> bodyMap = new HashMap<>();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson, currentDate,//当前时间
            currentDateStr;//当前时间戳
    private LoginApp loginApp;
    private String loginBody;//登录请求消息体
    private int flag;// 1 修改密码 2 注册
    private RegisterBean registerBean;
    private Intent intent = new Intent();
    private String schoolResponse;
//    private List<String> schoolList = new ArrayList<>();
//    private String schoolId;
//    ArrayAdapter<String> spinnerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        ButterKnife.bind(this);
        flag = getIntent().getIntExtra("flag", 0);
        btnChangePwdConfirm.setOnClickListener(this);
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        getCurrentDate();
        imvCusTitleBack.setOnClickListener(this);
        if (flag == 1) {
            tvCusTitleName.setText("修改密码");
        }
        if (flag == 2) {
            tvCusTitleName.setText("注册");
        }
//        selectSchool();
//        schoolList.add("请选择驾校");
//        spinnerAdapter = new ArrayAdapter<>(ChangePwdActivity.this, android.R.layout.simple_spinner_item, schoolList);
//        spinnerAdapter.setDropDownViewResource(android.
//                R.layout.simple_spinner_dropdown_item);
//        registerSelectSchool.setAdapter(spinnerAdapter);
    }
//    private void selectSchool() {
//        Request request = new Request.Builder()
//                .url(Constant.ServerURL + Constant.SELECT_SCHOOL)
//                .post(RequestBody.create(MEDIA_TYPE_JSON, ""))
//                .build();
//        Call call = new MyOkHttpClient(ChangePwdActivity.this).newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    schoolResponse =String.valueOf(response.body().string());
//                    mHandler.sendEmptyMessage(3);
//                    call.cancel();
//                }
//            }
//        });
//    }

    private boolean testData() {
        if (edtChangePwdPhone.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入手机号");
            return false;
        }
        if (edtChangePwdName.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入账号");
            return false;
        }
        if (edtChangePwdIdNum.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入身份证号");
            return false;
        }
        if (edtChangePwdNewPwd.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入新密码");
            return false;
        }
        if (edtChangePwdConfirm.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "请确认密码");
            return false;
        }
//        if(edtChangePwdNewPwd.getText().toString().length()<6){
//            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入至少6位的密码");
//            return false;
//        }
//        if (edtChangePwdConfirm.getText().toString().length() < 6) {
//            ToastUtils.ToastMessage(ChangePwdActivity.this, "请输入至少6位的密码");
//            return false;
//        }
        if (!(edtChangePwdConfirm.getText().toString().equals(edtChangePwdNewPwd.getText().toString()))) {
            ToastUtils.ToastMessage(ChangePwdActivity.this, "确认密码与新密码不一致");
            return false;
        }

//        if(registerSelectSchool.getSelectedItem().equals("请选择驾校")){
//            ToastUtils.ToastMessage(ChangePwdActivity.this, "请选择驾校");
//            return false;
//        }
        return true;
    }

    private void getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        currentDate = format.format(curDate);

        currentDateStr = MyDateUtils.getDateToString(currentDate);
        Log.e("获取当前时间戳-->", currentDateStr);
    }

    //修改
    private void changePwd(String body) {
        progressDialog.show();
        parmasMap.put("timestamp", currentDateStr);
//        parmasMap.put("school_id",schoolId);
        try {
            loginBody = DES.encode(MD5Util.get32MD5Str(body) + body).replaceAll(" ", "");
            Log.e("找回密码最终消息体-->", String.valueOf(loginBody));
            parmasMap.put("body", loginBody);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("加密失败-->", e.toString() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());
        }
        paramsJson = gson.toJson(parmasMap);
        Log.e("找回密码请求Body的Json-->", paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.FIND_PWD)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();

        Call call = new MyOkHttpClient(ChangePwdActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
//                    Log.e("okhttp异步请求返回-->",response.body().string());
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(0);
//                    loginBean = gson.fromJson(response.body().string(),LoginBean.class);
                    call.cancel();
                }
            }
        });

    }


    //注册
    private void registerApp(String body) {
        progressDialog.show();
        parmasMap.put("timestamp", currentDateStr);
//        parmasMap.put("school_id", schoolId);
        try {
            loginBody = DES.encode(MD5Util.get32MD5Str(body) + body).replaceAll(" ", "");
            Log.e("找回密码最终消息体-->", String.valueOf(loginBody));
            parmasMap.put("body", loginBody);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("加密失败-->", e.toString() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());
        }
        paramsJson = gson.toJson(parmasMap);
        Log.e("注册请求Body的Json-->", paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.REGISTER_APP)
//                .url("http://192.168.1.153/index.php/Client/Coach/coach_register")
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();

        Call call = new MyOkHttpClient(ChangePwdActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(2);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(1);
//                    loginBean = gson.fromJson(response.body().string(),LoginBean.class);
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
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(ChangePwdActivity.this, "修改成功");
                            ChangePwdActivity.this.finish();
                        } else {

                            ToastUtils.ToastMessage(ChangePwdActivity.this, "修改失败，请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    Log.e("注册返回Json-->", responseJson);
                    registerBean = gson.fromJson(responseJson, RegisterBean.class);
                    if ("0".equals(registerBean.getRc())) {
                        ToastUtils.ToastMessage(ChangePwdActivity.this, "注册成功");
                        BaseApplication.getInstance().setToken(registerBean.getData().getToken());
                        BaseApplication.getInstance().setCoachIdNum(registerBean.getData().getCoach_idnum());
                        BaseApplication.getInstance().setCoachPhone(registerBean.getData().getCoach_phone());
                        BaseApplication.getInstance().setCoachPhoto(registerBean.getData().getCoach_photo());
//                        BaseApplication.getInstance().setCoachPhoto(new ImageSubUtils().getEncodeImgUrl(loginBean.getData().getCoach_photo()));
                        BaseApplication.getInstance().setCoachName(registerBean.getData().getCoach_name());
//                        BaseApplication.getInstance().setSchoolId(schoolId);
                        intent.setClass(ChangePwdActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        ChangePwdActivity.this.finish();
                    } else {
                        ToastUtils.ToastMessage(ChangePwdActivity.this, "注册失败，请检查您的信息");
                    }
                    break;
                case 2:
                    ToastUtils.ToastMessage(ChangePwdActivity.this, "请检查您的网络连接");
                    break;
//                case 3:
//                    final SelectSchoolBean schoolBean = gson.fromJson(schoolResponse, SelectSchoolBean.class);
//                    if(schoolBean!=null){
//                        for (int i = 0; i < schoolBean.getSchoolList().size(); i++) {
//                            schoolList.add(schoolBean.getSchoolList().get(i).getName());
//                        }
//                        spinnerAdapter.notifyDataSetChanged();
//                    }
//
//                    registerSelectSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            if(schoolBean!=null){
//                                schoolId = schoolBean.getSchoolList().get(position-1).getId();
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//
//                        }
//                    });
//                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                ChangePwdActivity.this.finish();
                break;
            case R.id.btn_change_pwd_confirm:
                if (testData()) {
                    if (flag == 1) {
                        bodyMap.put("coach_idnum", edtChangePwdIdNum.getText().toString());
                        bodyMap.put("coach_phone", edtChangePwdPhone.getText().toString());
                        bodyMap.put("login_id", edtChangePwdName.getText().toString());
                        bodyMap.put("coach_newpwd", MD5Util.get32MD5Str(edtChangePwdNewPwd.getText().toString()));
                        bodyJson = gson.toJson(bodyMap);
                        Log.e("修改密码bodyJson-->", bodyJson);
                        changePwd(bodyJson);
                    }
                    if (flag == 2) {
                        bodyMap.put("coach_idnum", edtChangePwdIdNum.getText().toString());
                        bodyMap.put("coach_phone", edtChangePwdPhone.getText().toString());
                        bodyMap.put("login_id", edtChangePwdName.getText().toString());
                        bodyMap.put("coach_password", MD5Util.get32MD5Str(edtChangePwdNewPwd.getText().toString()));
                        bodyJson = gson.toJson(bodyMap);
                        Log.e("修改密码bodyJson-->", bodyJson);
                        registerApp(bodyJson);
                    }
                }
                break;
        }
    }
}
