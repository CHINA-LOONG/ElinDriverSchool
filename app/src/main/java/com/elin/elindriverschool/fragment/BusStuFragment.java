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
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.BusOrganizerAdapter;
import com.elin.elindriverschool.adapter.BusStuAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.BusOrganizerBean;
import com.elin.elindriverschool.model.BusStuBean;
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
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.FragmentMsg.MEDIA_TYPE_MARKDOWN;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusStuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_common)
    RecyclerView rvCommon;
    @Bind(R.id.srl_common)
    SwipeRefreshLayout srlCommon;

    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private String busDate,orderSub,busNum;
    private int pageNum=1;
    private BusStuAdapter adapter ;
    private List<BusStuBean.DataBean> dataBeanList = new ArrayList<>();
    public BusStuFragment() {
        // Required empty public constructor
    }
    public static BusStuFragment newInstance(String busDate,String orderSub,String busNum) {
        BusStuFragment fragment = new BusStuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("order_bus_date", busDate);
        bundle.putString("order_sub", orderSub);
        bundle.putString("order_bus_num", busNum);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.common_list, container, false);
        ButterKnife.bind(this, view);
        busDate = getArguments().getString("order_bus_date");
        orderSub = getArguments().getString("order_sub");
        busNum = getArguments().getString("order_bus_num");
        adapter = new BusStuAdapter(getActivity(),dataBeanList);
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
                BusStuBean.DataBean bean = (BusStuBean.DataBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.item_imv_stu_phone:
                        MobilePhoneUtils.makeCall(bean.getCoach_phone(),bean.getStu_name()+"("+bean.getStu_phone()+")",getActivity());
                        break;
                    case R.id.item_imv_coach_phone:
                        MobilePhoneUtils.makeCall(bean.getCoach_phone(),bean.getStu_coach()+"("+bean.getCoach_phone()+")",getActivity());
                        break;
                }
            }
        });
        loadData(pageNum+"");
        return view;
    }

    private void loadData(String num) {
        srlCommon.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("order_bus_date", busDate);
        parmasMap.put("order_sub", orderSub);
        parmasMap.put("order_bus_num", busNum);
        parmasMap.put("pageno", num);
        parmasMap.put("pagesize", "20");

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_ORDER_STU)
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
                        Log.e("班车学生-->", responseJson);
                        BusStuBean bean = new Gson().fromJson(responseJson, BusStuBean.class);
                        if (TextUtils.equals("0",bean.getRc())) {
                            dataBeanList = bean.getData();
                            int size = dataBeanList == null ? 0 : dataBeanList.size();
                            if (pageNum==1) {
                                adapter.setNewData(dataBeanList);
                            } else {
                                if (size > 0) {
                                    adapter.addData(dataBeanList);
                                }
                            }
                            if (size < 20) {
                                adapter.loadMoreEnd(false);
                            } else {
                                adapter.loadMoreComplete();
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
        pageNum = 1;
        adapter.setEnableLoadMore(false);
        loadData(pageNum+"");
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNum++;
        loadData(pageNum+"");
    }
}
