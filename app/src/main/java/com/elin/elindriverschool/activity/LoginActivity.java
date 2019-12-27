package com.elin.elindriverschool.activity;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.LoginApp;
import com.elin.elindriverschool.model.LoginBean;
import com.elin.elindriverschool.model.NotReadMessageBean;
import com.elin.elindriverschool.sharedpreferences.PreferenceManager;
import com.elin.elindriverschool.util.AppSPUtil;
import com.elin.elindriverschool.util.DES;
import com.elin.elindriverschool.util.MD5Util;
import com.elin.elindriverschool.util.MyDateUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;
import com.huawei.android.pushagent.api.PushManager;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;
import static com.elin.elindriverschool.application.BaseApplication.MI_APP_ID;
import static com.elin.elindriverschool.application.BaseApplication.MI_APP_KEY;

/**
 * Created by imac on 16/12/28.
 * 登录页面
 * <p>
 * ii.                                         ;9ABH,
 * SA391,                                    .r9GG35&G
 * &#ii13Gh;                               i3X31i;:,rB1
 * iMs,:,i5895,                         .5G91:,:;:s1:8A
 * 33::::,,;5G5,                     ,58Si,,:::,sHX;iH1
 * Sr.,:;rs13BBX35hh11511h5Shhh5S3GAXS:.,,::,,1AG3i,GG
 * .G51S511sr;;iiiishS8G89Shsrrsh59S;.,,,,,..5A85Si,h8
 * :SB9s:,............................,,,.,,,SASh53h,1G.
 * .r18S;..,,,,,,,,,,,,,,,,,,,,,,,,,,,,,....,,.1H315199,rX,
 * ;S89s,..,,,,,,,,,,,,,,,,,,,,,,,....,,.......,,,;r1ShS8,;Xi
 * i55s:.........,,,,,,,,,,,,,,,,.,,,......,.....,,....r9&5.:X1
 * 59;.....,.     .,,,,,,,,,,,...        .............,..:1;.:&s
 * s8,..;53S5S3s.   .,,,,,,,.,..      i15S5h1:.........,,,..,,:99
 * 93.:39s:rSGB@A;  ..,,,,.....    .SG3hhh9G&BGi..,,,,,,,,,,,,.,83
 * G5.G8  9#@@@@@X. .,,,,,,.....  iA9,.S&B###@@Mr...,,,,,,,,..,.;Xh
 * Gs.X8 S@@@@@@@B:..,,,,,,,,,,. rA1 ,A@@@@@@@@@H:........,,,,,,.iX:
 * ;9. ,8A#@@@@@@#5,.,,,,,,,,,... 9A. 8@@@@@@@@@@M;    ....,,,,,,,,S8
 * X3    iS8XAHH8s.,,,,,,,,,,...,..58hH@@@@@@@@@Hs       ...,,,,,,,:Gs
 * r8,        ,,,...,,,,,,,,,,.....  ,h8XABMMHX3r.          .,,,,,,,.rX:
 * :9, .    .:,..,:;;;::,.,,,,,..          .,,.               ..,,,,,,.59
 * .Si      ,:.i8HBMMMMMB&5,....                    .            .,,,,,.sMr
 * SS       :: h@@@@@@@@@@#; .                     ...  .         ..,,,,iM5
 * 91  .    ;:.,1&@@@@@@MXs.                            .          .,,:,:&S
 * hS ....  .:;,,,i3MMS1;..,..... .  .     ...                     ..,:,.99
 * ,8; ..... .,:,..,8Ms:;,,,...                                     .,::.83
 * s&: ....  .sS553B@@HX3s;,.    .,;13h.                            .:::&1
 * SXr  .  ...;s3G99XA&X88Shss11155hi.                             ,;:h&,
 * iH8:  . ..   ,;iiii;,::,,,,,.                                 .;irHA
 * ,8X5;   .     .......                                       ,;iihS8Gi
 * 1831,                                                 .,;irrrrrs&@
 * ;5A8r.                                            .:;iiiiirrss1H
 * :X@H3s.......                                .,:;iii;iiiiirsrh
 * r#h:;,...,,.. .,,:;;;;;:::,...              .:;;;;;;iiiirrss1
 * ,M8 ..,....,.....,,::::::,,...         .     .,;;;iiiiiirss11h
 * 8B;.,,,,,,,.,.....          .           ..   .:;;;;iirrsss111h
 * i@5,:::,,,,,,,,.... .                   . .:::;;;;;irrrss111111
 * 9Bi,:,,,,......                        ..r91;;;;;iirrsss1ss1111
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.imv_login_back)
    ImageView imvLoginBack;
    @Bind(R.id.edt_login_id_num)
    EditText edtLoginIdNum;
    @Bind(R.id.edt_login_pwd)
    EditText edtLoginPwd;
    @Bind(R.id.tv_login_forget_pwd)
    TextView tvLoginForgetPwd;
    @Bind(R.id.btn_login_app)
    Button btnLoginApp;
    @Bind(R.id.checkbox_remember_pass)
    CheckBox checkboxRememberPass;

    private MyProgressDialog myProgressDialog;
    private LoginApp loginApp;
    private LoginBean loginBean;
    private String currentDate,//当前时间
            currentDateStr;//当前时间戳
    private Map<String, String> parmasMap = new HashMap<>();
    private Intent intent = new Intent();
    Gson gson = new Gson();
    private String ImageBaseURL = "http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/";
    private boolean isFromMyInfo = false;
    private boolean isRememberPass;
    private String coachAccount,password;
    private String msgResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        imvLoginBack.setOnClickListener(this);
        btnLoginApp.setOnClickListener(this);
        tvLoginForgetPwd.setOnClickListener(this);
        getCurrentDate();

        myProgressDialog = new MyProgressDialog(LoginActivity.this, R.style.progress_dialog);
        isFromMyInfo = getIntent().getBooleanExtra("isFromMyInfo", false);
        isRememberPass = (boolean) AppSPUtil.get(this, PreferenceManager.IS_REMEMBER_PASS,false);
        checkboxRememberPass.setChecked(isRememberPass);
        if(isRememberPass){
            coachAccount = (String) AppSPUtil.get(this,PreferenceManager.COACH_ACCOUNT,"");
            password = (String) AppSPUtil.get(this,PreferenceManager.PASSWORD,"");
            edtLoginIdNum.setText(coachAccount);
            edtLoginPwd.setText(password);
            edtLoginIdNum.setSelection(coachAccount.length());
            edtLoginPwd.setSelection(password.length());
        }
        checkboxRememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppSPUtil.put(LoginActivity.this,PreferenceManager.IS_REMEMBER_PASS,isChecked);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        currentDate = format.format(curDate);
        currentDateStr = MyDateUtils.getDateToString(currentDate);
        Log.e("获取当前时间戳-->", currentDateStr);
    }

    private boolean testData() {
        if (edtLoginIdNum.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(LoginActivity.this, "请输入教练账号");
            return false;
        }
        if (edtLoginPwd.getText().toString().length() == 0) {
            ToastUtils.ToastMessage(LoginActivity.this, "请输入密码");
            return false;
        }

        return true;
    }

    /**
     * okhttp 异步post请求
     */
    private String loginBody;//登录请求消息体

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    private String paramsJsonStr;
    private String responseJson;

    private void loginApp() {
        myProgressDialog.show();
        loginApp = new LoginApp();
        loginApp.setCoach_idnum(edtLoginIdNum.getText().toString().toUpperCase());
        loginApp.setCoach_password(edtLoginPwd.getText().toString());
//        loginApp.setCoach_clientid(BaseApplication.getInstance().getCoach_clientid());clientID弃用
        loginApp.setCoach_client_type(Build.MANUFACTURER);
        parmasMap.put("timestamp", currentDateStr);
        try {
            Log.e("登录MD5加密body-->", MD5Util.get32MD5Str(loginApp.toString()));
            loginBody = DES.encode(MD5Util.get32MD5Str(loginApp.toString()) + loginApp.toString()).replaceAll(" ", "");
            Log.e("登录最终消息体-->", String.valueOf(loginBody));
            Log.e("解密需加密的消息体-->", MD5Util.get32MD5Str(loginApp.toString()) + loginApp.toString());
            parmasMap.put("body", loginBody);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("加密失败-->", e.toString() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage());
        }


        paramsJsonStr = gson.toJson(parmasMap);
        Log.e("请求Body的Json-->", paramsJsonStr);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.LOGIN_APP)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, paramsJsonStr))
                .build();
        Call call = new MyOkHttpClient(LoginActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (myProgressDialog.isShowing()) {
                    myProgressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(1);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (myProgressDialog.isShowing()) {
                        myProgressDialog.dismiss();
                    }
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(0);
                    call.cancel();
                }
            }


        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Log.e("responseJson-->", responseJson + "");
                    loginBean = gson.fromJson(responseJson, LoginBean.class);
                    if ("0".equals(loginBean.getRc())) {
                        BaseApplication.getInstance().setToken(loginBean.getData().getToken());
                        BaseApplication.getInstance().setCoachIdNum(loginBean.getData().getCoach_idnum());
                        BaseApplication.getInstance().setCoachPhone(loginBean.getData().getCoach_phone());
                        BaseApplication.getInstance().setCoachPhoto(loginBean.getData().getCoach_photo());
                        BaseApplication.getInstance().setCoachName(loginBean.getData().getCoach_name());
                        BaseApplication.getInstance().setSchoolId(loginBean.getData().getSchool_id());
                        AppSPUtil.put(LoginActivity.this,PreferenceManager.COACH_ACCOUNT,edtLoginIdNum.getText().toString().trim());
                        AppSPUtil.put(LoginActivity.this,PreferenceManager.PASSWORD,edtLoginPwd.getText().toString().trim());
                        if (isFromMyInfo) {
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }
                        getMsgCount(loginBean.getData().getToken());
                    } else {
                        if(TextUtils.isEmpty(loginBean.getDes().toString())){
                            ToastUtils.ToastMessage(LoginActivity.this, "登陆失败");
                        }else {
                            ToastUtils.ToastMessage(LoginActivity.this, loginBean.getDes().toString());
                        }
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(LoginActivity.this, "服务器错误，检查网络连接");
                    break;
                case 2:
                    if(!TextUtils.isEmpty(msgResponse)){
                        NotReadMessageBean msgCountBean = new Gson().fromJson(msgResponse, NotReadMessageBean.class);
                        if (msgCountBean.getData() != null) {
                            int all = msgCountBean.getData().getAll();
                            Bundle bundle = new Bundle();
                            bundle.putInt("msgCount",all);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            ToastUtils.ToastMessage(LoginActivity.this, "登录成功");
                            LoginActivity.this.finish();
                        }else {
                            ToastUtils.ToastMessage(LoginActivity.this, "登陆失败");
                        }
                    }
                    break;
            }
        }
    };

    //获取未读消息数量
    private void getMsgCount(String token) {
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.NOT_READ_MESSAGES)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    msgResponse = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(2);
                    call.cancel();
                }
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_login_back:
                LoginActivity.this.finish();
                break;
            case R.id.btn_login_app:
                if (testData()) {
                    loginApp();
                }
                break;
            case R.id.tv_login_forget_pwd:
                intent.setClass(LoginActivity.this, ChangePwdActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
                break;
        }
    }

    /**
     * 点击返回键两次退出
     */
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出教练端",
                    Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            BaseApplication.getInstance().exitActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            isExit = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
