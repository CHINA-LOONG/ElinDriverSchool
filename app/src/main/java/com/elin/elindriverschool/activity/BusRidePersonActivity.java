package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.MyFragmentPagerAdapter;
import com.elin.elindriverschool.fragment.BusOrganizerFragment;
import com.elin.elindriverschool.fragment.BusStuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.TabLayout.MODE_FIXED;

public class BusRidePersonActivity extends BaseActivity {

    @Bind(R.id.tab_bus_person)
    TabLayout tabBusPerson;
    @Bind(R.id.vp_bus_person)
    ViewPager vpBusPerson;
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private MyFragmentPagerAdapter adapter;
    private String busPerson,busDate,orderSub,busNum;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_ride_person);
        ButterKnife.bind(this);
        tvCusTitleName.setText("班车乘坐人员");
        bundle = getIntent().getExtras();
        busPerson = bundle.getString("order_bus_person");
        busDate = bundle.getString("order_bus_date");
        orderSub = bundle.getString("order_sub");
        busNum = bundle.getString("order_bus_num");
        fragments = new ArrayList<>();
        fragments.add(BusOrganizerFragment.newInstance(busPerson));
        fragments.add(BusStuFragment.newInstance(busDate,orderSub,busNum));
        strings.add("组织人员");
        strings.add("乘坐学员");
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments,strings);
        LinearLayout linearLayout = (LinearLayout) tabBusPerson.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_divider_vertical));
        tabBusPerson.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        tabBusPerson.setTabMode(MODE_FIXED);
        vpBusPerson.setAdapter(adapter);
        tabBusPerson.setupWithViewPager(vpBusPerson);
    }

    @OnClick(R.id.imv_cus_title_back)
    public void onClick() {
        finish();
    }
}
