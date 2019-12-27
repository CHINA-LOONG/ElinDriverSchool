package com.elin.elindriverschool.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.StudentInfoActivity;
import com.elin.elindriverschool.adapter.WaitTestAdapter;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import org.chenglei.widget.datepicker.DatePicker;
import org.json.JSONException;
import org.json.JSONObject;

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
 * A simple {@link Fragment} subclass.
 * 代考人员
 */
public class WaitTestFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,
        WaitTestAdapter.OnStuItemCheckedCallBack {

    @Bind(R.id.rv_wait_test)
    RecyclerView rvWaitTest;
    @Bind(R.id.srl_wait_test)
    SwipeRefreshLayout srlWaitTest;
    @Bind(R.id.tv_wait_test_class_num)
    TextView tvWaitTestClassNum;
    @Bind(R.id.tv_wait_test_date)
    TextView tvWaitTestDate;
    @Bind(R.id.tv_wait_test_address)
    TextView tvWaitTestAddress;
    @Bind(R.id.cb_wait_test_take_bus)
    CheckBox cbWaitTestTakeBus;
    @Bind(R.id.btn_wait_test_class_submit)
    Button btnWaitTestClassSubmit;
    @Bind(R.id.ll_wait_test_container)
    RelativeLayout llWaitTestContainer;

    View pv_date;
    @Bind(R.id.indexBar_contact)
    IndexBar indexBarContact;
    @Bind(R.id.tvSideBarHint_contact)
    TextView tvSideBarHintContact;
    private PopupWindow pw_date;
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, Object> stuSubmitParamMap = new HashMap<>();
    private String paramsJson, paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private WaitOptionTestStuBean waitOptionTestStuBean;
    private WaitTestAdapter adapter;
    private UpdateTitle updateTitle;
    private String[] testSites;
    private String testSiteJson;
    private TestSiteBean testSiteBean;
    private int takeBusFlag = 2;//1 坐车 2 不坐车
    private List<String> stuIdNumList = new ArrayList<>();
    private Intent intent = new Intent();
    List<WaitOptionTestStuBean.DataListBean> data_list = new ArrayList<>();
    private HashMap<Integer, Boolean> itemCheckedMap = new HashMap<>();
    private DatePicker datePicker;
    int flag;
    LinearLayout layoutNodata;
    LinearLayout layoutBottom;
    private LinearLayoutManager mManger;
    private SuspensionDecoration mDecoration;
    private boolean visible = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(visible){
                if (srlWaitTest != null) {
                    srlWaitTest.setOnRefreshListener(this);
                    srlWaitTest.setColorSchemeResources(android.R.color.holo_red_light);
                    srlWaitTest.setRefreshing(true);
                }
                getWaitTestClassOneStu();
                visible = false;
            }
        } else {
        }
    }

    //接口回调修改待考人员数量
    public void setUpdate(UpdateTitle updateTitle) {
        this.updateTitle = updateTitle;
    }

    public static WaitTestFragment newInstance(int flag) {
        WaitTestFragment fragment = new WaitTestFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", flag);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wait_test, container, false);
        ButterKnife.bind(this, view);
        layoutNodata = (LinearLayout) view.findViewById(R.id.ll_wait_test_no_data);
        layoutBottom = (LinearLayout) view.findViewById(R.id.ll_wait_test_bottom);
        flag = getArguments().getInt("flag");
        adapter = new WaitTestAdapter(data_list, this, getActivity(), false,"1");
        mDecoration = new SuspensionDecoration(getActivity(),data_list);
        adapter.openLoadAnimation();
        mManger = new LinearLayoutManager(getActivity());
        rvWaitTest.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWaitTest.setAdapter(adapter);
        rvWaitTest.setLayoutManager(mManger);
        rvWaitTest.addItemDecoration(mDecoration);
        rvWaitTest.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        indexBarContact.setmPressedShowTextView(tvSideBarHintContact)
                .setNeedRealIndex(true)
                .setmLayoutManager(mManger);
        pv_date = LayoutInflater.from(getActivity()).inflate(R.layout.popup_date_picker, null);
        pw_date = new PopupWindow(pv_date, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw_date.setFocusable(true);
        pw_date.setOutsideTouchable(false);
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        pw_date.setBackgroundDrawable(drawable);
        datePicker = (DatePicker) pv_date.findViewById(R.id.date_picker);
        pv_date.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
        pv_date.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);
        cbWaitTestTakeBus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    takeBusFlag = 1;
                } else {
                    takeBusFlag = 2;
                }
            }
        });
        rvWaitTest.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                intent.setClass(getActivity(), StudentInfoActivity.class);
                intent.putExtra("stuIdNum", bean.getStu_idnum());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                super.onItemLongClick(adapter, view, position);
                MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), getActivity());
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    //获取学员列表
    private void getWaitTestClassOneStu() {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("exam_sub", flag + "");//科目一
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                updateTitle.onUpdateTitle("0");
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }

    //获取考试场地
    private void getTestSites() {
        srlWaitTest.setRefreshing(true);
        Map<String, String> map = new HashMap<>();
        map.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_TEST_SITES)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(map)))
                .build();
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                testSiteJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(1);
                call.cancel();
            }
        });
    }


    //提交学员考试
    private void submitStuTest() {
        srlWaitTest.setRefreshing(true);
        stuSubmitParamMap.put("token", BaseApplication.getInstance().getToken());
        stuSubmitParamMap.put("coach_idnum", BaseApplication.getInstance().getCoachIdNum());
        stuSubmitParamMap.put("exam_sub", flag + "");
        stuSubmitParamMap.put("stu_list", stuIdNumList);
        stuSubmitParamMap.put("exam_date", tvWaitTestDate.getText().toString());
        stuSubmitParamMap.put("exam_site", tvWaitTestAddress.getText().toString());
        stuSubmitParamMap.put("exam_bus", takeBusFlag + "");
        stuSubmitParamMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsSubmitJson = gson.toJson(stuSubmitParamMap);
        Log.e("科目一提交请求Json-->", paramsSubmitJson);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU_APPLY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            srlWaitTest.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    Log.e("请求学员返回的Json-->", String.valueOf(responseJson));
                    if (srlWaitTest.isRefreshing()) {
                        srlWaitTest.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        waitOptionTestStuBean = gson.fromJson(responseJson, WaitOptionTestStuBean.class);
                        if (waitOptionTestStuBean.getRc().equals("0")) {
                            data_list = waitOptionTestStuBean.getData_list();
                            if (data_list != null && data_list.size() != 0) {
                                updateTitle.onUpdateTitle(waitOptionTestStuBean.getData_list().size() + "");
                                llWaitTestContainer.setVisibility(View.VISIBLE);
                                layoutNodata.setVisibility(View.GONE);
                                layoutBottom.setVisibility(View.VISIBLE);
                                adapter.setNewData(data_list);

                                adapter.loadMoreComplete();
                                indexBarContact.setmSourceDatas(data_list).invalidate();
                                mDecoration.setmDatas(data_list);
                            } else {
                                Log.e("1-->", responseJson);
                                llWaitTestContainer.setVisibility(View.GONE);
                                layoutNodata.setVisibility(View.VISIBLE);
                                adapter.setNewData(data_list);
                                adapter.loadMoreEnd(true);
                                updateTitle.onUpdateTitle("0");

                            }
                        } else if ("3000".equals(waitOptionTestStuBean.getRc())) {
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(), LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(), waitOptionTestStuBean.getDes());
                        }
                    }
                    break;
                case 1:
                    if (srlWaitTest.isRefreshing()) {
                        srlWaitTest.setRefreshing(false);
                    }
                    testSiteBean = gson.fromJson(testSiteJson, TestSiteBean.class);
                    testSites = new String[testSiteBean.getData_list().size()];
                    for (int i = 0; i < testSiteBean.getData_list().size(); i++) {
                        testSites[i] = testSiteBean.getData_list().get(i).getExam_site();
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("选择考试场地")
                            .setItems(testSites, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    tvWaitTestAddress.setText(testSiteBean.getData_list().get(i).getExam_site());
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .create().show();
                    break;
                case 2:
                    if (srlWaitTest.isRefreshing()) {
                        srlWaitTest.setRefreshing(false);
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(getActivity(), "提交成功");
                            tvWaitTestDate.setText("");
                            tvWaitTestAddress.setText("");
                            cbWaitTestTakeBus.setChecked(false);
                            getWaitTestClassOneStu();
                        } else {
                            ToastUtils.ToastMessage(getActivity(), "提交失败，请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    if (srlWaitTest.isRefreshing()) {
                        srlWaitTest.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        getWaitTestClassOneStu();
        srlWaitTest.setRefreshing(false);
        adapter.removeAllFooterView();
    }


    @Override
    public void getCheckedMap(HashMap<Integer, Boolean> map) {
        itemCheckedMap = map;
    }

    @OnClick({R.id.tv_wait_test_date, R.id.tv_wait_test_address, R.id.btn_wait_test_class_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wait_test_date:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                pw_date.setAnimationStyle(R.style.anim_popwindow);
                pw_date.showAtLocation(getView(), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wait_test_address:
                getTestSites();
                break;
            case R.id.btn_wait_test_class_submit:
                stuIdNumList.clear();
                for (int i = 0; i < adapter.mChecked.size(); i++) {
                    if (adapter.mChecked.get(i)) {
                        stuIdNumList.add(adapter.getItem(i).getStu_idnum());
                    }
                }
                if (stuIdNumList.size() == 0) {
                    ToastUtils.ToastMessage(getActivity(), "请选择学员");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("操作提示")
                            .setMessage("确认要提交吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (testData()) {
                                        submitStuTest();
                                        ToastUtils.ToastMessage(getActivity(), "提交成功");
                                    }
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
                break;
            case R.id.tv_datepicker_cancel:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                break;
            case R.id.tv_popup_datepicker_confirm:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                if (datePicker.getMonth() < 10) {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvWaitTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvWaitTestDate.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                } else {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvWaitTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvWaitTestDate.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                }
                break;
        }
    }

    private boolean testData() {
        if (tvWaitTestDate.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(getActivity(), "请选择考试日期");
            return false;
        }
        if (tvWaitTestAddress.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(getActivity(), "请选择考试场地");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
