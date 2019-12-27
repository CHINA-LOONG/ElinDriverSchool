package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 线上报名
 */
public class OnlineRegistrationActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.gv_drive_center)
    GridView gvDriveCenter;

    private List<Map<String, Object>> listDriving = new ArrayList<>();
    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_center);
        ButterKnife.bind(this);
        tvCusTitleName.setText("线上报名");
        initData();
        gvDriveCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://培训班别
                        Bundle trainClassBundle = new Bundle();
                        trainClassBundle.putString("url", Constant.DYNAMIC_URL + "/DrivingService/uploadBox.html?inner_id=10&short_name=pxbb&school_id="+ BaseApplication.getInstance().getSchoolId());
                        trainClassBundle.putString("title", "培训班别");
                        goToActivity(OnlineRegistrationActivity.this, WebViewActivity.class, trainClassBundle);
                        break;
                    case 1://预报名
                        goToActivity(OnlineRegistrationActivity.this,SignupAdvanceActivity.class);
                        break;
                }
            }
        });
    }
    private void initData() {
        String names[] = getResources().getStringArray(R.array.online_registration);
        String icons[] = getResources().getStringArray(R.array.online_registration_img);
        if (listDriving.size() == 0) {
            for (int i = 0; i < icons.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", names[i]);
                map.put("icon",
                        getResources().getIdentifier(icons[i], "drawable",
                                getPackageName()));
                listDriving.add(map);
            }
        }
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                listDriving,
                R.layout.item_grid_driver_center,
                new String[]{"name", "icon"},
                new int[]{R.id.item_grid_title, R.id.item_grid_img});
        gvDriveCenter.setAdapter(adapter);
    }
    @OnClick(R.id.imv_cus_title_back)
    public void onClick() {
        finish();
    }
}
