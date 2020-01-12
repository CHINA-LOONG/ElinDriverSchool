package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CrouseModelAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.bean.ModelBean;
import com.elin.elindriverschool.model.request.GetSettingsRequest;
import com.elin.elindriverschool.model.request.SetSettingsRequest;
import com.elin.elindriverschool.model.response.GetSettingsResponse;
import com.elin.elindriverschool.model.TrainModelBean;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

public class AppointmentSettingActivity extends AppCompatActivity {

    private MyProgressDialog progressDialog;


    @Bind(R.id.tv_title_text)
    TextView tvTitle;
    @Bind(R.id.ll_appointment)
    LinearLayout llAppointment;
    @Bind(R.id.cl_course)
    ConstraintLayout clCourse;

    @Bind(R.id.tv_appointment_weeks)
    TextView tvWeeks;
    @Bind(R.id.tv_appointment_times)
    TextView tvTimes;

    @Bind(R.id.cl_weeks_1)
    ConstraintLayout clWeeks1;
    @Bind(R.id.cl_weeks_2)
    ConstraintLayout clWeeks2;
    @Bind(R.id.cl_weeks_3)
    ConstraintLayout clWeeks3;
    @Bind(R.id.cl_weeks_4)
    ConstraintLayout clWeeks4;
    @Bind(R.id.cl_weeks_5)
    ConstraintLayout clWeeks5;
    @Bind(R.id.cl_weeks_6)
    ConstraintLayout clWeeks6;
    @Bind(R.id.cl_weeks_7)
    ConstraintLayout clWeeks7;

    @Bind(R.id.iv_weeks_1)
    ImageView ivWeeks1;
    @Bind(R.id.iv_weeks_2)
    ImageView ivWeeks2;
    @Bind(R.id.iv_weeks_3)
    ImageView ivWeeks3;
    @Bind(R.id.iv_weeks_4)
    ImageView ivWeeks4;
    @Bind(R.id.iv_weeks_5)
    ImageView ivWeeks5;
    @Bind(R.id.iv_weeks_6)
    ImageView ivWeeks6;
    @Bind(R.id.iv_weeks_7)
    ImageView ivWeeks7;

    @Bind(R.id.rv_crouse)
    RecyclerView rvCrouse;

    private boolean showAppointment = false;
    private boolean showCourse = false;


    private Gson gson = new Gson();

    private String settingsJson = "";
    private GetSettingsResponse getSettingsResponse;

    private ModelBean modelBean = null;

    private CrouseModelAdapter crouseModelAdapter=null;


    private List<Boolean> weeksState;
    private List<ConstraintLayout> weeksItemList;
    private List<ImageView> weeksItemSelects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_setting);
        ButterKnife.bind(this);
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);

        tvTitle.setText("设置");

        llAppointment.setVisibility(showAppointment ? View.VISIBLE : View.GONE);
        clCourse.setVisibility(showCourse ? View.VISIBLE : View.GONE);


        initView();

        loadSettingsData();
    }

    void initView(){
        weeksItemList = Arrays.asList(clWeeks1,clWeeks2,clWeeks3,clWeeks4,clWeeks5,clWeeks6,clWeeks7);
        weeksItemSelects = Arrays.asList(ivWeeks1,ivWeeks2,ivWeeks3,ivWeeks4,ivWeeks5,ivWeeks6,ivWeeks7);
        for (ConstraintLayout cl:weeksItemList){
            cl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = weeksItemList.indexOf(view);
                    weeksState.set(index,!weeksState.get(index));
                    weeksItemSelects.get(index).setImageResource(weeksState.get(index)?R.drawable.xzxy_xz:R.drawable.xzxy_wxz);
                    updateWeeks();
                    String weeksStr = "";
                    for (int i=0;i<weeksState.size();i++){
                        if (weeksState.get(i)){
                            if (weeksStr.isEmpty()){
                                weeksStr+=String.valueOf(i+1);
                            }else {
                                weeksStr+=","+String.valueOf(i+1);
                            }
                        }
                    }
                    getSettingsResponse.getData().setWeek(weeksStr);
                }
            });
        }

    }

