package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.elin.elindriverschool.adapter.NoticeStuListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.model.NoticeStuListBean;
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

import static com.elin.elindriverschool.activity.CheckStudentGradeActivity.MEDIA_TYPE_JSON;

public class NoticeStuListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_common)
    RecyclerView rvCommon;
    @Bind(R.id.srl_common)
    SwipeRefreshLayout srlCommon;

    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private List<NoticeStuListBean.DataBean> dataBeanList = new ArrayList<>();
    private NoticeStuListAdapter adapter ;
    private Bundle bundle;
    private String detailId,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_stu_list);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        detailId = bundle.getString("detail_id");
        title = bundle.getString("title");
        tvCusTitleName.setText(title);
        adapter = new NoticeStuListAdapter(NoticeStuListActivity.this,dataBeanList);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlCommon.setOnRefreshListener(this);
        srlCommon.setColorSchemeResources(android.R.color.holo_red_light);
        rvCommon.setLayoutManager(new LinearLayoutManager(NoticeStuListActivity.this));
        rvCommon.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_empty,rvCommon);
        rvCommon.addItemDecoration(new DividerItemDecoration(NoticeStuListActivity.this, DividerItemDecoration.VERTICAL_LIST));
        rvCommon.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NoticeStuListBean.DataBean bean = (NoticeStuListBean.DataBean) adapter.getItem(position);
                MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), NoticeStuListActivity.this);
            }
        });
        loadData();
    }

    private void loadData() {
        srlCommon.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("detail_id",  detailId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_NOTICE_STU_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(parmasMap)))
                .build();
        Call call = new MyOkHttpClient(NoticeStuListActivity.this).newCall(request);
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
                    if(srlCommon!=null){
                        srlCommon.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        NoticeStuListBean bean= new Gson().fromJson(responseJson, NoticeStuListBean.class);
                        if (TextUtils.equals("0",bean.getRc())) {
                            dataBeanList = bean.getData();
                            if (dataBeanList != null&&dataBeanList.size()!=0) {
                                adapter.setNewData(dataBeanList);
                                adapter.loadMoreComplete();
                                adapter.loadMoreEnd(false);
                            }else {
                                adapter.setNewData(dataBeanList);
                                adapter.loadMoreEnd(true);
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    if(srlCommon!=null){
                        srlCommon.setRefreshing(false);
                    }
                    ToastUtils.ToastMessage(NoticeStuListActivity.this,"网络连接出现问题");
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
        adapter.setEnableLoadMore(false);
        loadData();
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
