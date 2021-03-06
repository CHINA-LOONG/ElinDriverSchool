package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CheckTemplateAdapter;
import com.elin.elindriverschool.adapter.CheckTemplateInsideAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * 培训模板（修改）
 */
public class AddTemplateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

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
    @Bind(R.id.rb_template_sub)
    RadioButton rbTemplateSub;
//    @Bind(R.id.rb_template_sub_three)
//    RadioButton rbTemplateSubThree;
//    @Bind(R.id.rb_template_sub_five)
//    RadioButton rbTemplateSubFive;
    @Bind(R.id.rg_add_template)
    RadioGroup rgAddTemplate;
    @Bind(R.id.tv_add_time_template)
    TextView tvAddTimeTemplate;
    @Bind(R.id.tv_save_template)
    TextView tvSaveTemplate;
    @Bind(R.id.rv_add_template)
    RecyclerView rvAddTemplate;
    @Bind(R.id.rv_check_template)
    RecyclerView rvCheckTemplate;
    @Bind(R.id.srl_save_template)
    SwipeRefreshLayout srlSaveTemplate;
    @Bind(R.id.scroll_save_template)
    ScrollView scrollSaveTemplate;
    private ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    private ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> addTemplateTimeList = new ArrayList<>();
    private CheckTemplateTimeBean.DataBean.TimeListBean bean;
    private CheckTemplateInsideAdapter adapter;
    private String startHour, startMinute, endHour, endMinute;
    private String personLimit, startTime, endTime;
    public static final int START_TIME_REQUESTCODE = 100;
    public static final int END_TIME_REQUESTCODE = 101;
    private CheckTemplateAdapter checkTemplateAdapter;
    private List<CheckTemplateTimeBean.DataBean> checkTemplateList = new ArrayList<>();
    private Gson gson = new Gson();
    private String responseJson, delResponse;

    public static final int CREATE_TEMPLATE_CODE = 100;
    private String flag;
    private String startDay;
    private String trainSub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_template);
        ButterKnife.bind(this);
        tvCusTitleName.setText("培训模板");
        trainSub = BaseApplication.getInstance().getTrainSub();
        edtPersonLimit.setSelection(edtPersonLimit.getText().length());
        adapter = new CheckTemplateInsideAdapter(this, dataList);
        rvAddTemplate.setLayoutManager(new LinearLayoutManager(this));
        rvAddTemplate.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_empty, rvAddTemplate);
        rvAddTemplate.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        rvAddTemplate.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.remove(position);
            }
        });

        if(!TextUtils.isEmpty(trainSub)){
            switch (trainSub){
                case "2":
                    rbTemplateSub.setText("科目二");
                    break;
                case "3":
                    rbTemplateSub.setText("科目三");
                    break;
                case "5":
                    rbTemplateSub.setText("科二和科三");
                    break;
            }
        }

        //已有模板列表
        checkTemplateAdapter = new CheckTemplateAdapter(this, checkTemplateList);
        srlSaveTemplate.setOnRefreshListener(this);
        srlSaveTemplate.setColorSchemeResources(android.R.color.holo_red_light);
        rvCheckTemplate.setLayoutManager(new LinearLayoutManager(this));
        rvCheckTemplate.setAdapter(checkTemplateAdapter);
