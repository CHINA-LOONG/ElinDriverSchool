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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.NoticeAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NoticeBean;
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

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

public class NoticeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_notice)
    RecyclerView rvNotice;
    @Bind(R.id.srl_notice)
    SwipeRefreshLayout srlNotice;

    List<NoticeBean.NoticeInfoBean> data_list = new ArrayList<>();
    int rowBegin = 1;
    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private NoticeAdapter adapter;
    private Gson gson = new Gson();
    private NoticeBean bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.bind(this);
        tvCusTitleName.setText("公告");
        adapter = new NoticeAdapter(data_list, NoticeActivity.this);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlNotice.setOnRefreshListener(this);
        rvNotice.setLayoutManager(new LinearLayoutManager(NoticeActivity.this));
        srlNotice.setColorSchemeResources(android.R.color.holo_red_light);
        rvNotice.setAdapter(adapter);
        rvNotice.addItemDecoration(new DividerItemDecoration(NoticeActivity.this, DividerItemDecoration.VERTICAL_LIST));
        loadData(rowBegin);

    }


    private void loadData(int pageNo) {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("pageno", pageNo + "");
        parmasMap.put("pagesize", 20 + "");
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_ALL_NOTICE)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(1);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(0);
                    call.cancel();
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (srlNotice != null) {
                        srlNotice.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, NoticeBean.class);
                        Log.e("fdsfsdfsdfdsfs",responseJson);
                        if (TextUtils.equals("0", bean.getRc())) {
                            data_list = bean.getNotice_info();
                            if (data_list != null && data_list.size() != 0) {
                                if (rowBegin == 1) {
                                    adapter.setNewData(data_list);
                                } else {
                                    adapter.addData(data_list);
                                }
                                adapter.loadMoreComplete();
                            } else {
                                    Log.e("rowbegin=1",responseJson);
                                    adapter.setEmptyView(R.layout.layout_empty,rvNotice);
                                    adapter.loadMoreEnd(false);

                            }
                        } else {
                            Log.e("2-->", responseJson);
                            adapter.setEmptyView(R.layout.layout_empty,rvNotice);
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    if (srlNotice != null) {
                        srlNotice.setRefreshing(false);
                    }
                    if (rowBegin == 1) {
                        adapter.setEmptyView(R.layout.layout_empty);
                    } else {
                        ToastUtils.ToastMessage(NoticeActivity.this, "网络连接出现问题");
                    }
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        rowBegin = 1;
        loadData(rowBegin);
        srlNotice.setRefreshing(false);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        rowBegin = rowBegin + 1;
        loadData(rowBegin);
    }

    @OnClick(R.id.imv_cus_title_back)
    public void onClick() {
        finish();
    }
}
