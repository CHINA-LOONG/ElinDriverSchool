package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.MyDriveStudentActivity;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.MyDriveStudentBean;
import com.elin.elindriverschool.model.StudentPreviewBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17535 on 2017/9/5.
 */

public class StudentPreviewAdapter extends BaseQuickAdapter<StudentPreviewBean.DataBean.StudentsBean,BaseViewHolder> {
    Context context;
    StudentPreviewInsideAdapter adapter;
    public StudentPreviewAdapter(Context context,List<StudentPreviewBean.DataBean.StudentsBean> data) {
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
