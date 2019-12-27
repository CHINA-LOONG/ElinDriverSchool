package com.elin.elindriverschool.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.BusRideAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.BusRideBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

/**
 * 班车查看
 */
public class BusRideActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.sp_busride)
    Spinner spBusride;
    @Bind(R.id.tv_busride_date)
    TextView tvBusrideDate;
    @Bind(R.id.tv_busride_query)
    TextView tvBusrideQuery;
    @Bind(R.id.rv_busride)
    RecyclerView rvBusride;
    @Bind(R.id.ll_busride_nodata)
    LinearLayout layoutNodata;
    @Bind(R.id.srl_busride)
    SwipeRefreshLayout srlBusride;
    private Map<String, String> paramsMap = new HashMap<>();
    private String responseJson;
    private String busDate;//班车乘坐日期
    private int sub = 0;//科目
    private Gson gson = new Gson();
    private BusRideAdapter adapter;
    private List<BusRideBean.DataBean> data_list = new ArrayList<>();
    private String[] subArray;
    private BusRideBean busRideBean;
    private int yearT,monthT,dayT;//今天的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ride);
        ButterKnife.bind(this);
        tvCusTitleName.setText("班车查询");
        adapter = new BusRideAdapter(this,data_list);
        adapter.openLoadAnimation();
        srlBusride.setOnRefreshListener(this);
        srlBusride.setColorSchemeResources(android.R.color.holo_red_light);
        rvBusride.setLayoutManager(new LinearLayoutManager(this));
        rvBusride.setAdapter(adapter);
        rvBusride.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        subArray = getResources().getStringArray(R.array.sub);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, subArray);
        adapter1.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        spBusride.setAdapter(adapter1);
        spBusride.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sub = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        rvBusride.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                BusRideBean.DataBean bean = (BusRideBean.DataBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("order_bus_date",bean.getOrder_bus_date());
                bundle.putString("order_sub",bean.getOrder_sub());
                bundle.putString("order_bus_person",bean.getOrder_bus_person());
                bundle.putString("order_bus_person",bean.getOrder_bus_person());
                bundle.putString("order_bus_num",bean.getOrder_bus_num());
                goToActivity(BusRideActivity.this,BusRidePersonActivity.class,bundle);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                String phoneArray[] = new String[]{};

                BusRideBean.DataBean bean = (BusRideBean.DataBean) adapter.getItem(position);
                if(bean.getOrder_bus_perphone().contains(";")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(BusRideActivity.this);
                    builder.setTitle("拨打电话");
                    phoneArray = bean.getOrder_bus_perphone().split(";");
                    final String[] finalPhoneArray = phoneArray;
                    builder.setItems(phoneArray, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + finalPhoneArray[which]));
                            if (ActivityCompat.checkSelfPermission(BusRideActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(intent);
                        }
                    });
                    builder.show();
                } else {
                    MobilePhoneUtils.makeCall(bean.getOrder_bus_perphone(),bean.getOrder_bus_perphone(),BusRideActivity.this);
                }
            }
        });

        busDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        tvBusrideDate.setText(busDate);
        loadData();
    }

    private void loadData() {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("order_bus_date",busDate);
        paramsMap.put("order_sub", sub+"");
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_BUS_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON,gson.toJson(paramsMap) ))
                .build();
        Log.e("参数打印",gson.toJson(paramsMap));
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
                    Log.e("请求学员返回的Json-->", String.valueOf(responseJson));
                    if(srlBusride!=null){
                        srlBusride.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        busRideBean = gson.fromJson(responseJson, BusRideBean.class);
                        if (busRideBean.getRc().equals("0")) {
                            data_list = busRideBean.getData();
                            if (data_list != null&&data_list.size()!=0) {
                                layoutNodata.setVisibility(View.GONE);
                                adapter.setNewData(data_list);
                                srlBusride.setRefreshing(false);
                                adapter.loadMoreComplete();
                            }else {
                                layoutNodata.setVisibility(View.VISIBLE);
                                adapter.setNewData(data_list);
                                adapter.loadMoreEnd(true);
                            }
                        } else {
                            Log.e("2-->", responseJson);
                            layoutNodata.setVisibility(View.VISIBLE);
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    if(srlBusride!=null){
                        srlBusride.setRefreshing(false);
                    }
                    ToastUtils.ToastMessage(BusRideActivity.this,"网络连接出现问题");
                    break;
            }
        }
    };

    @OnClick({R.id.imv_cus_title_back, R.id.tv_busride_date, R.id.tv_busride_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_busride_date:
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
                        tvBusrideDate.setText(busDate);
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
            case R.id.tv_busride_query:
                srlBusride.setRefreshing(true);
                loadData();
                break;
        }
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        loadData();
        srlBusride.setRefreshing(false);
        adapter.removeAllFooterView();
    }
}
