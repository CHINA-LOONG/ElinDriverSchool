package com.elin.elindriverschool.fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.adapter.MyDriveStudentListAdapter;
import com.elin.elindriverschool.adapter.TagAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.FlowTagLayout;
import com.elin.elindriverschool.view.OnTagSelectListener;
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

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;
import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentTaskFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {


    @Bind(R.id.rv_stu_task)
    RecyclerView rvStuTask;
    @Bind(R.id.srl_stu_task)
    SwipeRefreshLayout srlStuTask;
    @Bind(R.id.tv_task_steps)
    TextView tvTaskSteps;
    @Bind(R.id.imv_task_steps)
    ImageView imvTaskSteps;
    @Bind(R.id.ll_task_steps)
    LinearLayout llTaskSteps;
    @Bind(R.id.view_bg)
    View viewBg;
    @Bind(R.id.isGraduate)
    CheckBox isGraduate;
    @Bind(R.id.rb_finished_task)
    RadioButton rbFinishedTask;
    @Bind(R.id.rb_unfinished_task)
    RadioButton rbUnfinishedTask;
    @Bind(R.id.rg_assisting_task_title)
    RadioGroup rgAssistingTaskTitle;

    private Map<String, String> paramsMap = new HashMap<>();
    int rowBegin = 1;
    String flag;
    Gson gson = new Gson();
    List<MyDriveStudentBean.DataListBean> data_list = new ArrayList<>();
    private MyDriveStudentBean myDriveStudentBean;
    private String responseJson;
    private MyDriveStudentListAdapter adapter;
    private TagAdapter<String> themeAdapter;
    private String[] tagStatus;
    private List<String> stringArrayList = new ArrayList<>();
    private String status = "";
    private String taskName = "";
    private PopupWindow taskView;
    private String graduate="0";//0未毕业  1已经毕业
    FragmentManager manager;
    private AssistingTaskFragment finishedTaskFragment;
    private AssistingTaskFragment unfinishedTaskFragment;
    public StudentTaskFragment() {
        // Required empty public constructor
    }

    public static StudentTaskFragment newInstance(String flag) {
        StudentTaskFragment fragment = new StudentTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_task, container, false);
        ButterKnife.bind(this, view);
        flag = getArguments().getString("flag");
        adapter = new MyDriveStudentListAdapter(data_list, getActivity());
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlStuTask.setOnRefreshListener(this);
        srlStuTask.setColorSchemeResources(android.R.color.holo_red_light);
        rvStuTask.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStuTask.setAdapter(adapter);
        adapter.setEmptyView(R.layout.layout_bg_nodata, rvStuTask);
        rvStuTask.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        switch (flag) {
            case "1":     //未使用学员端
//                llTaskSteps.setVisibility(View.GONE);
                viewBg.setVisibility(View.GONE);
                isGraduate.setVisibility(View.VISIBLE);
                loadData(rowBegin, Constant.MY_DRIVE_STU);
                isGraduate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            graduate = "1";
                        }else {
                            graduate ="0";
                        }
                        loadData(rowBegin, Constant.MY_DRIVE_STU);
                    }
                });
                break;
            case "2":       //未完成任务
                llTaskSteps.setVisibility(View.VISIBLE);
                viewBg.setVisibility(View.GONE);
