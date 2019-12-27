package com.elin.elindriverschool.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chanven.lib.cptr.loadmore.SwipeRefreshHelper;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.MyDriveStudentListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.fragment.SimpleCalendarDialogFragment;
import com.elin.elindriverschool.fragment.SimpleEndCalendarDialogFragment;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MySwipeRefreshLayout;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 16/12/27.
 * 我的学员
 */

public class MyDriveStudentActivity extends BaseActivity implements View.OnClickListener
        , SimpleCalendarDialogFragment.OnDateSelectedCompleteListener
        , SimpleEndCalendarDialogFragment.OnEndDateSelectedCompleteListener
        ,SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.sp_my_stu)
    Spinner spMyStu;
    @Bind(R.id.edt_my_stu_name)
    EditText edtMyStuName;
    @Bind(R.id.tv_my_stu_start_date)
    TextView tvMyStuStartDate;
    @Bind(R.id.ll_my_stu_start_date)
    LinearLayout llMyStuStartDate;
    @Bind(R.id.tv_my_stu_end_date)
    TextView tvMyStuEndDate;
    @Bind(R.id.ll_my_stu_end_date)
    LinearLayout llMyStuEndDate;

    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();

    private static String dateSelected;
    @Bind(R.id.tv_my_drive_stu_check)
    TextView tvMyDriveStuCheck;
    @Bind(R.id.ll_my_drive_stu_container)
    LinearLayout llMyDriveStuContainer;
    @Bind(R.id.swipe_refresh_my_stu)
    MySwipeRefreshLayout swipeRefreshMyStu;
    @Bind(R.id.ll_my_drive_stu_no_data)
    LinearLayout layoutNodata;
    @Bind(R.id.rv_my_student)
    RecyclerView rvMyStudent;
    private String[] myStus;
    private int stuType=2;// 1报名经手人；2教练员；4 已结业
    private Map<String, String> paramsMap = new HashMap<>();
    private String paramsJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private MyDriveStudentBean myDriveStudentBean;
    private MyDriveStudentListAdapter adapter;

    private SwipeRefreshHelper swipeRefreshHelper;
    private Intent intent = new Intent();
    private int yearT, monthT, dayT;//今天的时间
    private String date;
    List<MyDriveStudentBean.DataListBean> data_list = new ArrayList<>();
    int rowBegin = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_student);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("我的学员");
        llMyStuStartDate.setOnClickListener(this);
        llMyStuEndDate.setOnClickListener(this);
        tvMyDriveStuCheck.setOnClickListener(this);
        adapter = new MyDriveStudentListAdapter(data_list,MyDriveStudentActivity.this);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        swipeRefreshMyStu.setOnRefreshListener(this);
        swipeRefreshMyStu.setColorSchemeResources(android.R.color.holo_red_light);
        rvMyStudent.setLayoutManager(new LinearLayoutManager(MyDriveStudentActivity.this));
        rvMyStudent.setAdapter(adapter);
        rvMyStudent.addItemDecoration(new DividerItemDecoration(MyDriveStudentActivity.this, DividerItemDecoration.VERTICAL_LIST));
        edtMyStuName.setCursorVisible(false);
        edtMyStuName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edtMyStuName.setCursorVisible(true);
                return false;
            }
        });
        myStus = getResources().getStringArray(R.array.stu_types);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myStus);
        adapter1.setDropDownViewResource(android.
                R.layout.simple_spinner_dropdown_item);
        spMyStu.setAdapter(adapter1);
        spMyStu.setSelection(1);
        spMyStu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stuType = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        getMyDriveStus(1);
        rvMyStudent.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyDriveStudentBean.DataListBean bean = (MyDriveStudentBean.DataListBean) adapter.getItem(position);
                intent.setClass(MyDriveStudentActivity.this, StudentInfoActivity.class);
                intent.putExtra("stuIdNum", bean.getStu_idnum());
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                    MyDriveStudentBean.DataListBean bean = (MyDriveStudentBean.DataListBean) adapter.getItem(position);
                    MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), MyDriveStudentActivity.this);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                MyDriveStudentActivity.this.finish();
                break;
            case R.id.ll_my_stu_start_date:
                setDate(tvMyStuStartDate);
