package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.CheckScoreStuBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.List;

/**
 * Created by imac on 17/1/4.
 */

public class CheckScoreStuListAdapter extends BaseQuickAdapter<CheckScoreStuBean.DataListBeanX.DataListBean,BaseViewHolder> {
    Context context;
    public CheckScoreStuListAdapter(List<CheckScoreStuBean.DataListBeanX.DataListBean> data,Context context) {
        super(R.layout.student_item_check_score,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CheckScoreStuBean.DataListBeanX.DataListBean item) {
        helper.setText(R.id.tv_stu_item_check_score_name,item.getStu_name())
                .setText(R.id.tv_stu_item_check_score_id_num,item.getStu_idnum())
                .setText(R.id.tv_stu_item_check_score_date,item.getExam_date());
        TextView scoreClass = helper.getView(R.id.tv_stu_item_check_score_class);
        TextView testGrade = helper.getView(R.id.tv_stu_item_check_score_score);
        ImageView ivScoreHead = helper.getView(R.id.imv_stu_item_check_score_head);
        RelativeLayout phoneNuber = helper.getView(R.id.rl_stu_item_check_score_phone);
        switch (item.getExam_score()){
            case "1":
                testGrade.setText("合格");
                testGrade.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                break;
            case "2":
                testGrade.setText("不合格");
                testGrade.setTextColor(ContextCompat.getColor(mContext,R.color.text_red));
                break;
            case "3":
                testGrade.setText("缺考");
                testGrade.setTextColor(ContextCompat.getColor(mContext,R.color.text_color_light_dark));
                break;

        }
        switch (item.getExam_sub()){
            case "1":
                scoreClass.setText("科目一");
                break;
            case "2":
                scoreClass.setText("科目二");
                break;
            case "3":
                scoreClass.setText("科目三");
                break;
            case "4":
                scoreClass.setText("科目四");
                break;
        }
        phoneNuber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(item.getStu_phone(),item.getStu_name(), mContext);
            }
        });
        Glide.with(context).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivScoreHead);
    }
}
