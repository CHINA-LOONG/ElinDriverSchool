package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.AddAppointAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.EventDecorator;
import com.elin.elindriverschool.model.AddTrainScheduleBean;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.GetBeforeDayBean;
import com.elin.elindriverschool.model.TrainScheduleListBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE;

/**
 * 新增培训计划
 */
public class AddAppointActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_appoint_training)
    RecyclerView rvAppointTraining;
    @Bind(R.id.tv_empty_appoint)
    TextView tvEmptyAppoint;
    @Bind(R.id.srl_appoint_training)
    SwipeRefreshLayout srlAppointTraining;
    private String dateSelect;
    private List<String> dateList = new ArrayList<>();
    private List<Long> longArrayList = new ArrayList<>();
    private String scheduleJson;//获取教练员发布过计划的日期列表
    private String responseJson;
    private String getBeforeDayJson;//获取提前发布的天数
    private Gson gson = new Gson();
    private HashSet dateSet = new HashSet();
    private MaterialCalendarView calendarView;
    private Map<String, String> parmaMap = new HashMap<>();
    private List<CheckTemplateTimeBean.DataBean> dataList = new ArrayList<>();
    private AddAppointAdapter adapter;
    private HashMap<Integer, Boolean> isCheckedMap = new HashMap<>();
    private String startDay="";
    private HashMap<CalendarDay,String> strings = new HashMap<>();
    Calendar now;
    String format;
    private String minDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_training_student);
        ButterKnife.bind(this);
        tvCusTitleName.setText("新增培训计划");

        adapter = new AddAppointAdapter(this, dataList);
        srlAppointTraining.setOnRefreshListener(this);
        srlAppointTraining.setColorSchemeResources(android.R.color.holo_red_light);
        rvAppointTraining.setLayoutManager(new LinearLayoutManager(this));
        rvAppointTraining.setAdapter(adapter);

        startDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        BaseApplication.getInstance().setStartDay(startDay);
        loadData(startDay);
        getBeforeDay();

        rvAppointTraining.setOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                srlAppointTraining.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        rvAppointTraining.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CheckTemplateTimeBean.DataBean dataBean = (CheckTemplateTimeBean.DataBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.item_delete_appoint_template:
                        int min = Integer.parseInt(minDay);
                        if(dateList.size()<min){
                            ToastUtils.ToastMessage(AddAppointActivity.this,"请选择至少"+min+"天的计划");
                        }else {
                            releaseDialog(dataBean.getId());
                        }

                        break;
                }
            }
        });
    }

    //获取必须提前发布的天数
    private void getBeforeDay() {
        Map<String, String> getBeforeMap = new HashMap<>();
        getBeforeMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_BEFORE_DAY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(getBeforeMap)))
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
                getBeforeDayJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(4);
                call.cancel();
            }
        });
    }

    private void releaseDialog(final String modelId){
        final AlertDialog.Builder delDialog =
                new AlertDialog.Builder(this);
        delDialog.setTitle("发布");
        delDialog.setMessage("确定以该模板作为培训计划吗?");
        delDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addTrainSchedule(modelId);
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
    //添加培训计划
    private void addTrainSchedule(String modelId) {
        srlAppointTraining.setRefreshing(true);
        Map<String, Object> addTrainScheduleMap = new HashMap<>();
        addTrainScheduleMap.put("token", BaseApplication.getInstance().getToken());
        addTrainScheduleMap.put("model_id", modelId);
        addTrainScheduleMap.put("date_list", dateList);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.ADD_TRAIN_SCHEDULE)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(addTrainScheduleMap)))
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
                mHandler.sendEmptyMessage(3);
                call.cancel();
            }
        });
    }
    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
        }
    }
    private void addHeadview(String day) {
        getTrainScheduleList();
        View view = getLayoutInflater().inflate(R.layout.headview_appoint_training, null);
        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendar_appoint_training);
        TextView tvSelectDate = (TextView) view.findViewById(R.id.tv_select_date);

        now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + Integer.parseInt(day));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
        format = sdf.format(now.getTime());
        tvSelectDate.setText("须制定"+format+"后(包含当天)的日期");
        adapter.addHeaderView(view);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OUT_OF_RANGE);
        calendarView.state().edit().
                setMinimumDate(now.getTime())
                .commit();

        calendarView.setSelectionMode(SELECTION_MODE_MULTIPLE);
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
                if (selected) {
                    dateList.add(dateSelect);
                } else {
                    dateList.remove(dateSelect);
                }
                if(dateList.size()>0){
                    for (int i = 0; i < dateList.size(); i++) {
                        if(!TextUtils.isEmpty(dateList.get(i))){
                            try {
                                long time = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).parse(dateList.get(i)).getTime();
                                longArrayList.add(time);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(longArrayList.size()>0){
                        Long min = Collections.min(longArrayList);
                        startDay = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(min);
                    }else {
                        startDay = "";

                    }
                    BaseApplication.getInstance().setStartDay(startDay);
                    loadData(startDay);
                }
            }
        });
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
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }
    //加载培训模板
    private void loadData(String startday) {

        srlAppointTraining.setRefreshing(true);
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("start_day", startday);


        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TRAIN_MODEL_LIST)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(parmaMap)))
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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(srlAppointTraining!=null&&srlAppointTraining.isRefreshing()){
                srlAppointTraining.setRefreshing(false);
            }
            switch (msg.what) {
                case 1:
                    ToastUtils.ToastMessage(AddAppointActivity.this, "网络连接错误");
                    tvEmptyAppoint.setVisibility(View.VISIBLE);
                    break;
                case 0:
                    TrainScheduleListBean bean = gson.fromJson(scheduleJson, TrainScheduleListBean.class);
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
                    calendarView.addDecorator(new EventDecorator(AddAppointActivity.this,Color.RED, dateSet,strings,true));
                    break;
                case 2:
                    CheckTemplateTimeBean checkTemplateTimeBean = gson.fromJson(responseJson, CheckTemplateTimeBean.class);
                    if (checkTemplateTimeBean.getData() != null&&checkTemplateTimeBean.getData().size()!=0) {
                        adapter.setNewData(checkTemplateTimeBean.getData());
                        tvEmptyAppoint.setVisibility(View.GONE);
                    }else {
                        tvEmptyAppoint.setVisibility(View.VISIBLE);
                    }
                    break;
                case 3:
                    AddTrainScheduleBean addTrainScheduleBean  = gson.fromJson(responseJson,AddTrainScheduleBean.class);
                    if(TextUtils.equals("0",addTrainScheduleBean.getRc())){
                        ToastUtils.ToastMessage(AddAppointActivity.this,"添加预约计划成功");
                        Bundle bundle = new Bundle();
                        bundle.putString("checkTemplateTo","1");
                        goToActivity(AddAppointActivity.this,AppointTrainingStudentActivity.class,bundle);
                        finish();
                    }else if(TextUtils.equals("330",addTrainScheduleBean.getRc())){
                        StringBuilder builder = new StringBuilder("");
                        for (int i = 0; i < addTrainScheduleBean.getErrorDates().size(); i++) {
                            builder.append(addTrainScheduleBean.getErrorDates().get(i)+"  ");
                        }
//                        ToastUtils.ToastMessage(AddAppointActivity.this,builder.toString()+"已存在预约计划");
                        ToastUtils.ToastMessage(AddAppointActivity.this,addTrainScheduleBean.getDes());
                    }
                    break;
                case 4:
                    GetBeforeDayBean beforeDayBean = gson.fromJson(getBeforeDayJson, GetBeforeDayBean.class);
                    if(beforeDayBean.getRc()==0){
                        addHeadview(beforeDayBean.getData().getDay());
                        minDay = beforeDayBean.getData().getMinDay();
                    }else {
                        ToastUtils.ToastMessage(AddAppointActivity.this,beforeDayBean.getDes());
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.imv_cus_title_back)
    public void onClick() {
        finish();
    }

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