//        rvCheckTemplate.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        checkTemplateAdapter.setEmptyView(R.layout.layout_empty, rvCheckTemplate);
        startDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        BaseApplication.getInstance().setStartDay(startDay);
        loadData(startDay);
        rvCheckTemplate.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CheckTemplateTimeBean.DataBean dataBean = (CheckTemplateTimeBean.DataBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_delete_appoint_template:
                        deleteDialog(dataBean.getId(), position);
                        break;
                }
            }
        });

        if (scrollSaveTemplate != null) {
            scrollSaveTemplate.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (srlSaveTemplate != null) {
                        srlSaveTemplate.setEnabled(scrollSaveTemplate.getScrollY() == 0);
                    }
                }
            });
        }

    }

    //删除已有模板
    private void deleteDialog(final String modelId, final int position) {
        final AlertDialog.Builder delDialog =
                new AlertDialog.Builder(this);
        delDialog.setTitle("删除");
        delDialog.setMessage("确定删除该模板吗?");
        delDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delTrainModel(modelId);
                        checkTemplateAdapter.remove(position);
                    }
                });
        delDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        delDialog.show();
    }

    @OnClick({R.id.imv_cus_title_back, R.id.tv_time_start, R.id.tv_end_time, R.id.tv_add_time_template, R.id.tv_save_template})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_time_start:
                popStartTimePicker();
                break;
            case R.id.tv_end_time:
                popEndTimePicker();
                break;
            case R.id.tv_add_time_template:
                if (isTimeTemplate()) {
                    bean = new CheckTemplateTimeBean.DataBean.TimeListBean();
                    bean.setPerson_limit(personLimit);
                    bean.setStart_time(startTime);
                    bean.setEnd_time(endTime);
                    bean.setTrain_sub(trainSub);
//                    if (rbTemplateSubTwo.isChecked()) {
//                        bean.setTrain_sub("2");
//                    } else if (rbTemplateSubThree.isChecked()) {
//                        bean.setTrain_sub("3");
//                    } else if (rbTemplateSubFive.isChecked()) {
//                        bean.setTrain_sub("5");
//                    }
                    adapter.addData(bean);
                    scrollSaveTemplate.smoothScrollTo(0,0);
                }
                break;
            case R.id.tv_save_template:
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
                            addTemplateTimeList.clear();
                            for (int i = 0; i < count; i++) {
                                TextView tvPersonLimit = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_inside_person_limit);
                                TextView tvStartTime = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_tv_start_time);
                                TextView tvEndTime = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_tv_end_time);
                                TextView tvSub = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_inside_sub);
                                bean = new CheckTemplateTimeBean.DataBean.TimeListBean();
