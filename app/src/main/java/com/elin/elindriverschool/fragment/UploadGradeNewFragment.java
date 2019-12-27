//package com.elin.elindriverschool.fragment;
//
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v4.app.Fragment;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.elin.elindriverschool.R;
//import com.elin.elindriverschool.activity.LoginActivity;
//import com.elin.elindriverschool.activity.UploadGradeStuInfoActivity;
//import com.elin.elindriverschool.adapter.UploadGradeStuListAdapter;
//import com.elin.elindriverschool.api.UpdateTitle;
//import com.elin.elindriverschool.application.BaseApplication;
//import com.elin.elindriverschool.model.Constant;
//import com.elin.elindriverschool.model.UploadGradeStuBean;
//import com.elin.elindriverschool.util.LogoutUtil;
//import com.elin.elindriverschool.util.MyOkHttpClient;
//import com.elin.elindriverschool.util.ToastUtils;
//import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//import static android.app.Activity.RESULT_OK;
//import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class UploadGradeNewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, UploadGradeStuListAdapter.OnUpLoadGradeStuItemCheckedCallBack {
//
//
//    @Bind(R.id.rv_upload_grade)
//    RecyclerView rvUploadGrade;
//    @Bind(R.id.srl_upload_grade)
//    SwipeRefreshLayout srlUploadGrade;
//    @Bind(R.id.indexBar_contact)
//    IndexBar indexBarContact;
//    @Bind(R.id.tvSideBarHint_contact)
//    TextView tvSideBarHintContact;
//    @Bind(R.id.rb_upload_grade_class_1_qualified)
//    RadioButton rbUploadGradeClass1Qualified;
//    @Bind(R.id.rb_upload_grade_class_1_unqualified)
//    RadioButton rbUploadGradeClass1Unqualified;
//    @Bind(R.id.rb_upload_grade_class_1_no_test)
//    RadioButton rbUploadGradeClass1NoTest;
//    @Bind(R.id.rg_upload_grade_class_1)
//    RadioGroup rgUploadGradeClass1;
//    @Bind(R.id.tv_upload_grade_1_test_date)
//    TextView tvUploadGrade1TestDate;
//    @Bind(R.id.btn_upload_grade_class_1_submit)
//    Button btnUploadGradeClass1Submit;
//    @Bind(R.id.ll_upload_grade_class_1_bottom)
//    LinearLayout llUploadGradeClass1Bottom;
//    @Bind(R.id.ll_wait_test_container)
//    RelativeLayout llWaitTestContainer;
//    private boolean visible = true;
//
//    private Map<String, String> paramsMap = new HashMap<>();
//    public UploadGradeNewFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            if(visible){
//                if (srlUploadGrade != null) {
//                    srlUploadGrade.setOnRefreshListener(this);
//                    srlUploadGrade.setColorSchemeResources(android.R.color.holo_red_light);
//                    srlUploadGrade.setRefreshing(true);
//                }
//                getWaitTestClassOneStu();
//                visible = false;
//            }
//        } else {
//        }
//    }
//
//    public static UploadGradeNewFragment newInstance(int flag) {
//        UploadGradeNewFragment fragment = new UploadGradeNewFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("flag", flag);
//        fragment.setArguments(bundle);
//        return fragment;
//
//    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_upload_grade_new, container, false);
//        ButterKnife.bind(this, view);
//        return view;
//    }
//
//
//    @OnClick({R.id.tv_upload_grade_1_test_date, R.id.btn_upload_grade_class_1_submit})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv_upload_grade_1_test_date:
//                break;
//            case R.id.btn_upload_grade_class_1_submit:
//                break;
//        }
//    }
//
//    @Override
//    public void onRefresh() {
//        getUploadGradeClassOneStus();
//    }
//
//
//    //获取科目学员
//    private void getUploadGradeClassOneStus() {
//        paramsMap.put("token", BaseApplication.getInstance().getToken());
//        paramsMap.put("exam_sub", 1 + "");//科目一
//        paramsMap.put("pageno", 1 + "");
//        paramsMap.put("pagesize", 10000 + "");
//        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
//        paramsJson = gson.toJson(paramsMap);
//        Request request = new Request.Builder()
//                .url(Constant.ServerURL + Constant.GET_UPLOAD_GRADE_STUS)
//                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
//                .build();
//        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (progressDialog != null && progressDialog.isShowing()) {
//                    progressDialog.dismiss();
//                }
//                responseJson = String.valueOf(response.body().string());
//                mHandler.sendEmptyMessage(0);
//                call.cancel();
//
//            }
//        });
//
//    }
//    private void submitUploadStuGrade(){
//        stuSubmitParamMap.put("token", BaseApplication.getInstance().getToken());
//        stuSubmitParamMap.put("coach_idnum",BaseApplication.getInstance().getCoachIdNum());
//        stuSubmitParamMap.put("exam_date", tvExamDate1.getText().toString());
//        stuSubmitParamMap.put("exam_sub",1+"");
//        stuSubmitParamMap.put("stu_list",stuIdNumList);
//        stuSubmitParamMap.put("exam_score",examScore+"");
//        stuSubmitParamMap.put("school_id", BaseApplication.getInstance().getSchoolId());
//        paramsSubmitJson = gson.toJson(stuSubmitParamMap);
//        Log.e("上传成绩请求Json-->",paramsSubmitJson);
//        Request request = new Request.Builder()
//                .url(Constant.ServerURL + Constant.UPLOAD_GRADE_BY_LIST)
//                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsSubmitJson))
//                .build();
//        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                responseJson = String.valueOf(response.body().string());
//                mHandler.sendEmptyMessage(1);
//                call.cancel();
//
//            }
//        });
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    Log.e("上传成绩科目一Json--》", responseJson);
//                    uploadGradeStuBean = gson.fromJson(responseJson, UploadGradeStuBean.class);
//                    if (uploadGradeStuBean.getRc()==0){
//                        if (uploadGradeStuBean.getData_list().size() == 0) {
//                            llIploadeGradeClass1Container.setVisibility(View.GONE);
//                            layoutNodata.setVisibility(View.VISIBLE);
//                        } else {
//                            llIploadeGradeClass1Container.setVisibility(View.VISIBLE);
//                            layoutNodata.setVisibility(View.GONE);
//                            llUploadGradeClass1Bottom.setVisibility(View.VISIBLE);
//                            uploadGradeStuListAdapter = new UploadGradeStuListAdapter(getActivity(), UploadStuGradeClassOneFragment.this, uploadGradeStuBean);
//                            lvUploadGradeClass1.setAdapter(uploadGradeStuListAdapter);
//                            uploadGradeStuListAdapter.setItems(uploadGradeStuBean.getData_list());
//                            lvUploadGradeClass1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    intent .setClass(getActivity(), UploadGradeStuInfoActivity.class);
//                                    intent.putExtra("examSub",1);
//                                    intent.putExtra("stuIdNum",uploadGradeStuBean.getData_list().get(i).getStu_idnum());
//                                    intent.putExtra("testDate",uploadGradeStuBean.getData_list().get(i).getOrder_date());
//                                    if(!TextUtils.isEmpty(uploadGradeStuBean.getData_list().get(i).getScoreForCheck())){
//                                        intent.putExtra("score",uploadGradeStuBean.getData_list().get(i).getScoreForCheck());
//                                    }else {
//                                        intent.putExtra("score","1");
//                                    }                                    startActivityForResult(intent,100);
//                                }
//                            });
//
//                        }
//                    }else if(uploadGradeStuBean.getRc()==3000){
//                        LogoutUtil.clearData(getActivity());
//                        goToActivity(getActivity(),LoginActivity.class);
//                        ToastUtils.ToastMessage(getActivity(),uploadGradeStuBean.getDes());
//                    }
//
//                    break;
//                case 1:
//                    Log.e("上传成绩是否成功Json--》", responseJson);
//                    try {
//                        JSONObject jsonObject = new JSONObject(responseJson);
//                        if ("0".equals(jsonObject.getString("rc"))){
//                            ToastUtils.ToastMessage(getActivity(),"上传成功");
//                            for (Map.Entry<Integer,Boolean> entry : uploadGradeStuListAdapter.getIsCheckedMap().entrySet()) {
//                                if (entry.getValue()) {
//                                    uploadGradeStuBean.getData_list().get(entry.getKey()).setScoreForCheck("202");
//                                    Log.e("学员位置",entry.getKey()+"");
//                                }
//                            }
//                            uploadGradeStuListAdapter.notifyDataSetChanged();
//                        }else {
//                            ToastUtils.ToastMessage(getActivity(),"上传失败，请重试");
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//    @Override
//    public void getCheckedMap(HashMap<Integer, Boolean> map) {
//        itemCheckedMap = map;
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_upload_grade_class_1_submit:
//                stuIdNumList.clear();
////                for (int i = 0; i < uploadGradeStuListAdapter.mChecked.size(); i++) {
////                    if (uploadGradeStuListAdapter.mChecked.get(i)) {
////                        stuIdNumList.add(uploadGradeStuBean.getData_list().get(i).getStu_idnum());
////                    }
////                }
//                for (Map.Entry<Integer,Boolean> entry : uploadGradeStuListAdapter.getIsCheckedMap().entrySet()) {
//                    if (entry.getValue()) {
//                        stuIdNumList.add(uploadGradeStuBean.getData_list().get(entry.getKey()).getStu_idnum());
//                    }
//                }
//                if (stuIdNumList.size() == 0) {
//                    ToastUtils.ToastMessage(getActivity(), "请选择学员");
//                } else {
//                    if (examScore == 0) {
//                        ToastUtils.ToastMessage(getActivity(), "请选择成绩");
//                    } else {
//                        if (tvExamDate1.getText().toString().length()==0){
//                            ToastUtils.ToastMessage(getActivity(),"请选择考试日期");
//                        }else {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setTitle("操作提示")
//                                    .setMessage("确认要提交上传吗？")
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            submitUploadStuGrade();
//                                        }
//                                    })
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            dialogInterface.dismiss();
//                                        }
//                                    }).create().show();
//                        }
//
//                    }
//                }
//                break;
//            case R.id.tv_upload_grade_1_test_date:
//                if (popExamDate.isShowing()) {
//                    popExamDate.dismiss();
//                }
//                popExamDate.setAnimationStyle(R.style.anim_popwindow);
//                popExamDate.showAtLocation(UploadStuGradeClassOneFragment.this.getView(), Gravity.BOTTOM | Gravity.CLIP_HORIZONTAL, 0, 0);
//                break;
//            case R.id.tv_popup_datepicker_confirm:
//                if (popExamDate.isShowing()){
//                    popExamDate.dismiss();
//                }
//                if (datePicker.getMonth() < 10) {
//                    if (datePicker.getDayOfMonth() < 10) {
//                        tvExamDate1.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
//                    } else {
//                        tvExamDate1.setText(datePicker.getYear() + "-0" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
//                    }
//                } else {
//                    if (datePicker.getDayOfMonth() < 10) {
//                        tvExamDate1.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-0" + datePicker.getDayOfMonth());
//                    } else {
//                        tvExamDate1.setText(datePicker.getYear() + "-" + datePicker.getMonth() + "-" + datePicker.getDayOfMonth());
//                    }
//                }
//                break;
//            case R.id.tv_datepicker_cancel:
//                if (popExamDate.isShowing()){
//                    popExamDate.dismiss();
//                }
//
//                break;
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==100&&resultCode==RESULT_OK){
//            initRefreshLoadMore();
//        }
//    }
//}
