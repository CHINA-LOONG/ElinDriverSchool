package com.elin.elindriverschool.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NotReadMessageBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;
import com.huawei.android.pushagent.api.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

/**
 * Created by imac on 16/12/29.
 */

public class SplashActivity extends BaseActivity {

//    private boolean isFirstRun = false;
    private static final int GO_MAIN = 1000;
    private static final int GO_WELCOME = 1001;

    private static final long SPLASH_DELAY_MILLIS = 2000;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";
    SharedPreferences preferences;
    private Intent intent = new Intent();
    String coach_clientid;
    SplashReceiver splashReceiver;
    public static final String MESSAGE_REGISTRATION_ID = "splashactivity";
    private String responseJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        if(Build.MANUFACTURER.equals("HUAWEI")){
            PushManager.requestToken(this);
            PushManager.enableReceiveNotifyMsg(this, true);
        }
        registerMessageReceiver();
        if (!TextUtils.isEmpty(BaseApplication.getInstance().getCoach_clientid())) {
            coach_clientid = BaseApplication.getInstance().getCoach_clientid();
            Log.e("包含clientid-->", "clientid");
        }
        if(!isNetworkConnected(this)){
            ToastUtils.ToastMessage(SplashActivity.this,"请检查网络链接");
        }
        getMsgCount();
    }
    //获取未读消息数量
    private void getMsgCount() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.NOT_READ_MESSAGES)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessageAtTime(GO_MAIN, SPLASH_DELAY_MILLIS);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(0);
                    call.cancel();
                }
            }
        });
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return true;//有网
            }
        }
        return false;//没有网
    }
    public void registerMessageReceiver() {
        splashReceiver = new SplashReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_REGISTRATION_ID);
        registerReceiver(splashReceiver, filter);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN://通过token判断是否跳转到login页还是main页
                    if (!TextUtils.isEmpty(BaseApplication.getInstance().getToken())) {
                        goMain();
                    } else {
                        goLogin();
                    }
                    break;
                case 0:
                    if(!TextUtils.isEmpty(responseJson)){
                        NotReadMessageBean msgCountBean = new Gson().fromJson(responseJson, NotReadMessageBean.class);
                        if (msgCountBean.getData() != null) {
                            int all = msgCountBean.getData().getAll();
                            Bundle bundle = new Bundle();
                            bundle.putInt("msgCount",all);
                            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }else {
                            goLogin();
                        }
                    }

                    break;
            }
        }
    };

    private void goMain() {
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
//        finish();
    }

    private void goLogin() {
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
//        finish();

    }

    private void goWelcome() {
        intent.setClass(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(splashReceiver);
    }

    class SplashReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_REGISTRATION_ID.equals(intent.getAction())) {
                switch (Build.MANUFACTURER){
                    case "Xiaomi":
                        coach_clientid =  MiPushClient.getRegId(getApplicationContext());
                        break;
                    case "HUAWEI":
                        coach_clientid = intent.getStringExtra("hw_token");
                        break;
                    default:
                        coach_clientid = JPushInterface.getRegistrationID(getApplicationContext());
                        break;
                }
                BaseApplication.getInstance().setCoach_clientid(coach_clientid);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            BaseApplication.getInstance().exitActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}
