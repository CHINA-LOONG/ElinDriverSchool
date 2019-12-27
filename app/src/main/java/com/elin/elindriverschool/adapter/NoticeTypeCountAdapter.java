package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.NoticeTypeCountBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/9/9.
 */

public class NoticeTypeCountAdapter extends BaseQuickAdapter<NoticeTypeCountBean.DataBean,BaseViewHolder> {
    Context context;
    boolean flag = true;
    public NoticeTypeCountAdapter(Context context,List<NoticeTypeCountBean.DataBean> data) {
        super(R.layout.item_msg_top,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeTypeCountBean.DataBean item) {
        TextView tvMsgFlag = helper.getView(R.id.item_tv_message_flag);
        
        ImageView imgMsgIcon = helper.getView(R.id.item_img_message_icon);
        helper.setText(R.id.item_tv_message_name,item.getName());
        if(helper.getPosition()==0){
            Glide.with(context).load(item.getIcon())
                    .placeholder(R.drawable.icon_msg_xiaoxi)
                    .error(R.drawable.icon_msg_xiaoxi)
                    .transform(new GlideRoundTransform(context))
                    .into(imgMsgIcon);
        }else {
            Glide.with(context).load(item.getIcon())
                    .placeholder(R.drawable.icon_msg_gonggao)
                    .error(R.drawable.icon_msg_gonggao)
                    .transform(new GlideRoundTransform(context))
                    .into(imgMsgIcon);
        }

        if(TextUtils.equals("0",item.getCount())){
            tvMsgFlag.setVisibility(View.GONE);
        }else {
            tvMsgFlag.setVisibility(View.VISIBLE);
            if(Integer.parseInt(item.getCount())>99){
                tvMsgFlag.setText("99+");
            }else {
                tvMsgFlag.setText(item.getCount());
            }

        }

//        if(helper.getPosition()==0&&flag){//防止第二次请求加载背景冲突
//            RelativeLayout llMsgTop = helper.getView(R.id.item_ll_msg_top);
//            llMsgTop.setBackgroundColor(context.getResources().getColor(R.color.line_color_gray));
//            flag = false;
//        }

    }
}
