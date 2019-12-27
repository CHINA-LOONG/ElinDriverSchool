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

public class CompanyAdvertisingAdapter extends BaseQuickAdapter<AdvertisingBean.DataListBean,BaseViewHolder> {

    Context context;
    public CompanyAdvertisingAdapter(List<AdvertisingBean.DataListBean> data, Context context) {
        super(R.layout.item_company_advertising,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AdvertisingBean.DataListBean item) {
        ImageView imageView = helper.getView(R.id.iv_company_advertising_pic);
        Glide.with(context).load(item.getDynamic_img())
                .transform(new GlideRoundTransform(context))
                .into(imageView);
        helper.setText(R.id.tv_company_advertising_title,item.getDynamic_title());

    }
}
