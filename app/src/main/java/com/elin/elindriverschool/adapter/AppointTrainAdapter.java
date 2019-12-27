package com.elin.elindriverschool.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AppointTrainBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 17535 on 2017/8/21.
 */

public class AppointTrainAdapter extends BaseQuickAdapter<AppointTrainBean.DataBean.TimeListBean,BaseViewHolder> {


    public AppointTrainAdapter(List<AppointTrainBean.DataBean.TimeListBean> data) {
        super(R.layout.item_appoint_training,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppointTrainBean.DataBean.TimeListBean item) {
        String startTime="1",endTime="1";
        TextView tvTime = helper.getView(R.id.item_tv_time);
        ImageView ivTimeStart = helper.getView(R.id.iv_time_start);
        ImageView ivTimeEnd = helper.getView(R.id.iv_time_end);
        TextView tvStartTime = helper.getView(R.id.item_tv_start_time);
        TextView tvEndTime = helper.getView(R.id.item_tv_end_time);
        helper.setText(R.id.item_tv_already_stu,"已预约人数："+item.getAlready_stu())
                .setText(R.id.item_tv_person_limit,"可预约人数："+item.getPerson_limit());
        if(!TextUtils.isEmpty(item.getStart_time())){
            tvStartTime.setText(item.getStart_time());
            startTime = item.getStart_time().substring(0,2);
        }
        if(!TextUtils.isEmpty(item.getEnd_time())){
            tvEndTime.setText(item.getEnd_time());
            endTime = item.getEnd_time().substring(0,2);
        }
        if(Integer.parseInt(endTime)<=12){
            tvTime.setText("上午");
            tvTime.setVisibility(View.VISIBLE);
            ivTimeStart.setImageResource(R.mipmap.ic_am_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_am_end);
        }else if(Integer.parseInt(startTime)>=12){
            tvTime.setText("下午");
            ivTimeStart.setImageResource(R.mipmap.ic_pm_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_pm_end);
        }
        if(TextUtils.equals("1",item.getDefine_flag())){
            tvTime.setVisibility(View.VISIBLE);
        }else {
            tvTime.setVisibility(View.GONE);
        }

        switch (item.getOpen_flag()){
            case "1":
                helper.setText(R.id.item_tv_is_order,"是否可以预约：是");
                break;
            case "0":
                helper.setText(R.id.item_tv_is_order,"是否可以预约：否");
                break;
        }
        if(!TextUtils.isEmpty(item.getTrain_sub())){
            switch (item.getTrain_sub()){
                case "2":
                    helper.setText(R.id.item_tv_train_sub,"预约科目：科目二");
                    break;
                case "3":
                    helper.setText(R.id.item_tv_train_sub,"预约科目：科目三");
                    break;
                case "5":
                    helper.setText(R.id.item_tv_train_sub,"预约科目：科二和科三");
                    break;
            }
        }
    }
}
