package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.BusOrganizerBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/9/16.
 */

public class BusOrganizerAdapter extends BaseQuickAdapter<BusOrganizerBean.DataBean,BaseViewHolder> {
    Context context;
    public BusOrganizerAdapter(Context context,List<BusOrganizerBean.DataBean> data) {
        super(R.layout.contact_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BusOrganizerBean.DataBean item) {
        ImageView ivAvater = helper.getView(R.id.ivAvatar_contact);
        helper.setText(R.id.tvContact_name,item.getUser_name())
            .addOnClickListener(R.id.imv_contact_phone);
        Glide.with(mContext).load(item.getUser_photo())
                .placeholder(R.drawable.icon_contact_default)
                .error(R.drawable.icon_contact_default)
                .transform(new GlideRoundTransform(context))
                .into(ivAvater);
    }
}
