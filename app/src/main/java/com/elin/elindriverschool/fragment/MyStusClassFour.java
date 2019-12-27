package com.elin.elindriverschool.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.activity.StudentInfoActivity;
import com.elin.elindriverschool.adapter.WaitTestStuListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TestSiteBean;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.chenglei.widget.datepicker.DatePicker;
import org.chenglei.widget.datepicker.Sound;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 16/12/26.
 */

public class MyStusClassFour extends Fragment implements View.OnClickListener, WaitTestStuListAdapter.OnStuItemCheckedCallBack {

    View view, pv_date;
    ListView lvWaitTestClass4;
    PtrClassicFrameLayout ptrViewWaitTestStuClass4;
    TextView tvWaitTestClassNum;
    TextView tvWaitTestDate;
    TextView tvWaitTestAddress;
    CheckBox cbWaitTestTakeBus;
    Button btnWaitTestClassSubmit;
    LinearLayout layoutNodata;
    LinearLayout layoutBottom;
    LinearLayout llWaitTestOnContainer;

    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, Object> stuSubmitParamMap = new HashMap<>();
    //    private Map<String, String> paramsMapTestSite = new HashMap<>();
    private String paramsJson, paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private WaitOptionTestStuBean waitOptionTestStuBean;
    private WaitTestStuListAdapter waitTestStuListAdapter;
    private HashMap<Integer, Boolean> itemCheckedMap = new HashMap<>();
    private List<String> stuIdNumList = new ArrayList<>();
    private List<Integer> listItemID = new ArrayList<>();

    private PopupWindow pw_date;
    private DatePicker datePicker;
    private Sound sound;
    private String[] testSites;
    private String testSiteJson;
    private TestSiteBean testSiteBean;
    private MyProgressDialog progressDialog;

    private int takeBusFlag = 2;//1 坐车 2 不坐车
    private Intent intent = new Intent();
    private int stuCount = 0;
    private UpdateTitle updateTitle;

