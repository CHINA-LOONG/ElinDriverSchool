package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.StudentPreviewBean;

import java.util.List;

/**
 * Created by 17535 on 2017/9/7.
 */

public class StudentPreviewInsideAdapter extends BaseQuickAdapter<StudentPreviewBean.DataBean.StudentsBean,BaseViewHolder>{
    Context context;
    public StudentPreviewInsideAdapter(Context context,List<StudentPreviewBean.DataBean.StudentsBean> data) {
        super(R.layout.student_item_1,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StudentPreviewBean.DataBean.StudentsBean item) {

        helper.getView(R.id.tv_stu_item_1_car_type).setVisibility(View.GONE);
        helper.setText(R.id.tv_stu_item_1_name,item.getStu_name())
                .setText(R.id.tv_stu_item_1_id_num,item.getStu_idnum())
                .setText(R.id.tv_stu_item_1_sign_up_date,"申请时间：" + item.getStu_reg_date())
                .addOnClickListener(R.id.tv_stu_item_1_phone);
        ImageView ivHead = helper.getView(R.id.imv_stu_item_1_head);
        Glide.with(mContext).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivHead);
    }
}