//                new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
                break;
            case R.id.ll_my_stu_end_date:
                setDate(tvMyStuEndDate);
//                new SimpleEndCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");
                break;
            case R.id.tv_my_drive_stu_check:
                swipeRefreshMyStu.setRefreshing(true);
                rowBegin=1;
                getMyDriveStus(rowBegin);
                break;
        }
    }

    public void setDate(final TextView textView) {
        Calendar cal = Calendar.getInstance();
        yearT = cal.get(Calendar.YEAR);
        monthT = cal.get(Calendar.MONTH);
        dayT = cal.get(Calendar.DATE);
        DatePickerDialog startDialog = new DatePickerDialog(MyDriveStudentActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        startDialog.show();
    }

    private void getMyDriveStus(int pageNo) {
        swipeRefreshMyStu.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", pageNo + "");
        paramsMap.put("pagesize", "20");
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        if (tvMyStuStartDate.getText().toString().length() != 0) {
            paramsMap.put("reg_date_begin", tvMyStuStartDate.getText().toString());
        } else {
            paramsMap.put("reg_date_begin", "");
        }
        if (tvMyStuEndDate.getText().toString().length() != 0) {
            paramsMap.put("reg_date_end", tvMyStuEndDate.getText().toString());
        } else {
            paramsMap.put("reg_date_end", "");
        }
        if (edtMyStuName.getText().toString().length() != 0) {
            paramsMap.put("stu_name", edtMyStuName.getText().toString());
        } else {
            paramsMap.put("stu_name", "");
        }
        paramsMap.put("stu_type", stuType + "");

        paramsJson = gson.toJson(paramsMap);

        Log.e("我的学员请求Json-->", paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.MY_DRIVE_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(MyDriveStudentActivity.this).newCall(request);
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

                    if(swipeRefreshMyStu!=null){
                        swipeRefreshMyStu.setRefreshing(false);
                    }
                    myDriveStudentBean = gson.fromJson(responseJson, MyDriveStudentBean.class);
                    tvCusTitleName.setText("我的学员（"+myDriveStudentBean.getData_count()+"）");
                    if (myDriveStudentBean.getRc().equals("0")) {
                        data_list = myDriveStudentBean.getData_list();
                        if (data_list != null&&data_list.size()!=0) {
                            layoutNodata.setVisibility(View.GONE);
                            if (rowBegin == 1) {
                                adapter.setNewData(data_list);
                                swipeRefreshMyStu.setRefreshing(false);
                                adapter.loadMoreComplete();
                            } else {
                                adapter.addData(data_list);
                                adapter.loadMoreComplete();
                            }
                        }else {
                            if(rowBegin==1){
                                Log.e("1-->", responseJson);
                                layoutNodata.setVisibility(View.VISIBLE);
                                adapter.setNewData(data_list);
                                adapter.loadMoreEnd(true);
                            }else {
                                adapter.loadMoreEnd(false);
                            }
                        }
                    } else if(TextUtils.equals("3000",myDriveStudentBean.getRc())){
                        LogoutUtil.clearData(MyDriveStudentActivity.this);
                        goToActivity(MyDriveStudentActivity.this,LoginActivity.class);
                        ToastUtils.ToastMessage(MyDriveStudentActivity.this,myDriveStudentBean.getDes());
                    }

                    break;
                case 1:
                    tvCusTitleName.setText("我的学员（0）");
                    if(swipeRefreshMyStu!=null){
                        swipeRefreshMyStu.setRefreshing(false);
                    }
                    if (rowBegin==1){
                        layoutNodata.setVisibility(View.VISIBLE);
                    }else {
                        ToastUtils.ToastMessage(MyDriveStudentActivity.this,"网络连接出现问题");
                    }
                    break;
            }
        }
    };


    @Override
    public void getSelectedDate(String dateSelect) {
        tvMyStuStartDate.setText(dateSelect);
    }

    @Override
    public void getEndSelectedDate(String dateEndSelect) {
        tvMyStuEndDate.setText(dateEndSelect);
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        rowBegin = 1;
        getMyDriveStus(rowBegin);
        swipeRefreshMyStu.setRefreshing(false);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        rowBegin = rowBegin + 1;
        getMyDriveStus(rowBegin);
    }
}
