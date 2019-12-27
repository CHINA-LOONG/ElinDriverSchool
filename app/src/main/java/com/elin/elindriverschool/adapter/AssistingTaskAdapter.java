package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AssistingTaskBean;

import java.util.List;

/**
 * 作者：zzy 2018/4/25 11:31
 * 邮箱：zzyflute@163.com
 */
public class AssistingTaskAdapter extends BaseQuickAdapter<AssistingTaskBean.DataBean,BaseViewHolder> {
//    private Context context;
    public AssistingTaskAdapter(Context context,List<AssistingTaskBean.DataBean> data) {
        super(R.layout.item_assisting_task,data);
//        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AssistingTaskBean.DataBean item) {
        helper.setText(R.id.item_tv_name,item.getStu_name())
                .setText(R.id.item_tv_phone,item.getStu_phone())
                .addOnClickListener(R.id.item_tv_phone);
        ImageView ivPortrait = helper.getView(R.id.item_iv_portrait);
        TextView tvNum = helper.getView(R.id.item_tv_num);
        Glide.with(mContext).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivPortrait);
        if(item.getClick_num()!=null){
            helper.setText(R.id.item_tv_num,"助力值："+item.getClick_num());
        }else {
            helper.setText(R.id.item_tv_num,"助力值：暂未参加活动");
        }
    }
}
