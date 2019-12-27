package com.elin.elindriverschool.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AdvertisingDetailActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.adapter.PersonalAdvertisingAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.AdvertisingBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
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
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * 个人宣传
 */
public class PersonalAdvertiseFragment extends BaseFragment  implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_personal_advertising)
    RecyclerView rvPersonalAdvertising;
    @Bind(R.id.srl_personal_advertising)
    SwipeRefreshLayout srlPersonalAdvertising;
//    @Bind(R.id.tv_empty_personal_advertising)
//    TextView tvEmptyPersonalAdvertising;

    private MyProgressDialog myProgressDialog;
    PersonalAdvertisingAdapter adapter;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private Map<String, String> parmasMap = new HashMap<>();
    private int pageNo=1;
    private int rowMax = 20;
    private String responseJson;
    PersonalAdvertisingReceiver receiver;
    Bitmap bitmap;
    List<AdvertisingBean.DataListBean> data_list = new ArrayList<>();
    public PersonalAdvertiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_advertise, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        srlPersonalAdvertising.setRefreshing(true);
        rvPersonalAdvertising.setLayoutManager(new LinearLayoutManager(getActivity()));
        srlPersonalAdvertising.setColorSchemeResources(android.R.color.holo_red_light);
        adapter = new PersonalAdvertisingAdapter(data_list,getActivity());
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlPersonalAdvertising.setOnRefreshListener(this);
        rvPersonalAdvertising.setAdapter(adapter);
        rvPersonalAdvertising.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        receiver = new PersonalAdvertisingReceiver();
        IntentFilter filter = new IntentFilter("advertisingDataFresh");
        getActivity().registerReceiver(receiver,filter);

        rvPersonalAdvertising.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdvertisingBean.DataListBean bean = (AdvertisingBean.DataListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("inner_id",bean.getInner_id()+"");
                bundle.putString("coach_phone",bean.getCoach_phone());
                bundle.putString("coach_wx",bean.getCoach_wx());
                bundle.putString("coach_name",bean.getCoach_name());
                bundle.putString("coach_infor",bean.getCoach_infor());
                bundle.putString("dynamic_title",bean.getDynamic_title());
                bundle.putString("dynamic_content",bean.getDynamic_content());
                bundle.putString("dynamic_pic",bean.getDynamic_img());
                bundle.putString("dynamic_Intro",bean.getIntro());
                ImageView imageView = (ImageView) adapter.getViewByPosition(rvPersonalAdvertising,position,R.id.iv_item_advertising_pic);
                imageView.setDrawingCacheEnabled(true);
                bitmap = imageView.getDrawingCache();
                bundle.putParcelable("bitmap",bitmap);
                goToActivity(getActivity(), AdvertisingDetailActivity.class,bundle);
            }
        });
        loadData(pageNo);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        loadData(pageNo);
        srlPersonalAdvertising.setRefreshing(false);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        loadData(pageNo);
    }

    private void loadData(int num) {
        myProgressDialog.show();
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("pageno", num + "");
        parmasMap.put("pagesize", rowMax + "");
        parmasMap.put("type", 4 + "");
        parmasMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DYNAMIC_LIST)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();
        Log.e("=======", new Gson().toJson(parmasMap));
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(1);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (myProgressDialog.isShowing()) {
                        myProgressDialog.dismiss();
                    }
                    responseJson = String.valueOf(response.body().string());
                    Log.e("请求成功-->", responseJson.toString());
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
                    srlPersonalAdvertising.setRefreshing(false);
                    if (responseJson != null) {
                        AdvertisingBean bean = new Gson().fromJson(responseJson, AdvertisingBean.class);
                        if (bean.getRc() == 0) {
                            data_list = bean.getData_list();
                            if (data_list != null && data_list.size() != 0) {
                                if (pageNo == 1) {
                                    adapter.setNewData(data_list);
                                    srlPersonalAdvertising.setRefreshing(false);
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.addData(data_list);
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                adapter.setEmptyView(R.layout.layout_empty,rvPersonalAdvertising);
                                adapter.loadMoreEnd(false);
                            }
                        } else if(bean.getRc()==3000){
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(),LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(), bean.getDes());
                        }
                    }
                    break;
                case 1:
                    if (srlPersonalAdvertising != null) {
                        srlPersonalAdvertising.setRefreshing(false);
                    }
                    adapter.setEmptyView(R.layout.layout_empty,rvPersonalAdvertising);
                    break;
            }
        }
    };


    class PersonalAdvertisingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if("advertisingDataFresh".equals(intent.getAction())){
                onRefresh();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }
}
