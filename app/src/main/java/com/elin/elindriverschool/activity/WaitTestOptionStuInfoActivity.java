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
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.chenglei.widget.datepicker.DatePicker;

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

public class WaitTestOptionStuInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_wait_test_stu_info_name)
    TextView tvWaitTestStuInfoName;
    @Bind(R.id.tv_wait_test_stu_info_id_num)
    TextView tvWaitTestStuInfoIdNum;
    @Bind(R.id.tv_wait_test_stu_info_phone)
    TextView tvWaitTestStuInfoPhone;
    @Bind(R.id.tv_wait_test_stu_info_sign_up_date)
    TextView tvWaitTestStuInfoSignUpDate;
    @Bind(R.id.tv_wait_test_stu_info_car_type)
    TextView tvWaitTestStuInfoCarType;
    @Bind(R.id.tv_wait_test_stu_info_wait_class)
    TextView tvWaitTestStuInfoWaitClass;
    @Bind(R.id.ll_wait_test_wait_class)
    LinearLayout llWaitTestWaitClass;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.tv_wait_test_stu_info_pre_class)
    TextView tvWaitTestStuInfoPreClass;
    @Bind(R.id.tv_wait_test_stu_info_test_date)
    TextView tvWaitTestStuInfoTestDate;
    @Bind(R.id.tv_wait_test_stu_info_change_date)
    TextView tvWaitTestStuInfoChangeDate;
    @Bind(R.id.tv_wait_test_stu_info_test_site)
    TextView tvWaitTestStuInfoTestSite;
    @Bind(R.id.tv_wait_test_stu_info_change_site)
    TextView tvWaitTestStuInfoChangeSite;
    @Bind(R.id.tv_wait_test_stu_info_is_take_bus)
    TextView tvWaitTestStuInfoIsTakeBus;
    @Bind(R.id.ll_wait_test_test_info)
    LinearLayout llWaitTestTestInfo;

    private int waitTestFlag = 0;// 0，待处理学员信息 1 待考试学员信息
    private String stuIdNum;
    private MyProgressDialog progressDialog;
    private Map<String,String>parmaMap = new HashMap<>();

    private String paramsJson,paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson,testSiteJson;

    private View pv_date;
    private PopupWindow pw_date;
    private DatePicker datePicker;
    private TestSiteBean testSiteBean;
    private String[] testSites;
    private int examSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_test_stu_info);
        ButterKnife.bind(this);

        imvCusTitleBack.setOnClickListener(this);
        progressDialog = new MyProgressDialog(this,R.style.progress_dialog);
        waitTestFlag = getIntent().getIntExtra("waitTestFlag",0);
        stuIdNum = getIntent().getStringExtra("stuIdNum");
        if (waitTestFlag==0){
            llWaitTestWaitClass.setVisibility(View.GONE);
            llWaitTestTestInfo.setVisibility(View.GONE);
            tvCusTitleName.setText("待处理学员详情");
            tvWaitTestStuInfoName.setText(getIntent().getStringExtra("stuName"));
            tvWaitTestStuInfoIdNum.setText(stuIdNum);
            tvWaitTestStuInfoPhone.setText(getIntent().getStringExtra("stuPhone"));
            tvWaitTestStuInfoSignUpDate.setText(getIntent().getStringExtra("stuSignDate"));
            tvWaitTestStuInfoCarType.setText(getIntent().getStringExtra("stuCarType"));
        }
        if (waitTestFlag==1){
            tvCusTitleName.setText("待考学员详情");

            examSub = getIntent().getIntExtra("examSub",0);
            switch (examSub){
                case 1:
                    tvWaitTestStuInfoWaitClass.setText("科目一");
                    break;
                case 2:
                    tvWaitTestStuInfoWaitClass.setText("科目二");
                    break;
                case 3:
                    tvWaitTestStuInfoWaitClass.setText("科目三");
                    break;
                case 4:
                    tvWaitTestStuInfoWaitClass.setText("科目四");
                    break;
            }
            tvWaitTestStuInfoName.setText(getIntent().getStringExtra("stuName"));
            tvWaitTestStuInfoIdNum.setText(stuIdNum);
            tvWaitTestStuInfoPhone.setText(getIntent().getStringExtra("stuPhone"));
            tvWaitTestStuInfoSignUpDate.setText(getIntent().getStringExtra("stuSignDate"));
            tvWaitTestStuInfoCarType.setText(getIntent().getStringExtra("stuCarType"));


            llWaitTestWaitClass.setVisibility(View.VISIBLE);
            llWaitTestTestInfo.setVisibility(View.GONE);
            pv_date = LayoutInflater.from(this).inflate(R.layout.popup_date_picker,null);
            pw_date = new PopupWindow(pv_date, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pw_date.setFocusable(true);
            pw_date.setOutsideTouchable(false);
            ColorDrawable drawable = new ColorDrawable(0xb0000000);
            pw_date.setBackgroundDrawable(drawable);
            datePicker = (DatePicker) pv_date.findViewById(R.id.date_picker);
            pv_date.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
            pv_date.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);
            tvWaitTestStuInfoChangeDate.setOnClickListener(this);
            tvWaitTestStuInfoChangeSite.setOnClickListener(this);
            getWaitTestStuInfo();
        }
    }

    private void getWaitTestStuInfo(){
        progressDialog.show();
        parmaMap.put("stu_idnum",stuIdNum);
        parmaMap.put("exam_sub",examSub+"");
        parmaMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(parmaMap);
        Log.e("待考学员请求Json-->",paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.GET_WAIT_TEST_STU_INFO)
                .post(RequestBody.create(MEDIA_TYPE_JSON,paramsJson))
                .build();
        Call call = new MyOkHttpClient(WaitTestOptionStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });

    }
    //获取考试场地
    private void getTestSites(){
        progressDialog.show();
        Map<String,String> map = new HashMap<>();
        map.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TEST_SITES)
                .post(RequestBody.create(MEDIA_TYPE_JSON,new Gson().toJson(map)))
                .build();
        Call call = new MyOkHttpClient(WaitTestOptionStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                ToastUtils.ToastMessage(WaitTestOptionStuInfoActivity.this,"获取失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
                testSiteJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(1);
                call.cancel();
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("待考学员详情Json-->",responseJson.toString());
                    break;
                case 1:
                    testSiteBean = gson.fromJson(testSiteJson,TestSiteBean.class);
                    testSites = new String[testSiteBean.getData_list().size()];
                    for (int i = 0; i < testSiteBean.getData_list().size() ; i++) {
                        testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(WaitTestOptionStuInfoActivity.this);
                    builder.setTitle("选择考试场地")
                            .setItems(testSites, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tvWaitTestStuInfoTestSite.setText(testSiteBean.getData_list().get(i).getExam_site());
                                    //修改 考试场地...
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
            }
        }
    };

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.imv_cus_title_back:
        WaitTestOptionStuInfoActivity.this.finish();
            break;
        case R.id.tv_wait_test_stu_info_change_date:
            if (pw_date.isShowing()) {
                pw_date.dismiss();
            }
            pw_date.setAnimationStyle(R.style.anim_popwindow);
            pw_date.showAtLocation(findViewById(R.id.ll_wait_test_stu_info_container), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
            break;
        case R.id.tv_popup_datepicker_confirm:
            if (pw_date.isShowing()){
                pw_date.dismiss();
            }
            if (datePicker.getMonth() < 10) {
                if (datePicker.getDayOfMonth() < 10) {
                    tvWaitTestStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                } else {
                    tvWaitTestStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                }
            } else {
                if (datePicker.getDayOfMonth() < 10) {
                    tvWaitTestStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                } else {
                    tvWaitTestStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                }
            }
            //修改考试日期
            break;
        case R.id.tv_datepicker_cancel:
            if (pw_date.isShowing()){
                pw_date.dismiss();
            }
            break;
        case R.id.tv_wait_test_stu_info_change_site:
            getTestSites();
            break;
    }
    }
}
