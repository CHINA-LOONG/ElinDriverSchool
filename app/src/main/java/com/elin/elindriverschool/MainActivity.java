package com.elin.elindriverschool;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.elin.elindriverschool.activity.BaseActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.fragment.ContactFragment;
import com.elin.elindriverschool.fragment.FragmentMsg;
import com.elin.elindriverschool.fragment.HomeNewFragment;
import com.elin.elindriverschool.fragment.MessageNewFragment;
import com.elin.elindriverschool.fragment.UserFragment;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NotReadMessageBean;
import com.elin.elindriverschool.model.UpdateVetsionBean;
import com.elin.elindriverschool.service.UpdateService;
import com.elin.elindriverschool.sharedpreferences.PreferenceManager;
import com.elin.elindriverschool.util.AppSPUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.service.PushService;
import kr.co.namee.permissiongen.PermissionGen;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.fl_fragment_container)
    FrameLayout flFragmentContainer;
    @Bind(R.id.bottom_home_navi_bar)
    BottomNavigationBar bottomHomeNaviBar;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;


    private static boolean isExit = false;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String responseJson,versionJson,currentVersion;
    private int lastSelectedPosition;
    BottomNavigationItem homeItem;
    BottomNavigationItem messageItem;
    BottomNavigationItem contactItem;
    BottomNavigationItem userItem;
    HomeNewFragment homeNewFragment;
    ContactFragment contactFragment;
    MessageNewFragment fragmentMsg;
    UserFragment userFragment;
    private ReadMsgReceiver receiver;
    Gson gson = new Gson();
    int oldVersionCode,newVersionCode;
    Dialog updateDialog;
    String forceupdate;
    ProgressDialog loadProgressDialog;
    MyHandler handler;
    private static int msgCount;
    private Dialog tipDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handler = new MyHandler(this);
        receiver = new ReadMsgReceiver();
        loadProgressDialog = new ProgressDialog(MainActivity.this);
        loadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        loadProgressDialog.setCanceledOnTouchOutside(false);
        IntentFilter filter = new IntentFilter();
        filter.addAction("readMsg");
        filter.addAction("loginout");
        filter.addAction("update");
        registerReceiver(receiver, filter);
        homeItem = new BottomNavigationItem(R.drawable.icon_home, "首页").setActiveColorResource(R.color.colorPrimary);
        contactItem = new BottomNavigationItem(R.drawable.icon_contacts_new, "电话簿").setActiveColorResource(R.color.colorPrimary);
        messageItem = new BottomNavigationItem(R.drawable.icon_home_message_new, "消息").setActiveColorResource(R.color.colorPrimary);
        userItem = new BottomNavigationItem(R.drawable.icon_mine, "我的").setActiveColorResource(R.color.colorPrimary);
        bottomHomeNaviBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomHomeNaviBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.CALL_PHONE)
                .request();
        initView();
        updateVersion();
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            oldVersionCode = (int) AppSPUtil.get(this,PreferenceManager.VERSION_CODE,info.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), PushService.class.getName()))
                    .setPeriodic(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();
            jobScheduler.schedule(jobInfo);
        }

    }

    private void updateVersion() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.UPDATE_VERSION)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    versionJson = String.valueOf(response.body().string());
                    handler.sendEmptyMessage(2);
                    call.cancel();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getMsgCount();
    }


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
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    handler.sendEmptyMessage(0);
                    call.cancel();
                }
            }
        });
    }


    static class MyHandler extends Handler {
        //持有弱引用HandlerActivity,GC回收时会被回收掉.
        private final WeakReference<MainActivity> mActivty;
        public MyHandler(MainActivity activity){
            mActivty =new WeakReference<MainActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            final MainActivity activity=mActivty.get();
            super.handleMessage(msg);
            if(activity!=null){
                //执行业务逻辑
                switch (msg.what) {
                    case 0:
                        if(!TextUtils.isEmpty(activity.responseJson)){
                            Log.e("MainActivity",activity.responseJson);
                            NotReadMessageBean msgCountBean = activity.gson.fromJson(activity.responseJson, NotReadMessageBean.class);
                            String all = "";
                            msgCount = msgCountBean.getData().getAll();
                            if (msgCountBean.getData() != null) {
                                if(msgCountBean.getData().getAll()<=99){
                                    all = msgCountBean.getData().getAll()+"";
                                }else {
                                    all = "99+";
                                }
                                BadgeItem numberBadgeItm = new BadgeItem().setBorderWidth(4)
                                        .setBackgroundColor(Color.RED)
                                        .setText(all)
                                        .setHideOnSelect(false);
                                activity.bottomHomeNaviBar.clearAll();
                                BaseApplication.getInstance().setMessageCount(msgCountBean.getData().getMsg1()+"");
                                BaseApplication.getInstance().setNoticeCount(msgCountBean.getData().getMsg2()+"");
                                if(activity.fragmentMsg!=null){
                                    activity.fragmentMsg.onResume();
                                }
                                if (0 == (msgCountBean.getData().getAll())) {
                                    activity.messageItem.setBadgeItem(null).setActiveColorResource(R.color.colorPrimary);
                                } else {
                                    activity.messageItem.setBadgeItem(numberBadgeItm).setActiveColorResource(R.color.colorPrimary);
                                }
                                activity.bottomHomeNaviBar.addItem(activity.homeItem)
                                        .addItem(activity.contactItem)
                                        .addItem(activity.messageItem)
                                        .addItem(activity.userItem)
                                        .setFirstSelectedPosition(activity.lastSelectedPosition)
                                        .initialise();
                            }else {
                                activity.bottomHomeNaviBar.clearAll();
                                BaseApplication.getInstance().setMessageCount("0");
                                BaseApplication.getInstance().setNoticeCount("0");
                                activity.messageItem.setBadgeItem(null).setActiveColorResource(R.color.colorPrimary);
                                activity.bottomHomeNaviBar.addItem(activity.homeItem)
                                        .addItem(activity.contactItem)
                                        .addItem(activity.messageItem)
                                        .addItem(activity.userItem)
                                        .setFirstSelectedPosition(activity.lastSelectedPosition)
                                        .initialise();
                            }
                        }

                        break;
                    case 1:
                        BaseApplication.getInstance().setMessageCount("0");
                        BaseApplication.getInstance().setNoticeCount("0");
                        break;
                    case 2:
                        final UpdateVetsionBean info = activity.gson.fromJson(activity.versionJson, UpdateVetsionBean.class);
                        if(!TextUtils.isEmpty(info.getVersioncode())){
                            activity.newVersionCode = Integer.parseInt(info.getVersioncode());
                        }
                        activity.forceupdate = info.getForceupdate();
                        if (activity.oldVersionCode<activity.newVersionCode) {
                            activity.updateDialog = new Dialog(activity,R.style.MyDialog);
                            View v = LayoutInflater.from(activity).inflate(R.layout.dialog_version_update, null);
                            TextView tvDialogTitle = (TextView) v.findViewById(R.id.tv_dialog_title);
                            TextView versionDescrip = (TextView) v.findViewById(R.id.tv_updatedesdesc);
                            TextView versionNo = (TextView) v.findViewById(R.id.tv_update_version);
                            TextView versionDate = (TextView) v.findViewById(R.id.tv_release_time);
                            Button update = (Button) v.findViewById(R.id.btn_update);
                            Button cancle = (Button) v.findViewById(R.id.btn_cancle_update);
                            update.setText("更新");
                            tvDialogTitle.setText("版本更新");
                            versionDescrip.setText("版本描述：" + info.getDescription());
                            AppSPUtil.put(activity,PreferenceManager.UPDATE_CONTENT,versionDescrip.getText().toString());
                            versionNo.setText("版本号：" + info.getVersionname());
                            String date = info.getDate();
                            versionDate.setText("发布时间:"+ date);
                            activity.updateDialog.setContentView(v);
                            activity.updateDialog.setCanceledOnTouchOutside(false);
                            WindowManager m = activity.getWindowManager();
                            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                            WindowManager.LayoutParams p = activity.updateDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
                            p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
                            activity.updateDialog.getWindow().setAttributes(p);
                            activity.updateDialog.show();
                            if("2".equals(activity.forceupdate)){
                                cancle.setVisibility(View.VISIBLE);
                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, UpdateService.class);
                                        intent.putExtra("loadurl",info.getLoadurl());
                                        activity.startService(intent);
                                        activity.loadProgressDialog.show();
                                        activity.updateDialog.dismiss();
                                    }
                                });
                                cancle.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        activity.updateDialog.dismiss();
                                        AppSPUtil.put(activity,PreferenceManager.VERSION_CODE,activity.newVersionCode);
                                    }
                                });
                            }else if("1".equals(activity.forceupdate)){
                                cancle.setVisibility(View.GONE);
                                activity.updateDialog.setCancelable(false);
                                update.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(activity, UpdateService.class);
                                        intent.putExtra("loadurl",info.getLoadurl());
                                        activity.startService(intent);
                                        activity.updateDialog.dismiss();
                                        activity.loadProgressDialog.show();
                                    }
                                });
                            }
                        }
                        break;
                }
            }
        }
    }


    private void initView() {

        Bundle bundle = getIntent().getExtras();
        msgCount=0;
        if(bundle!=null){
            msgCount = bundle.getInt("msgCount",0);
        }
        setDefaultFragment(msgCount);
        if(msgCount==0){
            lastSelectedPosition = 0;
            bottomHomeNaviBar
                    .addItem(homeItem)
                    .addItem(contactItem)
                    .addItem(messageItem)
                    .addItem(userItem)
                    .setFirstSelectedPosition(0)
                    .initialise();
        }else {
            lastSelectedPosition = 2;
            bottomHomeNaviBar
                    .addItem(homeItem)
                    .addItem(contactItem)
                    .addItem(messageItem)
                    .addItem(userItem)
                    .setFirstSelectedPosition(2)
                    .initialise();
        }

        bottomHomeNaviBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认的
     */
    private void setDefaultFragment(int msgCount) {
        if(msgCount==0){
            if(homeNewFragment==null){
                homeNewFragment = new HomeNewFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.fl_fragment_container, homeNewFragment);
                transaction.commit();
            }
        }else {
            if(fragmentMsg==null){
                fragmentMsg = new MessageNewFragment();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.add(R.id.fl_fragment_container, fragmentMsg);
                transaction.commit();
            }
        }
    }

    @Override
    public void onTabSelected(int position) {

        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        if(!TextUtils.isEmpty(BaseApplication.getInstance().getToken())){
            switch (position) {
                case 0:
                    if(msgCount==0){
                        if (homeNewFragment == null) {
                            homeNewFragment = new HomeNewFragment();
                            transaction.add(R.id.fl_fragment_container, homeNewFragment);
                        } else {
                            transaction.show(homeNewFragment);
                        }
                    }else {
                        bottomHomeNaviBar.selectTab(lastSelectedPosition);
                        final Dialog tipDialog = new Dialog(this,R.style.MyDialog);
                        View view = LayoutInflater.from(this).inflate(R.layout.dialog_tv,null);
                        tipDialog.setContentView(view);
                        tipDialog.show();
                        Window window = tipDialog.getWindow();
                        WindowManager windowManager = window.getWindowManager();
                        Display display = windowManager.getDefaultDisplay();
                        WindowManager.LayoutParams params = window.getAttributes();
                        params.width =(int)(display.getWidth()*0.8);
                        params.height = (int)(display.getWidth()*0.3);
                        window.setAttributes(params);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tipDialog.dismiss();
                                    }
                                });

                            }
                        }).start();

