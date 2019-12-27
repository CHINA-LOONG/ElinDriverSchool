package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.elin.elindriverschool.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 17/2/5.
 * 驾考中心
 */

public class DriveCenterActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.gv_drive_center)
    GridView gvDriveCenter;

    private Intent intent = new Intent();
    List<Map<String, Object>> listDriving = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_center);
        ButterKnife.bind(this);
        tvCusTitleName.setText("驾考中心");
        imvCusTitleBack.setOnClickListener(this);
        initData();
        gvDriveCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://我的学员
                        intent.setClass(DriveCenterActivity.this, MyDriveStudentActivity.class);
                        startActivity(intent);
                        break;
                    case 1://待考学员
                        intent.setClass(DriveCenterActivity.this, MyStudentsActivity.class);
                        startActivity(intent);
                        break;
//                    case 2://报考
//                        intent.setClass(DriveCenterActivity.this, SubWaitTestActivity.class);
//                        startActivity(intent);
//                        break;
                    case 2://预约成功学员
                        intent.setClass(DriveCenterActivity.this, AppointmentSucStudentsActivity.class);
                        startActivity(intent);
                        break;
                    case 3://成绩上传
                        intent.setClass(DriveCenterActivity.this, UploadStuGradeActivity.class);
                        startActivity(intent);
                        break;
                    case 4://学员成绩
                        intent.setClass(DriveCenterActivity.this, CheckStudentGradeActivity.class);
                        startActivity(intent);
                        break;
                    case 5://班车乘坐
                        intent.setClass(DriveCenterActivity.this, BusRideActivity.class);
                        startActivity(intent);
                        break;
                    case 6://预约培训
                        intent.setClass(DriveCenterActivity.this, AppointTrainingStudentActivity.class);
                        startActivity(intent);
                        break;
                    case 7://考试学员统计
                        intent.setClass(DriveCenterActivity.this, StuForOrderActivity.class);
                        startActivity(intent);
                        break;
                    case 8://学员回访
                        intent.setClass(DriveCenterActivity.this, StudentVisitActivity.class);
                        startActivity(intent);
                        break;
                    case 9://学员任务
                        intent.setClass(DriveCenterActivity.this, StudentTaskActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
    }

    private void initData() {
        String names[] = getResources().getStringArray(R.array.drive_center_menu);
        String icons[] = getResources().getStringArray(R.array.drive_center_img); // 去 res - arrays下去拿数据
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
        }
    }
}
