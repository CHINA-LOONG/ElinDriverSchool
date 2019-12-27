package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.BusRideBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MyDriveStudentActivity.MEDIA_TYPE_JSON;

public class ChangPhoneActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.edt_chang_phone)
    EditText edtChangPhone;
    private String phone;
    private Map<String, String> paramsMap = new HashMap<>();
    private String responseJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_phone);
        ButterKnife.bind(this);
        tvCusTitleName.setText("修改电话号码");
        tvCusTitleRight.setText("保存");
        phone = BaseApplication.getInstance().getCoachPhone();
        edtChangPhone.setText(phone);
        edtChangPhone.setSelection(phone.length());
    }
    private void updatePhone(String phone) {

        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("new_phone", phone);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.UPDATE_PHONE)
                .post(RequestBody.create(MEDIA_TYPE_JSON,new Gson().toJson(paramsMap) ))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(responseJson!=null){
                        CommonDataBean bean = new Gson().fromJson(responseJson,CommonDataBean.class);
                        if(TextUtils.equals("0",bean.getRc())){
                            setResult(RESULT_OK);
                            ToastUtils.ToastMessage(ChangPhoneActivity.this,"修改成功");
                            BaseApplication.getInstance().setCoachPhone(edtChangPhone.getText().toString().trim());
                            finish();
                        }else {
                            ToastUtils.ToastMessage(ChangPhoneActivity.this,bean.getDes());
                        }

                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(ChangPhoneActivity.this,"请检查手机号格式");
                    break;
            }
        }
    };

    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_cus_title_right:
                if(TextUtils.isEmpty(edtChangPhone.getText().toString().trim())){
                    ToastUtils.ToastMessage(this,"手机号不能为空");
                    return;
                }
                if(isMoblePhoneNum(edtChangPhone.getText().toString().trim())){
                    updatePhone(edtChangPhone.getText().toString().trim());
                }else {
                    ToastUtils.ToastMessage(this,"请检查手机号格式");
                }
                break;
        }
    }
    public boolean isMoblePhoneNum(String phoneNum) {
        return phoneNum.matches("1[3|4|5|7|8|][0-9]{9}");
    }
}
