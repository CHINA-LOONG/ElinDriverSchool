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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.model.UploadGradeStuInfoBean;
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
 * Created by imac on 17/1/6.
 * 考试成绩
 */

public class UploadGradeStuInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_upload_grade_stu_info_class)
    TextView tvUploadGradeStuInfoClass;
    @Bind(R.id.tv_upload_grade_stu_info_test_date)
    TextView tvUploadGradeStuInfoTestDate;
    @Bind(R.id.tv_upload_grade_stu_info_change_date)
    TextView tvUploadGradeStuInfoChangeDate;
    @Bind(R.id.tv_upload_grade_stu_info_test_site)
    TextView tvUploadGradeStuInfoTestSite;
    @Bind(R.id.tv_upload_grade_stu_info_change_site)
    TextView tvUploadGradeStuInfoChangeSite;
    @Bind(R.id.tv_upload_grade_stu_info_score)
    TextView tvUploadGradeStuInfoScore;
    @Bind(R.id.btn_upload_grade_stu_info_submit)
    Button btnUploadGradeStuInfoSubmit;
    @Bind(R.id.ll_upload_grade_stu_info)
    LinearLayout llUploadGradeStuInfo;
    @Bind(R.id.ll_upload_grade_stu_info_container)
    LinearLayout llUploadGradeStuInfoContainer;

    private int examSub;
    private String stuIdNum,score;
    private int examScore = 1;

    private MyProgressDialog progressDialog;
    private Map<String,String> parmaMap = new HashMap<>();
    private Map<String,String> parmaSubmitMap = new HashMap<>();

    private String paramsJson,paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson,testSiteJson;
    private UploadGradeStuInfoBean uploadGradeStuInfoBean;

    private View pv_date;
    private PopupWindow pw_date;
    private DatePicker datePicker;
    private TestSiteBean testSiteBean;
    private String[] testSites;
    private String testDate;
    private String[] scoreArr = {"合格","不合格","缺考"};
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_grade_stu_info);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("考试成绩");
        examSub = getIntent().getIntExtra("examSub",0);
        stuIdNum = getIntent().getStringExtra("stuIdNum");
        score = getIntent().getStringExtra("score");
        testDate = getIntent().getStringExtra("testDate");
        builder = new AlertDialog.Builder(UploadGradeStuInfoActivity.this);
        switch (examSub){
            case 1:
                tvUploadGradeStuInfoClass.setText("科目一");
                break;
            case 2:
                tvUploadGradeStuInfoClass.setText("科目二");
                break;
            case 3:
                tvUploadGradeStuInfoClass.setText("科目三");
                break;
            case 4:
                tvUploadGradeStuInfoClass.setText("科目四");
                break;
        }
        switch (score){
            case "1":
                tvUploadGradeStuInfoScore.setText("合格");
                break;
            case "2":
                tvUploadGradeStuInfoScore.setText("不合格");
                break;
            case "3":
                tvUploadGradeStuInfoScore.setText("缺考");
                break;
        }
        pv_date = LayoutInflater.from(this).inflate(R.layout.popup_date_picker, null);
        pw_date = new PopupWindow(pv_date, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw_date.setFocusable(true);
        pw_date.setOutsideTouchable(false);
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        pw_date.setBackgroundDrawable(drawable);
        datePicker = (DatePicker) pv_date.findViewById(R.id.date_picker);
        pv_date.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
        pv_date.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);
        tvUploadGradeStuInfoChangeDate.setOnClickListener(this);
        tvUploadGradeStuInfoChangeSite.setOnClickListener(this);
        progressDialog = new MyProgressDialog(this,R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        btnUploadGradeStuInfoSubmit.setOnClickListener(this);
        tvUploadGradeStuInfoScore.setOnClickListener(this);
        tvUploadGradeStuInfoTestDate.setText(testDate);
//        getUploadGradeStuInfo();
    }

    private void getUploadGradeStuInfo(){
        progressDialog.show();
        parmaMap.put("stu_idnum",stuIdNum);
        parmaMap.put("exam_sub",examSub+"");
        parmaMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(parmaMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.UPLOAD_GRADE_STU_INFO)
                .post(RequestBody.create(MEDIA_TYPE_JSON,paramsJson))
                .build();
        Call call = new MyOkHttpClient(UploadGradeStuInfoActivity.this).newCall(request);
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

    //提交学员成绩
    private  void submitUploadStuGrade(){
        progressDialog.show();
        parmaSubmitMap.put("token", BaseApplication.getInstance().getToken());
        parmaSubmitMap.put("coach_idnum",BaseApplication.getInstance().getCoachIdNum());
        parmaSubmitMap.put("exam_sub",examSub+"");
        parmaSubmitMap.put("stu_idnum",stuIdNum);
        parmaSubmitMap.put("exam_date",tvUploadGradeStuInfoTestDate.getText().toString());
//        parmaSubmitMap.put("exam_site",tvUploadGradeStuInfoTestSite.getText().toString());
        parmaSubmitMap.put("exam_score",examScore+"");
        parmaSubmitMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsSubmitJson = gson.toJson(parmaSubmitMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.UPLOAD_GRADE_BY_INFO)
                .post(RequestBody.create(MEDIA_TYPE_JSON,paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(UploadGradeStuInfoActivity.this).newCall(request);
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
                mHandler.sendEmptyMessage(1);
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
        Call call = new MyOkHttpClient(UploadGradeStuInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this, "获取失败，请重试");
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
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("上传成绩学员详情Json-->",responseJson);

                    uploadGradeStuInfoBean = gson.fromJson(responseJson,UploadGradeStuInfoBean.class);
                    if (uploadGradeStuInfoBean.getRc()==0){
                        if (uploadGradeStuInfoBean.getData()!=null){
                            tvUploadGradeStuInfoTestDate.setText(uploadGradeStuInfoBean.getData().getOrder_date());
                            tvUploadGradeStuInfoTestSite.setText(uploadGradeStuInfoBean.getData().getOrder_site());
                        }else {
                            ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this,"获取失败，请重试");
                        }
                    }else {
                        ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this,"获取失败，请重试");
                    }
                    break;
                case 1:
                    Log.e("上传成绩学员详情Json-->",responseJson);
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))){
                            ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this,"学员成绩提交成功");
                            setResult(RESULT_OK);
                            UploadGradeStuInfoActivity.this.finish();
                        }else {
                            ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this,"学员成绩提交失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    testSiteBean = gson.fromJson(testSiteJson, TestSiteBean.class);
                    testSites = new String[testSiteBean.getData_list().size()];
                    for (int i = 0; i < testSiteBean.getData_list().size(); i++) {
                        testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(UploadGradeStuInfoActivity.this);
                    builder.setTitle("选择考试场地")
                            .setItems(testSites, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tvUploadGradeStuInfoTestSite.setText(testSiteBean.getData_list().get(i).getExam_site());
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
        UploadGradeStuInfoActivity.this.finish();
        break;
    case R.id.tv_upload_grade_stu_info_change_date:
        if (pw_date.isShowing()) {
            pw_date.dismiss();
        }
        pw_date.setAnimationStyle(R.style.anim_popwindow);
        pw_date.showAtLocation(findViewById(R.id.ll_upload_grade_stu_info_container), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
        break;
    case R.id.tv_popup_datepicker_confirm:
        if (pw_date.isShowing()) {
            pw_date.dismiss();
        }
        if (datePicker.getMonth() < 10) {
            if (datePicker.getDayOfMonth() < 10) {
                tvUploadGradeStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
            } else {
                tvUploadGradeStuInfoTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
            }
        } else {
            if (datePicker.getDayOfMonth() < 10) {
                tvUploadGradeStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
            } else {
                tvUploadGradeStuInfoTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
            }
        }
        break;
    case R.id.tv_datepicker_cancel:
        if (pw_date.isShowing()) {
        pw_date.dismiss();
    }
        break;
    case R.id.tv_upload_grade_stu_info_change_site:
        getTestSites();
        break;
    case R.id.tv_upload_grade_stu_info_score:
        builder.setTitle("选择成绩")
                .setItems(scoreArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i==0){
                            tvUploadGradeStuInfoScore.setText("合格");
                            examScore = 1;
                        }
                        if (i==1){
                            tvUploadGradeStuInfoScore.setText("不合格");
                            examScore = 2;
                        }
                        if (i==2){
                            tvUploadGradeStuInfoScore.setText("缺考");
                            examScore = 3;
                        }
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
    case R.id.btn_upload_grade_stu_info_submit:
        if (tvUploadGradeStuInfoTestDate.getText().toString().length()==0){
            ToastUtils.ToastMessage(UploadGradeStuInfoActivity.this,"请选择考试日期");
        }else {
            builder.setTitle("操作提示")
                    .setMessage("确定要提交吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            submitUploadStuGrade();//提交学员成绩
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }
        break;
}
    }
}
