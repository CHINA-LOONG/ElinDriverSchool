package com.elin.elindriverschool.activity;

import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.WaitTestAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

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

import static com.elin.elindriverschool.activity.SubWaitTestActivity.MEDIA_TYPE_JSON;

/**
 * 进行学员报考操作
 */
public class StudentOperationActivity extends BaseActivity implements WaitTestAdapter.OnStuItemCheckedCallBack{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_stu_operation)
    RecyclerView rvStuOperation;
    @Bind(R.id.stu_submit)
    Button stuSubmit;
    private String title;
    private String sub = "1";
    private MyProgressDialog myProgressDialog;
    private List<WaitOptionTestStuBean.DataListBean> dataList = new ArrayList<>();
    private WaitTestAdapter adapter;
    private RadioGroup rgSub;
    private Button tvSelectSite;//考场选择
    private CheckBox cbShuttle;//班车乘坐
    private Button tvSelectDate;//日期选择
    private String isTakeBus = "1";
    private String testDate;//考试日期
    private String testSiteJson;
    private TestSiteBean testSiteBean;
    private String[] testSites;
    private Map<String, Object> stuSubmitParamMap = new HashMap<>();
    private String paramsJson, paramsSubmitJson;
    private String responseJson;
    private ArrayList<String> stuIdNumList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_operation);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            title = bundle.getString("title");
            dataList = bundle.getParcelableArrayList("dataList");
        }
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        tvCusTitleName.setText(title);
        adapter = new WaitTestAdapter(dataList, this, StudentOperationActivity.this, false,"2");
        rvStuOperation.setLayoutManager(new LinearLayoutManager(this));
        rvStuOperation.setAdapter(adapter);
        rvStuOperation.addItemDecoration(new DividerItemDecoration(StudentOperationActivity.this, DividerItemDecoration.VERTICAL_LIST));
        adapter.setNewData(dataList);
        adapter.setEmptyView(R.layout.layout_bg_nodata, rvStuOperation);
        View headView = LayoutInflater.from(this).inflate(R.layout.header_stu_operation, null);
        rgSub = (RadioGroup) headView.findViewById(R.id.rg_sub);
        tvSelectDate = (Button) headView.findViewById(R.id.tv_select_date);
        tvSelectSite = (Button) headView.findViewById(R.id.tv_select_site);
        cbShuttle = (CheckBox) headView.findViewById(R.id.cb_shuttle);

        rgSub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_one:
                        sub = "1";
                        break;
                    case R.id.rb_two:
                        sub = "2";
                        break;
                    case R.id.rb_three:
                        sub = "3";
                        break;
                    case R.id.rb_four:
                        sub = "4";
                        break;

                }
            }
        });
        tvSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelect();
            }
        });
        tvSelectSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siteSelect();
            }
        });
        cbShuttle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    isTakeBus = "1";
                } else {
                    isTakeBus = "2";
                }
            }
        });

        adapter.addHeaderView(headView);
        rvStuOperation.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.imv_wait_test_item_phone:
                        WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                        MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(),StudentOperationActivity.this);
                        break;
                }
            }

            @Override
            public void onItemLongClick(final BaseQuickAdapter adapter, View view, final int position) {
                super.onItemLongClick(adapter, view, position);
                WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                new AlertDialog.Builder(StudentOperationActivity.this)
                        .setTitle("移除")
                        .setMessage("确定移除"+bean.getStu_name()+"?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.remove(position);
                            }
                        })
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
            }
        });
    }

    @OnClick({R.id.imv_cus_title_back, R.id.stu_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.stu_submit:
                if (TextUtils.equals(tvSelectDate.getText().toString(), getResources().getString(R.string.date_select_tip))) {
                    ToastUtils.ToastMessage(this, getResources().getString(R.string.date_select_tip));
                } else {
                    if (TextUtils.equals(tvSelectSite.getText().toString(), getResources().getString(R.string.site_select_tip))) {
                        ToastUtils.ToastMessage(this, getResources().getString(R.string.site_select_tip));
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle(title)
                                .setMessage("确认提交？")
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        for (int j = 0; j < dataList.size(); j++) {
                                            stuIdNumList.add(dataList.get(j).getStu_idnum());
                                        }
                                       submitStuTest(stuIdNumList);
                                    }
                                })
                                .create()
                                .show();
                    }
                }
                break;
        }
    }

    @Override
    public void getCheckedMap(HashMap<Integer, Boolean> map) {

    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            myProgressDialog.dismiss();
            switch (msg.what) {
                case 1:
                    testSiteBean = new Gson().fromJson(testSiteJson, TestSiteBean.class);
                    testSites = new String[testSiteBean.getData_list().size()];
                    for (int i = 0; i < testSiteBean.getData_list().size(); i++) {
                        testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentOperationActivity.this);
                    builder.setTitle("选择考试场地")
                            .setItems(testSites, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tvSelectSite.setText(testSiteBean.getData_list().get(i).getExam_site());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 2:
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(StudentOperationActivity.this, "提交成功");
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            ToastUtils.ToastMessage(StudentOperationActivity.this, "请移除已经预约的学员");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    ToastUtils.ToastMessage(StudentOperationActivity.this,"请求失败，请检查网络连接");
                    break;
            }
        }
    };
    //提交学员考试
    private void submitStuTest(List<String> strings) {
        myProgressDialog.show();
        stuSubmitParamMap.put("token", BaseApplication.getInstance().getToken());
        stuSubmitParamMap.put("coach_idnum", BaseApplication.getInstance().getCoachIdNum());
        stuSubmitParamMap.put("exam_sub", sub + "");
        stuSubmitParamMap.put("stu_list", strings);
        stuSubmitParamMap.put("exam_date", tvSelectDate.getText().toString());
        stuSubmitParamMap.put("exam_site", tvSelectSite.getText().toString());
        stuSubmitParamMap.put("exam_bus", isTakeBus + "");
        stuSubmitParamMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsSubmitJson = new Gson().toJson(stuSubmitParamMap);
        Log.e("科目一提交请求Json-->", paramsSubmitJson);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU_APPLY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });

    }
    //获取考试场地
    private void siteSelect() {
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
    //日期选择
    private void dateSelect() {
        int yearT, monthT, dayT;
        Calendar cal = Calendar.getInstance();
        yearT = cal.get(Calendar.YEAR);
        monthT = cal.get(Calendar.MONTH);
        dayT = cal.get(Calendar.DATE);
        DatePickerDialog startDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (monthOfYear < 9 && dayOfMonth < 10) {
                    testDate = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if (monthOfYear < 9) {
                    testDate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    testDate = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else {
                    testDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                tvSelectDate.setText(testDate);
            }
        }, yearT, monthT, dayT);
        DatePicker startDialogDatePicker = startDialog.getDatePicker();
        try {
            switch (title) {
                case "预约学员":
                    startDialogDatePicker.setMinDate(new Date().getTime());
                    break;
                case "成绩录入":
                    startDialogDatePicker.setMaxDate(new Date().getTime());
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        startDialog.show();
    }
}