    public void setUpdate(UpdateTitle updateTitle) {
        this.updateTitle = updateTitle;
    }
    private boolean flag = true;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (flag){
                progressDialog = new MyProgressDialog(getContext(), R.style.progress_dialog);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                initRefreshLoadMore();
//                getWaitTestClassTwoStu();
                flag = false;
            }
            updateTitle.onUpdateTitle(stuCount + "");
        } else {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_stus_class_4, null);

        lvWaitTestClass4 = (ListView) view.findViewById(R.id.lv_wait_test_class_4);
        ptrViewWaitTestStuClass4 = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_view_wait_test_stu_class_4);
        tvWaitTestClassNum = (TextView) view.findViewById(R.id.tv_wait_test_class_num);
        tvWaitTestDate = (TextView) view.findViewById(R.id.tv_wait_test_date);
        tvWaitTestAddress = (TextView) view.findViewById(R.id.tv_wait_test_address);
        cbWaitTestTakeBus = (CheckBox) view.findViewById(R.id.cb_wait_test_take_bus);
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
        btnWaitTestClassSubmit = (Button) view.findViewById(R.id.btn_wait_test_class_submit);
        layoutNodata = (LinearLayout) view.findViewById(R.id.ll_wait_test_class_4_no_data);
        layoutBottom = (LinearLayout) view.findViewById(R.id.ll_wait_test_class_4_bottom);
        llWaitTestOnContainer = (LinearLayout) view.findViewById(R.id.ll_wait_test_four_container);
        tvWaitTestClassNum.setText("科目四");
        tvWaitTestAddress.setOnClickListener(this);
        tvWaitTestDate.setOnClickListener(this);
        btnWaitTestClassSubmit.setOnClickListener(this);

        pv_date = LayoutInflater.from(getActivity()).inflate(R.layout.popup_date_picker, null);
        pw_date = new PopupWindow(pv_date, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw_date.setFocusable(true);
        pw_date.setOutsideTouchable(false);
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        pw_date.setBackgroundDrawable(drawable);
        datePicker = (DatePicker) pv_date.findViewById(R.id.date_picker);
        pv_date.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
        pv_date.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);
        return view;
    }


    //获取科目四待考学员


    private void getWaitTestClassTwoStu() {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("exam_sub", 4 + "");//科目四
        paramsMap.put("pageno", 1 + "");
        paramsMap.put("pagesize", 10000 + "");
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(404);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();

            }
        });
    }

    //初始化下拉刷新 上拉加载
    private void initRefreshLoadMore() {
        ptrViewWaitTestStuClass4.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrViewWaitTestStuClass4.autoRefresh(true);
            }
        }, 200);
        ptrViewWaitTestStuClass4.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWaitTestClassTwoStu();
                        ptrViewWaitTestStuClass4.refreshComplete();
                        if (!ptrViewWaitTestStuClass4.isLoadMoreEnable()) {
                            ptrViewWaitTestStuClass4.setLoadMoreEnable(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void getTestSites() {
        progressDialog.show();
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
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                ToastUtils.ToastMessage(getActivity(), "获取失败，请重试");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                testSiteJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(1);
                call.cancel();
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("科目四待考Json-->", responseJson);
                    waitOptionTestStuBean = gson.fromJson(responseJson, WaitOptionTestStuBean.class);
                    if (waitOptionTestStuBean.getRc().equals("0")) {
                        if (waitOptionTestStuBean.getData_list().size() == 0) {
                            llWaitTestOnContainer.setVisibility(View.GONE);
                            layoutNodata.setVisibility(View.VISIBLE);
                            updateTitle.onUpdateTitle(waitOptionTestStuBean.getData_list().size() + "");
                        } else {
                            llWaitTestOnContainer.setVisibility(View.VISIBLE);
                            layoutBottom.setVisibility(View.VISIBLE);
                            layoutNodata.setVisibility(View.GONE);
                            waitTestStuListAdapter = new WaitTestStuListAdapter(waitOptionTestStuBean, MyStusClassFour.this, getActivity(), false);
                            lvWaitTestClass4.setAdapter(waitTestStuListAdapter);
                            stuCount = waitOptionTestStuBean.getData_list().size();
                            updateTitle.onUpdateTitle(stuCount + "");
                            waitTestStuListAdapter.setItems(waitOptionTestStuBean.getData_list());
                            lvWaitTestClass4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    intent.setClass(getActivity(), StudentInfoActivity.class);
                                    intent.putExtra("stuIdNum", waitOptionTestStuBean.getData_list().get(i).getStu_idnum());
                                    startActivity(intent);
                                }
                            });
                            lvWaitTestClass4.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    MobilePhoneUtils.makeCall(waitOptionTestStuBean.getData_list().get(i).getStu_phone(), waitOptionTestStuBean.getData_list().get(i).getStu_name(), getActivity());
                                    return false;
                                }
                            });
                        }
                    } else if("3000".equals(waitOptionTestStuBean.getRc())){
                        LogoutUtil.clearData(getActivity());
                        goToActivity(getActivity(),LoginActivity.class);
                        ToastUtils.ToastMessage(getActivity(), waitOptionTestStuBean.getDes());
                    }
                    break;
                case 1:
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
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(getActivity(), "提交成功");
                            tvWaitTestDate.setText("");
                            tvWaitTestAddress.setText("");
                            cbWaitTestTakeBus.setChecked(false);
                            for (Map.Entry<Integer,Boolean> entry : waitTestStuListAdapter.getIsCheckedMap().entrySet()) {
                                if (entry.getValue()) {
                                    waitOptionTestStuBean.getData_list().get(entry.getKey()).setStu_exam_wait_flag("202");
                                    Log.e("学员位置",entry.getKey()+"");
                                }
                            }
                            waitTestStuListAdapter.notifyDataSetChanged();
//                            initRefreshLoadMore();
                        } else {
                            ToastUtils.ToastMessage(getActivity(), "提交失败，请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 404:
                    updateTitle.onUpdateTitle("0");
                    break;
            }
        }
    };

    //提交科目四学员
    private void submitStuTest() {
        progressDialog.show();
        stuSubmitParamMap.put("token", BaseApplication.getInstance().getToken());
        stuSubmitParamMap.put("coach_idnum", BaseApplication.getInstance().getCoachIdNum());
        stuSubmitParamMap.put("exam_sub", 4 + "");
        stuSubmitParamMap.put("stu_list", stuIdNumList);
        stuSubmitParamMap.put("exam_date", tvWaitTestDate.getText().toString());
        stuSubmitParamMap.put("exam_site", tvWaitTestAddress.getText().toString());
        stuSubmitParamMap.put("exam_bus", takeBusFlag + "");
        stuSubmitParamMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsSubmitJson = gson.toJson(stuSubmitParamMap);
        Log.e("科目四提交请求Json-->", paramsSubmitJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU_APPLY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_wait_test_date:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }
                pw_date.setAnimationStyle(R.style.anim_popwindow);
                pw_date.showAtLocation(MyStusClassFour.this.getView(), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_wait_test_address:
                getTestSites();
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
            case R.id.tv_datepicker_cancel:
                if (pw_date.isShowing()) {
                    pw_date.dismiss();
                }

                break;
            case R.id.btn_wait_test_class_submit:
                stuIdNumList.clear();
                for (Map.Entry<Integer,Boolean> entry : waitTestStuListAdapter.getIsCheckedMap().entrySet()) {
                    if (entry.getValue()) {
                        stuIdNumList.add(waitOptionTestStuBean.getData_list().get(entry.getKey()).getStu_idnum());
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
    public void getCheckedMap(HashMap<Integer, Boolean> map) {
        itemCheckedMap = map;
    }
}
