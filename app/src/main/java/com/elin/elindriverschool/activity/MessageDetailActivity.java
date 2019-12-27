package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MessageBean;
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
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessageDetailActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_message_detail)
    TextView tvMessageDetail;
    @Bind(R.id.tv_date_detail)
    TextView tvDateDetail;
    @Bind(R.id.tv_title_detail)
    TextView tvTitleDetail;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    @Bind(R.id.tv_delete_message)
    TextView tvDeleteMessage;

    private int position;
    private String responseJson;
    private String reminderId,date,title,content;
    MessageBean.DataListBean bean;
    MessageBean.DataListBean dataListBean;
    private MyProgressDialog myProgressDialog;
    private AlertDialog.Builder builder;
    private LocalBroadcastManager broadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        broadcastManager = LocalBroadcastManager.getInstance(this);
        myProgressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        tvCusTitleName.setText("消息详情");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        bean = bundle.getParcelable("messageDetail");
        reminderId = bundle.getString("reminderId");
        date = bundle.getString("date");
        content = bundle.getString("content");
        title = bundle.getString("title");
        position = intent.getIntExtra("position",0);
        if(position!=-1){
            tvMessageDetail.setText(content);
            tvDateDetail.setText(date);
            tvTitleDetail.setText(title);
        }else {
            position=0;
            String tilte = intent.getExtras().getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = intent.getExtras().getString(JPushInterface.EXTRA_ALERT);
            reminderId= intent.getExtras().getString(JPushInterface.EXTRA_MSG_ID);
            tvTitleDetail.setText(tilte);
            tvMessageDetail.setText(content);
        }
    }

    @OnClick({R.id.imv_cus_title_back,R.id.tv_delete_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_delete_message:
                showDialog();
                break;

        }
    }

    private void showDialog() {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("操作提示");
        builder.setMessage("是否删除该条消息?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setRead(reminderId);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void setRead(String reminder_id) {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("reminder_id", reminder_id);
        params.put("del_flag", "1");
        params.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.READ_MESSAGES)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ToastUtils.ToastMessage(MessageDetailActivity.this,"删除成功");
//                    Intent intent = new Intent();
//                    intent.setAction("MessageDetailActivity.deleteSuccess");
//                    intent.putExtra("position",position);
//                    broadcastManager.sendBroadcast(intent);
                    setResult(RESULT_OK);
                    finish();
                    break;
            }
        }
    };
}
