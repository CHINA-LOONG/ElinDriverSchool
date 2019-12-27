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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.MyDriveStudentActivity;
import com.elin.elindriverschool.activity.StudentInfoActivity;
import com.elin.elindriverschool.adapter.AssistingTaskAdapter;
import com.elin.elindriverschool.adapter.MyDriveStudentListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.AssistingTaskBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.util.LogoutUtil;
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

import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistingTaskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_assisting_task)
    RecyclerView rvAssistingTask;
    @Bind(R.id.srl_assisting_task)
    SwipeRefreshLayout srlAssistingTask;
    private String flag;

    private List<AssistingTaskBean.DataBean> data_list = new ArrayList<>();
    private AssistingTaskAdapter adapter;
    private int rowBegin = 1;
    private Map<String, String> paramsMap = new HashMap<>();
    Gson gson = new Gson();
    private String responseJson;
    private AssistingTaskBean assistingTaskBean;
    public AssistingTaskFragment() {
        // Required empty public constructor
    }

    public static AssistingTaskFragment newInstance(String flag) {
        AssistingTaskFragment fragment = new AssistingTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assisting_task, container, false);
        ButterKnife.bind(this, view);
        flag = getArguments().getString("flag");
        adapter = new AssistingTaskAdapter(getActivity(),data_list);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlAssistingTask.setOnRefreshListener(this);
        srlAssistingTask.setColorSchemeResources(android.R.color.holo_red_light);
        rvAssistingTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAssistingTask.setAdapter(adapter);

        rvAssistingTask.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        loadData(rowBegin,Constant.GET_ASSISTING_TASK_STU);
        rvAssistingTask.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AssistingTaskBean.DataBean bean = (AssistingTaskBean.DataBean) adapter.getItem(position);
                MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), getActivity());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        rowBegin = 1;
        loadData(rowBegin, Constant.GET_ASSISTING_TASK_STU);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {

    }
    private void loadData(int pageNo, String url) {
        srlAssistingTask.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", pageNo + "");
        paramsMap.put("pagesize", "20");
        if (TextUtils.equals("1", flag)) {
            paramsMap.put("attend_task", "1");  //参加助力任务

        } else if (TextUtils.equals("2", flag)) {
            paramsMap.put("attend_task", "0");    //未参加
        }
        Request request = new Request.Builder()
                .url(Constant.ServerURL + url)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(paramsMap)))
                .build();
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
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
            if (srlAssistingTask != null && srlAssistingTask.isRefreshing()) {
                srlAssistingTask.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    if (responseJson != null) {

                        assistingTaskBean = gson.fromJson(responseJson, AssistingTaskBean.class);
                        if (assistingTaskBean.getRc().equals("0")) {
                            data_list = assistingTaskBean.getData();
                            if (data_list != null && data_list.size() != 0) {
                                if (rowBegin == 1) {
                                    adapter.setNewData(data_list);
                                    if(data_list.size()<20){
                                        adapter.loadMoreEnd(true);
                                    }
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.addData(data_list);
                                    adapter.loadMoreComplete();
                                }
                            } else {
                                adapter.setEmptyView(R.layout.layout_bg_nodata, rvAssistingTask);
                            }
                        } else if(TextUtils.equals("3000",assistingTaskBean.getRc())){
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(),LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(),assistingTaskBean.getDes());
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_bg_nodata, rvAssistingTask);
                    ToastUtils.ToastMessage(getActivity(), "网络连接出现问题");
                    break;
            }
        }
    };
}
