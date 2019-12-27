package com.elin.elindriverschool.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AdvertisingActivity;
import com.elin.elindriverschool.activity.DriveCenterActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.OnlineRegistrationActivity;
import com.elin.elindriverschool.activity.SignupAdvanceActivity;
import com.elin.elindriverschool.activity.WebViewActivity;
import com.elin.elindriverschool.adapter.HomeDynamicAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.DynamicBean;
import com.elin.elindriverschool.sharedpreferences.PreferenceManager;
import com.elin.elindriverschool.util.AppSPUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.elin.elindriverschool.view.ScrollGridView;
import com.elin.elindriverschool.view.ScrollListView;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeNewFragment extends BaseFragment {


    @Bind(R.id.banner_home_guide)
    BGABanner bannerHomeGuide;
    @Bind(R.id.gv_home)
    ScrollGridView gvHome;
    @Bind(R.id.lv_home)
    ScrollListView lvHome;
    @Bind(R.id.tv_home_checkall)
    TextView tvHomeCheckall;
    List<View> views = new ArrayList<>();
    // 数据声明出来
    List<Map<String, Object>> listDriving = new ArrayList<Map<String, Object>>();
    @Bind(R.id.srl_home)
    SwipeRefreshLayout srlHome;

    private MyProgressDialog myProgressDialog;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private AlertDialog.Builder builder;
    private Map<String, String> parmasMap = new HashMap<>();
    HomeDynamicAdapter adapter;

    public HomeNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_new, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
        initData();
        adapter = new HomeDynamicAdapter(getActivity());
        lvHome.setAdapter(adapter);
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DynamicBean.DataListBean bean = (DynamicBean.DataListBean) adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("title", bean.getDynamic_title());
                bundle.putString("url", Constant.DYNAMIC_URL + Constant.DYNAMIC_ITEM + bean.getInner_id());
                goToActivity(getActivity(), WebViewActivity.class, bundle);

            }
        });
        srlHome.setColorSchemeResources(android.R.color.holo_red_light);
        srlHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        clickEvent();
        loadData();
        return view;
    }

    private void initData() {
        String names[] = getResources().getStringArray(R.array.home_menu);
        String icons[] = getResources().getStringArray(R.array.home_menu_img); // 去 res - arrays下去拿数据
        if (listDriving.size() == 0) {
            for (int i = 0; i < icons.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", names[i]);
                map.put("icon",
                        getResources().getIdentifier(icons[i], "drawable",
                                getActivity().getPackageName()));
                listDriving.add(map);
            }
        }
        SimpleAdapter adapterHealth = new SimpleAdapter(
                getActivity(),
                listDriving,
                R.layout.item_grid_home,
                new String[]{"name", "icon"},
                new int[]{R.id.item_grid_title, R.id.item_grid_img});
        gvHome.setAdapter(adapterHealth);
    }

    private void loadData() {
        myProgressDialog.show();
        parmasMap.put("type", 1 + "");
        parmasMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DYNAMIC_LIST)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

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

    @Override
    public void onStart() {
        super.onStart();
        uploadClientId();
    }

    //提交设备id
    private void uploadClientId(){
        Map<String, String> parmasClientId = new HashMap<>();
        parmasClientId.put("token", BaseApplication.getInstance().getToken());
        parmasClientId.put("coach_clientid", BaseApplication.getInstance().getCoach_clientid());
        parmasClientId.put("coach_client_type", Build.MANUFACTURER);
        Request requestClientId = new Request.Builder()
                .url(Constant.ServerURL + Constant.COMMIT_CLIENTID)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasClientId)))
                .build();

        Call callClientId = new MyOkHttpClient(getActivity()).newCall(requestClientId);
        callClientId.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (myProgressDialog.isShowing()) {
                        myProgressDialog.dismiss();
                    }
                    Log.e("请求成功", String.valueOf(response.body().string()));
                    call.cancel();
                }
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(srlHome!=null&&srlHome.isRefreshing()){
                srlHome.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    if (responseJson != null) {
                        final DynamicBean bean = new Gson().fromJson(responseJson, DynamicBean.class);
                        if (bean.getRc() == 0) {
                            adapter.clear();
                            adapter.addList(bean.getData_list());
                            bannerHomeGuide.setData(bean.getCarousels(),null);
                            bannerHomeGuide.setAdapter(new BGABanner.Adapter() {
                                @Override
                                public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                                    DynamicBean.CarouselsBean carouselsBean = (DynamicBean.CarouselsBean) model;
                                    Glide.with(getActivity()).
                                            load(carouselsBean.getCarousel_img()).placeholder(R.drawable.img_default).
                                            error(R.drawable.img_default).into((ImageView) itemView);
                                }
                            });
                        }
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(getActivity(),"请检查网络连接");
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        bannerHomeGuide.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        bannerHomeGuide.startAutoPlay();
    }

    private void clickEvent() {
        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://驾考中心
                        Intent drivecenterIntent = new Intent(getActivity(), DriveCenterActivity.class);
                        getActivity().startActivity(drivecenterIntent);
                        break;
                    case 1:
                        Bundle trainVenueBundle = new Bundle();
                        trainVenueBundle.putString("url", Constant.DYNAMIC_URL + "/DrivingService/driveDlist2.html?school_id="+BaseApplication.getInstance().getSchoolId());
                        trainVenueBundle.putString("title", "培训场地");
                        goToActivity(getActivity(), WebViewActivity.class, trainVenueBundle);
                        break;
                    case 2:
                        Bundle testVenueBundle = new Bundle();
                        testVenueBundle.putString("url", Constant.DYNAMIC_URL + "/DrivingService/driveDlist3.html?school_id="+BaseApplication.getInstance().getSchoolId());
                        testVenueBundle.putString("title", "考试场地");
                        goToActivity(getActivity(), WebViewActivity.class, testVenueBundle);
                        break;
                    case 3:
                       goToActivity(getActivity(), OnlineRegistrationActivity.class);
                        break;
                    case 4:
                        Bundle busRouteBundle = new Bundle();
                        busRouteBundle.putString("url", Constant.DYNAMIC_URL + "/DrivingService/shuttleRoute.html?school_id="+BaseApplication.getInstance().getSchoolId());
                        busRouteBundle.putString("title", "班车路线");
                        goToActivity(getActivity(), WebViewActivity.class, busRouteBundle);
                        break;
                    case 5:
                        if (TextUtils.isEmpty(BaseApplication.getInstance().getCoachIdNum())) {
                            goToActivity(getActivity(), LoginActivity.class);
                        } else {
                            goToActivity(getActivity(), AdvertisingActivity.class);
                        }
                        break;
                    case 6:
                        Bundle drivingProcess = new Bundle();
                        drivingProcess.putString("url", Constant.DYNAMIC_URL + "/DrivingService/studyProcess.html?school_id="+BaseApplication.getInstance().getSchoolId());
                        drivingProcess.putString("title", "学驾流程");
                        goToActivity(getActivity(), WebViewActivity.class, drivingProcess);
                        break;
                }
            }
        });
    }

    private void showDialog() {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("操作提示");
        builder.setMessage("敬请期待...");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_home_checkall)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString("title", "全部动态");
        bundle.putString("url", Constant.DYNAMIC_URL + Constant.ALL_DYNAMIC_LIST+"?school_id="+BaseApplication.getInstance().getSchoolId());
        goToActivity(getActivity(), WebViewActivity.class, bundle);
    }
}