//    void initCrouse(){
//
//    }


    void updateAppointmentOfWeeks() {
        String weeks = getSettingsResponse.getData().getWeek();
        weeks = weeks==null?"":weeks;
        List<String> strArray = Arrays.asList(weeks.split(","));
        weeksState = new ArrayList<>(Arrays.asList(new Boolean[7]));

        for (int i = 0; i < weeksState.size(); i++) {
            String weeksNum = String.valueOf(i + 1);
            weeksState.set(i, strArray.contains(weeksNum));
            weeksItemSelects.get(i).setImageResource(weeksState.get(i) ? R.drawable.xzxy_xz : R.drawable.xzxy_wxz);
        }

        updateWeeks();
    }

    void updateAppointmentOfTimes() {
        tvTimes.setText(getSettingsResponse.getData().getNum());
    }

    void updateAppointmentOfCourse(){
        if (getSettingsResponse==null){
            loadSettingsData();
            return;
        }
        List<ModelBean> models = getSettingsResponse.getData().getModel();
        if (models!=null&&models.size()>0){
            modelBean = models.get(0);
        }else {
            modelBean = new ModelBean();
        }
        if (crouseModelAdapter==null){
            crouseModelAdapter = new CrouseModelAdapter(this,modelBean.time_list);
            rvCrouse.setAdapter(crouseModelAdapter);
        }
    }

    void updateWeeks() {
        String strWeeks = "";
        for (int i = 0; i < weeksState.size(); i++) {
            boolean isSelect = weeksState.get(i);
            if (isSelect) {
                if (strWeeks.isEmpty()) {
                    strWeeks += WeeksNumToString(String.valueOf(i + 1));
                } else {
                    strWeeks += "," + WeeksNumToString(String.valueOf(i + 1));
                }
            }
        }
        if (strWeeks.isEmpty()) {
            tvWeeks.setText("未设置");
        } else {
            tvWeeks.setText(strWeeks);
        }
    }

    void loadSettingsData() {
        progressDialog.show();

        GetSettingsRequest param = new GetSettingsRequest();
        param.coach_idnum = BaseApplication.getInstance().getCoachIdNum();
        param.school_id =BaseApplication.getInstance().getSchoolId();
        param.token =BaseApplication.getInstance().getToken();

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_COURSE_SYSTEM)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(param)))
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mGetSettingsHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                settingsJson = String.valueOf(response.body().string());
                mGetSettingsHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }
    private Handler mGetSettingsHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.i("输出获取设置内容：", settingsJson);
                    getSettingsResponse = gson.fromJson(settingsJson, GetSettingsResponse.class);
                    updateAppointmentOfWeeks();
                    updateAppointmentOfTimes();
                    updateAppointmentOfCourse();
                    break;
                case 1:
                    break;
            }
        }
    };

    void setSettingsData(){
        progressDialog.show();

        SetSettingsRequest param = new SetSettingsRequest();
        param.token =BaseApplication.getInstance().getToken();
        param.school_id =BaseApplication.getInstance().getSchoolId();
        param.coach_idnum = BaseApplication.getInstance().getSchoolId();
        param.week = getSettingsResponse.getData().getWeek();
        param.trainNums = getSettingsResponse.getData().getNum();

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.SET_COURSE_SYSTEM)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(param)))
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mSetSettingsHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                Log.i("设置输出：",response.body().string());
                mSetSettingsHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }
    private Handler mSetSettingsHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };


    @OnClick({R.id.iv_title_back, R.id.tv_save, R.id.cl_edit_weeks, R.id.cl_edit_times, R.id.cl_edit_course,R.id.btn_crouse_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_save:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("操作提示")
                        .setMessage("确认要提交吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if (testData()) {
//                                    submitStuTest();
//                                }
                                dialogInterface.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create().show();
//                setSettingsData();
                break;
            case R.id.cl_edit_weeks:
                showAppointment = !showAppointment;
                llAppointment.setVisibility(showAppointment ? View.VISIBLE : View.GONE);
                break;
            case R.id.cl_edit_times:
                break;
            case R.id.cl_edit_course:
                showCourse = !showCourse;
                clCourse.setVisibility(showCourse ? View.VISIBLE : View.GONE);
                break;
            case R.id.btn_crouse_add:
                if (modelBean==null){
                    updateAppointmentOfCourse();
                }
                else {
                    ModelBean.TimeBean timeBean = new ModelBean.TimeBean();
                    modelBean.time_list.add(timeBean);
                    crouseModelAdapter.notifyItemRangeInserted(modelBean.time_list.size()-1,1);
                }
                break;
        }
    }

    String WeeksNumToString(String weeksNum) {
        String temp = "";
        switch (Integer.valueOf(weeksNum)) {
            case 1:
                temp = "周一";
                break;
            case 2:
                temp = "周二";
                break;
            case 3:
                temp = "周三";
                break;
            case 4:
                temp = "周四";
                break;
            case 5:
                temp = "周五";
                break;
            case 6:
                temp = "周六";
                break;
            case 7:
                temp = "周日";
                break;
        }
        return temp;
    }
}
