package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.view.MyProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 17/2/9.
 */

public class HomeWebInfoActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.web_home_info)
    WebView webHomeInfo;

    private MyProgressDialog progressDialog;
    private String loadUrl;
    private int webFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_layout);
        ButterKnife.bind(this);
        webFlag = getIntent().getIntExtra("webFlag",0);
        if (webFlag==1){
            tvCusTitleName.setText("学员报名");

        }
        if (webFlag==2){
            tvCusTitleName.setText("陪驾动态");
        }
        imvCusTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeWebInfoActivity.this.finish();
            }
        });
        progressDialog = new MyProgressDialog(this,R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
        loadUrl = getIntent().getStringExtra("loadUrl");

        if (webHomeInfo!=null){
            webHomeInfo.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            });
            webHomeInfo.loadUrl(loadUrl);
            WebSettings webSettings = webHomeInfo.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
            webSettings.setLoadWithOverviewMode(true);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (webHomeInfo.canGoBack())) {
            webHomeInfo.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
