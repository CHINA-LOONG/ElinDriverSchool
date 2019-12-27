package com.elin.elindriverschool.fragment;


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
import com.elin.elindriverschool.activity.CompanyAdvertisingDetailActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.adapter.CompanyAdvertisingAdapter;
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
 * 公司宣传
 */
public class CompanyAdvertiseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_company_advertising)
    RecyclerView rvCompanyAdvertising;
    @Bind(R.id.srl_company_advertising)
    SwipeRefreshLayout srlCompanyAdvertising;
//    @Bind(R.id.tv_empty_company_advertising)
//    TextView tvEmptyCompanyAdvertising;

    private MyProgressDialog myProgressDialog;
    CompanyAdvertisingAdapter adapter;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private Map<String, String> parmasMap = new HashMap<>();
    private int pageNo=1;
    private int rowMax = 20;
    private String responseJson;
    Bitmap bitmap;
//    CompanyAdvertisingReceiver receiver;
    List<AdvertisingBean.DataListBean> data_list = new ArrayList<>();
    public CompanyAdvertiseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_company_advertise, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        srlCompanyAdvertising.setRefreshing(true);
        rvCompanyAdvertising.setLayoutManager(new LinearLayoutManager(getActivity()));
        srlCompanyAdvertising.setColorSchemeResources(android.R.color.holo_red_light);
        adapter = new CompanyAdvertisingAdapter(data_list,getActivity());
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlCompanyAdvertising.setOnRefreshListener(this);
        rvCompanyAdvertising.setAdapter(adapter);
        rvCompanyAdvertising.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST),data_list.size());

        rvCompanyAdvertising.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                AdvertisingBean.DataListBean bean = (AdvertisingBean.DataListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("dynamic_content",bean.getDynamic_content());
                bundle.putString("dynamic_title",bean.getDynamic_title());
                bundle.putString("dynamic_pic",bean.getDynamic_img());
                bundle.putString("dynamic_Intro",bean.getIntro());
                ImageView imageView = (ImageView) adapter.getViewByPosition(rvCompanyAdvertising,position,R.id.iv_company_advertising_pic);
                imageView.setDrawingCacheEnabled(true);
                bitmap = imageView.getDrawingCache();
                bundle.putParcelable("bitmap",bitmap);
                goToActivity(getActivity(), CompanyAdvertisingDetailActivity.class,bundle);
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


    private void loadData(int num) {
        myProgressDialog.show();
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("pageno", num + "");
        parmasMap.put("pagesize", rowMax + "");
        parmasMap.put("type", 5 + "");
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
                    srlCompanyAdvertising.setRefreshing(false);
                    if (responseJson != null) {
                        AdvertisingBean bean = new Gson().fromJson(responseJson, AdvertisingBean.class);
                        if (bean.getRc() == 0) {
                            data_list = bean.getData_list();
                            if (data_list != null && data_list.size() != 0) {
                                if (pageNo == 1) {
                                    adapter.setNewData(data_list);
                                    srlCompanyAdvertising.setRefreshing(false);
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.addData(data_list);
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                adapter.setEmptyView(R.layout.layout_empty,rvCompanyAdvertising);                                    adapter.setNewData(data_list);
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
                    if (srlCompanyAdvertising != null) {
                        srlCompanyAdvertising.setRefreshing(false);
                    }
                    adapter.setEmptyView(R.layout.layout_empty,rvCompanyAdvertising);
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        pageNo = 1;
        loadData(pageNo);
        srlCompanyAdvertising.setRefreshing(false);
        adapter.removeAllFooterView();
    }


    @Override
    public void onLoadMoreRequested() {
        pageNo++;
        loadData(pageNo);
    }

}
