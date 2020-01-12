package com.elin.elindriverschool.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CheckTemplateInsideAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import static com.elin.elindriverschool.activity.CheckStudentGradeActivity.MEDIA_TYPE_JSON;

/**
 * 添加模板内容
 */
public class AddTemplateTimeActivity extends BaseActivity {


    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.edt_template_name)
    EditText edtTemplateName;
    @Bind(R.id.ll_add_template_time)
    LinearLayout llAddTemplateTime;
    @Bind(R.id.edt_person_limit)
    EditText edtPersonLimit;
    @Bind(R.id.tv_time_start)
    TextView tvStartTime;
    @Bind(R.id.tv_end_time)
    TextView tvEndTime;
    @Bind(R.id.rb_template_sub_two)
    RadioButton rbTemplateSubTwo;
    @Bind(R.id.rb_template_sub_three)
    RadioButton rbTemplateSubThree;
    @Bind(R.id.rb_template_sub_five)
    RadioButton rbTemplateSubFive;
    @Bind(R.id.rg_add_template)
    RadioGroup rgAddTemplate;
    @Bind(R.id.tv_add_time_template)
    TextView tvAddTimeTemplate;
    @Bind(R.id.rv_add_template_time)
    RecyclerView rvAddTemplateTime;
    private ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    private ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> addTemplateTimeList = new ArrayList<>();
    private CheckTemplateTimeBean.DataBean.TimeListBean bean;
    private CheckTemplateInsideAdapter adapter;
    private String startHour, startMinute, endHour, endMinute;
    public static final int START_TIME_REQUESTCODE = 100;
    public static final int END_TIME_REQUESTCODE = 101;
    private Dialog saveDialog;
    private String responseJson;
    private String personLimit,startTime,endTime;
    private Map<String, Object> parmaMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template_time);
        ButterKnife.bind(this);
        tvCusTitleName.setText("添加模板内容");
        tvCusTitleRight.setText("保存");
        edtPersonLimit.setSelection(edtPersonLimit.getText().length());
        adapter = new CheckTemplateInsideAdapter(this,dataList);
        rvAddTemplateTime.setLayoutManager(new LinearLayoutManager(this));
        rvAddTemplateTime.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_empty,rvAddTemplateTime);
        rvAddTemplateTime.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvAddTemplateTime.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.remove(position);
            }
        });
    }

    private void popEndTimePicker() {
        Intent intent = new Intent(this, DialogPickerView.class);
        startActivityForResult(intent, END_TIME_REQUESTCODE);
    }

    private void popStartTimePicker() {
        Intent intent = new Intent(this, DialogPickerView.class);
        startActivityForResult(intent, START_TIME_REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == START_TIME_REQUESTCODE) {
                startHour = data.getStringExtra("hour");
                startMinute = data.getStringExtra("minute");
                tvStartTime.setText(startHour + ":" + startMinute);
            } else if (requestCode == END_TIME_REQUESTCODE) {
                endHour = data.getStringExtra("hour");
                endMinute = data.getStringExtra("minute");
                tvEndTime.setText(endHour + ":" + endMinute);
            }
        }

    }

    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right, R.id.tv_add_time_template,R.id.tv_time_start,R.id.tv_end_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_cus_title_right:
                if (testData()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("保存");
                    builder.setMessage("是否保存这些模板?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int count = adapter.getItemCount();
                            for (int i = 0; i < count; i++) {
                                TextView tvPersonLimit = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_inside_person_limit);
                                TextView tvStartTime = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_tv_start_time);
                                TextView tvEndTime = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_tv_end_time);
                                TextView tvSub = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_inside_sub);
                                bean = new CheckTemplateTimeBean.DataBean.TimeListBean();
                                switch (tvSub.getText().toString()){
                                    case "科目二":
                                        bean.setTrain_sub("2");
                                        break;
                                    case "科目三":
                                        bean.setTrain_sub("3");
                                        break;
                                    case "科二和科三":
                                        bean.setTrain_sub("5");
                                        break;
                                }
                                bean.setPerson_limit(tvPersonLimit.getText().toString());
                                bean.setStart_time(tvStartTime.getText().toString());
                                bean.setEnd_time(tvEndTime.getText().toString());
                                addTemplateTimeList.add(bean);
                            }
                            addTrainModel(addTemplateTimeList);
                        }
                    });
                    builder.create().show();
                }
                break;
            case R.id.tv_add_time_template:
                if(isTimeTemplate()){
                    bean = new CheckTemplateTimeBean.DataBean.TimeListBean();
                    bean.setPerson_limit(personLimit);
                    bean.setStart_time(startTime);
                    bean.setEnd_time(endTime);
                    if (rbTemplateSubTwo.isChecked()) {
                        bean.setTrain_sub("2");
                    } else if (rbTemplateSubThree.isChecked()) {
                        bean.setTrain_sub("3");
                    } else if (rbTemplateSubFive.isChecked()) {
                        bean.setTrain_sub("5");
                    }
                    adapter.addData(bean);
                }
                break;
            case R.id.tv_time_start:
                popStartTimePicker();
                break;
            case R.id.tv_end_time:
                popEndTimePicker();
                break;
        }
    }

    //判断时间模板是否符合规范
    private boolean isTimeTemplate() {
        personLimit = edtPersonLimit.getText().toString().trim();
        startTime = tvStartTime.getText().toString().trim();
        endTime = tvEndTime.getText().toString().trim();
        long startTimeTo = 0, endTimeTo = 0,noonTime=0,zeroPointTime = 0;
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("HH:mm").parse(startTime));
            startTimeTo = calendar.getTimeInMillis();
            calendar.setTime(new SimpleDateFormat("HH:mm").parse(endTime));
            endTimeTo = calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(personLimit) && Integer.parseInt(personLimit) > 0) {
            ToastUtils.ToastMessage(this, "请设置大于0的人数");
            return false;
        }
        if (TextUtils.equals("点击设置", startTime) || TextUtils.equals("点击设置", endTime)) {
            ToastUtils.ToastMessage(this, "请设置时间");
            return false;
        }
        if (startTimeTo >= endTimeTo) {
            ToastUtils.ToastMessage(this, "开始时间应小于结束时间");
            return false;
        }
        try {
            noonTime =  new SimpleDateFormat("HH:mm").parse("12:00").getTime();
            zeroPointTime =  new SimpleDateFormat("HH:mm").parse("00:00").getTime();
            if(endTimeTo<=noonTime||startTimeTo>=noonTime){

            }else {
                ToastUtils.ToastMessage(this, "以12点为分割线,不可跨越两个时段");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    //判断模板内容是否符合规范
    private boolean testData() {
        int count = adapter.getItemCount();
        long startTime = 0, endTime = 0, startOtherTime = 0, endOtherTime = 0;
        if (dataList.size()==0) {
            ToastUtils.ToastMessage(this, "请添加时间段");
            return false;
        }
        for (int i = 0; i < count; i++) {
            TextView tvTimeStart = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_tv_start_time);
            TextView tvTimeEnd = (TextView) adapter.getViewByPosition(rvAddTemplateTime, i, R.id.item_tv_end_time);
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvTimeStart.getText().toString().trim()));
                startTime = calendar.getTimeInMillis();
                calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvTimeEnd.getText().toString().trim()));
                endTime = calendar.getTimeInMillis();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < count; j++) {
                if (j != i) {
                    TextView tvOtherTimeStart = (TextView) adapter.getViewByPosition(rvAddTemplateTime, j, R.id.item_tv_start_time);
                    TextView tvOtherTimeEnd = (TextView) adapter.getViewByPosition(rvAddTemplateTime, j, R.id.item_tv_end_time);
                    try {
                        calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvOtherTimeStart.getText().toString().trim()));
                        startOtherTime = calendar.getTimeInMillis();
                        calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvOtherTimeEnd.getText().toString().trim()));
                        endOtherTime = calendar.getTimeInMillis();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (endOtherTime <= startTime || startOtherTime >= endTime) {

                    } else {
                        ToastUtils.ToastMessage(this, "时间段重合");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //保存模板
    private void addTrainModel(ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> list) {
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("name", edtTemplateName.getText().toString().trim());
        parmaMap.put("time_list", list);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.ADD_TRAIN_MODEL)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(parmaMap)))
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
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                    break;
                case 1:
                    ToastUtils.ToastMessage(AddTemplateTimeActivity.this, "请检查网络连接");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (saveDialog != null) {
            saveDialog.dismiss();
        }
        mHandler.removeCallbacksAndMessages(null);
    }
}
