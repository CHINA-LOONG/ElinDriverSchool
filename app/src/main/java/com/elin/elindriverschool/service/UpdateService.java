package com.elin.elindriverschool.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.elin.elindriverschool.MainActivity;
import com.elin.elindriverschool.R;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.text.DecimalFormat;

public class UpdateService extends Service {
    private String down_url;
    private static final int DOWN_OK = 1; // 下载完成
    private static final int DOWN_ERROR = 0;
    private NotificationManager manager;
    private Intent updateIntent;
    private PendingIntent pendingIntent;
    private String updateFile;
    private NotificationCompat.Builder builder;
    private Intent intent = new Intent("update");
    /***
     * 更新UI
     */
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_OK:
                    // 下载完成，点击安装
                    Notification notification = builder.build();
                    notification.contentIntent = pendingIntent;
                    notification.flags |= Notification.FLAG_AUTO_CANCEL;
                    contentView.setTextViewText(R.id.textView, "下载完成");
                    builder.setTicker("软件下载完成");
                    manager.notify(0, builder.build());
                    stopService(updateIntent);
                    break;
                case DOWN_ERROR:
                    contentView.setTextViewText(R.id.textView, "下载失败");
                    contentView.setProgressBar(R.id.progressBar,0,0,false);
                    builder.setTicker("软件下载失败");
                    manager.notify(0, builder.build());
                    break;
                default:
                    stopService(updateIntent);
                    break;
            }
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            try {
                down_url = intent.getStringExtra("loadurl");
                // 创建文件
                File file = getFile(this, "驾考之星.apk");
                updateFile = file.getAbsolutePath();
                // 开始下载
                downloadUpdateFile(down_url, file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /***
     * 创建通知栏
     */
    RemoteViews contentView;


    /***
     * 下载文件
     */
    public void downloadUpdateFile(String path, String file) throws Exception {

        RequestParams params = new RequestParams(path);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(file);
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                Toast.makeText(x.app(), "开始下载", Toast.LENGTH_SHORT).show();
                manager = (NotificationManager) getSystemService(UpdateService.NOTIFICATION_SERVICE);
                builder = new NotificationCompat.Builder(UpdateService.this);
                //此设置在界面没有体现，必须要写的原因是RemoteViews显示
                builder.setSmallIcon(R.mipmap.ic_launcher);
                // 这个参数是通知提示闪出来的值.
                builder.setTicker("驾考之星开始下载");

                /***
                 * 在这里我们用自定的view来显示Notification
                 */
                contentView = new RemoteViews(getPackageName(), R.layout.notification_layout);
                contentView.setTextViewText(R.id.textView, "正在下载");
                contentView.setProgressBar(R.id.progressBar, 100, 0, false);
                //设置自定义的view
                builder.setContent(contentView);
                updateIntent = new Intent(UpdateService.this, MainActivity.class);
                updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(UpdateService.this, 0, updateIntent, 0);
                manager.notify(0,builder.build());
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                double x_double = current * 1.0;
                double tempresult = x_double / total;
                DecimalFormat df1 = new DecimalFormat("0.00"); // ##.00%
                // 百分比格式，后面不足2位的用0补齐
                String result = df1.format(tempresult);
                //发送Action为com.example.communication.RECEIVER的广播
                intent.putExtra("progress", result);
                sendBroadcast(intent);
                contentView.setTextViewText(R.id.textView, "下载进度"+(int) (Float.parseFloat(result) * 100) + "%");
                contentView.setProgressBar(R.id.progressBar, 100, (int) (Float.parseFloat(result) * 100), false);
                manager.notify(0, builder.build());
            }

            @Override
            public void onSuccess(File result) {

                contentView.setTextViewText(R.id.textView, "下载完成");
                builder.setTicker("软件下载完成");
                manager.notify(0, builder.build());
                File mFile = new File(updateFile);
                if(mFile.getName().endsWith(".apk")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    // 由于没有在Activity环境下启动Activity,设置下面的标签
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
                        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                        Uri apkUri = FileProvider.getUriForFile(UpdateService.this, "com.elin.elindriverschool.fileprovider", mFile);
                        //添加这一句表示对目标应用临时授权该Uri所代表的文件
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    }else{
                        intent.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
                    }
                    startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                stopService(updateIntent);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message message = handler.obtainMessage();
                message.what = DOWN_ERROR;
                handler.sendMessage(message);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static File getFile(Context context, String name) {
        String temp_file = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //sd根目录下Android/data/<包名>
            if (context.getExternalCacheDir()!=null){
                temp_file = context.getExternalCacheDir().getAbsolutePath();
            }else {
                temp_file = Environment.getExternalStorageDirectory().getPath();
            }

        } else {
            temp_file = context.getCacheDir().getAbsolutePath();
        }
        return new File(temp_file, name);
    }
}
