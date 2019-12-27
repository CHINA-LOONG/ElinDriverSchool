package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.CheckTemplateActivity;
import com.elin.elindriverschool.activity.StudentPreviewActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;

import java.util.List;

/**
 * Created by 17535 on 2017/9/4.
 * 查看模板和添加模板内容适配器
 */

public class CheckTemplateInsideAdapter extends BaseQuickAdapter<CheckTemplateTimeBean.DataBean.TimeListBean,BaseViewHolder> {
    Context context ;
    public CheckTemplateInsideAdapter(Context context,List<CheckTemplateTimeBean.DataBean.TimeListBean> data) {
        super(R.layout.item_inside_add_template,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckTemplateTimeBean.DataBean.TimeListBean item) {
        TextView tvSub = helper.getView(R.id.item_inside_sub);
        ImageView ivTimeStart = helper.getView(R.id.iv_time_start);
        ImageView ivTimeEnd = helper.getView(R.id.iv_time_end);
        TextView tvDeleteTimeTemplate = helper.getView(R.id.item_delete_time_template);
        TextView tvStudentPreview = helper.getView(R.id.item_tv_student_preview);
        if(TextUtils.isEmpty(item.getAble_stu_count())){
            tvStudentPreview.setText("预览学员");
            tvStudentPreview.setVisibility(View.GONE);
        }else {
            tvStudentPreview.setText("预览学员（"+item.getAble_stu_count()+"人）");
            tvStudentPreview.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(item.getTrain_sub())) {
            switch (item.getTrain_sub()){
                case "2":
                    tvSub.setText("科目二");
                    break;
                case "3":
                    tvSub.setText("科目三");
                    break;
                case "5":
                    tvSub.setText("科二和科三");
                    break;
            }
        }
        String startTime = item.getStart_time().substring(0,2);
        String endTime = item.getEnd_time().substring(0,2);
        if(Integer.parseInt(endTime)<=12){
            ivTimeStart.setImageResource(R.mipmap.ic_am_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_am_end);
        }else if(Integer.parseInt(startTime)>=12){
            ivTimeStart.setImageResource(R.mipmap.ic_pm_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_pm_end);
        }
        if(TextUtils.isEmpty(item.getModel_id())){
            tvDeleteTimeTemplate.setVisibility(View.VISIBLE);
        }else {
            tvDeleteTimeTemplate.setVisibility(View.GONE);
        }
        helper.setText(R.id.item_inside_person_limit,item.getPerson_limit())
                .setText(R.id.item_tv_start_time,item.getStart_time())
                .setText(R.id.item_tv_end_time,item.getEnd_time())
                .addOnClickListener(R.id.item_delete_time_template)
        .addOnClickListener(R.id.item_tv_student_preview);
        tvStudentPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("modelId",item.getId());
                bundle.putString("trainSub",item.getTrain_sub());
                bundle.putString("startDay", BaseApplication.getInstance().getStartDay());
                Intent intent = new Intent(context,StudentPreviewActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
