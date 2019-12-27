package com.elin.elindriverschool.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.BusRideAdapter;
import com.elin.elindriverschool.adapter.StuForOrderAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.BusRideBean;
import com.elin.elindriverschool.model.BusStuBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.model.StuForOrderBean;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

import static com.elin.elindriverschool.activity.MyDriveStudentActivity.MEDIA_TYPE_JSON;
import static com.elin.elindriverschool.fragment.FragmentMsg.MEDIA_TYPE_MARKDOWN;

public class StuForOrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_test_date)
    TextView tvTestDate;
    @Bind(R.id.tv_examination_room)
    TextView tvExaminationRoom;
    @Bind(R.id.tv_test_person_sub)
    TextView tvTestPersonSub;
    @Bind(R.id.tv_test_person_query)
    TextView tvTestPersonQuery;
    @Bind(R.id.rv_test_person)
    RecyclerView rvTestPerson;
    @Bind(R.id.srl_test_person)
    SwipeRefreshLayout srlTestPerson;

    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private String busDate;//班车乘坐日期
    private int sub = 0;//科目
    private Gson gson = new Gson();
    private StuForOrderAdapter adapter;
    private List<StuForOrderBean.DataBean> data_list = new ArrayList<>();
    private String[] subArray;
    private String orderSite;//考试场地
    private int yearT,monthT,dayT;//今天的时间
    private String testSiteJson;
    private TestSiteBean testSiteBean;
    private String[] testSites;
    private String orderSub = 0+"";
    private int pageNum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_for_order);
        ButterKnife.bind(this);
        tvCusTitleName.setText("驾考学员统计");
        adapter = new StuForOrderAdapter(data_list,this);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlTestPerson.setOnRefreshListener(this);
        srlTestPerson.setColorSchemeResources(android.R.color.holo_red_light);
        rvTestPerson.setLayoutManager(new LinearLayoutManager(this));
        rvTestPerson.setAdapter(adapter);
        rvTestPerson.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        subArray = getResources().getStringArray(R.array.sub);
        adapter.setEmptyView(R.layout.layout_bg_nodata,rvTestPerson);
        loadData(pageNum+"");
        rvTestPerson.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StuForOrderBean.DataBean bean = (StuForOrderBean.DataBean) adapter.getItem(position);
                MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getOrder_name(), StuForOrderActivity.this);
            }
        });
    }

    @OnClick({R.id.imv_cus_title_back, R.id.tv_test_date, R.id.tv_examination_room, R.id.tv_test_person_sub, R.id.tv_test_person_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_test_date:
                Calendar cal = Calendar.getInstance();
                yearT = cal.get(Calendar.YEAR);
                monthT = cal.get(Calendar.MONTH);
                dayT = cal.get(Calendar.DATE);
                DatePickerDialog startDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9&&dayOfMonth<10){
                            busDate = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                        }else if(monthOfYear<9){
                            busDate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }else if(dayOfMonth<10){
                            busDate = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                        }else {
                            busDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }
                        tvTestDate.setText(busDate);
                    }
                },yearT,monthT,dayT);
                DatePicker startDialogDatePicker = startDialog.getDatePicker();
                try {
                    startDialogDatePicker.setMinDate(new Date().getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startDialog.show();
                break;
            case R.id.tv_examination_room:
                getTestSites();
                break;
            case R.id.tv_test_person_sub:
                getTestSub();
                break;
            case R.id.tv_test_person_query:
                onRefresh();
                break;
        }
    }

    //获取考试科目
    private void getTestSub() {
        final String[] subArray = getResources().getStringArray(R.array.sub);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择考试科目")
                .setItems(subArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tvTestPersonSub.setText(subArray[i]);
                        orderSub = i+"";
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
    private void loadData(String num) {
        srlTestPerson.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("order_date", busDate);
        parmasMap.put("order_sub", orderSub);
        parmasMap.put("pageno", num);
        parmasMap.put("pagesize", "20");
        parmasMap.put("self_flag", "1");
        parmasMap.put("order_site",orderSite);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_STU_FOR_ORDER)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(2);
                    call.cancel();
                }
            }
        });
    }
    //获取考试场地
    private void getTestSites() {
        Map<String, String> map = new HashMap<>();
        map.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TEST_SITES)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(map)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
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
            if(srlTestPerson!=null&&srlTestPerson.isRefreshing()){
                srlTestPerson.setRefreshing(false);
            }
            switch (msg.what){
                case 1:
                    testSiteBean = gson.fromJson(testSiteJson, TestSiteBean.class);
                    testSites = new String[testSiteBean.getData_list().size()];
                    for (int i = 0; i < testSiteBean.getData_list().size(); i++) {
                        testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StuForOrderActivity.this);
                    builder.setTitle("选择考试场地")
                            .setItems(testSites, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tvExaminationRoom.setText(testSiteBean.getData_list().get(i).getExam_site());
                                    orderSite = testSiteBean.getData_list().get(i).getExam_site();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    orderSite = "";
                                    tvExaminationRoom.setText("选择场地");
                                    dialogInterface.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 2:
                    if (responseJson != null) {
                        StuForOrderBean bean = gson.fromJson(responseJson, StuForOrderBean.class);
                        if (TextUtils.equals("0",bean.getRc())) {
                            data_list = bean.getData();
                            if (data_list != null && data_list.size() != 0) {
                                if (pageNum == 1) {
                                    adapter.setNewData(data_list);
                                } else {
                                    adapter.addData(data_list);
                                }
                                adapter.loadMoreComplete();
                            } else {
                                if (pageNum == 1) {
                                    adapter.setNewData(data_list);
                                    adapter.loadMoreEnd(true);
                                } else {
                                    adapter.loadMoreEnd(false);
                                }
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 3:
                    ToastUtils.ToastMessage(StuForOrderActivity.this, "获取失败，请重试");
                    break;
            }
        }
    };
    @Override
    public void onRefresh() {
        pageNum = 1;
        adapter.setEnableLoadMore(false);
        loadData(pageNum+"");
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        loadData(pageNum+"");
    }
}
