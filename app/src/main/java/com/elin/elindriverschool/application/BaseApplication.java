package com.elin.elindriverschool.application;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.BaseActivity;
import com.elin.elindriverschool.util.Cockroach;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by imac on 16/12/29.
 */

public class BaseApplication extends Application {
    public static BaseApplication application;
    private String token,coachPhone,coachIdNum,coachPhoto,coachName,coach_clientid,schoolId;
    private SharedPreferences sp_token,sp_phone,sp_idnum,sp_photo,sp_name,sp_clientId,sp_schoolId;
    private List<BaseActivity> activityList = new LinkedList<BaseActivity>();
    private String messageCount;//消息数量
    private String noticeCount;//通知数量
    private String startDay;//最小的日期（预约培训）
    private String trainSub;//教练科目
    public static final String MI_APP_ID = "2882303761517629323";
    public static final String MI_APP_KEY = "5901762941323";

    public String getTrainSub() {
        return trainSub;
    }

    public void setTrainSub(String trainSub) {
        this.trainSub = trainSub;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public String getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(String noticeCount) {
        this.noticeCount = noticeCount;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);
        UMShareAPI.get(this);

        switch (Build.MANUFACTURER){
            case "Xiaomi":
                if (shouldInit()) {
                    MiPushClient.registerPush(this, MI_APP_ID, MI_APP_KEY);
                }
                break;
            default:
                //初始化极光推送
                JPushInterface.setDebugMode(true);
                JPushInterface.init(this);
                BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
                builder.statusBarDrawable = R.mipmap.ic_launcher;
                builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                        | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
                builder.notificationDefaults = Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
                JPushInterface.setPushNotificationBuilder(1, builder);
                Log.e("极光推送", "jiguang");
                break;
        }

        application = this;
        sp_token = getSharedPreferences("sp_token",this.MODE_PRIVATE);
        sp_phone = getSharedPreferences("sp_phone",this.MODE_PRIVATE);
        sp_idnum = getSharedPreferences("sp_idnum",this.MODE_PRIVATE);
        sp_photo = getSharedPreferences("sp_photo",this.MODE_PRIVATE);
        sp_name = getSharedPreferences("sp_name",this.MODE_PRIVATE);
        sp_clientId = getSharedPreferences("sp_clientId",this.MODE_PRIVATE);
        sp_schoolId = getSharedPreferences("sp_schoolId",this.MODE_PRIVATE);

        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("Cockroach", thread + "\n" + throwable.toString());
                            throwable.printStackTrace();
                            //     Toast.makeText(App.this, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {
                        }
                    }
                });
            }
        });

        // 卸载代码
//        Cockroach.uninstall();
    }
    {
//        PlatformConfig.setWeixin("wx19ba7b43d3417881", "11134307926572193e03f196a72717a8");//易学易驾教练端
        PlatformConfig.setWeixin("wxb23896de506daf57", "11134307926572193e03f196a72717a8");//驾考之星
        PlatformConfig.setQQZone("1106122862", "4bc5EzH6OScTp1UI");
    }
    public boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
    public String getSchoolId() {
        if (sp_schoolId!=null){
            return sp_schoolId.getString("sp_schoolId","");
        }else{
            return "";
        }
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
        SharedPreferences.Editor editor_schoold = sp_schoolId.edit();
        editor_schoold.putString("sp_schoolId",this.schoolId);
        editor_schoold.apply();
    }

    public String getCoach_clientid() {
        if (sp_clientId!=null){
            return sp_clientId.getString("client_id","");
        }else{
            return "";
        }
    }

    public void setCoach_clientid(String coach_clientid) {
        this.coach_clientid = coach_clientid;
        SharedPreferences.Editor editor_clienId = sp_clientId.edit();
        editor_clienId.putString("client_id",this.coach_clientid);
        editor_clienId.apply();
    }

    public String getToken() {
        if (sp_token!=null){
            return sp_token.getString("token","");
        }else {
            return "";
        }
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences.Editor editor_token = sp_token.edit();
        editor_token.putString("token",this.token);
        editor_token.apply();
    }

    public String getCoachIdNum() {
        if (sp_idnum!=null){
            return sp_idnum.getString("coachIdNum","");
        }else{
            return "";
        }
    }

    public void setCoachIdNum(String coachIdNum) {
        this.coachIdNum = coachIdNum;
        SharedPreferences.Editor editor_IdNum = sp_idnum.edit();
        editor_IdNum.putString("coachIdNum",this.coachIdNum);
        editor_IdNum.apply();
    }

    public String getCoachName() {
        if (sp_name!=null){
            return sp_name.getString("coachName","");
        }else{
            return "";
        }
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
        SharedPreferences.Editor editor_name = sp_name.edit();
        editor_name.putString("coachName",this.coachName);
        editor_name.apply();
    }

    public String getCoachPhone() {
        if (sp_phone!=null){
            return sp_phone.getString("coachPhone","");
        }else{
            return "";
        }
    }

    public void setCoachPhone(String coachPhone) {
        this.coachPhone = coachPhone;
        SharedPreferences.Editor editor_phone = sp_phone.edit();
        editor_phone.putString("coachPhone",this.coachPhone);
        editor_phone.apply();
    }

    public String getCoachPhoto() {
        if (sp_photo!=null){
            return sp_photo.getString("coachPhoto","");
        }else{
            return "";
        }
    }

    public void setCoachPhoto(String coachPhoto) {
        this.coachPhoto = coachPhoto;
        SharedPreferences.Editor editor_photo = sp_photo.edit();
        editor_photo.putString("coachPhoto",this.coachPhoto);
        editor_photo.apply();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public synchronized static BaseApplication getInstance() {
        if (null == application) {
            application = new BaseApplication();
        }
        return application;
    }

    /**
     * addActivy
     * @param activity
     */
    public void addActivity(BaseActivity activity){
        activityList.add(activity);
    }
    public void exitActivity(){
        try {
            for (BaseActivity activity:activityList){
                if (activity!=null){
                    activity.finish();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.exit(0);
        }
    }

}
