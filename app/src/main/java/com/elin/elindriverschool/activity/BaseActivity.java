package com.elin.elindriverschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elin.elindriverschool.application.BaseApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by imac on 16/12/21.
 */

public class BaseActivity extends AppCompatActivity {

    public String TAG ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getLocalClassName();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        BaseApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * 跳转到另一个Activity，携带数据
     *
     * @param context
     *            上下文
     * @param cls
     *            目标类
     *
     */
    public static void goToActivity(Context context, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        intent.putExtras(bundle);
        context.startActivity(intent);
//        ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

    /**
     * 启动一个activity
     *
     * @param context
     *            上下文
     * @param cls
     *            目标类
     */
    public static void goToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        context.startActivity(intent);
//        ((AppCompatActivity) context).overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
    }

}
