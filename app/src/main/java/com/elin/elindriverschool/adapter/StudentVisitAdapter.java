package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.StudentVisitBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.List;

/**
 * Created by 17535 on 2017/10/31.
 */

public class StudentVisitAdapter extends BaseQuickAdapter<StudentVisitBean.DataBean,BaseViewHolder> {
    Context context;
    public StudentVisitAdapter(Context context,List<StudentVisitBean.DataBean> data) {
        super(R.layout.item_student_visit,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, StudentVisitBean.DataBean item) {
        ImageView ivStuPortrait = helper.getView(R.id.imv_visit_portrait);
        TextView tvSub = helper.getView(R.id.tv_visit_step);
        TextView tvReturnVisit = helper.getView(R.id.tv_return_visit);
        TextView tvVisitStatus = helper.getView(R.id.tv_visit_status);
        TextView tvVisitResult = helper.getView(R.id.tv_visit_result);
        TextView tvVisitTime = helper.getView(R.id.tv_visit_time);
        LinearLayout llVisitResult = helper.getView(R.id.ll_visit_result);
        LinearLayout llVisitTime = helper.getView(R.id.ll_visit_time);

        helper.setText(R.id.tv_visit_name,item.getStu_name())
                .setText(R.id.tv_visit_num,item.getStu_idnum())
                .addOnClickListener(R.id.rl_visit_phone)
                .addOnClickListener(R.id.tv_return_visit);
        Glide.with(context).load(item.getStu_photo())
                .placeholder(R.drawable.icon_contact_default)
                .error(R.drawable.icon_contact_default)
                .transform(new GlideRoundTransform(context))
                .into(ivStuPortrait);
        switch (item.getStatus()){
            case "0":
                tvVisitStatus.setText("已回访");
                llVisitTime.setVisibility(View.VISIBLE);
                llVisitResult.setVisibility(View.VISIBLE);
                tvVisitResult.setText((String)item.getReturn_content());
                tvVisitTime.setText((String)item.getReturn_date());
                break;
            case "1":
                tvVisitStatus.setText("未回访");
                llVisitTime.setVisibility(View.GONE);
                llVisitResult.setVisibility(View.GONE);
                break;
        }
        switch (item.getNode()){
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
                tvSub.setText("分车");
                break;
            case "6":
                tvSub.setText("调车");
                break;
            case "7":
                tvSub.setText("支队建档");
                break;
        }

        switch (item.getStatus()){
            case "0":
                tvReturnVisit.setVisibility(View.GONE);
                break;
            case "1":
                tvReturnVisit.setVisibility(View.VISIBLE);
                break;
        }
    }
}
