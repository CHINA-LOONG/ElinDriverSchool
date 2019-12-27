package com.elin.elindriverschool.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.PreSucStuInfoActivity;
import com.elin.elindriverschool.adapter.PreSucStuWaitOptionListAdapter;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.PreSucStuOptionBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 17/1/4.
 */

public class PreSucStuWaitOptionFragment extends Fragment {


    ListView lvPreSucWaitOption;
    PtrClassicFrameLayout ptrViewPreSucWaitOption;
    LinearLayout llPreSucWaitOptionContainer;
    LinearLayout layoutNodata;
    private View view;

    private Map<String, String> paramsMap = new HashMap<>();
    private String paramsJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private PreSucStuOptionBean preSucStuOptionBean;
    private PreSucStuWaitOptionListAdapter preSucStuWaitOptionListAdapter;
    private Intent intent = new Intent();
    private UpdateTitle updateTitle;
    private int stuCount = 0;
    private boolean flag = true;
    private MyProgressDialog progressDialog;
    public void setUpdate(UpdateTitle updateTitle) {
        this.updateTitle = updateTitle;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            if (flag) {
                if (getActivity() != null) {
                    progressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
                flag = false;
            }
            updateTitle.onUpdateTitle(stuCount + "");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pre_suc_wait_option, null);
        lvPreSucWaitOption = (ListView) view.findViewById(R.id.lv_pre_suc_wait_option);
        ptrViewPreSucWaitOption = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_view_pre_suc_wait_option);
        llPreSucWaitOptionContainer = (LinearLayout) view.findViewById(R.id.ll_pre_suc_wait_option_container);
        layoutNodata = (LinearLayout) view.findViewById(R.id.ll_pre_suc_wait_option_no_data);
        initRefreshLoadMore();
        return view;
    }


    //初始化下拉刷新 上拉加载
    private void initRefreshLoadMore() {
        ptrViewPreSucWaitOption.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrViewPreSucWaitOption.autoRefresh(true);
            }
        }, 200);
        ptrViewPreSucWaitOption.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPreSucWaitOption();
                        ptrViewPreSucWaitOption.refreshComplete();
                        if (!ptrViewPreSucWaitOption.isLoadMoreEnable()) {
                            ptrViewPreSucWaitOption.setLoadMoreEnable(false);
                        }
                    }
                }, 1500);
            }
        });
    }
    private void getPreSucWaitOption(){

        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", 1+"");
        paramsMap.put("pagesize", 10000+"");
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_PRE_SUC_WAIT_OPTION)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();

            }
        });

    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("预约成功待处理Json-->",responseJson);
                    preSucStuOptionBean = gson.fromJson(responseJson,PreSucStuOptionBean.class);
                    stuCount = preSucStuOptionBean.getData_list().size();
                    updateTitle.onUpdateTitle(stuCount + "");
                    if (preSucStuOptionBean.getRc()==0){
                        if (preSucStuOptionBean.getData_list().size()==0){
                            llPreSucWaitOptionContainer.setVisibility(View.GONE);
                            layoutNodata.setVisibility(View.VISIBLE);
                        }else {
                            llPreSucWaitOptionContainer.setVisibility(View.VISIBLE);
                            layoutNodata.setVisibility(View.GONE);
                            preSucStuWaitOptionListAdapter = new PreSucStuWaitOptionListAdapter(getActivity(),preSucStuOptionBean);
                            lvPreSucWaitOption.setAdapter(preSucStuWaitOptionListAdapter);
                            lvPreSucWaitOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    intent = new Intent(getActivity(), PreSucStuInfoActivity.class);
                                    intent.putExtra("preSucFlag",0);
                                    intent.putExtra("stuIdNum",preSucStuOptionBean.getData_list().get(i).getStu_idnum());
                                    startActivity(intent);
                                }
                            });
                        }
                    }else if(preSucStuOptionBean.getRc()==3000){
                        LogoutUtil.clearData(getActivity());
                        goToActivity(getActivity(),LoginActivity.class);
                        ToastUtils.ToastMessage(getActivity(),preSucStuOptionBean.getDes());
                    }
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
