package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.MyFragmentPagerAdapter;
import com.elin.elindriverschool.api.UpdateTitle;
import com.elin.elindriverschool.fragment.MyStusWaitOption;
import com.elin.elindriverschool.fragment.WaitTestFragment;
import com.elin.elindriverschool.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by imac on 16/12/26.
 * 待考学员
 */

public class MyStudentsActivity extends BaseActivity {
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.slid_my_stus)
    SlidingTabLayout slidMyStus;
    @Bind(R.id.vp_my_stus)
    ViewPager vpMyStus;

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private MyStusWaitOption stusWaitOption;
    //    private MyStusClassOne classOne;
//    private MyStusClassTwo classTwo;
//    private MyStusClassThree classThree;
//    private MyStusClassFour classFour;
    private WaitTestFragment classOne;
    private WaitTestFragment classTwo;
    private WaitTestFragment classThree;
    private WaitTestFragment classFour;
    private List<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stus);
        ButterKnife.bind(this);
        tvCusTitleName.setText("待受理学员");
        tvCusTitleRight.setText("全部待考学员");
        stusWaitOption = new MyStusWaitOption();
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.add(R.id.rl_container, stusWaitOption);
//        transaction.commit();
        classOne = WaitTestFragment.newInstance(1);
        classTwo = WaitTestFragment.newInstance(2);
        classThree = WaitTestFragment.newInstance(3);
        classFour = WaitTestFragment.newInstance(4);
        fragments.add(stusWaitOption);
        fragments.add(classOne);
        fragments.add(classTwo);
        fragments.add(classThree);
        fragments.add(classFour);
        strings.add("待受理");
        strings.add("科目一");
        strings.add("科目二");
        strings.add("科目三");
        strings.add("科目四");
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments, strings);
        vpMyStus.setAdapter(myFragmentPagerAdapter);
        slidMyStus.setTextColor(ContextCompat.getColor(MyStudentsActivity.this, R.color.text_color_light_dark));
        slidMyStus.setTextColorSelected(ContextCompat.getColor(MyStudentsActivity.this, R.color.colorPrimary));
        slidMyStus.setDistributeEvenly();
        slidMyStus.setViewPager(vpMyStus);
        slidMyStus.setTabSelected(0);
        vpMyStus.setOffscreenPageLimit(5);
        slidMyStus.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(MyStudentsActivity.this, R.color.colorPrimary);
            }
        });
        stusWaitOption.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("待受理学员（" + count + "）");
            }
        });
        classOne.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("待考学员（" + count + "）");
            }
        });
        classTwo.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("待考学员（" + count + "）");
            }
        });
        classThree.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("待考学员（" + count + "）");
            }
        });
        classFour.setUpdate(new UpdateTitle() {
            @Override
            public void onUpdateTitle(String count) {
                tvCusTitleName.setText("待考学员（" + count + "）");
            }
        });

    }

    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_cus_title_right:
                goToActivity(this,SubWaitTestActivity.class);
                break;
        }
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.imv_cus_title_back:
//                    MyStudentsActivity.this.finish();
//                break;
//        }
//    }
}
