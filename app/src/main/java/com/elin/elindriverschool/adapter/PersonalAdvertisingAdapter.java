package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AdvertisingBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/4/24.
 */

public class PersonalAdvertisingAdapter extends BaseQuickAdapter<AdvertisingBean.DataListBean,BaseViewHolder> {

    Context context;
    public PersonalAdvertisingAdapter(List<AdvertisingBean.DataListBean> data, Context context) {
        super(R.layout.item_personal_advertising,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisingBean.DataListBean item) {
        ImageView imageView = helper.getView(R.id.iv_item_advertising_pic);
        Glide.with(context).load(item.getDynamic_img())
                .transform(new GlideRoundTransform(context))
                .error(R.drawable.img_default)
                .placeholder(R.drawable.img_default)
                .into(imageView);

        helper.setText(R.id.tv_item_advertising_title,item.getDynamic_title())
                .setText(R.id.tv_item_advertising_introduce,item.getIntro())
                .setText(R.id.tv_item_advertising_readcount,"阅读量 "+item.getRead_count())
                .setText(R.id.tv_item_advertising_sharecount,"转发量 "+item.getShare_count())
        ;
    }
}
