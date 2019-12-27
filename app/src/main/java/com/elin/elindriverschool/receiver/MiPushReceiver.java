package com.elin.elindriverschool.receiver;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
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
import com.google.gson.Gson;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class MiPushReceiver extends PushMessageReceiver {
    private SharedPreferences sp_token,sp_phone,sp_idnum,sp_photo,sp_name,sp_schoolId;
    private SharedPreferences.Editor editor_token,editor_phone,editor_idnum,editor_photo,editor_name,editor_schoolId;


    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        Log.e("透传消息",message.toString());
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {//点击消息
        String extra = message.getExtra().get("extras");
        Gson gson = new Gson();
        NotificationExtraBean bean = gson.fromJson(extra, NotificationExtraBean.class);

        if("登陆失效".equals(message.getTitle())){

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
                Intent mIntent = new Intent(context, MainActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            }
        }
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        if("登陆失效".equals(message.getTitle())){
            sp_token = context.getSharedPreferences("sp_token", Context.MODE_PRIVATE);
            editor_token = sp_token.edit();
            sp_idnum = context.getSharedPreferences("sp_idnum", Context.MODE_PRIVATE);
            editor_idnum = sp_idnum.edit();
            sp_name = context.getSharedPreferences("sp_name", Context.MODE_PRIVATE);
            editor_name = sp_name.edit();
            sp_phone = context.getSharedPreferences("sp_phone", Context.MODE_PRIVATE);
            editor_phone = sp_phone.edit();
            sp_photo = context.getSharedPreferences("sp_photo", Context.MODE_PRIVATE);
            editor_photo = sp_photo.edit();
            sp_schoolId = context.getSharedPreferences("sp_schoolId", Context.MODE_PRIVATE);
            editor_schoolId = sp_schoolId.edit();
            editor_token.clear();
            editor_photo.clear();
            editor_phone.clear();
            editor_name.clear();
            editor_idnum.clear();
            editor_schoolId.clear();
            editor_token.commit();
            editor_idnum.commit();
            editor_name.commit();
            editor_phone.commit();
            editor_photo.commit();
            editor_schoolId.commit();
            Intent failIntent = new Intent(context, DialogActivity.class);
            failIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(failIntent);
        }else {
            Intent intent1 = new Intent("readMsg");
            context.sendBroadcast(intent1);
        }
        Log.e("通知消息",message.toString());
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {  //注册成功
                if(TextUtils.isEmpty(BaseApplication.getInstance().getCoach_clientid())){
                    Intent msgIntent = new Intent(SplashActivity.MESSAGE_REGISTRATION_ID);
                    context.sendBroadcast(msgIntent);
                }
                Log.e("id",cmdArg1);
            } else {

            }
        }

    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {

    }

}
