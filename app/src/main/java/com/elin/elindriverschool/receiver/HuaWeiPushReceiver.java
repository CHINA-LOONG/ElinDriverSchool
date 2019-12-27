package com.elin.elindriverschool.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.ChatActivity;
import com.elin.elindriverschool.activity.DialogActivity;
import com.elin.elindriverschool.activity.SplashActivity;
import com.elin.elindriverschool.adapter.NoticeAdapter;
import com.elin.elindriverschool.model.NotificationExtraBean;
import com.elin.elindriverschool.model.SigninInvalidBean;
import com.elin.elindriverschool.service.UpdateService;
import com.google.gson.Gson;
import com.huawei.android.pushagent.PushReceiver;
import com.huawei.android.pushagent.api.PushEventReceiver;

/**
 * Created by 17535 on 2017/10/24.
 */

public class HuaWeiPushReceiver extends PushEventReceiver {
    /*
    * 显示Push消息
    */
    private SharedPreferences sp_token,sp_phone,sp_idnum,sp_photo,sp_name,sp_schoolId;
    private SharedPreferences.Editor editor_token,editor_phone,editor_idnum,editor_photo,editor_name,editor_schoolId;
    private NotificationManager notificationManager;
    public static final String TAG = "HuaWeiPushReceiver";
    public void showPushMessage(int type, String msg) {

    }

    @Override
    public void onToken(Context context, String token, Bundle extras){
        String belongId = extras.getString("belongId");
        String content = "获取token和belongId成功，token = " + token + ",belongId = " + belongId;
        Intent msgIntent = new Intent(SplashActivity.MESSAGE_REGISTRATION_ID);
        msgIntent.putExtra("hw_token",token);
        context.sendBroadcast(msgIntent);
    }

    /**
     * 透传消息回调
     * @param context
     * @param msg
     * @param bundle
     * @return
     */
    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            String content = new String(msg, "UTF-8");
            SigninInvalidBean bean = new Gson().fromJson(content,SigninInvalidBean.class);
            if (null==notificationManager){
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(context)
                        .setContentTitle(bean.getTitle())
                        .setContentText(bean.getContent())
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)//只能使用纯alpha图层的图片进行设置
                        .setAutoCancel(true)//点击后，自动消失
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                notificationManager.notify(0,notification);
            }

            if(bean.getTitle().equals("登陆失效")){
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



    public void onEvent(Context context, PushReceiver.Event event, Bundle extras) {
        if (PushReceiver.Event.NOTIFICATION_OPENED.equals(event) || PushReceiver.Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            int notifyId = extras.getInt(PushReceiver.BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
            String content = extras.getString(PushReceiver.BOUND_KEY.pushMsgKey);
            Gson gson = new Gson();
            NotificationExtraBean bean = gson.fromJson(content, NotificationExtraBean.class);
            if(TextUtils.equals("3",bean.getType()+"")){
                Bundle mBundle = new Bundle();
                mBundle.putString("detail_id",bean.getMessageId());
                Intent mIntent = new Intent(context, ChatActivity.class);
                mIntent.putExtras(mBundle);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent,mBundle);
                NoticeAdapter.markRead(bean.getMessageId());
            }
            Log.d(TAG, "收到通知附加消息： " +content);
        }
        super.onEvent(context, event, extras);
    }
}
