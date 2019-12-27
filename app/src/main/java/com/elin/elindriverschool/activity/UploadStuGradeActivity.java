package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.UploadGradeFragmentPagerAdapter;
import com.elin.elindriverschool.fragment.UploadStuGradeClassFourFragment;
import com.elin.elindriverschool.fragment.UploadStuGradeClassOneFragment;
import com.elin.elindriverschool.fragment.UploadStuGradeClassThreeFragment;
import com.elin.elindriverschool.fragment.UploadStuGradeClassTwoFragment;
import com.elin.elindriverschool.view.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 16/12/27.
 * 上传学员成绩
 */

public class UploadStuGradeActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.slid_upload_stu_grade)
    SlidingTabLayout slidUploadStuGrade;
    @Bind(R.id.vp_upload_stu_grade)
    ViewPager vpUploadStuGrade;

    UploadGradeFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_stu_grade);
        ButterKnife.bind(this);

        tvCusTitleName.setText("学员成绩上传");
        imvCusTitleBack.setOnClickListener(this);

        fragments.add(new UploadStuGradeClassOneFragment());
        fragments.add(new UploadStuGradeClassTwoFragment());
        fragments.add(new UploadStuGradeClassThreeFragment());
        fragments.add(new UploadStuGradeClassFourFragment());
        myFragmentPagerAdapter = new UploadGradeFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        vpUploadStuGrade.setAdapter(myFragmentPagerAdapter);
        vpUploadStuGrade.setOffscreenPageLimit(5);
        slidUploadStuGrade.setTextColor(ContextCompat.getColor(UploadStuGradeActivity.this,R.color.text_color_light_dark));
        slidUploadStuGrade.setTextColorSelected(ContextCompat.getColor(UploadStuGradeActivity.this,R.color.colorPrimary));
        slidUploadStuGrade.setDistributeEvenly();
        slidUploadStuGrade.setViewPager(vpUploadStuGrade);
        slidUploadStuGrade.setTabSelected(0);
        slidUploadStuGrade.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(UploadStuGradeActivity.this,R.color.colorPrimary);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                    UploadStuGradeActivity.this.finish();
                break;
        }
    }
}
