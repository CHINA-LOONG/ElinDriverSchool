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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.UploadGradeStuInfoActivity;
import com.elin.elindriverschool.adapter.UploadGradeStuListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.UploadGradeStuBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import org.chenglei.widget.datepicker.DatePicker;
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

import static android.app.Activity.RESULT_OK;
import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 17/1/5.
 */

public class UploadStuGradeClassThreeFragment extends Fragment implements View.OnClickListener,UploadGradeStuListAdapter.OnUpLoadGradeStuItemCheckedCallBack{

    ListView lvUploadGradeClass3;
    PtrClassicFrameLayout ptrViewUploadGradeClass3;
    RadioButton rbUploadGradeClass3Qualified;
    RadioButton rbUploadGradeClass3Unqualified;
    RadioButton rbUploadGradeClass3NoTest;
    RadioGroup rgUploadGradeClass3;
    Button btnUploadGradeClass3Submit;
    LinearLayout llIploadeGradeClass3Container;
    LinearLayout layoutNodata;
    LinearLayout llUploadGradClass3Bottom;

    private View view,pvExamDate;
    private TextView tvExamDate3;
    private String examDate;//考试日期
    private PopupWindow popExamDate;
    private DatePicker datePicker;

    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String,Object> stuSubmitParamMap = new HashMap<>();
    private String paramsJson,paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;

