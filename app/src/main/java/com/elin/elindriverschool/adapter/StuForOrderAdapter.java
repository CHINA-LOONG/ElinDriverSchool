package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.StuForOrderBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/10/30.
 */

public class StuForOrderAdapter extends BaseQuickAdapter<StuForOrderBean.DataBean,BaseViewHolder> {
    Context context;
    public StuForOrderAdapter(List<StuForOrderBean.DataBean> data,Context context) {
        super(R.layout.item_statistics_test,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StuForOrderBean.DataBean item) {
        ImageView ivStuPortrait = helper.getView(R.id.item_statistics_head);
        TextView tvSub = helper.getView(R.id.item_tv_statistics_sub);

        helper.setText(R.id.item_statistics_name,item.getOrder_name())
                .setText(R.id.item_tv_statistics_num,item.getOrder_idnum())
                .setText(R.id.item_tv_statistics_coach,item.getOrder_coach())
                .setText(R.id.item_tv_statistics_date,"考试时间："+item.getOrder_date())
                .setText(R.id.item_tv_statistics_site,item.getOrder_site())
                .addOnClickListener(R.id.item_rl_statistics_phone);
        Glide.with(context).load(item.getStu_photo())
                .placeholder(R.drawable.icon_contact_default)
                .error(R.drawable.icon_contact_default)
                .transform(new GlideRoundTransform(context))
                .into(ivStuPortrait);
        switch (item.getOrder_sub()){
            case "1":
                tvSub.setText("科目一");
                break;
            case "2":
                tvSub.setText("科目二");
                break;
            case "3":
                tvSub.setText("科目三");
                break;
            case "4":
                tvSub.setText("科目四");
                break;
                case "5":
                tvSub.setText("支队建档");
                break;
        }
    }
}
