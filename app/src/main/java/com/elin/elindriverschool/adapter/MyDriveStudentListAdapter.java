package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.List;

/**
 * Created by imac on 17/1/2.
 */

public class MyDriveStudentListAdapter extends BaseQuickAdapter<MyDriveStudentBean.DataListBean,BaseViewHolder> {

    private Context mContext;
    public MyDriveStudentListAdapter(List<MyDriveStudentBean.DataListBean> data, Context mContext) {
        super(R.layout.student_item_1,data);
        this.mContext = mContext;
    }


    @Override
    protected void convert(BaseViewHolder helper, final MyDriveStudentBean.DataListBean item) {
        helper.setText(R.id.tv_stu_item_1_name,item.getStu_name())
                .setText(R.id.tv_stu_item_1_id_num,item.getStu_idnum())
                .setText(R.id.tv_stu_item_1_sign_up_date,"申请时间：" + item.getStu_reg_date())
                .setText(R.id.tv_stu_item_1_car_type,item.getStu_cartype())
                .addOnClickListener(R.id.tv_stu_item_1_phone);
        ImageView ivHead = helper.getView(R.id.imv_stu_item_1_head);
        Glide.with(mContext).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivHead);
//
    }
}
