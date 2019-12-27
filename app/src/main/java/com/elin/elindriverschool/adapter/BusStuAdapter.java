package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.BusStuBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/9/16.
 */

public class BusStuAdapter extends BaseQuickAdapter<BusStuBean.DataBean,BaseViewHolder> {
    Context context;
    public BusStuAdapter(Context context,List<BusStuBean.DataBean> data) {
        super(R.layout.item_bus_stu,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusStuBean.DataBean item) {
        ImageView ivStuPortrait = helper.getView(R.id.item_imv_stu_portrait);
        helper.setText(R.id.item_tv_stu_name,item.getStu_name())
                .setText(R.id.item_tv_stu_num,item.getStu_idnum())
                .setText(R.id.item_tv_coach_name,item.getStu_coach())
                .setText(R.id.item_tv_coach_phone,item.getCoach_phone())
                .addOnClickListener(R.id.item_imv_stu_phone)
                .addOnClickListener(R.id.item_imv_coach_phone);
        Glide.with(context).load(item.getStu_photo())
                .placeholder(R.drawable.icon_contact_default)
                .error(R.drawable.icon_contact_default)
                .transform(new GlideRoundTransform(context))
                .into(ivStuPortrait);
    }
}