//                        ToastUtils.ToastMessage(this,"请先阅读完通知消息");
                        return;
                    }

                    break;
                case 1:
                    if (contactFragment == null) {
                        contactFragment = new ContactFragment();
                        transaction.add(R.id.fl_fragment_container, contactFragment);
                    } else {
                        transaction.show(contactFragment);
                    }
                    break;

                case 2:
                    if (fragmentMsg == null) {
                        fragmentMsg = new MessageNewFragment();
                        transaction.add(R.id.fl_fragment_container, fragmentMsg);
                    } else {
                        transaction.show(fragmentMsg);
                    }
                    break;
                case 3:
                    if (userFragment == null) {
                        userFragment = new UserFragment();
                        transaction.add(R.id.fl_fragment_container, userFragment);
                    } else {
                        transaction.show(userFragment);
                    }
                    break;
                default:
                    break;
            }

        }else {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        // 事务提交
        transaction.commit();
        lastSelectedPosition = position;
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }


    private void hideFragment(FragmentTransaction transaction) {
        if (homeNewFragment != null) {
            transaction.hide(homeNewFragment);
        }
        if (fragmentMsg != null) {
            transaction.hide(fragmentMsg);
        }
        if (contactFragment != null) {
            transaction.hide(contactFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }
    }

    /**
     * 点击返回键两次退出
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出教练端",
                    Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            BaseApplication.getInstance().exitActivity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(oldVersionCode<newVersionCode&&("1".equals(forceupdate))){
            return false;
        }else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                exit();
            }
            return false;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            isExit = false;
        }
    };

    class ReadMsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("readMsg".equals(intent.getAction())) {
                getMsgCount();
                if(fragmentMsg!=null){
                    fragmentMsg.onResume();
                }
            }else if("loginout".equals(intent.getAction())){
                bottomHomeNaviBar.clearAll();
                BaseApplication.getInstance().setMessageCount("0");
                BaseApplication.getInstance().setNoticeCount("0");
                messageItem.setBadgeItem(null).setActiveColorResource(R.color.colorPrimary);
                bottomHomeNaviBar.addItem(homeItem)
                        .addItem(contactItem)
                        .addItem(messageItem)
                        .addItem(userItem)
                        .setFirstSelectedPosition(lastSelectedPosition)
                        .initialise();
            }else if("update".equals(intent.getAction())){
                String result = intent.getStringExtra("progress");
                loadProgressDialog.setMax(100);
                loadProgressDialog.setProgress((int) (Float.parseFloat(result) * 100));
                if((int) (Float.parseFloat(result) * 100)==100){
                    loadProgressDialog.dismiss();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        handler.removeCallbacksAndMessages(null);
    }
}
