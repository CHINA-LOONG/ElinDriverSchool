package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.view.ProgressWebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.webView)
    ProgressWebView webView;

    String url;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        url = intent.getExtras().getString("url");
        title = intent.getExtras().getString("title");
        tvCusTitleName.setText(title);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @OnClick(R.id.imv_cus_title_back)
    public void onClick() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
