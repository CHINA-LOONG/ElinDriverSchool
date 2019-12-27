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
import com.elin.elindriverschool.adapter.MyFragmentPagerAdapter;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.fragment.PreSucStuWaitOptionFragment;
import com.elin.elindriverschool.fragment.PreSucWaitTestFourFragment;
import com.elin.elindriverschool.fragment.PreSucWaitTestOneFragment;
import com.elin.elindriverschool.fragment.PreSucWaitTestThreeFragment;
import com.elin.elindriverschool.fragment.PreSucWaitTestTwoFragment;
import com.elin.elindriverschool.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 16/12/26.
 * 预约成功学员
 */

public class AppointmentSucStudentsActivity extends BaseActivity implements View.OnClickListener {
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.slid_appointment_suc_stus)
    SlidingTabLayout slidAppointmentSucStus;
    @Bind(R.id.vp_appointment_suc_stus)
    ViewPager vpAppointmentSucStus;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private String[] mTabTitle = new String[]{"待受理", "科目一", "科目二","科目三","科目四"};
    private List<String> strings = new ArrayList<>();
    PreSucStuWaitOptionFragment waitOptionFragment = new PreSucStuWaitOptionFragment();
    PreSucWaitTestOneFragment oneFragment = new PreSucWaitTestOneFragment();
    PreSucWaitTestTwoFragment twoFragment = new PreSucWaitTestTwoFragment();
    PreSucWaitTestThreeFragment threeFragment = new PreSucWaitTestThreeFragment();
    PreSucWaitTestFourFragment fourFragment = new PreSucWaitTestFourFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_success_stus);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("预约成功学员");

        fragments.add(waitOptionFragment);
        fragments.add(oneFragment);
        fragments.add(twoFragment);
        fragments.add(threeFragment);
        fragments.add(fourFragment);
        strings.add("待受理");
        strings.add("科目一");
        strings.add("科目二");
        strings.add("科目三");
        strings.add("科目四");
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments,strings);
        vpAppointmentSucStus.setAdapter(myFragmentPagerAdapter);
        slidAppointmentSucStus.setTextColor(ContextCompat.getColor(AppointmentSucStudentsActivity.this, R.color.text_color_light_dark));
        slidAppointmentSucStus.setTextColorSelected(ContextCompat.getColor(AppointmentSucStudentsActivity.this, R.color.colorPrimary));
        slidAppointmentSucStus.setDistributeEvenly();
        slidAppointmentSucStus.setViewPager(vpAppointmentSucStus);
        slidAppointmentSucStus.setTabSelected(0);
        vpAppointmentSucStus.setOffscreenPageLimit(5);
        slidAppointmentSucStus.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(AppointmentSucStudentsActivity.this, R.color.colorPrimary);
            }
        });
        waitOptionFragment.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("预约成功学员（"+count+"）");
            }
        });oneFragment.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("预约成功学员（"+count+"）");
            }
        });twoFragment.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("预约成功学员（"+count+"）");
            }
        });threeFragment.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("预约成功学员（"+count+"）");
            }
        });fourFragment.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("预约成功学员（"+count+"）");
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                AppointmentSucStudentsActivity.this.finish();
                break;
        }
    }
}
