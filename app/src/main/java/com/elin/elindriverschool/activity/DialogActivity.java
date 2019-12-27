package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    @Bind(R.id.btn_exit)
    Button btnExit;
    @Bind(R.id.btn_login_again)
    Button btnLoginAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
    @OnClick({R.id.btn_exit, R.id.btn_login_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                BaseApplication.getInstance().exitActivity();
                break;
            case R.id.btn_login_again:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
