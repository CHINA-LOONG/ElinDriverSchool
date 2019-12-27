package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.AppointTrainAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.EventDecorator;
import com.elin.elindriverschool.model.AppointTrainBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TrainScheduleListBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
 * 预约培训页面
 */
public class AppointTrainingStudentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_appoint_training)
    RecyclerView rvAppointTraining;
    @Bind(R.id.srl_appoint_training)
    SwipeRefreshLayout srlAppointTraining;
    @Bind(R.id.tv_empty_appoint)
    TextView tvEmptyAppoint;

    private Gson gson = new Gson();
    private Map<String, String> paramsMap = new HashMap<>();
    private String responseJson;//通过日期获取该日期的计划
    private String scheduleJson;//获取教练员发布过计划的日期列表
    private AppointTrainAdapter adapter;
    private List<AppointTrainBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    private AppointTrainBean bean;
    private int yearT, monthT, dayT;
    private String dateSelect;
    private MaterialCalendarView calendarView;
    private TextView tvSelectDate;
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    private HashSet dateSet = new HashSet();
    private HashMap<CalendarDay,String> strings = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_training_student);
        ButterKnife.bind(this);
        tvCusTitleName.setText("预约培训");
        adapter = new AppointTrainAdapter(dataList);
        srlAppointTraining.setOnRefreshListener(this);
        srlAppointTraining.setColorSchemeResources(android.R.color.holo_red_light);
        rvAppointTraining.setLayoutManager(new LinearLayoutManager(this));
        rvAppointTraining.setAdapter(adapter);
        addHeadview();
        loadData();
        rvAppointTraining.setAdapter(adapter);
        rvAppointTraining.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AppointTrainBean.DataBean.TimeListBean bean = (AppointTrainBean.DataBean.TimeListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("recordId", bean.getId());
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if (TextUtils.equals(today, bean.getTrain_date())) {
                    bundle.putString("canSign", "1");
                }
                goToActivity(AppointTrainingStudentActivity.this, AppointAlreadyStudentActivity.class, bundle);
            }
        });
        getTrainScheduleList();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String flag = bundle.getString("checkTemplateTo");
            if (TextUtils.equals("1", flag)) {
                loadData();
                getTrainScheduleList();
            }
        }
    }

    private void addHeadview() {
        dateSelect = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        View view = getLayoutInflater().inflate(R.layout.headview_appoint_training, null);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendar_appoint_training);
        tvSelectDate = (TextView) view.findViewById(R.id.tv_select_date);
        adapter.addHeaderView(view);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        calendarView.setSelectedDate(new Date());
        tvSelectDate.setText(dateSelect);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year = date.getYear();
                int month = date.getMonth();
                int day = date.getDay();
                if (month < 9 && day < 10) {
                    dateSelect = year + "-0" + (month + 1) + "-0" + day;
                } else if (month < 9) {
                    dateSelect = year + "-0" + (month + 1) + "-" + day;
                } else if (day < 10) {
                    dateSelect = year + "-" + (month + 1) + "-0" + day;
                } else {
                    dateSelect = year + "-" + (month + 1) + "-" + day;
                }
                tvSelectDate.setText(dateSelect);
                loadData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTrainScheduleList();
    }

    //获取教练员发布过计划的日期列表
    private void getTrainScheduleList() {
        Map<String, String> scheduleMap = new HashMap<>();
        scheduleMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TRAIN_SCHEDULELIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(scheduleMap)))
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
                scheduleJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });
    }

    //通过日期获取该日期的计划
    private void loadData() {
        srlAppointTraining.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("date", dateSelect);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TRAIN_MODEL)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(paramsMap)))
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
                    if (srlAppointTraining != null) {
                        srlAppointTraining.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, AppointTrainBean.class);
                        if (bean.getRc().equals("0")) {
                            if (bean.getData() != null) {
                                tvEmptyAppoint.setVisibility(View.GONE);
                                dataList = bean.getData().getTimeList();
                                int startCount = 1, endCount = 1;
                                for (int i = 0; i < dataList.size(); i++) {
                                    AppointTrainBean.DataBean.TimeListBean bean = dataList.get(i);
                                    String startTime = bean.getStart_time().substring(0, 2).toString();
                                    String endTime = bean.getEnd_time().substring(0, 2).toString();
                                    if (Integer.parseInt(endTime) <= 12) {
                                        bean.setDefine_flag(startCount + "");
                                        startCount++;
                                    } else if (Integer.parseInt(startTime) >= 12) {
                                        bean.setDefine_flag(endCount + "");
                                        endCount++;
                                    }
                                }
                                if (dataList != null && dataList.size() != 0) {
                                    adapter.setNewData(dataList);
                                    srlAppointTraining.setRefreshing(false);
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.loadMoreEnd(true);
                                }
                            } else {
                                adapter.setNewData(null);
                                tvEmptyAppoint.setVisibility(View.VISIBLE);
                            }

                        } else {
                            tvEmptyAppoint.setVisibility(View.VISIBLE);
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(AppointTrainingStudentActivity.this, "网络连接错误");
                    break;
                case 2:
                    TrainScheduleListBean bean = gson.fromJson(scheduleJson, TrainScheduleListBean.class);
                    Log.e("==scheduleJson-->", String.valueOf(scheduleJson));
                    BaseApplication.getInstance().setTrainSub(bean.getData().getTrain_sub());
                    if(bean.getData().isCan_publish()){
                        tvCusTitleRight.setVisibility(View.VISIBLE);
                        tvCusTitleRight.setBackgroundResource(R.mipmap.ic_more);
                    }else {
                        tvCusTitleRight.setVisibility(View.GONE);
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    for (int i = 0; i < bean.getData().getDates().size(); i++) {
                        try {
                            Date date = sdf.parse(bean.getData().getDates().get(i).getDate());
                            dateSet.add(CalendarDay.from(date));
                            strings.put(CalendarDay.from(date),bean.getData().getDates().get(i).getIs_all());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    calendarView.addDecorator(new EventDecorator(AppointTrainingStudentActivity.this,Color.RED, dateSet,strings,false));
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
                showPop(view);
                break;
        }
    }

    //点击按钮时弹出弹出菜单
    public void showPop(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        getMenuInflater().inflate(R.menu.menu_appoint_training, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_add_appoint://新增培训计划
                        goToActivity(AppointTrainingStudentActivity.this, AddAppointActivity.class);
                        break;
                    case R.id.appoint_templet://培训模板
                        goToActivity(AppointTrainingStudentActivity.this,AddTemplateActivity.class);
                        break;
                    case R.id.menu_my_all_appoint://我的全部计划
                        goToActivity(AppointTrainingStudentActivity.this,MyAllAppointActivity.class);
                        break;
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}

