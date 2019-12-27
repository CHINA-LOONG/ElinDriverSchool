package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.MessageDetailActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MessageBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by imac on 17/2/5.
 */

public class MessageListAdapter extends BaseQuickAdapter<MessageBean.DataListBean,BaseViewHolder> {

    private Context mContext;
//    private ArrayList<MessageBean> messageBeanArrayList;
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");
    public MessageListAdapter(List<MessageBean.DataListBean> data,Context mContext) {
        super(R.layout.msg_item_layout,data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MessageBean.DataListBean dataListBean) {
        String date = dataListBean.getReminder_date();
        int index = date.indexOf(" ");
        helper.setText(R.id.item_tv_message_title,dataListBean.getReminder_title())
                .setText(R.id.item_tv_message_date, date.substring(0,index))
                .setText(R.id.item_tv_message_content,dataListBean.getReminder_content());

        final TextView tvTitle = helper.getView(R.id.item_tv_message_title);
        final TextView tvDate = helper.getView(R.id.item_tv_message_date);
        final TextView tvContent = helper.getView(R.id.item_tv_message_content);
        LinearLayout layout = helper.getView(R.id.item_ll_message);
//        layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putString("reminderId",dataListBean.getReminder_id());
//                bundle.putString("date",dataListBean.getReminder_date());
//                bundle.putString("content",dataListBean.getReminder_content());
//                bundle.putString("title",dataListBean.getReminder_title());
//                bundle.putInt("position",helper.getPosition());
//                Intent intent = new Intent(mContext,MessageDetailActivity.class);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent,bundle);
//                if(2==dataListBean.getReminder_status()){
//                    markRead(dataListBean.getReminder_id());
//                }
//                dataListBean.setReminder_status(1);
//                tvTitle.setTextColor(Color.LTGRAY);
//                tvDate.setTextColor(Color.LTGRAY);
//                tvContent.setTextColor(Color.LTGRAY);
//            }
//        });
        if(1==(dataListBean.getReminder_status())){
            tvTitle.setTextColor(Color.LTGRAY);
            tvDate.setTextColor(Color.LTGRAY);
            tvContent.setTextColor(Color.LTGRAY);
        }else if(2==(dataListBean.getReminder_status())){
            tvTitle.setTextColor(Color.DKGRAY);
            tvDate.setTextColor(Color.DKGRAY);
            tvContent.setTextColor(Color.DKGRAY);
        }
    }


//    public void markRead(String reminderId){
//        Map<String, String> params = new HashMap<>();
//        params.put("token", BaseApplication.getInstance().getToken());
//        params.put("reminder_id", reminderId);
//        params.put("del_flag","0");
//        params.put("school_id", BaseApplication.getInstance().getSchoolId());
//        Request request = new Request.Builder()
//                .url(Constant.ServerURL + Constant.READ_MESSAGES)
//                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
//                .build();
//        Call call = new MyOkHttpClient(mContext).newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    call.cancel();
//                }
//            }
//        });
//    }
}
