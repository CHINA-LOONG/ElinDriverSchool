package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 个人分享
 */
public class AdvertisingDetailActivity extends BaseActivity {


    String innerId, coachPhone, coachWx, coachName, coachInfor,
            dynamicTitle, dynamicContent, dynamicPic, dynamicIntro;
    Bundle extras;
    public static final int ADVERTISING_REQUEST_CODE = 100;
    @Bind(R.id.imv_advertising_back)
    ImageView imvAdvertisingBack;
    @Bind(R.id.tv_advertising_title)
    TextView tvAdvertisingTitle;
    @Bind(R.id.iv_advertising_share)
    ImageView ivAdvertisingShare;
    @Bind(R.id.pwv_advertising)
    WebView webView;
    @Bind(R.id.iv_advertising_detail_edit)
    ImageView ivAdvertisingDetailEdit;
    @Bind(R.id.tv_advertising_detail_name)
    TextView tvAdvertisingDetailName;
    @Bind(R.id.tv_advertising_detail_phone)
    TextView tvAdvertisingDetailPhone;
    @Bind(R.id.tv_advertising_detail_wx)
    TextView tvAdvertisingDetailWx;
    @Bind(R.id.tv_advertising_detail_suggestion)
    TextView tvAdvertisingDetailSuggestion;

    String shareUrl = "/Dynamic/promotion.html?inner_id=%s&coach_id=%s";
    private ShareAction mShareAction;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private Map<String, String> parmasMap = new HashMap<>();
    private String responseJson;
    private MyProgressDialog myProgressDialog;
    private Bitmap bitmap;
    String format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertising_detail);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        extras = getIntent().getExtras();
        innerId = extras.getString("inner_id");
        coachPhone = extras.getString("coach_phone");
        coachWx = extras.getString("coach_wx");
        coachName = extras.getString("coach_name");
        coachInfor = extras.getString("coach_infor");
        dynamicTitle = extras.getString("dynamic_title");
        dynamicContent = extras.getString("dynamic_content");
        dynamicPic = extras.getString("dynamic_pic");
        dynamicIntro = extras.getString("dynamic_Intro");
        bitmap = extras.getParcelable("bitmap");
        tvAdvertisingTitle.setText(dynamicTitle);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        format = String.format(shareUrl, innerId, BaseApplication.getInstance().getCoachIdNum());
        webView.loadUrl(Constant.DYNAMIC_URL + Constant.DYNAMIC_ITEM+innerId);
        onSetText();
        mShareAction = new ShareAction(AdvertisingDetailActivity.this).setDisplayList(
                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        format = String.format(shareUrl, innerId, BaseApplication.getInstance().getCoachIdNum());
                        final UMWeb web = new UMWeb(Constant.ServerURL + format);
                        web.setTitle(dynamicTitle);//标题
//                        bitmap = BitmapUtils.compressBitmap(bitmap,50);
                        UMImage image = new UMImage(AdvertisingDetailActivity.this, bitmap);
                        web.setThumb(image);
                        if(!TextUtils.isEmpty(dynamicIntro)){
                            web.setDescription(dynamicIntro);//描述
                        }else {
                            web.setDescription(dynamicTitle);
                        }
                        new ShareAction(AdvertisingDetailActivity.this)
                                .withMedia(web)
                                .setPlatform(share_media)
                                .setCallback(mShareListener)
                                .share();

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(myProgressDialog.isShowing()){
            myProgressDialog.dismiss();
        }
    }

    public void onSetText() {
        if (!TextUtils.isEmpty(coachName)) {
            tvAdvertisingDetailName.setText(coachName);
        } else {
            tvAdvertisingDetailName.setText("请编辑您的信息");
        }
        if (!TextUtils.isEmpty(coachPhone)) {
            tvAdvertisingDetailPhone.setText(coachPhone);
        } else {
            tvAdvertisingDetailPhone.setText("请编辑您的信息");
        }
        if (!TextUtils.isEmpty(coachWx)) {
            tvAdvertisingDetailWx.setText(coachWx);
        } else {
            tvAdvertisingDetailWx.setText("请编辑您的信息");
        }
        if (!TextUtils.isEmpty(coachInfor)) {
            tvAdvertisingDetailSuggestion.setText(coachInfor);
        } else {
            tvAdvertisingDetailSuggestion.setText("暂无");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ADVERTISING_REQUEST_CODE && data != null) {
            coachName = data.getStringExtra("coach_name");
            coachPhone = data.getStringExtra("coach_phone");
            coachWx = data.getStringExtra("coach_wx");
            coachInfor = data.getStringExtra("coach_infor");
            onSetText();
        }
    }

    @OnClick({R.id.imv_advertising_back, R.id.iv_advertising_share, R.id.iv_advertising_detail_edit})
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
            case R.id.iv_advertising_detail_edit:
                Intent intent = new Intent(this, CoachInfoActivity.class);
                intent.putExtras(extras);
                startActivityForResult(intent, ADVERTISING_REQUEST_CODE);
                break;
        }
    }

    private UMShareListener mShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            Log.e("plat", "分享了");
            myProgressDialog.show();
            updateShareCount();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.e("plat", "platform" + platform);
            if(myProgressDialog.isShowing()){
                myProgressDialog.dismiss();
            }
            ToastUtils.ToastMessage(AdvertisingDetailActivity.this,platform+"分享成功");

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (t != null) {
                Log.e("throw", "throw:" + t.getMessage()+t.getLocalizedMessage());
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
            ToastUtils.ToastMessage(AdvertisingDetailActivity.this,platform+"分享已取消");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView = null;
    }

    public void updateShareCount() {
        parmasMap.put("promotion_id", innerId);
        parmasMap.put("coach_id", BaseApplication.getInstance().getCoachIdNum());
        parmasMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.SHARE_COUNT)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("请求成功-->", response.toString());
                Intent intent1 = new Intent("advertisingDataFresh");
                intent1.setPackage(getPackageName());
                sendBroadcast(intent1);
                call.cancel();
            }
        });
    }
}
