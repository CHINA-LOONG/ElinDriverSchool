package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 16/12/28.
 */

public class ForgetPwdActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.edt_forget_pwd_phone)
    EditText edtForgetPwdPhone;
    @Bind(R.id.edt_forget_pwd_name)
    EditText edtForgetPwdName;
    @Bind(R.id.edt_forget_pwd_id_licence)
    EditText edtForgetPwdIdLicence;
    @Bind(R.id.edt_forget_pwd_new_pwd)
    EditText edtForgetPwdNewPwd;
    @Bind(R.id.edt_forget_pwd_confirm)
    EditText edtForgetPwdConfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("找回密码");
        tvCusTitleRight.setVisibility(View.VISIBLE);
        tvCusTitleRight.setText("确定");
        tvCusTitleRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                ForgetPwdActivity.this.finish();
                break;
            case R.id.tv_cus_title_right:
                break;
        }

    }
}