    private UploadGradeStuBean uploadGradeStuBean;
    private UploadGradeStuListAdapter uploadGradeStuListAdapter;
    private HashMap<Integer, Boolean> itemCheckedMap = new HashMap<>();
    private List<String> stuIdNumList = new ArrayList<>();
    private int examScore;// 1 合格 2 不合格 3 未考试
    private Intent intent = new Intent();
    private MyProgressDialog progressDialog;
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
//                getUploadGradeClassOneStus();
                flag = false;
            }

        } else {

        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upload_stu_grade_class_3, null);
        lvUploadGradeClass3 = (ListView) view.findViewById(R.id.lv_upload_grade_class_3);
        ptrViewUploadGradeClass3 = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_view_upload_grade_class_3);
        rgUploadGradeClass3 = (RadioGroup) view.findViewById(R.id.rg_upload_grade_class_3);
        rbUploadGradeClass3Qualified = (RadioButton) view.findViewById(R.id.rb_upload_grade_class_3_qualified);
        rbUploadGradeClass3Unqualified = (RadioButton) view.findViewById(R.id.rb_upload_grade_class_3_unqualified);
        rbUploadGradeClass3NoTest = (RadioButton) view.findViewById(R.id.rb_upload_grade_class_3_no_test);
        btnUploadGradeClass3Submit = (Button) view.findViewById(R.id.btn_upload_grade_class_3_submit);
        llIploadeGradeClass3Container = (LinearLayout) view.findViewById(R.id.ll_upload_grade_class_3_container);
        layoutNodata = (LinearLayout) view.findViewById(R.id.ll_upload_grade_class_3_no_data);
        llUploadGradClass3Bottom = (LinearLayout) view.findViewById(R.id.ll_upload_grade_class_3_bottom);
        btnUploadGradeClass3Submit.setOnClickListener(this);

        tvExamDate3 = (TextView) view.findViewById(R.id.tv_upload_grade_3_test_date);
        tvExamDate3.setOnClickListener(this);
        pvExamDate = LayoutInflater.from(getActivity()).inflate(R.layout.popup_date_picker,null);
        popExamDate = new PopupWindow(pvExamDate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popExamDate.setFocusable(true);
        popExamDate.setOutsideTouchable(false);
        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        popExamDate.setBackgroundDrawable(drawable);
        datePicker = (DatePicker) pvExamDate.findViewById(R.id.date_picker);
//         sound = new Sound(getActivity());
        pvExamDate.findViewById(R.id.tv_datepicker_cancel).setOnClickListener(this);
        pvExamDate.findViewById(R.id.tv_popup_datepicker_confirm).setOnClickListener(this);


        rgUploadGradeClass3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==rbUploadGradeClass3Qualified.getId()){
//                    ToastUtils.ToastMessage(getActivity(),"合格");
                    examScore = 1;
                }
                if (i==rbUploadGradeClass3Unqualified.getId()){
//                    ToastUtils.ToastMessage(getActivity(),"不合格");
                    examScore = 2;
                }
                if (i==rbUploadGradeClass3NoTest.getId()){
//                    ToastUtils.ToastMessage(getActivity(),"未考试");
                    examScore = 3;
                }
            }
        });
        return view;
    }
    //获取科目三学员 成绩上传
    private void getUploadGradeClassOneStus(){
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("exam_sub",3+"");//科目三
        paramsMap.put("pageno",1+"");
        paramsMap.put("pagesize",10000+"");
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_UPLOAD_GRADE_STUS)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
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
                mHandler.sendEmptyMessage(0);
                call.cancel();

            }
        });

    }

    //初始化下拉刷新 上拉加载
    private void initRefreshLoadMore() {
        ptrViewUploadGradeClass3.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrViewUploadGradeClass3.autoRefresh(true);
            }
        }, 200);
        ptrViewUploadGradeClass3.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUploadGradeClassOneStus();
                        ptrViewUploadGradeClass3.refreshComplete();
                        if (!ptrViewUploadGradeClass3.isLoadMoreEnable()) {
                            ptrViewUploadGradeClass3.setLoadMoreEnable(false);
                        }
                    }
                }, 1000);
            }
        });
    }

    private void submitUploadStuGrade(){
        stuSubmitParamMap.put("token",BaseApplication.getInstance().getToken());
        stuSubmitParamMap.put("coach_idnum",BaseApplication.getInstance().getCoachIdNum());
        stuSubmitParamMap.put("exam_date", tvExamDate3.getText().toString());
        stuSubmitParamMap.put("exam_sub",3+"");
        stuSubmitParamMap.put("stu_list",stuIdNumList);
        stuSubmitParamMap.put("exam_score",examScore+"");
        paramsSubmitJson = gson.toJson(stuSubmitParamMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.UPLOAD_GRADE_BY_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(1);
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
                    Log.e("上传成绩科目三Json--》",responseJson);
                    uploadGradeStuBean = gson.fromJson(responseJson,UploadGradeStuBean.class);
                    if(uploadGradeStuBean.getRc()==0){
                        if(uploadGradeStuBean.getData_list()!=null){
                            if (uploadGradeStuBean.getData_list().size()==0){
                                llIploadeGradeClass3Container.setVisibility(View.GONE);
                                layoutNodata.setVisibility(View.VISIBLE);
                            }else {
                                llIploadeGradeClass3Container.setVisibility(View.VISIBLE);
                                llUploadGradClass3Bottom.setVisibility(View.VISIBLE);
                                layoutNodata.setVisibility(View.GONE);
                                uploadGradeStuListAdapter = new UploadGradeStuListAdapter(getActivity(),UploadStuGradeClassThreeFragment.this,uploadGradeStuBean);
                                lvUploadGradeClass3.setAdapter(uploadGradeStuListAdapter);
                                uploadGradeStuListAdapter.setItems(uploadGradeStuBean.getData_list());
                                lvUploadGradeClass3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        intent .setClass(getActivity(), UploadGradeStuInfoActivity.class);
                                        intent.putExtra("examSub",3);
                                        intent.putExtra("stuIdNum",uploadGradeStuBean.getData_list().get(i).getStu_idnum());
                                        intent.putExtra("testDate",uploadGradeStuBean.getData_list().get(i).getOrder_date());
                                        if(!TextUtils.isEmpty(uploadGradeStuBean.getData_list().get(i).getScoreForCheck())){
                                            intent.putExtra("score",uploadGradeStuBean.getData_list().get(i).getScoreForCheck());
                                        }else {
                                            intent.putExtra("score","1");
                                        }
                                        startActivityForResult(intent,100);
                                    }
                                });

                            }
                        }else {
                            llIploadeGradeClass3Container.setVisibility(View.GONE);
                            layoutNodata.setVisibility(View.VISIBLE);
                        }
                    }else if(uploadGradeStuBean.getRc()==3000){
                        LogoutUtil.clearData(getActivity());
                        goToActivity(getActivity(),LoginActivity.class);
                        ToastUtils.ToastMessage(getActivity(),uploadGradeStuBean.getDes());
                    }


                    break;
                case 1:
                    Log.e("上传成绩是否成功Json--》", responseJson);
                    try {
                        JSONObject jsonObject = new JSONObject(responseJson);
                        if ("0".equals(jsonObject.getString("rc"))){
                            ToastUtils.ToastMessage(getActivity(),"上传成功");
                            for (Map.Entry<Integer,Boolean> entry : uploadGradeStuListAdapter.getIsCheckedMap().entrySet()) {
                                if (entry.getValue()) {
                                    uploadGradeStuBean.getData_list().get(entry.getKey()).setScoreForCheck("202");
                                    Log.e("学员位置",entry.getKey()+"");
                                }
                            }
                            uploadGradeStuListAdapter.notifyDataSetChanged();
                        }else {
                            ToastUtils.ToastMessage(getActivity(),"上传失败，请重试");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void getCheckedMap(HashMap<Integer, Boolean> map) {
        itemCheckedMap = map;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_upload_grade_class_3_submit:
                stuIdNumList.clear();
//                for (int i = 0; i <uploadGradeStuListAdapter.mChecked.size(); i++) {
//                    if (uploadGradeStuListAdapter.mChecked.get(i)){
//                        stuIdNumList.add(uploadGradeStuBean.getData_list().get(i).getStu_idnum());
//                    }
//                }
                for (Map.Entry<Integer,Boolean> entry : uploadGradeStuListAdapter.getIsCheckedMap().entrySet()) {
                    if (entry.getValue()) {
                        stuIdNumList.add(uploadGradeStuBean.getData_list().get(entry.getKey()).getStu_idnum());
                    }
                }
                if (stuIdNumList.size()==0){
                    ToastUtils.ToastMessage(getActivity(),"请选择学员");
                }else {
                    if (examScore==0){
                        ToastUtils.ToastMessage(getActivity(),"请选择成绩");
                    }else {
                        if (tvExamDate3.getText().toString().length()==0){
                            ToastUtils.ToastMessage(getActivity(),"请选择考试日期");
                        }else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("操作提示")
                                    .setMessage("确认要提交上传吗？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            submitUploadStuGrade();
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

                }
                break;

            case R.id.tv_upload_grade_3_test_date:
                if (popExamDate.isShowing()) {
                    popExamDate.dismiss();
                }
                popExamDate.setAnimationStyle(R.style.anim_popwindow);
                popExamDate.showAtLocation(UploadStuGradeClassThreeFragment.this.getView(), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
                break;
            case R.id.tv_popup_datepicker_confirm:
                if (popExamDate.isShowing()){
                    popExamDate.dismiss();
                }
                if (datePicker.getMonth() < 10) {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvExamDate3.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvExamDate3.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                } else {
                    if (datePicker.getDayOfMonth() < 10) {
                        tvExamDate3.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
                    } else {
                        tvExamDate3.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
                    }
                }
                break;
            case R.id.tv_datepicker_cancel:
                if (popExamDate.isShowing()){
                    popExamDate.dismiss();
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode==RESULT_OK){
            initRefreshLoadMore();
        }
    }
}
