package com.elin.elindriverschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AppointmentSucStudentsActivity;
import com.elin.elindriverschool.activity.CheckStudentGradeActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.MyDriveStudentActivity;
import com.elin.elindriverschool.activity.MyStudentsActivity;
import com.elin.elindriverschool.activity.UploadStuGradeActivity;
import com.elin.elindriverschool.application.BaseApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DriveFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.ll_drive_my_stus)
    LinearLayout llDriveMyStus;
    @Bind(R.id.ll_drive_wait_test_stus)
    LinearLayout llDriveWaitTestStus;
    @Bind(R.id.ll_drive_pre_suc_stus)
    LinearLayout llDrivePreSucStus;
    @Bind(R.id.ll_drive_grade_upload)
    LinearLayout llDriveGradeUpload;
    @Bind(R.id.ll_drive_check_stu_grade)
    LinearLayout llDriveCheckStuGrade;

    private Intent intent  = new Intent();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drive, container, false);
        ButterKnife.bind(this, view);
        llDriveMyStus.setOnClickListener(this);
        llDriveWaitTestStus.setOnClickListener(this);
        llDriveCheckStuGrade.setOnClickListener(this);
        llDrivePreSucStus.setOnClickListener(this);
        llDriveGradeUpload.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_drive_my_stus:
                if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
                    intent.setClass(getActivity(), MyDriveStudentActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo",true);
                    startActivity(intent);
                }
                break;
            case R.id.ll_drive_check_stu_grade:
                if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
                    intent.setClass(getActivity(), CheckStudentGradeActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo",true);
                    startActivity(intent);
                }

                break;
            case R.id.ll_drive_wait_test_stus:
                if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
                    intent.setClass(getActivity(), MyStudentsActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo",true);
                    startActivity(intent);
                }

                break;
            case R.id.ll_drive_pre_suc_stus:
                if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
                    intent.setClass(getActivity(), AppointmentSucStudentsActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo",true);
                    startActivity(intent);
                }
                break;
            case R.id.ll_drive_grade_upload:
                if (BaseApplication.getInstance().getToken()!=null&&!("".equals(BaseApplication.getInstance().getToken()))) {
                    intent.setClass(getActivity(), UploadStuGradeActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo",true);
                    startActivity(intent);
                }
                break;
        }

    }
}
