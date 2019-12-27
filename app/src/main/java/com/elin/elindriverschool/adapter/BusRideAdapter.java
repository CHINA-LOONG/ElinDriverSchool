package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.BusRideBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/8/16.
 */

public class BusRideAdapter extends BaseQuickAdapter<BusRideBean.DataBean,BaseViewHolder> {
    Context context;
    public BusRideAdapter(Context context,List<BusRideBean.DataBean> data) {
        super(R.layout.item_bus_list,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusRideBean.DataBean item) {
        ImageView ivPortrait = helper.getView(R.id.item_iv_busride_portrait);
        TextView ivSub = helper.getView(R.id.item_tv_busride_sub);
        helper.setText(R.id.item_tv_busride_name,item.getOrder_bus_num())
                .setText(R.id.item_tv_busride_date,item.getOrder_bus_date())
                .setText(R.id.item_tv_busride_coach,item.getOrder_bus_person())
                .setText(R.id.item_tv_busride_addr,item.getOrder_bus_addr())
                .setText(R.id.item_tv_busride_count,item.getOrder_stu_count())
                .addOnClickListener(R.id.item_iv_busride_phone);
        Glide.with(mContext).load("")
                .placeholder(R.drawable.icon_msg_item)
                .error(R.drawable.icon_msg_item)
                .transform(new GlideRoundTransform(context))
                .into(ivPortrait);
        switch (item.getOrder_sub()){
            case "1":
                ivSub.setText("科目一");
                break;
            case "2":
                ivSub.setText("科目二");
                break;
            case "3":
                ivSub.setText("科目三");
                break;
            case "4":
                ivSub.setText("科目四");
                break;
            case "5":
                ivSub.setText("支队建档");
                break;
        }
    }
}