//                                switch (tvSub.getText().toString()) {
//                                    case "科目二":
//                                        bean.setTrain_sub("2");
//                                        break;
//                                    case "科目三":
//                                        bean.setTrain_sub("3");
//                                        break;
//                                    case "科二和科三":
//                                        bean.setTrain_sub("5");
//                                        break;
//                                }
                                bean.setTrain_sub(trainSub);
                                bean.setPerson_limit(tvPersonLimit.getText().toString());
                                bean.setStart_time(tvStartTime.getText().toString());
                                bean.setEnd_time(tvEndTime.getText().toString());
                                addTemplateTimeList.add(bean);
                            }
                            Collections.sort(addTemplateTimeList, new Comparator<CheckTemplateTimeBean.DataBean.TimeListBean>() {
                                @Override
                                public int compare(CheckTemplateTimeBean.DataBean.TimeListBean o1, CheckTemplateTimeBean.DataBean.TimeListBean o2) {
                                    String startTime1 = o1.getStart_time();
                                    String startTime2 = o2.getStart_time();
                                    long startTimeTo=0,endTimeTo=0;
                                    Calendar calendar = Calendar.getInstance();
                                    try {
                                        calendar.setTime(new SimpleDateFormat("HH:mm").parse(startTime1));
                                        startTimeTo = calendar.getTimeInMillis();
                                        calendar.setTime(new SimpleDateFormat("HH:mm").parse(startTime2));
                                        endTimeTo = calendar.getTimeInMillis();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if(startTimeTo>endTimeTo){
                                        return 1;
                                    }else {
                                        return -1;
                                    }
                                }
                            });
                            addTrainModel(addTemplateTimeList);
                        }
                    });
                    builder.create().show();
                }
                break;
        }
    }

    //设置结束时间
    private void popEndTimePicker() {
        Intent intent = new Intent(this, DialogPickerView.class);
        startActivityForResult(intent, END_TIME_REQUESTCODE);
    }

    //设置开始时间
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

    //判断时间模板是否符合规范
    private boolean isTimeTemplate() {
        personLimit = edtPersonLimit.getText().toString().trim();
        startTime = tvStartTime.getText().toString().trim();
        endTime = tvEndTime.getText().toString().trim();
        long startTimeTo = 0, endTimeTo = 0, noonTime = 0, zeroPointTime = 0;
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
            noonTime = new SimpleDateFormat("HH:mm").parse("12:00").getTime();
            zeroPointTime = new SimpleDateFormat("HH:mm").parse("00:00").getTime();
            if (endTimeTo <= noonTime || startTimeTo >= noonTime) {

            } else {
                ToastUtils.ToastMessage(this, "以12点为分割线,不可跨越两个时段");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int count = adapter.getItemCount();
        long startTime = 0, endTime = 0, startOtherTime = 0, endOtherTime = 0;

        for (int i = 0; i < count; i++) {
            TextView tvTimeStart = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_tv_start_time);
            TextView tvTimeEnd = (TextView) adapter.getViewByPosition(rvAddTemplate, i, R.id.item_tv_end_time);
//            Calendar calendar = Calendar.getInstance();
            if (tvTimeStart != null && tvTimeEnd != null) {
                try {
                    calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvTimeStart.getText().toString().trim()));
                    startTime = calendar.getTimeInMillis();
                    calendar.setTime(new SimpleDateFormat("HH:mm").parse(tvTimeEnd.getText().toString().trim()));
                    endTime = calendar.getTimeInMillis();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (endTimeTo <= startTime || startTimeTo >= endTime) {

                } else {
                    ToastUtils.ToastMessage(this, "时间段有重合");
                    return false;
                }
            }
        }

        return true;
    }

    //判断模板内容是否符合规范
    private boolean testData() {
        if (dataList.size() == 0) {
            ToastUtils.ToastMessage(this, "请添加时间段");
            return false;
        }
        return true;
    }

    //保存模板
    private void addTrainModel(ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> list) {
        Map<String, Object> parmaMap = new HashMap<>();
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

    //加载已有模板
    private void loadData(String startDay) {
        srlSaveTemplate.setRefreshing(true);
        Map<String, Object> parmaMap = new HashMap<>();
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("start_day", startDay);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TRAIN_MODEL_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(parmaMap)))
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
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });
    }

    //删除已有模板
    private void delTrainModel(String modelId) {
        srlSaveTemplate.setRefreshing(true);
        Map<String, Object> parmaMap = new HashMap<>();
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("model_id", modelId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DEL_TRAIN_MODEL)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(parmaMap)))
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
                delResponse = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(3);
                call.cancel();
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (srlSaveTemplate != null && srlSaveTemplate.isRefreshing()) {
                srlSaveTemplate.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    loadData(startDay);
                    dataList.clear();
                    adapter.notifyDataSetChanged();
                    ToastUtils.ToastMessage(AddTemplateActivity.this, "保存模板成功");
                    break;
                case 1:
                    ToastUtils.ToastMessage(AddTemplateActivity.this, "请检查网络连接");
                    break;
                case 2:
                    Log.e(TAG,responseJson);
                    CheckTemplateTimeBean checkTemplateTimeBean = gson.fromJson(responseJson, CheckTemplateTimeBean.class);
                    if (checkTemplateTimeBean.getData() != null && checkTemplateTimeBean.getData().size() != 0) {
                        checkTemplateAdapter.setNewData(checkTemplateTimeBean.getData());
                    }
                    break;
                case 3:
                    CommonDataBean commonDataBean = gson.fromJson(delResponse, CommonDataBean.class);
                    if (commonDataBean.getRc().equals("0")) {
                        ToastUtils.ToastMessage(AddTemplateActivity.this, "删除成功");
                    } else {
                        ToastUtils.ToastMessage(AddTemplateActivity.this, commonDataBean.getDes());
                    }
                    break;
            }
        }
    };


    @Override
    public void onRefresh() {
        loadData(startDay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
