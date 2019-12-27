package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AppointAlreadyStudentActivity;
import com.elin.elindriverschool.model.MyAllAppointBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by 17535 on 2017/10/12.
 */

public class MyAllAppointInsideAdapter extends BaseQuickAdapter<MyAllAppointBean.DataBean.TimeListBean,BaseViewHolder>{
    Context context;
    public MyAllAppointInsideAdapter(Context context,List<MyAllAppointBean.DataBean.TimeListBean> data) {
        super(R.layout.item_appoint_training,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyAllAppointBean.DataBean.TimeListBean item) {
        String startTime="1",endTime="1";
        TextView tvTime = helper.getView(R.id.item_tv_time);
        LinearLayout llAllAppointInside = helper.getView(R.id.item_ll_all_appoint_inside);
        TextView tvStartTime = helper.getView(R.id.item_tv_start_time);
        TextView tvEndTime = helper.getView(R.id.item_tv_end_time);
        tvTime.setVisibility(View.GONE);
        ImageView ivTimeStart = helper.getView(R.id.iv_time_start);
        ImageView ivTimeEnd = helper.getView(R.id.iv_time_end);
        helper.setText(R.id.item_tv_already_stu,"已预约人数："+item.getAlready_stu())
                .setText(R.id.item_tv_person_limit,"可预约人数："+item.getPerson_limit());
        if(!TextUtils.isEmpty(item.getStart_time())){
            tvStartTime.setText(item.getStart_time());
            startTime = item.getStart_time().substring(0,2);
        }else if(!TextUtils.isEmpty(item.getEnd_time())){
            tvEndTime.setText(item.getEnd_time());
            endTime = item.getEnd_time().substring(0,2);
        }
        if(Integer.parseInt(endTime)<=12){
            ivTimeStart.setImageResource(R.mipmap.ic_am_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_am_end);
        }else if(Integer.parseInt(startTime)>=12){
            ivTimeStart.setImageResource(R.mipmap.ic_pm_start);
            ivTimeEnd.setImageResource(R.mipmap.ic_pm_end);
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

        llAllAppointInside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("recordId", item.getId());
                String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                if (TextUtils.equals(today, item.getCreate_date())) {
                    bundle.putString("canSign", "0");
                }
                Intent intent = new Intent(context,AppointAlreadyStudentActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }
}
