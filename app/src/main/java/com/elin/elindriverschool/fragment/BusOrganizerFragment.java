package com.elin.elindriverschool.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.BusRideActivity;
import com.elin.elindriverschool.adapter.BusOrganizerAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.BusOrganizerBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MessageBean;
import com.elin.elindriverschool.model.NoticeBean;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.FragmentMsg.MEDIA_TYPE_MARKDOWN;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusOrganizerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_common)
    RecyclerView rvCommon;
    @Bind(R.id.srl_common)
    SwipeRefreshLayout srlCommon;
    private Map<String, String> parmasMap = new HashMap<>();
    private String busPerson;
    private String responseJson;
    private BusOrganizerAdapter adapter;
    private List<BusOrganizerBean.DataBean> dataBeanList = new ArrayList<>();
    protected boolean isVisible;
    public BusOrganizerFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(getUserVisibleHint()) {
//            isVisible = true;
//            loadData();
//        }
//    }

    public static BusOrganizerFragment newInstance(String busPerson) {
        BusOrganizerFragment fragment = new BusOrganizerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("order_bus_person", busPerson);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.common_list, container, false);
        ButterKnife.bind(this, view);
        busPerson = getArguments().getString("order_bus_person");
        adapter = new BusOrganizerAdapter(getActivity(),dataBeanList);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlCommon.setOnRefreshListener(this);
        srlCommon.setColorSchemeResources(android.R.color.holo_red_light);
        rvCommon.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCommon.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        rvCommon.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_empty,rvCommon);
        rvCommon.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BusOrganizerBean.DataBean bean = (BusOrganizerBean.DataBean) adapter.getItem(position);
                MobilePhoneUtils.makeCall(bean.getUser_phone(),bean.getUser_name(),getActivity());
            }
        });
        loadData();
        return view;
    }

    private void loadData() {
        srlCommon.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("order_bus_person", busPerson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_ORGANIZER)
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
                    if (srlCommon != null&&srlCommon.isRefreshing()) {
                        srlCommon.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        Log.e("班车人员-->", responseJson);
                        BusOrganizerBean bean = new Gson().fromJson(responseJson, BusOrganizerBean.class);
                        if (bean.getRc() == 0) {
                            dataBeanList = bean.getData();
                            if (dataBeanList != null && dataBeanList.size() != 0) {
                                adapter.setNewData(dataBeanList);
                                adapter.loadMoreComplete();
                                adapter.loadMoreEnd(false);
                            } else {
                                adapter.setNewData(dataBeanList);
                                adapter.loadMoreEnd(true);
                            }
                        } else {
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    if (srlCommon != null&&srlCommon.isRefreshing()) {
                        srlCommon.setRefreshing(false);
                    }
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
