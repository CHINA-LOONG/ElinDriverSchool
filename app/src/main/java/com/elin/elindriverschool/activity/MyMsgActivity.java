package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 17/1/10.
 */

public class MyMsgActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.layout_nodata)
    LinearLayout layoutNodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);
        ButterKnife.bind(this);
        tvCusTitleName.setText("消息");
        imvCusTitleBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                MyMsgActivity.this.finish();
                break;
        }
    }
}
