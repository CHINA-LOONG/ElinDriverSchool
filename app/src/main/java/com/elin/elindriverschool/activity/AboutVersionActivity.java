package com.elin.elindriverschool.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.UpdateVetsionBean;
import com.elin.elindriverschool.service.UpdateService;
import com.elin.elindriverschool.sharedpreferences.PreferenceManager;
import com.elin.elindriverschool.util.AppSPUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

public class AboutVersionActivity extends BaseActivity {

    @Bind(R.id.id_tv_version_name)
    TextView idTvVersionName;
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.img_about_us)
    ImageView imgAboutUs;
    @Bind(R.id.btn_check_version)
    Button btnCheckVersion;
    @Bind(R.id.id_tv_update_content)
    TextView idTvUpdateContent;

    private PackageInfo info;
    int oldVersionCode, newVersionCode;
    private String responseJson, versionJson, currentVersion, forceupdate;
    private Gson gson = new Gson();
    Dialog updateDialog;
    private MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_version);
        ButterKnife.bind(this);
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        tvCusTitleName.setText("关于我们");
        PackageManager manager = getPackageManager();
        try {
            info = manager.getPackageInfo(getPackageName(), 0);
            currentVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        idTvVersionName.setText("驾考之星  " + currentVersion);
        idTvUpdateContent.setText((String)AppSPUtil.get(this, PreferenceManager.UPDATE_CONTENT,""));
    }

    @OnClick({R.id.imv_cus_title_back, R.id.btn_check_version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.btn_check_version:
                updateVersion();
                break;
        }
    }

    private void updateVersion() {
        myProgressDialog.show();
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    try {
                        PackageManager manager = getPackageManager();
                        PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
                        currentVersion = info.versionName;
                        oldVersionCode = info.versionCode;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    final UpdateVetsionBean info = gson.fromJson(versionJson, UpdateVetsionBean.class);
                    newVersionCode = Integer.parseInt(info.getVersioncode());
                    forceupdate = info.getForceupdate();
                    if (oldVersionCode < newVersionCode) {
                        updateDialog = new Dialog(AboutVersionActivity.this, R.style.MyDialog);
                        View v = LayoutInflater.from(AboutVersionActivity.this).inflate(R.layout.dialog_version_update, null);
                        TextView tvDialogTitle = (TextView) v.findViewById(R.id.tv_dialog_title);
                        TextView versionDescrip = (TextView) v.findViewById(R.id.tv_updatedesdesc);
                        TextView versionNo = (TextView) v.findViewById(R.id.tv_update_version);
                        TextView versionDate = (TextView) v.findViewById(R.id.tv_release_time);
                        Button update = (Button) v.findViewById(R.id.btn_update);
                        Button cancle = (Button) v.findViewById(R.id.btn_cancle_update);
                        update.setText("更新");
                        tvDialogTitle.setText("版本更新");
                        versionDescrip.setText("版本描述：" + info.getDescription());
                        versionNo.setText("版本号：" + info.getVersionname());
                        String date = info.getDate();
                        versionDate.setText("发布时间:" + date);
                        updateDialog.setContentView(v);
                        updateDialog.setCanceledOnTouchOutside(false);
                        WindowManager m = getWindowManager();
                        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                        WindowManager.LayoutParams p = updateDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
                        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.8
                        updateDialog.getWindow().setAttributes(p);
                        updateDialog.show();
                        if ("2".equals(forceupdate)) {
                            cancle.setVisibility(View.VISIBLE);
                            //对话框确定
                            update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(AboutVersionActivity.this, UpdateService.class);
                                    intent.putExtra("loadurl", info.getLoadurl());
                                    startService(intent);
                                    updateDialog.dismiss();
                                }
                            });
                            //取消
                            cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    updateDialog.dismiss();
                                }
                            });
                        } else if ("1".equals(forceupdate)) {
                            cancle.setVisibility(View.GONE);
                            updateDialog.setCancelable(false);
                            //对话框确定
                            update.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(AboutVersionActivity.this, UpdateService.class);
                                    intent.putExtra("loadurl", info.getLoadurl());
                                    startService(intent);
                                    updateDialog.dismiss();
                                }
                            });

                        }

                    } else {
                        if (myProgressDialog != null && myProgressDialog.isShowing()) {
                            myProgressDialog.dismiss();
                        }
                        ToastUtils.ToastMessage(AboutVersionActivity.this, "当前已是最新版本");
                    }
                    break;
                case 3:
                    ToastUtils.ToastMessage(AboutVersionActivity.this, "请检查网络连接");
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
