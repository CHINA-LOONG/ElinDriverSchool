package com.elin.elindriverschool.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.MyAllAppointAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyAllAppointBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MySwipeRefreshLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import static com.elin.elindriverschool.activity.MyDriveStudentActivity.MEDIA_TYPE_JSON;

/**
 * 我的全部计划
 */
public class MyAllAppointActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.sp_my_stu)
    Spinner spMyStu;
    @Bind(R.id.tv_all_appoint_start_date)
    TextView tvAllAppointStartDate;
    @Bind(R.id.ll_my_stu_start_date)
    LinearLayout llMyStuStartDate;
    @Bind(R.id.tv_all_appoint_end_date)
    TextView tvAllAppointEndDate;
    @Bind(R.id.ll_my_stu_end_date)
    LinearLayout llMyStuEndDate;
    @Bind(R.id.tv_all_appoint_check)
    TextView tvAllAppointCheck;
    @Bind(R.id.rv_all_appoint)
    RecyclerView rvAllAppoint;
    @Bind(R.id.srl_all_appoint)
    MySwipeRefreshLayout srlAllAppoint;

    private String[] templateSub;
    private String date;
    private int yearT, monthT, dayT;//今天的时间
    private Map<String, String> paramsMap = new HashMap<>();
    private String paramsJson;
    private Gson gson = new Gson();
    private String responseJson;
    private String startDate="",endDate="",trainSub="";
    private int pageNo = 1;
    private MyAllAppointAdapter adapter;
    private List<MyAllAppointBean.DataBean> dataList = new ArrayList<>();
    private MyAllAppointBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_appoint);
        ButterKnife.bind(this);
        tvCusTitleName.setText("我的全部计划");
        templateSub = getResources().getStringArray(R.array.template_sub);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, templateSub);
        adapter1.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        spMyStu.setAdapter(adapter1);
        spMyStu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        trainSub = "";
                        break;
                    case 1:
                        trainSub = "2";
                        break;
                    case 2:
                        trainSub = "3";
                        break;
                    case 3:
                        trainSub = "5";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        adapter = new MyAllAppointAdapter(this,dataList);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlAllAppoint.setOnRefreshListener(this);
        srlAllAppoint.setColorSchemeResources(android.R.color.holo_red_light);
        rvAllAppoint.setLayoutManager(new LinearLayoutManager(this));
        rvAllAppoint.setAdapter(adapter);
        rvAllAppoint.addItemDecoration(new DividerItemDecoration(MyAllAppointActivity.this, DividerItemDecoration.VERTICAL_LIST));
        adapter.setEmptyView(R.layout.layout_empty,rvAllAppoint);
        searchAppoint(pageNo);

        rvAllAppoint.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                srlAllAppoint.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    /**
     *
     * @param textView
     * @param flag  区分开始或者结束日期 来进行时间联动  开始0，结束1
     */
    public void setDate(final TextView textView,int flag) {
        startDate = tvAllAppointStartDate.getText().toString().trim();
        endDate = tvAllAppointEndDate.getText().toString().trim();
        Calendar cal = Calendar.getInstance();
        yearT = cal.get(Calendar.YEAR);
        monthT = cal.get(Calendar.MONTH);
        dayT = cal.get(Calendar.DATE);
        DatePickerDialog startDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (monthOfYear < 9 && dayOfMonth < 10) {
                    date = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if (monthOfYear < 9) {
                    date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    date = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else {
                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }

                textView.setText(date);
            }
        }, yearT, monthT, dayT);
        if(flag==0){
            if(!TextUtils.equals("结束日期",endDate)){
                try {
                    long endTime = new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime();
                    startDialog.getDatePicker().setMaxDate(endTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }else if(flag==1){
            if(!TextUtils.equals("起始日期",startDate)){
                try {
                    long startTime = new SimpleDateFormat("yyyy-MM-dd").parse(startDate).getTime();
                    startDialog.getDatePicker().setMinDate(startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        startDialog.show();
    }
    @OnClick({R.id.imv_cus_title_back, R.id.tv_all_appoint_start_date, R.id.tv_all_appoint_end_date, R.id.tv_all_appoint_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_all_appoint_start_date:
                setDate(tvAllAppointStartDate,0);
                break;
            case R.id.tv_all_appoint_end_date:
                setDate(tvAllAppointEndDate,1);
                break;
            case R.id.tv_all_appoint_check:
                startDate = tvAllAppointStartDate.getText().toString().trim();
                endDate = tvAllAppointEndDate.getText().toString().trim();
                pageNo = 1;
                searchAppoint(pageNo);
                break;
        }
    }

    private void searchAppoint(int pageNo) {
        srlAllAppoint.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", pageNo + "");
        paramsMap.put("pagesize", "20");
        paramsMap.put("start_date", startDate);
        paramsMap.put("end_date", endDate);
        paramsMap.put("train_sub",trainSub );
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.QUERY_MY_TRAIN_PLAN)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
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
                    if (srlAllAppoint != null) {
                        srlAllAppoint.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, MyAllAppointBean.class);
                        if (bean.getRc().equals("0")) {
                            dataList = bean.getData();
                            if (dataList != null&&dataList.size()!=0) {
                                if (pageNo == 1) {
                                    adapter.setNewData(dataList);
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.addData(dataList);
                                    adapter.loadMoreComplete();
                                }
                            }else {
                                if(pageNo==1){
                                    adapter.setNewData(dataList);
                                    adapter.loadMoreEnd(true);
                                }else {
                                    adapter.loadMoreEnd(false);
                                }
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(MyAllAppointActivity.this, "网络连接错误");
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        searchAppoint(pageNo);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo = pageNo + 1;
        searchAppoint(pageNo);
    }
}
