package com.elin.elindriverschool.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CancleReasonAdapter;
import com.elin.elindriverschool.adapter.CheckTemplateAdapter;
import com.elin.elindriverschool.adapter.StudentVisitAdapter;
import com.elin.elindriverschool.adapter.SubmitReasonAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.AppointAlreadyStuBean;
import com.elin.elindriverschool.model.CancleReasonBean;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.StuForOrderBean;
import com.elin.elindriverschool.model.StudentVisitBean;
import com.elin.elindriverschool.model.SubmitReasonBean;
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

import static com.elin.elindriverschool.fragment.FragmentMsg.MEDIA_TYPE_MARKDOWN;

/**
 * 学员回访
 */
public class StudentVisitActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseSectionQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.cb_student_visit)
    CheckBox cbStudentVisit;
    @Bind(R.id.rv_student_visit)
    RecyclerView rvStudentVisit;
    @Bind(R.id.srl_student_visit)
    SwipeRefreshLayout srlStudentVisit;

    private StudentVisitAdapter adapter;
    private List<StudentVisitBean.DataBean> dataList = new ArrayList<>();
    private List<SubmitReasonBean.DataBean> submitList = new ArrayList<>();
    private Map<String, String> parmasMap = new HashMap<>();
    private Gson gson = new Gson();
    private int pageNo = 1;
    private int location;
    private String responseJson;
    private String status = "1";//未回访
    private Dialog submitDialog;
    private SubmitReasonAdapter submitReasonAdapter;
    private String submitReason;//回访结果
    private String nextDate;
    private int yearT,monthT,dayT;//今天的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_visit);
        ButterKnife.bind(this);
        tvCusTitleName.setText("学员回访");
        adapter = new StudentVisitAdapter(this,dataList);
        srlStudentVisit.setOnRefreshListener(this);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        adapter.setAutoLoadMoreSize(20);
        srlStudentVisit.setOnRefreshListener(this);
        srlStudentVisit.setColorSchemeResources(android.R.color.holo_red_light);
        rvStudentVisit.setLayoutManager(new LinearLayoutManager(this));
        rvStudentVisit.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_bg_nodata,rvStudentVisit);
        rvStudentVisit.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        loadData(pageNo+"",status);
        cbStudentVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    status = "0";
                    pageNo = 1;
                    loadData(pageNo+"",status);
                }else {
                    status = "1";
                    pageNo = 1;
                    loadData(pageNo+"",status);
                }
            }
        });
        rvStudentVisit.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                StudentVisitBean.DataBean bean = (StudentVisitBean.DataBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.rl_visit_phone:
                        MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), StudentVisitActivity.this);
                        break;
                    case R.id.tv_return_visit:
                        location = position;
                        submitDialog(bean.getId(),bean.getStu_id());
                        break;
                }
            }
        });
    }
    //提交结果对话框
    private void submitDialog(final String recordId, final String stuId){
        getSubmitReason();
        submitDialog = new Dialog(StudentVisitActivity.this,R.style.MyDialog);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_appoint_cancle, null);
        final EditText edtReasonCancle = (EditText) v.findViewById(R.id.edt_reason_cancle);
        edtReasonCancle.setHint("请填写学员回访结果");
        TextView dialogTitle = (TextView) v.findViewById(R.id.dialog_title);
        final TextView tvNextDate = (TextView) v.findViewById(R.id.tv_next_date);
        edtReasonCancle.setVisibility(View.GONE);
        dialogTitle.setText("提交回访结果");
        RecyclerView rvCancleReason = (RecyclerView) v.findViewById(R.id.rv_cancle_reason);
        rvCancleReason.setLayoutManager(new LinearLayoutManager(this));
        submitReasonAdapter = new SubmitReasonAdapter(submitList);
        rvCancleReason.setAdapter(submitReasonAdapter);
        Button cancle = (Button) v.findViewById(R.id.btn_cancle_appoint);
        Button ensure = (Button) v.findViewById(R.id.btn_ensure_appoint);
        tvNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                yearT = cal.get(Calendar.YEAR);
                monthT = cal.get(Calendar.MONTH);
                dayT = cal.get(Calendar.DATE);
                DatePickerDialog startDialog = new DatePickerDialog(StudentVisitActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear<9&&dayOfMonth<10){
                            nextDate = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                        }else if(monthOfYear<9){
                            nextDate = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }else if(dayOfMonth<10){
                            nextDate = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                        }else {
                            nextDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        }
                        tvNextDate.setText(nextDate);
                    }
                },yearT,monthT,dayT);
                DatePicker startDialogDatePicker = startDialog.getDatePicker();
                try {
                    startDialogDatePicker.setMinDate(new Date().getTime());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startDialog.show();
            }
        });
        submitReasonAdapter.setOnCancleReasonListener(new SubmitReasonAdapter.OnCancalReaonListener() {
            @Override
            public void cancleReason(String reason) {
                submitReason = reason;
                if(TextUtils.equals("其它",reason)){
                    edtReasonCancle.setVisibility(View.VISIBLE);
                }else {
                    edtReasonCancle.setVisibility(View.GONE);
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReason="";
                submitDialog.dismiss();
            }
        });
        ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextDate = tvNextDate.getText().toString().trim();
                if(TextUtils.equals("请选择下次回访时间",nextDate)){
                    nextDate = "";
                }

                if(TextUtils.isEmpty(submitReason)){
                    ToastUtils.ToastMessage(StudentVisitActivity.this,"请选择回访结果");
                }else {
                    if(TextUtils.equals("其它",submitReason)){
                        String reason = edtReasonCancle.getText().toString();
                        if(TextUtils.isEmpty(reason)){
                            ToastUtils.ToastMessage(StudentVisitActivity.this,"请填写回访结果");
                        }else {
                            submitResult(recordId,stuId,reason,nextDate);
                            submitDialog.dismiss();
                        }
                    }else {
                        submitResult(recordId,stuId,submitReason,nextDate);
                        submitDialog.dismiss();
                    }
                }
            }
        });
        submitDialog.setContentView(v);
        submitDialog.setCanceledOnTouchOutside(false);
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = submitDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
        submitDialog.getWindow().setAttributes(p);
        submitDialog.show();
    }
    //提交回访结果
    public void submitResult(String recordId,String stuId,String reason,String nextDate){
        Map<String, String> delMap = new HashMap<>();
        delMap.put("token", BaseApplication.getInstance().getToken());
        delMap.put("id", recordId);
        delMap.put("content", reason);
        delMap.put("next_date", nextDate);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.ADD_RETURN)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(delMap)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(4);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(3);
                call.cancel();
            }
        });
    }
    //获取回访结果类型
    public void getSubmitReason(){
        Map<String, String> delMap = new HashMap<>();
        delMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_RETURN_TYPE)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(delMap)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });
    }
    //加载数据
    private void loadData(String num,String staus) {
        srlStudentVisit.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("pageno", num);
        parmasMap.put("pagesize", "20");
        parmasMap.put("status", staus);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_STU_FOR_RETURN)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(1);
                    call.cancel();
                }
            }
        });
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(srlStudentVisit!=null&&srlStudentVisit.isRefreshing()){
                srlStudentVisit.setRefreshing(false);
            }
            switch (msg.what){
                case 1:
                    if (responseJson != null) {
                        StudentVisitBean bean = gson.fromJson(responseJson, StudentVisitBean.class);
                        if (bean.getRc() == 0 ) {
                            dataList = bean.getData();
                            if (dataList != null && dataList.size() != 0) {
                                if (pageNo == 1) {
                                    adapter.setNewData(dataList);
                                } else {
                                    adapter.addData(dataList);
                                }
                                adapter.loadMoreComplete();
                            } else {
                                if (pageNo == 1) {
                                    adapter.setNewData(dataList);
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
                case 2:
                    SubmitReasonBean reasonBean = gson.fromJson(responseJson,SubmitReasonBean.class);
                    if(reasonBean.getRc()==0){
                        if(reasonBean.getData()!=null){
                            submitList = reasonBean.getData();
                            SubmitReasonBean.DataBean dataBean = new SubmitReasonBean.DataBean();
                            dataBean.setContent("其它");
                            submitList.add(dataBean);
                            submitReasonAdapter.setNewData(submitList);
                        }
                    }
                    break;
                case 3:
                    CommonDataBean bean = gson.fromJson(responseJson, CommonDataBean.class);
                    if(TextUtils.equals("0",bean.getRc())){
                        ToastUtils.ToastMessage(StudentVisitActivity.this,"回访成功");
                        submitReason="";
//                        StudentVisitBean.DataBean studentsBean = dataList.get(location);
//                        studentsBean.setStatus("0");
//                        adapter.setData(location,studentsBean);
                        adapter.remove(location);
                    }else if (TextUtils.equals("406",bean.getRc())){
                        ToastUtils.ToastMessage(StudentVisitActivity.this,bean.getDes());
                    }
                    adapter.setEmptyView(R.layout.layout_bg_nodata,rvStudentVisit);
                    break;
                case 4:
                    ToastUtils.ToastMessage(StudentVisitActivity.this, "获取失败，请重试");
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
        pageNo = 1;
        adapter.setEnableLoadMore(false);
        loadData(pageNo+"",status);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        loadData(pageNo+"",status);
    }
}
