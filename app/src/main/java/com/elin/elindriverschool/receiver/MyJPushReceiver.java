package com.elin.elindriverschool.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.activity.ChatActivity;
import com.elin.elindriverschool.activity.DialogActivity;
import com.elin.elindriverschool.activity.SplashActivity;
import com.elin.elindriverschool.adapter.NoticeAdapter;
import com.elin.elindriverschool.api.GetRegisterId;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.NotificationExtraBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by imac on 17/2/16.
 * 极光推送自定义消息Receiver
 */

public class MyJPushReceiver extends BroadcastReceiver {

    private static final String TAG = "MyJPushReceiver";
    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null==notificationManager){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        Bundle bundle = intent.getExtras();
        Log.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.e(TAG, "JPush用户注册成功");
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            getRegisterId.getClientId(regId);
            if(TextUtils.isEmpty(BaseApplication.getInstance().getCoach_clientid())){
                Intent msgIntent = new Intent(SplashActivity.MESSAGE_REGISTRATION_ID);
                context.sendBroadcast(msgIntent);
            }
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 接收到推送下来的通知");
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);

            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            if("登陆失效".equals(title)){
                LogoutUtil.clearData(context);
                Intent failIntent = new Intent(context, DialogActivity.class);
                failIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(failIntent);
            }else {
                Intent intent1 = new Intent("readMsg");
                context.sendBroadcast(intent1);
            }
            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Gson gson = new Gson();
            NotificationExtraBean bean = gson.fromJson(extra, NotificationExtraBean.class);

            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            if("登陆失效".equals(title)){

            }else {
                if(TextUtils.equals("3",bean.getType()+"")){
                    Bundle mBundle = new Bundle();
                    mBundle.putString("detail_id",bean.getMessageId());
                    Intent mIntent = new Intent(context, ChatActivity.class);
                    mIntent.putExtras(mBundle);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mIntent,mBundle);
                    NoticeAdapter.markRead(bean.getMessageId());
                }else {
                    openNotification(context,bundle);
                }
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        } else {
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        String content = bundle.getString(JPushInterface.EXTRA_ALERT);
        String myValue = "";
        try {
            Log.e("推送得到的Json-->",bundle.toString());
            // extras json 格式 {"messageId":"37"}
            //在此处通过messageId 解析跳转到相关页面
            Intent mIntent = new Intent(context, MainActivity.class);
//            Bundle mainBundle = new Bundle();
//            bundle.putString("JPushData","1");
//            mIntent.putExtras(mainBundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            mIntent.putExtras(bundle);
            context.startActivity(mIntent);
        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
    }
}
