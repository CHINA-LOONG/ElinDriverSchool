package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CheckTemplateAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import static com.elin.elindriverschool.activity.CheckStudentGradeActivity.MEDIA_TYPE_JSON;

/**
 * 查看培训模板
 */
public class CheckTemplateActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_check_template)
    RecyclerView rvCheckTemplate;
    @Bind(R.id.srl_check_template)
    SwipeRefreshLayout srlCheckTemplate;
    @Bind(R.id.btn_empty_create_template)
    Button btnEmptyCreateTemplate;

    private CheckTemplateAdapter adapter;
    private List<CheckTemplateTimeBean.DataBean> dataList = new ArrayList<>();
    private Map<String, String> parmaMap = new HashMap<>();
    private Gson gson = new Gson();
    private String responseJson,delResponse;
    private CheckTemplateTimeBean bean;
    public static final int CREATE_TEMPLATE_CODE = 100;
    private String flag;
    private String startDay;
//    private ArrayList<String> dateList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_template);
        ButterKnife.bind(this);
        tvCusTitleName.setText("培训模板");
        tvCusTitleRight.setText("添加模板");
        adapter = new CheckTemplateAdapter(this, dataList);
        srlCheckTemplate.setOnRefreshListener(this);
        srlCheckTemplate.setColorSchemeResources(android.R.color.holo_red_light);
        rvCheckTemplate.setLayoutManager(new LinearLayoutManager(this));
        rvCheckTemplate.setAdapter(adapter);
//        rvCheckTemplate.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        adapter.setEmptyView(R.layout.layout_empty,rvCheckTemplate);
        startDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        BaseApplication.getInstance().setStartDay(startDay);
        loadData(startDay);
//        Intent intent = getIntent();
//        flag = intent.getStringExtra("flag");
//        dateList = intent.getStringArrayListExtra("dateList");
        rvCheckTemplate.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                CheckTemplateTimeBean.DataBean dataBean = (CheckTemplateTimeBean.DataBean) adapter.getItem(position);
                switch (view.getId()){
//                    case R.id.item_tv_student_preview:
//
//                        break;
                    case R.id.item_delete_appoint_template:
                        deleteDialog(dataBean.getId(),position);
                        break;
                }
            }
        });
    }
    private void deleteDialog(final String modelId, final int position){
        final AlertDialog.Builder delDialog =
                new AlertDialog.Builder(this);
        delDialog.setTitle("删除");
        delDialog.setMessage("确定删除该模板吗?");
        delDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delTrainModel(modelId);
                        adapter.remove(position);
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

    private void delTrainModel(String modelId){
        srlCheckTemplate.setRefreshing(true);
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("model_id", modelId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DEL_TRAIN_MODEL)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(parmaMap)))
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
                delResponse = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });
    }

    private void loadData(String startDay) {
        srlCheckTemplate.setRefreshing(true);
        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("start_day", startDay);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TRAIN_MODEL_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(parmaMap)))
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
            if (srlCheckTemplate != null && srlCheckTemplate.isRefreshing()) {
                srlCheckTemplate.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    bean = gson.fromJson(responseJson, CheckTemplateTimeBean.class);
                    if (bean.getData() != null&&bean.getData().size()!=0) {
                        btnEmptyCreateTemplate.setVisibility(View.GONE);
                        adapter.setNewData(bean.getData());
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(CheckTemplateActivity.this, "请检查网络连接");
                    break;
                case 2:
                    CommonDataBean commonDataBean = gson.fromJson(delResponse,CommonDataBean.class);
                    if(commonDataBean.getRc().equals("0")){
                        ToastUtils.ToastMessage(CheckTemplateActivity.this,"删除成功");
                    }else {
                        ToastUtils.ToastMessage(CheckTemplateActivity.this,commonDataBean.getDes());
                    }
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
                Intent intent = new Intent(this,AddTemplateActivity.class);
                startActivityForResult(intent,CREATE_TEMPLATE_CODE);
                break;
//            case R.id.btn_empty_create_template:
//                intent.putExtra("flag","1");
//                startActivityForResult(intent,CREATE_TEMPLATE_CODE);
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CREATE_TEMPLATE_CODE&&resultCode==RESULT_OK&&data!=null){
//            ArrayList<CheckTemplateTimeBean.DataBean.TimeListBean> listBeen = new ArrayList<>();
//            AddTemplateBean bean = data.getParcelableExtra("bean");
//            CheckTemplateTimeBean.DataBean dataBean = new CheckTemplateTimeBean.DataBean();
//            dataBean.setName(bean.getTemplateName());
//            dataBean.setCreate_time(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//            for (int i = 0; i < bean.getData().size(); i++) {
//                CheckTemplateTimeBean.DataBean.TimeListBean timeListBean = new CheckTemplateTimeBean.DataBean.TimeListBean();
//                timeListBean.setStart_time(bean.getData().get(i).getStart_time());
//                timeListBean.setEnd_time(bean.getData().get(i).getEnd_time());
//                timeListBean.setPerson_limit(bean.getData().get(i).getPerson_limit());
//                timeListBean.setTrain_sub(bean.getData().get(i).getTrain_sub());
//                listBeen.add(timeListBean);
//            }
//            dataBean.setTimeList(listBeen);
//            adapter.addData(dataBean);
            loadData(startDay);
        }
    }

    @Override
    public void onRefresh() {
        loadData(startDay);
    }

}
