package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.PreSucOptionStuInfoBean;
import com.elin.elindriverschool.model.PreSucWaitTestStuInfoBean;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.chenglei.widget.datepicker.DatePicker;
import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by imac on 17/1/4.
 * 待受理以及待考试学员信息
 */

public class PreSucStuInfoActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_pre_suc_stu_info_name)
    TextView tvPreSucStuInfoName;
    @Bind(R.id.tv_pre_suc_stu_info_id_num)
    TextView tvPreSucStuInfoIdNum;
    @Bind(R.id.tv_pre_suc_stu_info_phone)
    TextView tvPreSucStuInfoPhone;
    @Bind(R.id.imv_pre_suc_stu_info_phone)
    ImageView imvPreSucStuInfoPhone;
    @Bind(R.id.tv_pre_suc_stu_info_sign_up_date)
    TextView tvPreSucStuInfoSignUpDate;
    @Bind(R.id.tv_pre_suc_stu_info_car_type)
    TextView tvPreSucStuInfoCarType;
    @Bind(R.id.tv_pre_suc_stu_info_class)
    TextView tvPreSucStuInfoClass;
    @Bind(R.id.tv_pre_suc_stu_info_jiandang_date)
    TextView tvPreSucStuInfoJiandangDate;
    @Bind(R.id.tv_pre_suc_stu_info_jiandang_site)
    TextView tvPreSucStuInfoJiandangSite;
    @Bind(R.id.tv_pre_suc_stu_info_take_bus)
    TextView tvPreSucStuInfoTakeBus;
    @Bind(R.id.ll_pre_suc_jiandang_info)
    LinearLayout llPreSucJiandangInfo;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.tv_pre_suc_stu_info_pre_class)
    TextView tvPreSucStuInfoPreClass;
    @Bind(R.id.tv_pre_suc_stu_info_test_date)
    TextView tvPreSucStuInfoTestDate;
    @Bind(R.id.tv_pre_suc_stu_info_change_date)
    TextView tvPreSucStuInfoChangeDate;
    @Bind(R.id.tv_pre_suc_stu_info_test_site)
    TextView tvPreSucStuInfoTestSite;
    @Bind(R.id.tv_pre_suc_stu_info_change_site)
    TextView tvPreSucStuInfoChangeSite;
    @Bind(R.id.tv_pre_suc_stu_info_is_take_bus)
    TextView tvPreSucStuInfoIsTakeBus;
    @Bind(R.id.ll_pre_suc_test_info)
    LinearLayout llPreSucTestInfo;
    @Bind(R.id.ll_pre_suc_stu_info_container)
    LinearLayout llPreSucStuInfoContainer;
    @Bind(R.id.tv_pre_suc_stu_info_change_bus)
    TextView tvPreSucStuInfoChangeBus;

    private int preSucFlag = 0;// 0，待处理学员信息 1 待考试学员信息
    private String stuIdNum;
    private MyProgressDialog progressDialog;
    private Map<String, String> parmaMap = new HashMap<>();//待考 请求参数
    private Map<String, String> parmaMapOption = new HashMap<>();//待受理 请求参数
    private Map<String, String> paramMapUpdate = new HashMap<>();//待受理 请求参数

    private String paramsJson, paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson, testSiteJson;

    private View pv_date;
    private PopupWindow pw_date;
    private DatePicker datePicker;
    private TestSiteBean testSiteBean;
    private String[] testSites;
    private int examSub;// 科目 1 2 3 4；
    private PreSucOptionStuInfoBean preSucOptionStuInfoBean;
    private PreSucWaitTestStuInfoBean preSucWaitTestStuInfoBean;
    private String[] busArr = {"是","否"};
    private int isTakeBus = 1;//1 坐 2 不坐

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_suc_stu_info);
        ButterKnife.bind(this);

        imvCusTitleBack.setOnClickListener(this);
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        preSucFlag = getIntent().getIntExtra("preSucFlag", 0);
        stuIdNum = getIntent().getStringExtra("stuIdNum");
        tvCusTitleName.setText("预约成功学员详情");
        if (preSucFlag == 0) {
            //待受理学员详情
            llPreSucTestInfo.setVisibility(View.GONE);
            llPreSucJiandangInfo.setVisibility(View.VISIBLE);
            getPreSucWaitOptionStuInfo();

//            tvWaitTestStuInfoName.setText(getIntent().getStringExtra("stuName"));
//            tvWaitTestStuInfoIdNum.setText(stuIdNum);
//            tvWaitTestStuInfoPhone.setText(getIntent().getStringExtra("stuPhone"));
//            tvWaitTestStuInfoSignUpDate.setText(getIntent().getStringExtra("stuSignDate"));
//            tvWaitTestStuInfoCarType.setText(getIntent().getStringExtra("stuCarType"));
        }
        if (preSucFlag == 1) {
            //科目待考学员详情
            examSub = getIntent().getIntExtra("examSub", 0);
            llPreSucTestInfo.setVisibility(View.VISIBLE);
            llPreSucJiandangInfo.setVisibility(View.GONE);
            pv_date = LayoutInflater.from(this).inflate(R.layout.popup_date_picker, null);
            pw_date = new PopupWindow(pv_date, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pw_date.setFocusable(true);
            pw_date.setOutsideTouchable(false);
            ColorDrawable drawable = new ColorDrawable(0xb0000000);
            pw_date.setBackgroundDrawable(drawable);
            datePicker = (DatePicker) pv_date.findViewById(R.id.date_picker);
            pv_date.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
            pv_date.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);
            tvPreSucStuInfoChangeDate.setOnClickListener(this);
            tvPreSucStuInfoChangeSite.setOnClickListener(this);
            tvPreSucStuInfoChangeBus.setOnClickListener(this);
            getPreSucWaitTestStuInfo();
        }
    }

    // 科目待考学员 详情
    private void getPreSucWaitTestStuInfo() {
        progressDialog.show();
        parmaMap.put("stu_idnum", stuIdNum);
        parmaMap.put("exam_sub", examSub + "");
        parmaMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(parmaMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.PRE_SUC_WAIT_TEST_INFO)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(PreSucStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }

    // 待处理学员 详情
    private void getPreSucWaitOptionStuInfo() {
        progressDialog.show();
        parmaMapOption.put("stu_idnum", stuIdNum);
        parmaMapOption.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(parmaMapOption);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.PRE_SUC_WAIT_OPTION_INFO)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(PreSucStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(1);
                call.cancel();
            }
        });
    }

    //修改 信息
    private void updatePreStuInfo() {
        progressDialog.show();
        paramMapUpdate.put("token", BaseApplication.getInstance().getToken());
        paramMapUpdate.put("exam_sub", examSub + "");
        paramMapUpdate.put("stu_idnum", tvPreSucStuInfoIdNum.getText().toString());
        paramMapUpdate.put("exam_date", tvPreSucStuInfoTestDate.getText().toString());
        paramMapUpdate.put("exam_site", tvPreSucStuInfoTestSite.getText().toString());
        paramMapUpdate.put("exam_bus",isTakeBus+"");
        paramMapUpdate.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsSubmitJson = gson.toJson(paramMapUpdate);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.PRE_STU_INFO_UPDATE)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(PreSucStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(3);
                call.cancel();
            }
        });
    }

    //获取考试场地
    private void getTestSites() {
        progressDialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TEST_SITES)
                .post(RequestBody.create(MEDIA_TYPE_JSON,new Gson().toJson(map)))
                .build();
        Call call = new MyOkHttpClient(PreSucStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "获取失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                testSiteJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
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
                    Log.e("PreSuc科目待考学员详情Json-->", responseJson.toString());
                    preSucWaitTestStuInfoBean = gson.fromJson(responseJson, PreSucWaitTestStuInfoBean.class);
                    if (preSucWaitTestStuInfoBean.getRc() == 0) {
                        tvPreSucStuInfoName.setText(preSucWaitTestStuInfoBean.getData().getStu_name());
                        tvPreSucStuInfoIdNum.setText(preSucWaitTestStuInfoBean.getData().getStu_idnum());
                        tvPreSucStuInfoPhone.setText(preSucWaitTestStuInfoBean.getData().getStu_phone());
                        imvPreSucStuInfoPhone.setOnClickListener(PreSucStuInfoActivity.this);
                        tvPreSucStuInfoSignUpDate.setText(preSucWaitTestStuInfoBean.getData().getStu_reg_date());
                        tvPreSucStuInfoCarType.setText(preSucWaitTestStuInfoBean.getData().getStu_cartype());
                        tvPreSucStuInfoTestDate.setText(preSucWaitTestStuInfoBean.getData().getOrder_date());
                        tvPreSucStuInfoTestSite.setText(preSucWaitTestStuInfoBean.getData().getOrder_site());
                        switch (examSub) {
                            case 1:
                                tvPreSucStuInfoClass.setText("科目一");
                                tvPreSucStuInfoPreClass.setText("科目一");
                                break;
                            case 2:
                                tvPreSucStuInfoClass.setText("科目二");
                                tvPreSucStuInfoPreClass.setText("科目二");
                                break;
                            case 3:
                                tvPreSucStuInfoClass.setText("科目三");
                                tvPreSucStuInfoPreClass.setText("科目三");
                                break;
                            case 4:
                                tvPreSucStuInfoClass.setText("科目四");
                                tvPreSucStuInfoPreClass.setText("科目四");
                                break;
                        }
                        if (preSucWaitTestStuInfoBean.getData().getOrder_bus().equals("1")) {
                            tvPreSucStuInfoIsTakeBus.setText("是");
                        } else {
                            tvPreSucStuInfoIsTakeBus.setText("否");
                        }

                    } else {
                        ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "获取失败，请重试");
                    }

                    break;
                case 1:
                    Log.e("PreSuc待处理学员详情Json-->", responseJson.toString());
                    preSucOptionStuInfoBean = gson.fromJson(responseJson, PreSucOptionStuInfoBean.class);

                    if (preSucOptionStuInfoBean.getRc() == 0) {
                        tvPreSucStuInfoName.setText(preSucOptionStuInfoBean.getData().getStu_name());
                        tvPreSucStuInfoIdNum.setText(preSucOptionStuInfoBean.getData().getStu_idnum());
                        tvPreSucStuInfoPhone.setText(preSucOptionStuInfoBean.getData().getStu_phone());
                        imvPreSucStuInfoPhone.setOnClickListener(PreSucStuInfoActivity.this);
                        tvPreSucStuInfoSignUpDate.setText(preSucOptionStuInfoBean.getData().getStu_reg_date());
                        tvPreSucStuInfoCarType.setText(preSucOptionStuInfoBean.getData().getStu_cartype());
                        tvPreSucStuInfoClass.setText("待受理");
                        tvPreSucStuInfoJiandangDate.setText(preSucOptionStuInfoBean.getData().getDetarec_order_date());
                        tvPreSucStuInfoJiandangSite.setText(preSucOptionStuInfoBean.getData().getDetarec_order_site());
                        if (preSucOptionStuInfoBean.getData().getDetarec_order_bus().equals("1")) {
                            tvPreSucStuInfoTakeBus.setText("是");
                        } else {
                            tvPreSucStuInfoTakeBus.setText("否");
                        }
                    } else {
                        ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "获取失败，请重试");
                    }
                    break;
                case 2:
                    testSiteBean = gson.fromJson(testSiteJson, TestSiteBean.class);
                    if (testSiteBean.getRc() == 0) {
                        testSites = new String[testSiteBean.getData_list().size()];
                        for (int i = 0; i < testSiteBean.getData_list().size(); i++) {
                            testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(PreSucStuInfoActivity.this);
                        builder.setTitle("选择考试场地")
                                .setItems(testSites, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tvPreSucStuInfoTestSite.setText(testSiteBean.getData_list().get(i).getExam_site());
                                        //修改 考试场地...
                                        updatePreStuInfo();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .create().show();
                    } else {
                        ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "获取失败，请重试");
                    }
                    break;
                case 3:
                    Log.e("修改预约信息Json-->", responseJson);
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "修改成功");
                        } else {
                            ToastUtils.ToastMessage(PreSucStuInfoActivity.this, "修改失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                PreSucStuInfoActivity.this.finish();
                break;
            case R.id.tv_pre_suc_stu_info_change_date:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                pw_date.setAnimationStyle(R.style.anim_popwindow);
                pw_date.showAtLocation(findViewById(R.id.ll_pre_suc_stu_info_container), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_popup_datepicker_confirm:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                if (datePicker.getMonth() < 10) {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvPreSucStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvPreSucStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                } else {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvPreSucStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvPreSucStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                }
                //修改考试日期
                updatePreStuInfo();
                break;
            case R.id.tv_datepicker_cancel:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                break;
            case R.id.tv_pre_suc_stu_info_change_site:
                getTestSites();
                break;
            case R.id.tv_pre_suc_stu_info_change_bus:
                AlertDialog.Builder builder = new AlertDialog.Builder(PreSucStuInfoActivity.this);
                builder.setTitle("是否乘坐班车")
                        .setItems(busArr, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvPreSucStuInfoIsTakeBus.setText(busArr[i]);
                                isTakeBus = i+1;
                                //修改 考试场地...
                                updatePreStuInfo();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create().show();
                break;
            case R.id.imv_pre_suc_stu_info_phone:
                MobilePhoneUtils.makeCall(tvPreSucStuInfoPhone.getText().toString(), tvPreSucStuInfoName.getText().toString(),PreSucStuInfoActivity.this);
                break;
        }
    }
}
