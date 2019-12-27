package com.elin.elindriverschool.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.StudentInfoActivity;
import com.elin.elindriverschool.adapter.ToAcceptAdapter;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.ToAcceptBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 16/12/26.
 * 待受理frgment
 */

public class MyStusWaitOption extends Fragment implements ToAcceptAdapter.OnStuItemCheckedCallBack{
    View view;
    ListView lvWaitTestWaitOption;
    PtrClassicFrameLayout ptrViewWaitTestStuOption;
    Button btnWaitOptionStuSubmit;
    LinearLayout llWaitOptionContainer;
    LinearLayout layoutNodata;
    TextView tvWaitOperationDate;
    CheckBox tvWaitTestBusRide;
    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, Object> stuSubmitParamMap = new HashMap<>();
    private String paramsJson, paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private ToAcceptBean waitOptionTestStuBean;
    private ToAcceptAdapter waitTestStuListAdapter;
    private HashMap<Integer, Boolean> itemCheckedMap = new HashMap<>();
    private List<String> stuIdNumList = new ArrayList<>();

    private Intent intent = new Intent();
    private MyProgressDialog progressDialog;
    private UpdateTitle updateTitle;
    private int stuCount = 0;
    private String nextDetarecDate;
//    private String testSiteJson;

    public void setUpdate(UpdateTitle updateTitle) {
        this.updateTitle = updateTitle;
    }

    //    private TestSiteBean testSiteBean;
    private boolean flag = true;
    private String takeBus = "2";//1.Y 2.N
//    private CheckBox cbBusRide;
//    private String[] testSites;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (flag) {
                if (getActivity() != null) {
                    progressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                }
//                getWaitOptionStu();
                flag = false;
            }
            updateTitle.onUpdateTitle(stuCount + "");
        } else {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_stus_wait_option, container, false);
        view = inflater.inflate(R.layout.fragment_my_stus_wait_option, null);
        lvWaitTestWaitOption = (ListView) view.findViewById(R.id.lv_wait_test_wait_option);
        tvWaitOperationDate = (TextView) view.findViewById(R.id.tv_wait_operation_date);
        ptrViewWaitTestStuOption = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_view_wait_test_stu_option);
        btnWaitOptionStuSubmit = (Button) view.findViewById(R.id.btn_wait_option_stu_submit);
        llWaitOptionContainer = (LinearLayout) view.findViewById(R.id.ll_wait_option_container);
        layoutNodata = (LinearLayout) view.findViewById(R.id.ll_wait_option_no_data);
        tvWaitTestBusRide = (CheckBox) view.findViewById(R.id.tv_wait_test_bus_ride);
        btnWaitOptionStuSubmit = (Button) view.findViewById(R.id.btn_wait_option_stu_submit);
        initRefreshLoadMore();
        tvWaitTestBusRide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    takeBus = "1";
                } else {
                    takeBus = "2";
                }
            }
        });
        btnWaitOptionStuSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stuIdNumList.clear();
                for (Map.Entry<Integer,Boolean> entry : waitTestStuListAdapter.getIsCheckedMap().entrySet()) {
                    if (entry.getValue()) {
                        stuIdNumList.add(waitOptionTestStuBean.getData_list().get(entry.getKey()).getInner_id());
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
                                    submitStuTest();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                }
            }
        });
        return view;
    }

    private void getWaitOptionStu() {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", 1 + "");
        paramsMap.put("pagesize", "10000");
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);
        Log.e("待受理请求-->", paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_OPTION_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(404);
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

    //提交待处理学员
    private void submitStuTest() {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else {
            progressDialog.show();
        }
        stuSubmitParamMap.put("token", BaseApplication.getInstance().getToken());
        stuSubmitParamMap.put("stu_list", stuIdNumList);
        stuSubmitParamMap.put("order_date", tvWaitOperationDate.getText().toString());
        stuSubmitParamMap.put("order_bus", takeBus);

        paramsSubmitJson = gson.toJson(stuSubmitParamMap);
        Log.e("科目一提交请求Json-->", paramsSubmitJson);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_OPTION_APPLY)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
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
                mHandler.sendEmptyMessage(1);
                call.cancel();
            }
        });
    }

    //初始化下拉刷新 上拉加载
    private void initRefreshLoadMore() {
        ptrViewWaitTestStuOption.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrViewWaitTestStuOption.autoRefresh(true);
            }
        }, 200);
        ptrViewWaitTestStuOption.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getWaitOptionStu();
                        ptrViewWaitTestStuOption.refreshComplete();
                        if (!ptrViewWaitTestStuOption.isLoadMoreEnable()) {
                            ptrViewWaitTestStuOption.setLoadMoreEnable(false);
                        }
                    }
                }, 1000);
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    waitOptionTestStuBean = gson.fromJson(responseJson, ToAcceptBean.class);
                    nextDetarecDate = waitOptionTestStuBean.getNext_detarec_date();
                    tvWaitOperationDate.setText(nextDetarecDate);
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        date = simpleDateFormat.parse(waitOptionTestStuBean.getNext_detarec_date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Date now = new Date();
                    if (TextUtils.isEmpty(nextDetarecDate) || date.before(now)) {
                        btnWaitOptionStuSubmit.setVisibility(View.GONE);
                    } else {
                        btnWaitOptionStuSubmit.setVisibility(View.VISIBLE);
                    }
                    if ("0".equals(waitOptionTestStuBean.getRc())) {
                        if (waitOptionTestStuBean.getData_list().size() == 0) {
                            llWaitOptionContainer.setVisibility(View.GONE);
                            layoutNodata.setVisibility(View.VISIBLE);
                            updateTitle.onUpdateTitle(waitOptionTestStuBean.getData_list().size() + "");
                        } else {
                            llWaitOptionContainer.setVisibility(View.VISIBLE);
                            layoutNodata.setVisibility(View.GONE);
                            waitTestStuListAdapter = new ToAcceptAdapter(waitOptionTestStuBean, MyStusWaitOption.this, getActivity(), true);
                            lvWaitTestWaitOption.setAdapter(waitTestStuListAdapter);
                            stuCount = waitOptionTestStuBean.getData_list().size();
                            updateTitle.onUpdateTitle(stuCount + "");
                            waitTestStuListAdapter.setItems(waitOptionTestStuBean.getData_list());

                            Log.e("待处理学员-size-》", waitOptionTestStuBean.getData_list().size() + "AAAA");

                            lvWaitTestWaitOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    intent.setClass(getActivity(), StudentInfoActivity.class);
                                    intent.putExtra("stuIdNum", waitOptionTestStuBean.getData_list().get(i).getStu_idnum());
                                    startActivity(intent);
                                }
                            });

                            lvWaitTestWaitOption.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    MobilePhoneUtils.makeCall(waitOptionTestStuBean.getData_list().get(i).getStu_phone(), waitOptionTestStuBean.getData_list().get(i).getStu_name(), getActivity());
                                    return true;
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
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))) {
                            ToastUtils.ToastMessage(getActivity(), "提交成功");
                            for (Map.Entry<Integer, Boolean> entry : waitTestStuListAdapter.getIsCheckedMap().entrySet()) {
                                if (entry.getValue()) {
                                    waitOptionTestStuBean.getData_list().get(entry.getKey()).setStu_detarec_wait_flag("102");
                                    Log.e("学员位置", entry.getKey() + "");
//                                    waitTestStuListAdapter.remove(entry.getKey());
                                }
                            }
                            waitTestStuListAdapter.notifyDataSetChanged();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void getCheckedMap(HashMap<Integer, Boolean> map) {
        itemCheckedMap = map;
    }

}
