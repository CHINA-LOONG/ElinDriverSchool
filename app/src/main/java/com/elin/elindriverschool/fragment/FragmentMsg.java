package com.elin.elindriverschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.NoticeTypeCountAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NoticeTypeCountBean;
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

/**
 * Created by imac on 17/1/10.
 */

public class FragmentMsg extends BaseFragment {
    @Bind(R.id.rv_msg_module)
    RecyclerView rvMsgModule;
    @Bind(R.id.tv_msg_type)
    TextView tvMsgType;
    @Bind(R.id.tv_read_all)
    TextView tvReadAll;

    private View view;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private List<NoticeTypeCountBean.DataBean> dataList = new ArrayList<>();
    private NoticeTypeCountAdapter adapter;
    private FragmentManager manager;
    private int location;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_msg, null);
        ButterKnife.bind(this, view);
        adapter = new NoticeTypeCountAdapter(getActivity(), dataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMsgModule.setLayoutManager(linearLayoutManager);
        rvMsgModule.setAdapter(adapter);
        setDefaultFragment();
        tvReadAll.setVisibility(View.VISIBLE);
        rvMsgModule.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                location = position;
                if(position!=0){
                    tvReadAll.setVisibility(View.GONE);
                }else {
                    tvReadAll.setVisibility(View.VISIBLE);
                }
                NoticeTypeCountBean.DataBean bean = (NoticeTypeCountBean.DataBean) adapter.getItem(position);
                FragmentTransaction transaction = manager.beginTransaction();
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    RelativeLayout llMsgTop = (RelativeLayout) adapter.getViewByPosition(rvMsgModule, i, R.id.item_ll_msg_top);
                    NotificationFragment fragment = (NotificationFragment) manager.findFragmentByTag(i + "");
                    if (fragment != null) {
                        transaction.hide(fragment);
                    }
                    if (i == position) {
                        llMsgTop.setBackgroundColor(getResources().getColor(R.color.line_color_gray));
                    } else {
                        llMsgTop.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
                NotificationFragment fragmentByTag = (NotificationFragment) manager.findFragmentByTag(position + "");
                if (fragmentByTag != null) {
                    transaction.show(fragmentByTag);
                } else {
                    NotificationFragment fragment = NotificationFragment.newInstance(bean.getId());
                    transaction.add(R.id.rl_msg_container, fragment, position + "");
                }
                transaction.commit();
                tvMsgType.setText(bean.getName());
            }
        });
        return view;
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        NotificationFragment fragment = NotificationFragment.newInstance("0");
        manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.rl_msg_container, fragment, "0");
        transaction.commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_NOTICE_TYPE_COUNT)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(getActivity()).newCall(request);
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
                    if (responseJson != null) {
                        NoticeTypeCountBean bean = new Gson().fromJson(responseJson, NoticeTypeCountBean.class);
                        if (TextUtils.equals("0", bean.getRc())) {
                            dataList = bean.getData();
                            if (dataList != null && dataList.size() != 0) {
                                tvMsgType.setText(dataList.get(location).getName());
                                adapter.setNewData(dataList);
                                adapter.loadMoreComplete();
                            } else {
                                adapter.setNewData(dataList);
                                adapter.loadMoreEnd(true);
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }

                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(getActivity(), "网络连接出现问题");
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_read_all)
    public void onClick() {
        NotificationFragment fragment = (NotificationFragment) manager.findFragmentByTag("0");
        fragment.readAllReminder();
    }
}
