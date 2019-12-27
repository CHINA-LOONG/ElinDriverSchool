package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.PreSignupStudentBean;

import java.util.List;

/**
 * Created by 17535 on 2017/8/2.
 */

public class PreSignUpAdapter extends BaseQuickAdapter<PreSignupStudentBean.PreStuListBean,BaseViewHolder> {
    Context context;
    public PreSignUpAdapter(List<PreSignupStudentBean.PreStuListBean> data,Context context) {
        super(R.layout.item_presignup,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PreSignupStudentBean.PreStuListBean item) {
        helper.setText(R.id.item_tv_name,item.getStu_name())
                .setText(R.id.item_tv_id_number,item.getStu_idnum())
                .setText(R.id.item_tv_class,item.getStu_class())
                .setText(R.id.item_tv_cartype,item.getStu_cartype())
                .setText(R.id.item_tv_signup_time,item.getStu_reg_date())
                .setText(R.id.item_tv_sex,item.getStu_sex())
                .addOnClickListener(R.id.item_iv_phone)
                .addOnClickListener(R.id.item_tv_del_stu);
        ImageView ivPortrait = helper.getView(R.id.item_iv_portrait);
        TextView tvRemark = helper.getView(R.id.item_tv_remark);
        TextView tvSex = helper.getView(R.id.item_tv_sex);
        if(TextUtils.isEmpty(item.getStu_remarks())){
            tvRemark.setText("无");
        }else {
            tvRemark.setText(item.getStu_remarks());
        }
        if(TextUtils.equals("1",item.getStu_sex())){
            tvSex.setText("男");
        }else if (TextUtils.equals("2",item.getStu_sex())){
            tvSex.setText("女");
        }
        Glide.with(mContext).load(R.drawable.icon_user_head_default)
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivPortrait);
    }
}
