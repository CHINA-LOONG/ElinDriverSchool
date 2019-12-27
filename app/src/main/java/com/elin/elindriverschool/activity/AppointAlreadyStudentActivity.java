package com.elin.elindriverschool.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.AppointAlreadyStudentAdapter;
import com.elin.elindriverschool.adapter.CancleReasonAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.AppointAlreadyStuBean;
import com.elin.elindriverschool.model.CancleReasonBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
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

import static com.elin.elindriverschool.activity.StudentPreviewActivity.MEDIA_TYPE_JSON;

/**
 * 已经预约培训学员
 */
public class AppointAlreadyStudentActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_appoint_already_stu)
    RecyclerView rvAppointAlreadyStu;
    @Bind(R.id.srl_appoint_already_stu)
    SwipeRefreshLayout srlAppointAlreadyStu;

    private String recordId, canSign;
    private List<AppointAlreadyStuBean.StudentsBean> dataList = new ArrayList<>();
    private List<CancleReasonBean.DataBean> cancleList = new ArrayList<>();
    private AppointAlreadyStudentAdapter adapter;
    private CancleReasonAdapter cancleReasonAdapter;
    private Map<String, String> paramsMap = new HashMap<>();
    private String paramsJson;
    private Gson gson = new Gson();
    private String responseJson;
    private String cancleJson;
    private AppointAlreadyStuBean bean;
    private int location;
    private Dialog cancleAppointDialog;
    private String cancleReason;//取消理由

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_already_student);
        ButterKnife.bind(this);
        tvCusTitleName.setText("已预约培训学员");
        tvCusTitleRight.setText("预约");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recordId = bundle.getString("recordId");
            canSign = bundle.getString("canSign");
        }
        adapter = new AppointAlreadyStudentAdapter(this, dataList, canSign);
        adapter.openLoadAnimation();

        srlAppointAlreadyStu.setOnRefreshListener(this);
        srlAppointAlreadyStu.setColorSchemeResources(android.R.color.holo_red_light);
        rvAppointAlreadyStu.setLayoutManager(new LinearLayoutManager(this));
        rvAppointAlreadyStu.setAdapter(adapter);
        rvAppointAlreadyStu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        loadData();
        rvAppointAlreadyStu.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AppointAlreadyStuBean.StudentsBean bean = (AppointAlreadyStuBean.StudentsBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.img_appoint_stu_phone:
                        MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), AppointAlreadyStudentActivity.this);
                        break;
                    case R.id.item_tv_cancle_appoint:
                        location = position;

                        showCancleDialog(bean.getRecord_id(), bean.getStu_id());
                        break;
                    case R.id.item_btn_sign:

                        break;
                }
            }
        });
    }

    private void showCancleDialog(final String recordId, final String stuId) {
        getCancleReason();
        cancleAppointDialog = new Dialog(AppointAlreadyStudentActivity.this, R.style.MyDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_appoint_cancle, null);
        final EditText edtReasonCancle = (EditText) v.findViewById(R.id.edt_reason_cancle);
        edtReasonCancle.setVisibility(View.GONE);

        RecyclerView rvCancleReason = (RecyclerView) v.findViewById(R.id.rv_cancle_reason);
        rvCancleReason.setLayoutManager(new LinearLayoutManager(this));
        cancleReasonAdapter = new CancleReasonAdapter(cancleList);
        rvCancleReason.setAdapter(cancleReasonAdapter);
        Button cancle = (Button) v.findViewById(R.id.btn_cancle_appoint);
        Button ensure = (Button) v.findViewById(R.id.btn_ensure_appoint);

        cancleReasonAdapter.setOnCancleReasonListener(new CancleReasonAdapter.OnCancalReaonListener() {
            @Override
            public void cancleReason(String reason) {
                if (TextUtils.equals("其它", reason)) {
                    cancleReason = edtReasonCancle.getText().toString().trim();//输入框理由
                    edtReasonCancle.setVisibility(View.VISIBLE);
                } else {
                    cancleReason = reason;
                    edtReasonCancle.setVisibility(View.GONE);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancleAppointDialog.dismiss();
            }
        });
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(cancleReason)) {
                    delPreStu(recordId, stuId, cancleReason);
                    cancleAppointDialog.dismiss();
                } else {
                    ToastUtils.ToastMessage(AppointAlreadyStudentActivity.this, "请填写取消原因");
                }
            }
        });
        cancleAppointDialog.setContentView(v);
        cancleAppointDialog.setCanceledOnTouchOutside(false);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = cancleAppointDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
        cancleAppointDialog.getWindow().setAttributes(p);
        cancleAppointDialog.show();
    }

    //获取取消原因
    public void getCancleReason() {
        Map<String, String> delMap = new HashMap<>();
        delMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_CANCEL_REASON)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(delMap)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cancleJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(4);
                call.cancel();
            }
        });
    }

    //删除预约学员
    public void delPreStu(String recordId, String stuId, String reason) {
        Map<String, String> delMap = new HashMap<>();
        delMap.put("token", BaseApplication.getInstance().getToken());
        delMap.put("record_id", recordId);
        delMap.put("stu_id", stuId);
        delMap.put("cancel_reason", reason);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DEL_ORDER_STUDENT)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(delMap)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
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

    private void loadData() {
        srlAppointAlreadyStu.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("record_id", recordId);
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_ORDER_STUDENTS)
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
            if (srlAppointAlreadyStu != null && srlAppointAlreadyStu.isRefreshing()) {
                srlAppointAlreadyStu.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    Log.e("==请求学员返回的Json-->", String.valueOf(responseJson));
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, AppointAlreadyStuBean.class);
                        adapter.setEmptyView(R.layout.layout_empty, rvAppointAlreadyStu);
                        if (bean.getRc().equals("0")) {
                            dataList = bean.getStudents();
                            if (dataList != null && dataList.size() != 0) {
                                adapter.setNewData(dataList);
                                adapter.loadMoreComplete();
                            } else {
                                adapter.loadMoreEnd(false);
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_empty, rvAppointAlreadyStu);
                    ToastUtils.ToastMessage(AppointAlreadyStudentActivity.this, "网络连接出现问题");
                    break;

                case 2:
                    CommonDataBean bean = gson.fromJson(responseJson, CommonDataBean.class);
                    if (TextUtils.equals("0", bean.getRc())) {
                        ToastUtils.ToastMessage(AppointAlreadyStudentActivity.this, "删除成功");
                        AppointAlreadyStuBean.StudentsBean studentsBean = dataList.get(location);
                        studentsBean.setComplete_flag("3");
                        adapter.setData(location, studentsBean);
                    } else if (TextUtils.equals("406", bean.getRc())) {
                        ToastUtils.ToastMessage(AppointAlreadyStudentActivity.this, bean.getDes());
                    }
                    adapter.setEmptyView(R.layout.layout_empty, rvAppointAlreadyStu);

                    break;
                case 3:
                    ToastUtils.ToastMessage(AppointAlreadyStudentActivity.this, "请检查网络连接");
                    break;

                case 4:
                    CancleReasonBean reasonBean = gson.fromJson(cancleJson, CancleReasonBean.class);
                    if (reasonBean.getRc() == 0) {
                        if (reasonBean.getData() != null) {
                            cancleList = reasonBean.getData();
                            CancleReasonBean.DataBean dataBean = new CancleReasonBean.DataBean();
                            dataBean.setName("其它");
                            cancleList.add(dataBean);
                            cancleReasonAdapter.setNewData(cancleList);
                        }
                    }
                    break;

            }
        }
    };


    @Override
    public void onRefresh() {
        loadData();
    }


    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_cus_title_right://教练添加预约学员

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
