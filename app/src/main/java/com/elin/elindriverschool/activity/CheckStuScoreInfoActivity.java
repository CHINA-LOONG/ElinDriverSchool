package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

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
 * 学员成绩核对
 */

public class CheckStuScoreInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_stu_score_info_test_class)
    TextView tvStuScoreInfoTestClass;
    @Bind(R.id.tv_stu_score_info_test_date)
    TextView tvStuScoreInfoTestDate;
    @Bind(R.id.tv_stu_score_info_test_score)
    TextView tvStuScoreInfoTestScore;
    @Bind(R.id.textView3)
    TextView textView3;

//    String scoreClass;
    int examSub;
    String examScore;
    String testDate,stuIdNum;



    MyProgressDialog myProgressDialog;
    private Map<String,String> paramsMap = new HashMap<>();
    private String paramsJson,responseJson;

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private Gson gson = new Gson();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_score_info);
        ButterKnife.bind(this);
        tvCusTitleName.setText("学员成绩");
        imvCusTitleBack.setOnClickListener(this);

        examSub = getIntent().getIntExtra("examSub",0);
        examScore = getIntent().getStringExtra("examScore");
        testDate = getIntent().getStringExtra("testDate");
        stuIdNum = getIntent().getStringExtra("stuIdNum");

        myProgressDialog = new MyProgressDialog(this,R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);

        switch (examSub){
            case 1:
                tvStuScoreInfoTestClass.setText("科目一");
                break;
            case 2:
                tvStuScoreInfoTestClass.setText("科目二");
                break;
            case 3:
                tvStuScoreInfoTestClass.setText("科目三");
                break;
            case 4:
                tvStuScoreInfoTestClass.setText("科目四");
                break;
        }

        tvStuScoreInfoTestDate.setText(testDate);
        switch (examScore){
            case "1":
                tvStuScoreInfoTestScore.setText("合格");
                break;
            case "2":
                tvStuScoreInfoTestScore.setText("不合格");
                break;
            case "3":
                tvStuScoreInfoTestScore.setText("未考试");
                break;
        }
        textView3.setOnClickListener(this);
    }

    private void verifyStuScore(String grade){

        paramsMap.put("token", BaseApplication.getInstance().getToken());
//        paramsMap.put("coach_idnum",BaseApplication.getInstance().getCoachIdNum());
        paramsMap.put("exam_sub",examSub+"");
        paramsMap.put("stu_idnum",stuIdNum);
        paramsMap.put("exam_date",testDate);
        paramsMap.put("exam_score",grade+"");
        paramsJson = gson.toJson(paramsMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.CHECK_STU_GRADE_CHECK)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(CheckStuScoreInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
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
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if (jsonObject.getString("rc").equals("0")){
                            ToastUtils.ToastMessage(CheckStuScoreInfoActivity.this,"核对成功");
                            finish();
                        }else {
                            ToastUtils.ToastMessage(CheckStuScoreInfoActivity.this,"核对失败");
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
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.textView3:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("成绩核对");
                final String[] array = new String[2];
                if(TextUtils.equals("1",examScore)){
                    array[0] = "不合格";
                    array[1] = "未考试";
                }else if(TextUtils.equals("2",examScore)){
                    array[0] = "合格";
                    array[1] = "未考试";
                }else if(TextUtils.equals("3",examScore)){
                    array[0] = "合格";
                    array[1] = "不合格";
                }
                builder.setSingleChoiceItems(array, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(TextUtils.equals(array[which],"合格")){
                            verifyStuScore("1");
                        }else if(TextUtils.equals(array[which],"不合格")){
                            verifyStuScore("2");
                        }else if(TextUtils.equals(array[which],"未考试")){
                            verifyStuScore("3");
                        }
                    }
                });
                builder.create().show();
                break;
        }
    }
}
