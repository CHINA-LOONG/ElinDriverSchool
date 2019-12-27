package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.StudentPreviewAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.StudentPreviewBean;
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
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentPreviewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_stu_preview)
    RecyclerView rvStuPreview;
    @Bind(R.id.srl_stu_preview)
    SwipeRefreshLayout srlStuPreview;

    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Bind(R.id.preview_stu_slot)
    TextView previewStuSlot;
    @Bind(R.id.preview_stu_person_limit)
    TextView previewStuPersonLimit;
    @Bind(R.id.preview_stu_sub)
    TextView previewStuSub;
    private String modelId, trainSub, startDay;
    private Map<String, String> paramsMap = new HashMap<>();
    private String paramsJson;
    private Gson gson = new Gson();
    private String responseJson;
    private StudentPreviewBean bean;
    private List<StudentPreviewBean.DataBean.StudentsBean> dataList = new ArrayList<>();
    private StudentPreviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_preview);
        ButterKnife.bind(this);
        tvCusTitleName.setText("可预约学员预览");
        modelId = getIntent().getExtras().getString("modelId");
        trainSub = getIntent().getExtras().getString("trainSub");
        startDay = getIntent().getExtras().getString("startDay");
        adapter = new StudentPreviewAdapter(this, dataList);
        srlStuPreview.setOnRefreshListener(this);
        srlStuPreview.setColorSchemeResources(android.R.color.holo_red_light);
        rvStuPreview.setLayoutManager(new LinearLayoutManager(this));
        rvStuPreview.setAdapter(adapter);
        rvStuPreview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        loadData();
    }

    private void loadData() {
        srlStuPreview.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("model_time_id", modelId);
        paramsMap.put("train_sub", trainSub);
        paramsMap.put("start_day", startDay);
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_PRE_STUDENTS_BY_MODEL_TIME_ID)
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
            if (srlStuPreview != null && srlStuPreview.isRefreshing()) {
                srlStuPreview.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, StudentPreviewBean.class);
                        if (bean.getRc().equals("0")) {
                            previewStuSlot.setText(bean.getData().getStart_time()+"-"+bean.getData().getEnd_time());
                            previewStuPersonLimit.setText(bean.getData().getPerson_limit());
                            if(!TextUtils.isEmpty(bean.getData().getTrain_sub())){
                                switch (bean.getData().getTrain_sub()){
                                    case "2":
                                        previewStuSub.setText("科目二");
                                        break;
                                    case "3":
                                        previewStuSub.setText("科目三");
                                        break;
                                    case "5":
                                        previewStuSub.setText("科二和科三");
                                        break;
                                }
                            }
                            dataList = bean.getData().getStudents();
                            if (dataList != null && dataList.size() != 0) {
                                adapter.setNewData(dataList);
                                adapter.loadMoreComplete();
                            } else {
                                adapter.setEmptyView(R.layout.layout_empty, rvStuPreview);
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_empty, rvStuPreview);
                    ToastUtils.ToastMessage(StudentPreviewActivity.this, "网络连接出现问题");
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
        loadData();
    }
}
