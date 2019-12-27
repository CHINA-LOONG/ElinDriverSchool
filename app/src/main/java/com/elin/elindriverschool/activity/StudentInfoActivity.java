package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MyStuInfoBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;

/**
 * Created by imac on 16/12/28.
 * 学员详情
 */

public class StudentInfoActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.tv_stu_info_name)
    TextView tvStuInfoName;
    @Bind(R.id.tv_stu_info_phone)
    TextView tvStuInfoPhone;
    @Bind(R.id.tv_stu_info_sign_up_date)
    TextView tvStuInfoSignUpDate;
    @Bind(R.id.tv_stu_info_car_type)
    TextView tvStuInfoCarType;
    @Bind(R.id.tv_stu_info_class_1)
    TextView tvStuInfoClass1;
    @Bind(R.id.tv_stu_info_class_2)
    TextView tvStuInfoClass2;
    @Bind(R.id.tv_stu_info_class_3)
    TextView tvStuInfoClass3;
    @Bind(R.id.tv_stu_info_class_4)
    TextView tvStuInfoClass4;
    @Bind(R.id.tv_stu_info_pre_class)
    TextView tvStuInfoPreClass;
    @Bind(R.id.tv_stu_info_test_date)
    TextView tvStuInfoTestDate;
    @Bind(R.id.tv_stu_info_test_place)
    TextView tvStuInfoTestPlace;
    @Bind(R.id.tv_stu_info_bus_status)
    TextView tvStuInfoBusStatus;
    @Bind(R.id.tv_stu_info_id_num)
    TextView tvStuInfoIdNum;
    @Bind(R.id.imv_my_stu_info_phone)
    ImageView imvMyStuInfoPhone;
    @Bind(R.id.ll_stu_info_test_info)
    LinearLayout llStuInfoTestInfo;
    @Bind(R.id.tv_stu_info_sign_up_fee)
    TextView tvStuInfoSignUpFee;
    @Bind(R.id.ll_stu_info_test_info_1)
    LinearLayout llStuInfoTestInfo1;
    @Bind(R.id.tv_stu_info_pre_class_2)
    TextView tvStuInfoPreClass2;
    @Bind(R.id.tv_stu_info_test_date_2)
    TextView tvStuInfoTestDate2;
    @Bind(R.id.tv_stu_info_test_place_2)
    TextView tvStuInfoTestPlace2;
    @Bind(R.id.tv_stu_info_bus_status_2)
    TextView tvStuInfoBusStatus2;
    @Bind(R.id.ll_stu_info_test_info_2)
    LinearLayout llStuInfoTestInfo2;
    @Bind(R.id.tv_stu_info_is_recorder)
    TextView tvStuInfoIsRecorder;
    @Bind(R.id.tv_stu_info_coacher)
    TextView tvStuInfoCoacher;
    @Bind(R.id.tv_stu_info_creat_record)
    TextView tvStuInfoCreatRecord;
    @Bind(R.id.tv_stu_info_ndphone)
    TextView tvStuInfoNdphone;
    @Bind(R.id.imv_my_stu_info_ndphone)
    ImageView imvMyStuInfoNdphone;
    @Bind(R.id.ll_stu_info_ndphone)
    LinearLayout llStuInfoNdphone;
    /*@Bind(R.id.creat_record_type)
    TextView creatRecordType;
    @Bind(R.id.creat_record_time)
    TextView creatRecordTime;*/

    private Map<String, String> paramsMap = new HashMap<>();
    private String stuIdNum;
    private MyProgressDialog myProgressDialog;

    private String paramsJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private MyStuInfoBean myStuInfoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_info);
        ButterKnife.bind(this);
        tvCusTitleName.setText("学员详情");
        stuIdNum = getIntent().getStringExtra("stuIdNum");
        imvCusTitleBack.setOnClickListener(this);
        imvMyStuInfoPhone.setOnClickListener(StudentInfoActivity.this);
        imvMyStuInfoNdphone.setOnClickListener(StudentInfoActivity.this);
        myProgressDialog = new MyProgressDialog(StudentInfoActivity.this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        getMyStuDetailInfo();

    }

    private void getMyStuDetailInfo() {
        myProgressDialog.show();
        paramsMap.put("stu_idnum", stuIdNum);
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.STU_DETAIL)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(StudentInfoActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });

    }

    String scoreStatus = "", class1Score = "", class2Score = "", class3Score = "", class4Score = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("获取学员信息Json--》", responseJson);
                    myStuInfoBean = gson.fromJson(responseJson, MyStuInfoBean.class);
                    if (myStuInfoBean.getRc() == 0) {
                        tvStuInfoName.setText(myStuInfoBean.getData().getStu_info().getStu_name());
                        tvStuInfoIdNum.setText(myStuInfoBean.getData().getStu_info().getStu_idnum());
                        tvStuInfoPhone.setText(myStuInfoBean.getData().getStu_info().getStu_phone());
                        if(TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_homphone())){
                            llStuInfoNdphone.setVisibility(View.GONE);
                        }else {
                            tvStuInfoNdphone.setText(myStuInfoBean.getData().getStu_info().getStu_homphone());
                        }
                        tvStuInfoSignUpDate.setText(myStuInfoBean.getData().getStu_info().getStu_reg_date());
                        tvStuInfoCarType.setText(myStuInfoBean.getData().getStu_info().getStu_cartype());
                        tvStuInfoSignUpFee.setText(myStuInfoBean.getData().getStu_info().getStu_sum_pay() + "元");
                        if (!TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_coach())) {
                            tvStuInfoCoacher.setText(myStuInfoBean.getData().getStu_info().getStu_coach());
                        } else {
                            tvStuInfoCoacher.setText("无");
                        }


                        if ("1".equals(myStuInfoBean.getData().getStu_info().getStu_drivrec_flag())) {
                            tvStuInfoIsRecorder.setText("已建档    " + myStuInfoBean.getData().getStu_info().getStu_drivrec_date());
                        } else {
                            tvStuInfoIsRecorder.setText("未建档");
                        }
                        if (!TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_detarec_date())) {
                            tvStuInfoCreatRecord.setText("已建档    建档日期：" + myStuInfoBean.getData().getStu_info().getStu_detarec_date());
                        }
                        if (!TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_detarec_predate())) {
                            tvStuInfoCreatRecord.setText("未建档    预约受理日期：" + myStuInfoBean.getData().getStu_info().getStu_detarec_predate());
                        }
                        if (TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_detarec_date()) && TextUtils.isEmpty(myStuInfoBean.getData().getStu_info().getStu_detarec_predate())) {
                            tvStuInfoCreatRecord.setText("未建档");
                        }
                        switch (myStuInfoBean.getData().getStu_info().getStu_status()) {
                            case 1:
                                tvStuInfoClass1.setText("未开始");
                                tvStuInfoClass2.setText("未开始");
                                tvStuInfoClass3.setText("未开始");
                                tvStuInfoClass4.setText("未开始");
                                break;
                            case 2:
                                tvStuInfoClass1.setText("未开始");
                                tvStuInfoClass2.setText("未开始");
                                tvStuInfoClass3.setText("未开始");
                                tvStuInfoClass4.setText("未开始");
                                break;
                            case 3:
                                tvStuInfoClass1.setText("未开始");
                                tvStuInfoClass2.setText("未开始");
                                tvStuInfoClass3.setText("未开始");
                                tvStuInfoClass4.setText("未开始");
                                break;
                            //判断学员状态
                            case 4://已建档 待考科目一

                                //科一
                                if (myStuInfoBean.getData().getExam_info().getSub_one_list().size() != 0) {
//                                    if ("1".equals(myStuInfoBean.getData().getExam_info().getSub_one_list().get(0).getExam_score())) {
//                                        tvStuInfoClass1.setText("合格");
//                                    } else if ("2".equals(myStuInfoBean.getData().getExam_info().getSub_one_list().get(0).getExam_score())) {
//                                        tvStuInfoClass1.setText("不合格");
//                                    } else {
//                                        tvStuInfoClass1.setText("未考试");
//                                    }
                                    for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                        if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                            scoreStatus = "合    格";
                                        }
                                        if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                            scoreStatus = "不合格";
                                        }
                                        class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";
                                    }
                                    tvStuInfoClass1.setText(class1Score);
                                    tvStuInfoClass2.setText("未开始");
                                    tvStuInfoClass3.setText("未开始");
                                    tvStuInfoClass4.setText("未开始");
                                } else {
                                    tvStuInfoClass1.setText("进行中");
                                    tvStuInfoClass2.setText("未开始");
                                    tvStuInfoClass3.setText("未开始");
                                    tvStuInfoClass4.setText("未开始");
                                }
                                break;
                            case 5:
                                //已考科一 未过
                                tvStuInfoClass1.setText("进行中");
                                tvStuInfoClass2.setText("未开始");
                                tvStuInfoClass3.setText("未开始");
                                tvStuInfoClass4.setText("未开始");
                                break;
                            case 6:
                                //待考科四
                                // 科目一历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass1.setText(class1Score);

                                // 科目二历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_two_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class2Score = class2Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass2.setText(class2Score);

                                // 科目三历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_three_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class3Score = class3Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass3.setText(class3Score);
                                tvStuInfoClass4.setText("进行中");
                                break;
                            case 7:
                                //已结业 领证

                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";
                                }
                                tvStuInfoClass1.setText(class1Score);

                                // 科目二历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_two_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class2Score = class2Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass2.setText(class2Score);

                                // 科目三历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_three_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class3Score = class3Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass3.setText(class3Score);

                                // 科目四历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_four_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_four_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_four_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class4Score = class4Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_four_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass4.setText(class4Score);
                                break;
                            case 51:
                                // 待考科二 待考科三 （二 三 进行中）
                                // 科目一历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass1.setText(class1Score);
                                tvStuInfoClass2.setText("进行中");
                                tvStuInfoClass3.setText("进行中");
                                tvStuInfoClass4.setText("未开始");

                                break;
                            case 52:
                                //待考科二 已考科三（二待 三已完成）
                                // 科目一历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";

                                }

                                // 科目三历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_three_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class3Score = class3Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_three_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass1.setText(class1Score);
                                tvStuInfoClass3.setText(class3Score);
                                tvStuInfoClass2.setText("进行中");
                                tvStuInfoClass4.setText("未开始");
                                break;
                            case 53:
                                // 待考科三 已考科二（二完成，三待考）
                                // 科目一历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_one_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class1Score = class1Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_one_list().get(i).getExam_examdate() + "\n";

                                }

                                // 科目二历史记录
                                for (int i = 0; i < myStuInfoBean.getData().getExam_info().getSub_two_list().size(); i++) {

                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("1")) {
                                        scoreStatus = "合    格";
                                    }
                                    if (myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_score().equals("2")) {
                                        scoreStatus = "不合格";
                                    }
                                    class2Score = class2Score + scoreStatus + "     " + myStuInfoBean.getData().getExam_info().getSub_two_list().get(i).getExam_examdate() + "\n";

                                }
                                tvStuInfoClass1.setText(class1Score);
                                tvStuInfoClass2.setText(class2Score);
                                tvStuInfoClass3.setText("进行中");
                                tvStuInfoClass4.setText("未开始");
                                break;
                        }

                        if (myStuInfoBean.getData().getOrder_info().size() != 0) {
                            llStuInfoTestInfo.setVisibility(View.VISIBLE);

                            if (myStuInfoBean.getData().getOrder_info().size() == 1) {
                                //一条预约记录
                                llStuInfoTestInfo2.setVisibility(View.GONE);
                                switch (Integer.parseInt(myStuInfoBean.getData().getOrder_info().get(0).getOrder_sub())) {
                                    case 1:
                                        tvStuInfoPreClass.setText("科目一");
                                        break;
                                    case 2:
                                        tvStuInfoPreClass.setText("科目二");
                                        break;
                                    case 3:
                                        tvStuInfoPreClass.setText("科目三");
                                        break;
                                    case 4:
                                        tvStuInfoPreClass.setText("科目四");
                                        break;
                                }
                                tvStuInfoTestDate.setText(myStuInfoBean.getData().getOrder_info().get(0).getOrder_date());
                                tvStuInfoTestPlace.setText(myStuInfoBean.getData().getOrder_info().get(0).getOrder_site());
                                if ("1".equals(myStuInfoBean.getData().getOrder_info().get(0).getOrder_bus())) {
                                    tvStuInfoBusStatus.setText("是");
                                } else {
                                    tvStuInfoBusStatus.setText("否");
                                }

                            }
                            if (myStuInfoBean.getData().getOrder_info().size() == 2) {
                                //一条预约记录
                                llStuInfoTestInfo2.setVisibility(View.VISIBLE);

                                switch (Integer.parseInt(myStuInfoBean.getData().getOrder_info().get(0).getOrder_sub())) {
                                    case 1:
                                        tvStuInfoPreClass.setText("科目一");
                                        break;
                                    case 2:
                                        tvStuInfoPreClass.setText("科目二");
                                        break;
                                    case 3:
                                        tvStuInfoPreClass.setText("科目三");
                                        break;
                                    case 4:
                                        tvStuInfoPreClass.setText("科目四");
                                        break;
                                }
                                tvStuInfoTestDate.setText(myStuInfoBean.getData().getOrder_info().get(0).getOrder_date());
                                tvStuInfoTestPlace.setText(myStuInfoBean.getData().getOrder_info().get(0).getOrder_site());
                                if ("1".equals(myStuInfoBean.getData().getOrder_info().get(0).getOrder_bus())) {
                                    tvStuInfoBusStatus.setText("是");
                                } else {
                                    tvStuInfoBusStatus.setText("否");
                                }


                                switch (Integer.parseInt(myStuInfoBean.getData().getOrder_info().get(1).getOrder_sub())) {
                                    case 1:
                                        tvStuInfoPreClass2.setText("科目一");
                                        break;
                                    case 2:
                                        tvStuInfoPreClass2.setText("科目二");
                                        break;
                                    case 3:
                                        tvStuInfoPreClass2.setText("科目三");
                                        break;
                                    case 4:
                                        tvStuInfoPreClass2.setText("科目四");
                                        break;
                                }
                                tvStuInfoTestDate2.setText(myStuInfoBean.getData().getOrder_info().get(1).getOrder_date());
                                tvStuInfoTestPlace2.setText(myStuInfoBean.getData().getOrder_info().get(1).getOrder_site());
                                if ("1".equals(myStuInfoBean.getData().getOrder_info().get(1).getOrder_bus())) {
                                    tvStuInfoBusStatus2.setText("是");
                                } else {
                                    tvStuInfoBusStatus2.setText("否");
                                }


                            }
                        } else {
                            llStuInfoTestInfo.setVisibility(View.GONE);
                        }
                    } else if(myStuInfoBean.getRc()==3000){
                        LogoutUtil.clearData(StudentInfoActivity.this);
                        goToActivity(StudentInfoActivity.this,LoginActivity.class);
                        ToastUtils.ToastMessage(StudentInfoActivity.this, myStuInfoBean.getDes());
                    }


                    break;
            }
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_my_stu_info_phone:
                MobilePhoneUtils.makeCall(tvStuInfoPhone.getText().toString(), tvStuInfoName.getText().toString(), StudentInfoActivity.this);
                break;
            case R.id.imv_my_stu_info_ndphone:
                MobilePhoneUtils.makeCall(tvStuInfoNdphone.getText().toString(), tvStuInfoName.getText().toString(), StudentInfoActivity.this);
                break;
            case R.id.imv_cus_title_back:
                StudentInfoActivity.this.finish();
                break;
        }
    }
}
