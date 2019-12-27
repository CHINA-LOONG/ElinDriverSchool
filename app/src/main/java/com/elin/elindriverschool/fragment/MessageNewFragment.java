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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.MessageActivity;
import com.elin.elindriverschool.adapter.NoticeTypeCountAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NoticeTypeCountBean;
import com.elin.elindriverschool.util.LogoutUtil;
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
public class MessageNewFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.rv_fragment_msg)
    RecyclerView rvFragmentMsg;
    @Bind(R.id.srl_fragment_msg)
    SwipeRefreshLayout srlFragmentMsg;
    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private List<NoticeTypeCountBean.DataBean> dataList = new ArrayList<>();
    private NoticeTypeCountAdapter adapter;

    public MessageNewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_new, container, false);
        ButterKnife.bind(this, view);
        adapter = new NoticeTypeCountAdapter(getActivity(), dataList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        srlFragmentMsg.setOnRefreshListener(this);
        srlFragmentMsg.setColorSchemeResources(android.R.color.holo_red_light);
        rvFragmentMsg.setLayoutManager(linearLayoutManager);
        rvFragmentMsg.setAdapter(adapter);

        rvFragmentMsg.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rvFragmentMsg.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                NoticeTypeCountBean.DataBean bean = (NoticeTypeCountBean.DataBean) adapter.getItem(position);
                if (position == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", bean.getId());
                    bundle.putString("title", bean.getName());
                    goToActivity(getActivity(), MessageActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("flag", bean.getId());
                    bundle.putString("title", bean.getName());
                    goToActivity(getActivity(), MessageActivity.class, bundle);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        srlFragmentMsg.setRefreshing(true);
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

    //验证改写法也有可能造成内存泄漏
    private Handler mHandler  = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (srlFragmentMsg != null && srlFragmentMsg.isRefreshing()) {
                srlFragmentMsg.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    if (responseJson != null) {
                        Log.e("MessageNewFragment", responseJson);
                        NoticeTypeCountBean bean = new Gson().fromJson(responseJson, NoticeTypeCountBean.class);
                        if (TextUtils.equals("0", bean.getRc())) {
                            dataList = bean.getData();
                            if (dataList != null && dataList.size() != 0) {
                                adapter.setNewData(dataList);
                                adapter.loadMoreComplete();
                            } else {
                                adapter.setNewData(dataList);
                                adapter.loadMoreEnd(true);
                            }
                        } else if(TextUtils.equals("3000",bean.getRc())){
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(),LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(),bean.getDes());
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_empty,rvFragmentMsg);
                    ToastUtils.ToastMessage(getActivity(), "网络连接出现问题");
                    break;
            }
            return false;
        }
    });
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (srlFragmentMsg != null && srlFragmentMsg.isRefreshing()) {
//                srlFragmentMsg.setRefreshing(false);
//            }
//            switch (msg.what) {
//                case 0:
//                    if (responseJson != null) {
//                        Log.e("MessageNewFragment", responseJson);
//                        NoticeTypeCountBean bean = new Gson().fromJson(responseJson, NoticeTypeCountBean.class);
//                        if (TextUtils.equals("0", bean.getRc())) {
//                            dataList = bean.getData();
//                            if (dataList != null && dataList.size() != 0) {
//                                adapter.setNewData(dataList);
//                                adapter.loadMoreComplete();
//                            } else {
//                                adapter.setNewData(dataList);
//                                adapter.loadMoreEnd(true);
//                            }
//                        } else {
//                            adapter.loadMoreEnd(true);
//                        }
//                    }
//                    break;
//                case 1:
//                    ToastUtils.ToastMessage(getActivity(), "网络连接出现问题");
//                    break;
//            }
//        }
//    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRefresh() {
        onResume();
    }
}