//                isGraduate.setVisibility(View.GONE);
                loadData(rowBegin, Constant.GET_HAS_JOB_STU_LIST);
                final View tagView = LayoutInflater.from(getActivity()).inflate(R.layout.tag_layout_status, null);
                taskView = new PopupWindow(tagView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ColorDrawable drawable = new ColorDrawable(0xffffffff);
                taskView.setBackgroundDrawable(drawable);
                taskView.setTouchable(true);
                taskView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        viewBg.setVisibility(View.GONE);
                        imvTaskSteps.setBackgroundResource(R.drawable.icon_arrow_down);
                        tvTaskSteps.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_light_dark));
                    }
                });
                FlowTagLayout flowTagStatus = (FlowTagLayout) tagView.findViewById(R.id.flow_tag_status);
                tagStatus = getResources().getStringArray(R.array.steps);
                for (int i = 0; i < tagStatus.length; i++) {
                    stringArrayList.add(tagStatus[i]);
                }
                themeAdapter = new TagAdapter<>(getActivity());
                flowTagStatus.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
                flowTagStatus.setAdapter(themeAdapter);
                themeAdapter.onlyAddAll(stringArrayList);
                flowTagStatus.setOnTagSelectListener(new OnTagSelectListener() {
                    @Override
                    public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {

                        String[] strings = getResources().getStringArray(R.array.steps_id);
                        if (selectedList.size() > 0) {
                            status = strings[selectedList.get(0)];
                            taskName = tagStatus[selectedList.get(0)];
                        } else {
                            status = "";
                            taskName = "全部阶段任务";
                        }
                        tvTaskSteps.setText(taskName);
                        loadData(rowBegin, Constant.GET_HAS_JOB_STU_LIST);
                        if (taskView.isShowing()) {
                            taskView.dismiss();
                        }
                    }
                });
                break;
            case "3":
                rgAssistingTaskTitle.setVisibility(View.VISIBLE);
                viewBg.setVisibility(View.GONE);
                srlStuTask.setVisibility(View.GONE);
                rvStuTask.setVisibility(View.GONE);
                manager = getChildFragmentManager();
                finishedTaskFragment = AssistingTaskFragment.newInstance("1");
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.fl_stu_task, finishedTaskFragment);
                transaction.commit();
                rgAssistingTaskTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        FragmentTransaction transaction = manager.beginTransaction();
                        hideFragment(transaction);
                        switch (checkedId){
                            case R.id.rb_finished_task:
                                if(finishedTaskFragment==null){
                                    finishedTaskFragment = AssistingTaskFragment.newInstance("1");
                                    transaction.add(R.id.fl_stu_task,finishedTaskFragment);
                                }else {
                                    transaction.show(finishedTaskFragment);
                                }
                                break;
                            case R.id.rb_unfinished_task:
                                if(unfinishedTaskFragment==null){
                                    unfinishedTaskFragment = AssistingTaskFragment.newInstance("2");
                                    transaction.add(R.id.fl_stu_task,unfinishedTaskFragment);
                                }else {
                                    transaction.show(unfinishedTaskFragment);
                                }
                                break;
                        }
                        transaction.commit();
                    }
                });
                break;
        }
        rvStuTask.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                MyDriveStudentBean.DataListBean bean = (MyDriveStudentBean.DataListBean) adapter.getItem(position);
                MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), getActivity());
            }
        });
        return view;
    }
    private void hideFragment(FragmentTransaction transaction) {
        if (finishedTaskFragment != null) {
            transaction.hide(finishedTaskFragment);
        }
        if (unfinishedTaskFragment != null) {
            transaction.hide(unfinishedTaskFragment);
        }
    }
    private void loadData(int pageNo, String url) {
        srlStuTask.setRefreshing(true);
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", pageNo + "");
        paramsMap.put("pagesize", "20");
        if (TextUtils.equals("1", flag)) {
            paramsMap.put("stu_type", "7");
            paramsMap.put("is_graduate",graduate);
        } else if (TextUtils.equals("2", flag)) {
            paramsMap.put("steps", status);
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
            if (srlStuTask != null && srlStuTask.isRefreshing()) {
                srlStuTask.setRefreshing(false);
            }
            switch (msg.what) {
                case 0:
                    if (responseJson != null) {
                        myDriveStudentBean = gson.fromJson(responseJson, MyDriveStudentBean.class);
                        if (myDriveStudentBean.getRc().equals("0")) {
                            data_list = myDriveStudentBean.getData_list();
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
                                if (rowBegin == 1) {
                                    adapter.setNewData(data_list);
                                    adapter.loadMoreEnd(true);
                                } else {
                                    adapter.loadMoreEnd(false);
                                }
                            }
                        } else if("3000".equals(myDriveStudentBean.getDes())){
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(),LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(),myDriveStudentBean.getDes());
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
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        rowBegin = 1;
        switch (flag) {
            case "1":
                loadData(rowBegin, Constant.MY_DRIVE_STU);
                break;
            case "2":
                loadData(rowBegin, Constant.GET_HAS_JOB_STU_LIST);
                break;
        }
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        rowBegin = rowBegin + 1;
        switch (flag) {
            case "1":
                loadData(rowBegin, Constant.MY_DRIVE_STU);
                break;
            case "2":
                loadData(rowBegin, Constant.GET_HAS_JOB_STU_LIST);
                break;
        }
    }

    @OnClick({R.id.ll_task_steps, R.id.view_bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_task_steps:
                if (taskView.isShowing()) {
                    taskView.dismiss();
                } else {
                    imvTaskSteps.setBackgroundResource(R.drawable.icon_arrow_up);
                    tvTaskSteps.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    taskView.showAsDropDown(llTaskSteps, 0, 0);
                    if (taskView.isShowing()) {
                        viewBg.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.view_bg:
                if (taskView.isShowing()) {
                    taskView.dismiss();
                }
                break;
        }
    }
}
