package com.elin.elindriverschool.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.ChatBean;

import java.util.List;

/**
 * Created by 17535 on 2017/8/2.
 */

public class ChatAdapter extends BaseQuickAdapter<ChatBean.DataBean,BaseViewHolder> {

    public ChatAdapter(List<ChatBean.DataBean> data) {
        super(R.layout.item_chat, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatBean.DataBean item) {

        TextView tvPersonLeft = helper.getView(R.id.item_person_left);
        TextView tvPersonRight = helper.getView(R.id.item_person_right);
        LinearLayout llLeft = helper.getView(R.id.item_ll_left);
        RelativeLayout rlRight = helper.getView(R.id.item_rl_right);
        if(TextUtils.equals("0",item.getReply_person())){
            tvPersonRight.setText("：我");
            helper.setText(R.id.item_content_right,item.getReply_content());
            llLeft.setVisibility(View.GONE);
            rlRight.setVisibility(View.VISIBLE);
        }else {
            tvPersonLeft.setText(item.getReply_person()+"：");
            helper.setText(R.id.item_content_left,item.getReply_content());
            llLeft.setVisibility(View.VISIBLE);
            rlRight.setVisibility(View.GONE);
        }
    }
}
