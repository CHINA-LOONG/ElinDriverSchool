package com.elin.elindriverschool.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.elin.elindriverschool.view.ProgressWebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompanyAdvertisingDetailActivity extends BaseActivity {

    @Bind(R.id.imv_advertising_back)
    ImageView imvAdvertisingBack;
    @Bind(R.id.tv_advertising_title)
    TextView tvAdvertisingTitle;
    @Bind(R.id.iv_advertising_share)
    ImageView ivAdvertisingShare;
    @Bind(R.id.rl_advertising_detail_title)
    RelativeLayout rlAdvertisingDetailTitle;
    @Bind(R.id.pwv_company_advertising)
    ProgressWebView webView;
    private ShareAction mShareAction;
    private MyProgressDialog myProgressDialog;
    Bundle extras;
    String dynamicContent, dynamicTitle,dynamicIntro,dynamicPic;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_advertising_detail);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        extras = getIntent().getExtras();
        dynamicContent = extras.getString("dynamic_content");
        dynamicTitle = extras.getString("dynamic_title");
        dynamicPic = extras.getString("dynamic_pic");
        dynamicIntro = extras.getString("dynamic_Intro");
        bitmap = extras.getParcelable("bitmap");
        if (!TextUtils.isEmpty(dynamicTitle)) {
            tvAdvertisingTitle.setText(dynamicTitle);
        }
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        dynamicContent = dynamicContent.replaceAll("</?[^>]+>", "");
        webView.loadUrl(dynamicContent);
        mShareAction = new ShareAction(CompanyAdvertisingDetailActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        final UMWeb web = new UMWeb(dynamicContent);
                        web.setTitle(dynamicTitle);//标题
                        web.setThumb(new UMImage(CompanyAdvertisingDetailActivity.this,bitmap));
                        if(!TextUtils.isEmpty(dynamicIntro)){
                            web.setDescription(dynamicIntro);//描述
                        }else {
                            web.setDescription(dynamicTitle);
                        }
                        new ShareAction(CompanyAdvertisingDetailActivity.this).withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();

                    }
                });
    }

    @OnClick({R.id.imv_advertising_back, R.id.iv_advertising_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_advertising_back:
                finish();
                break;
            case R.id.iv_advertising_share:
                ShareBoardConfig config = new ShareBoardConfig();
                config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
                config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景
                mShareAction.open(config);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myProgressDialog.isShowing()){
            myProgressDialog.dismiss();
        }
    }

    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            myProgressDialog.show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat", "platform" + platform);
            if(myProgressDialog.isShowing()){
                myProgressDialog.dismiss();
            }
            ToastUtils.ToastMessage(CompanyAdvertisingDetailActivity.this,platform+"分享成功");
        }
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.e("throw", "throw:" + t.getMessage());
            }
            if(myProgressDialog.isShowing()){
                myProgressDialog.dismiss();
            }
        }
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            if(myProgressDialog.isShowing()){
                myProgressDialog.dismiss();
            }
            ToastUtils.ToastMessage(CompanyAdvertisingDetailActivity.this,platform+"分享已取消");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView=null;
    }
}
