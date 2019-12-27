package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MultiAttrBean;
import com.elin.elindriverschool.model.PreSignUp;
import com.elin.elindriverschool.model.SignUpBean;
import com.elin.elindriverschool.util.IdcardValidator;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

/**
 * 预报名
 */
public class SignupActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.edt_signup_name)
    EditText edtSignupName;
    @Bind(R.id.edt_signup_number)
    EditText edtSignupNumber;
    @Bind(R.id.edt_signup_phone)
    EditText edtSignupPhone;
    @Bind(R.id.sp_signup_campus)
    Spinner spSignupCampus;
    @Bind(R.id.sp_signup_class)
    Spinner spSignupClass;
    @Bind(R.id.sp_signup_car)
    Spinner spSignupCar;
    @Bind(R.id.et_signup_remark)
    EditText etSignupRemark;
    @Bind(R.id.sp_signup_sex)
    Spinner spSignupSex;

    private MyProgressDialog progressDialog;
    private Map<String, String> parmasMap = new HashMap<>();
    private Map<String, Object> preSignUpMap = new HashMap<>();
    private String responseJson;
    private Gson gson = new Gson();
    private String carType ;
    private String classes ;
    private String branches ;
    private String sex = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);

        tvCusTitleName.setText("预报名");
        tvCusTitleRight.setText("提交");
        getMultiAttr();
    }

    private void getMultiAttr() {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_MULTI_ATTR)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(1);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    Log.e("请求成功-->", responseJson);
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
                    final MultiAttrBean bean = gson.fromJson(responseJson, MultiAttrBean.class);
                    Log.e("multiattrbean", bean.toString());
                    ArrayAdapter<String> carTypeAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_item, bean.getCarType());
                    ArrayAdapter<String> classesAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_item, bean.getClasses());
//                    ArrayAdapter<String> branchesAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_item, bean.getBranches());
                    ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sex));
                    carTypeAdapter.setDropDownViewResource(android.
                            R.layout.simple_spinner_dropdown_item);
                    classesAdapter.setDropDownViewResource(android.
                            R.layout.simple_spinner_dropdown_item);
//                    branchesAdapter.setDropDownViewResource(android.
//                            R.layout.simple_spinner_dropdown_item);
                    sexAdapter.setDropDownViewResource(android.
                            R.layout.simple_spinner_dropdown_item);
                    spSignupCar.setAdapter(carTypeAdapter);
                    spSignupClass.setAdapter(classesAdapter);
//                    spSignupCampus.setAdapter(branchesAdapter);
                    spSignupSex.setAdapter(sexAdapter);
                    spSignupCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            carType = bean.getCarType().get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spSignupClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            classes = bean.getClasses().get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spSignupCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            branches = bean.getBranches().get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spSignupSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position==0){
                                sex="1";
                            }else {
                                sex="2";
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 1:
                    ToastUtils.ToastMessage(SignupActivity.this, "服务器数据错误");
                    break;
                case 2:
                    SignUpBean signUpBean = gson.fromJson(responseJson, SignUpBean.class);
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (TextUtils.equals("0", signUpBean.getRc())) {
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtils.ToastMessage(SignupActivity.this, signUpBean.getDes());
                    }
                    break;
                case 3:
                    ToastUtils.ToastMessage(SignupActivity.this, "服务器数据错误");
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
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
                if (testData()) {
                    progressDialog.show();
                    preSignUp();
                }
                break;
        }
    }

    //学员信息预报名提交
    private void preSignUp() {
        preSignUpMap.put("token", BaseApplication.getInstance().getToken());
        PreSignUp signUp = new PreSignUp();
        signUp.setStu_name(edtSignupName.getText().toString());
        signUp.setStu_idnum(edtSignupNumber.getText().toString());
        signUp.setStu_cartype(carType);
        signUp.setStu_class(classes);
        signUp.setStu_phone(edtSignupPhone.getText().toString());
//        signUp.setBranch_no(branches);
        signUp.setStu_sex(sex);
        signUp.setStu_remarks(etSignupRemark.getText().toString());
        preSignUpMap.put("body", signUp);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.PRE_SIGNUP)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(preSignUpMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(2);
                    call.cancel();
                }
            }
        });
    }

    private boolean testData() {
        carType = spSignupCar.getSelectedItem().toString();
        classes = spSignupClass.getSelectedItem().toString();
//        branches = spSignupCampus.getSelectedItem().toString();
        if (TextUtils.isEmpty(edtSignupName.getText().toString())) {
            ToastUtils.ToastMessage(this, "请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(edtSignupNumber.getText().toString())) {
            ToastUtils.ToastMessage(this, "请输入身份证号");
            return false;
        }
        if (TextUtils.isEmpty(edtSignupPhone.getText().toString())) {
            ToastUtils.ToastMessage(this, "请输入手机号");
            return false;
        }
        if (!new IdcardValidator().isValidatedAllIdcard(edtSignupNumber.getText().toString())) {
            ToastUtils.ToastMessage(this, "请输入合法身份证号");
            return false;
        }
        if (!isTelPhoneNumber(edtSignupPhone.getText().toString())) {
            ToastUtils.ToastMessage(this, "请输入合法手机号");
            return false;
        }
        if (TextUtils.isEmpty(sex)) {
            ToastUtils.ToastMessage(this, "请选择性别");
            return false;
        }
        if (TextUtils.isEmpty(carType)) {
            ToastUtils.ToastMessage(this, "请选择车型");
            return false;
        }
        if (TextUtils.isEmpty(classes)) {
            ToastUtils.ToastMessage(this, "请选择班别");
            return false;
        }
//        if (TextUtils.isEmpty(branches)) {
//            ToastUtils.ToastMessage(this, "请选择驾校");
//            return false;
//        }
        return true;
    }


    /**
     * 手机号号段校验，
     * 第1位：1；
     * 第2位：{3、4、5、6、7、8}任意数字；
     * 第3—11位：0—9任意数字
     *
     * @param value
     * @return
     */
    public static boolean isTelPhoneNumber(String value) {
        if (value != null && value.length() == 11) {
            Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }
        return false;
    }
}
