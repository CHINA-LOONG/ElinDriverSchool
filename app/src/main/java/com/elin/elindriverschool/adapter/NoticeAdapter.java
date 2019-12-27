package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.ChatActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.NoticeBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.adapter.MessageListAdapter.MEDIA_TYPE_MARKDOWN;

/**
 * Created by 17535 on 2017/8/2.
 */

public class NoticeAdapter extends BaseQuickAdapter<NoticeBean.NoticeInfoBean,BaseViewHolder> {
    private Context context;
    public NoticeAdapter(List<NoticeBean.NoticeInfoBean> data,Context context) {
        super(R.layout.item_notice,data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NoticeBean.NoticeInfoBean item) {
//
        helper.setText(R.id.item_tv_notice_title, item.getTitle())
                .setText(R.id.item_tv_notice_date, item.getCreate_date().substring(0,10))
                .setText(R.id.item_tv_notice_content, item.getContent())
                .addOnClickListener(R.id.item_tv_check_stu);

        final TextView tvNoticeCount = helper.getView(R.id.item_tv_notice_count);
        TextView tvCheckStu = helper.getView(R.id.item_tv_check_stu);
        if(TextUtils.equals("0",item.getNo_read_count())){
            tvNoticeCount.setVisibility(View.GONE);
        }else {
            tvNoticeCount.setVisibility(View.VISIBLE);
             tvNoticeCount.setText(item.getNo_read_count());
        }
        LinearLayout layout = helper.getView(R.id.item_ll_notice);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("detail_id",item.getId());
                bundle.putString("is_del",item.getIs_del());
                Intent intent = new Intent(mContext,ChatActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent,bundle);
                markRead(item.getId());
                item.setNo_read_count("0");
                tvNoticeCount.setVisibility(View.GONE);
            }
        });
        switch (item.getHas_stu()){
            case "0":
                tvCheckStu.setVisibility(View.GONE);
                break;
            case "1":
                tvCheckStu.setVisibility(View.VISIBLE);
                break;
        }

    }
    public static void markRead(String detailId){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("detail_id", detailId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.READ_NOTICE)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance().getApplicationContext()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    call.cancel();
                }
            }
        });
    }
}
