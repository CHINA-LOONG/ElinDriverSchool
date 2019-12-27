package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.CheckScoreStuListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.CheckScoreStuBean;
import com.elin.elindriverschool.model.Constant;
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
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by imac on 16/12/26.
 * 学员成绩查看
 */

public class CheckStudentGradeActivity extends BaseActivity implements View.OnClickListener
        , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    PopupWindow pw_class, pw_score;
    View pv_class, pv_score, viewContent;
    @Bind(R.id.tv_percent)
    TextView tvPercent;


    private Map<String, String> parmaMap = new HashMap<>();//请求参数
    private String paramsJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private int examSub = 1;//1 科目一 2 科目二 3 科目三 4 科目四
    private String examScore = "";// 1 合格 2 不合格 3 未考试
    private CheckScoreStuBean checkScoreStuBean;
    private CheckScoreStuListAdapter checkScoreStuListAdapter;
    ImageView imvCusTitleBack;
    TextView tvCusTitleName;
    TextView tvCusTitleRight;
    TextView tvStuGradeClass;
    ImageView imvStuGradeClassArrow;
    LinearLayout llStuGradeClass;
    TextView tvStuGradeScore;
    ImageView imvStuGradeScoreArrow;
    LinearLayout llStuGradeScore;
    LinearLayout llStuGradeTitle2;
    SwipeRefreshLayout srlGrade;
    RecyclerView rvGrade;
    LinearLayout layoutNodata;
    private Intent intent = new Intent();
    private int page = 1;
    List<CheckScoreStuBean.DataListBeanX.DataListBean> data_list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_grade);
        ButterKnife.bind(this);
        imvCusTitleBack = (ImageView) findViewById(R.id.imv_cus_title_back);
        tvCusTitleName = (TextView) findViewById(R.id.tv_cus_title_name);
        tvCusTitleRight = (TextView) findViewById(R.id.tv_cus_title_right);
        imvStuGradeScoreArrow = (ImageView) findViewById(R.id.imv_stu_grade_score_arrow);
        imvStuGradeClassArrow = (ImageView) findViewById(R.id.imv_stu_grade_class_arrow);
        llStuGradeClass = (LinearLayout) findViewById(R.id.ll_stu_grade_class);
        tvStuGradeScore = (TextView) findViewById(R.id.tv_stu_grade_score);
        tvStuGradeClass = (TextView) findViewById(R.id.tv_stu_grade_class);
        viewContent = findViewById(R.id.ll_check_score_container);
        llStuGradeScore = (LinearLayout) findViewById(R.id.ll_stu_grade_score);
        llStuGradeTitle2 = (LinearLayout) findViewById(R.id.ll_stu_grade_title_2);
        srlGrade = (SwipeRefreshLayout) findViewById(R.id.srl_grade);
        rvGrade = (RecyclerView) findViewById(R.id.rv_grade);
        layoutNodata = (LinearLayout) findViewById(R.id.ll_check_score_no_data);

        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("学员成绩查看");
        llStuGradeClass.setOnClickListener(this);
        llStuGradeScore.setOnClickListener(this);
        pv_class = LayoutInflater.from(this).inflate(R.layout.popup_stu_grade_class, null);
        pv_score = LayoutInflater.from(this).inflate(R.layout.popup_stu_grade_score, null);
        pw_class = new PopupWindow(pv_class, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pw_score = new PopupWindow(pv_score, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ColorDrawable drawable = new ColorDrawable(0xb0000000);
        pw_class.setBackgroundDrawable(drawable);
        pw_class.setTouchable(true);
        pw_score.setBackgroundDrawable(drawable);
        pw_score.setTouchable(true);
//        pv_class.findViewById(R.id.ll_stu_grade_class_all).setOnClickListener(this);
        pv_class.findViewById(R.id.ll_stu_grade_class_one).setOnClickListener(this);
        pv_class.findViewById(R.id.ll_stu_grade_class_two).setOnClickListener(this);
        pv_class.findViewById(R.id.ll_stu_grade_class_three).setOnClickListener(this);
        pv_class.findViewById(R.id.ll_stu_grade_class_four).setOnClickListener(this);

        pv_score.findViewById(R.id.ll_stu_grade_score_qualified).setOnClickListener(this);
        pv_score.findViewById(R.id.ll_stu_grade_score_unqualified).setOnClickListener(this);
        pv_score.findViewById(R.id.ll_stu_grade_score_no_test).setOnClickListener(this);
        pv_score.findViewById(R.id.ll_stu_grade_score_all).setOnClickListener(this);

        pw_class.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imvStuGradeClassArrow.setBackgroundResource(R.drawable.icon_arrow_down);
                tvStuGradeClass.setTextColor(ContextCompat.getColor(CheckStudentGradeActivity.this, R.color.text_color_light_dark));
            }
        });
        pw_score.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                imvStuGradeScoreArrow.setBackgroundResource(R.drawable.icon_arrow_down);
                tvStuGradeScore.setTextColor(ContextCompat.getColor(CheckStudentGradeActivity.this, R.color.text_color_light_dark));
            }
        });
        srlGrade.setRefreshing(true);
        checkScoreStuListAdapter = new CheckScoreStuListAdapter(data_list, this);
        checkScoreStuListAdapter.openLoadAnimation();
        checkScoreStuListAdapter.setOnLoadMoreListener(this);
        srlGrade.setRefreshing(true);
        srlGrade.setOnRefreshListener(this);
        rvGrade.setLayoutManager(new LinearLayoutManager(CheckStudentGradeActivity.this));
        srlGrade.setColorSchemeResources(android.R.color.holo_red_light);
        rvGrade.setAdapter(checkScoreStuListAdapter);
        rvGrade.addItemDecoration(new DividerItemDecoration(CheckStudentGradeActivity.this, DividerItemDecoration.VERTICAL_LIST));
        getCheckScoreStuList(page, examScore, examSub);
        rvGrade.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                CheckScoreStuBean.DataListBeanX.DataListBean bean = (CheckScoreStuBean.DataListBeanX.DataListBean) adapter.getItem(position);
                intent.setClass(CheckStudentGradeActivity.this, CheckStuScoreInfoActivity.class);
                intent.putExtra("examSub", examSub);
                intent.putExtra("examScore", bean.getExam_score());
                intent.putExtra("testDate", bean.getExam_date());
                intent.putExtra("stuIdNum", bean.getStu_idnum());
                startActivity(intent);
            }
        });
    }

    private void getCheckScoreStuList(int pageNo, String examScore, int examSub) {

        parmaMap.put("token", BaseApplication.getInstance().getToken());
        parmaMap.put("pageno", pageNo + "");
        parmaMap.put("pagesize", 20 + "");
        parmaMap.put("exam_sub", examSub + "");
        parmaMap.put("exam_score", examScore + "");
        parmaMap.put("school_id", BaseApplication.getInstance().getSchoolId());

        paramsJson = gson.toJson(parmaMap);
        Log.e("qingqiu -->", paramsJson);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.CHECK_STU_GRADE_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(CheckStudentGradeActivity.this).newCall(request);
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
            switch (msg.what) {
                case 0:
                    Log.e("查看成绩Json-->", responseJson);
                    if (srlGrade != null) {
                        srlGrade.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        CheckScoreStuBean bean = new Gson().fromJson(responseJson, CheckScoreStuBean.class);
                        if (bean.getRc().equals("0")) {
                            data_list = bean.getData_list().getData_list();
                            tvPercent.setText(tvStuGradeClass.getText().toString()+"合格率："+bean.getData_list().getPrecent());
                            if (data_list != null && data_list.size() != 0) {
                                viewContent.setVisibility(View.VISIBLE);
                                layoutNodata.setVisibility(View.GONE);
                                if (page == 1) {
                                    checkScoreStuListAdapter.setNewData(data_list);
                                    checkScoreStuListAdapter.loadMoreComplete();
                                } else {
                                    checkScoreStuListAdapter.addData(data_list);
                                    checkScoreStuListAdapter.loadMoreComplete();
                                }
                            } else {
                                if (page == 1) {
                                    Log.e("1-->", responseJson);
                                    viewContent.setVisibility(View.GONE);
                                    layoutNodata.setVisibility(View.VISIBLE);
                                    checkScoreStuListAdapter.setNewData(data_list);
                                    checkScoreStuListAdapter.loadMoreEnd(true);
                                } else {
                                    checkScoreStuListAdapter.loadMoreEnd(false);
                                }
                            }
                        } else {
                            Log.e("2-->", responseJson);
                            viewContent.setVisibility(View.GONE);
                            layoutNodata.setVisibility(View.VISIBLE);
                            checkScoreStuListAdapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    if (srlGrade != null) {
                        srlGrade.setRefreshing(false);
                    }
                    if (page == 1) {
                        viewContent.setVisibility(View.GONE);
                        layoutNodata.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtils.ToastMessage(CheckStudentGradeActivity.this, "网络连接出现问题");
                    }
                    break;
            }

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_stu_grade_class:
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                }
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                } else {
                    imvStuGradeClassArrow.setBackgroundResource(R.drawable.icon_arrow_up);
                    tvStuGradeClass.setTextColor(ContextCompat.getColor(CheckStudentGradeActivity.this, R.color.colorPrimary));
                    viewContent.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    pw_class.showAsDropDown(llStuGradeTitle2, 0, 0);
                }
                break;
            case R.id.ll_stu_grade_score:
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                }
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                } else {
                    imvStuGradeScoreArrow.setBackgroundResource(R.drawable.icon_arrow_up);
                    tvStuGradeScore.setTextColor(ContextCompat.getColor(CheckStudentGradeActivity.this, R.color.colorPrimary));
                    viewContent.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    pw_score.showAsDropDown(llStuGradeTitle2, 0, 0);
                }
                break;
            case R.id.imv_cus_title_back:
                CheckStudentGradeActivity.this.finish();
                break;
            case R.id.ll_stu_grade_class_one:
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                }
                tvStuGradeClass.setText("科目一");
                examSub = 1;
                page = 1;
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_class_two:
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                }
                tvStuGradeClass.setText("科目二");
                examSub = 2;
                page = 1;
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_class_three:
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                }
                tvStuGradeClass.setText("科目三");
                examSub = 3;
                page = 1;
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_class_four:
                if (pw_class.isShowing()) {
                    pw_class.dismiss();
                }
                tvStuGradeClass.setText("科目四");
                examSub = 4;
                page = 1;
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_score_qualified:
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                }
                tvStuGradeScore.setText("合格");
                examScore = "1";
                page = 1;
                tvPercent.setVisibility(View.GONE);
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_score_unqualified:
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                }
                tvStuGradeScore.setText("不合格");
                examScore = "2";
                page = 1;
                tvPercent.setVisibility(View.GONE);
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_score_no_test:
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                }
                tvStuGradeScore.setText("缺考");
                examScore = "3";
                page = 1;
                tvPercent.setVisibility(View.GONE);
                getCheckScoreStuList(page, examScore, examSub);
                break;
            case R.id.ll_stu_grade_score_all:
                if (pw_score.isShowing()) {
                    pw_score.dismiss();
                }
                tvStuGradeScore.setText("全部成绩");
                examScore = "";
                page = 1;
                tvPercent.setVisibility(View.VISIBLE);
                getCheckScoreStuList(page, examScore, examSub);
                break;
        }

    }

    @Override
    public void onRefresh() {
        page = 1;
        getCheckScoreStuList(page, examScore, examSub);
        checkScoreStuListAdapter.setEnableLoadMore(false);
        srlGrade.setRefreshing(true);
        checkScoreStuListAdapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getCheckScoreStuList(page, examScore, examSub);
    }
}
