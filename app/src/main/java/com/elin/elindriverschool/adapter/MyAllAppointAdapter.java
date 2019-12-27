package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.MyAllAppointBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17535 on 2017/10/11.
 */

public class MyAllAppointAdapter extends BaseQuickAdapter<MyAllAppointBean.DataBean,BaseViewHolder> {
    Context context;
    MyAllAppointInsideAdapter adapter;
    RecyclerView recyclerView;
    List<MyAllAppointBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    public MyAllAppointAdapter(Context context,List<MyAllAppointBean.DataBean> data) {
        super(R.layout.item_all_appoint,data);//copy的培训模板布局，稍作改动
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyAllAppointBean.DataBean item) {
        CheckBox cbAppointTemplate = helper.getView(R.id.cb_appoint_template);
        LinearLayout llAllAppoint = helper.getView(R.id.item_ll_all_appoint);
        CardView cvSelectTemplate = helper.getView(R.id.item_cv_select_template);
        TextView tvTrainTime = helper.getView(R.id.item_tv_create_time);
        helper.getView(R.id.item_tv_student_preview).setVisibility(View.GONE);
        helper.getView(R.id.item_delete_appoint_template).setVisibility(View.GONE);
        tvTrainTime.setText("培训日期");
        cbAppointTemplate.setVisibility(View.GONE);
        adapter = new MyAllAppointInsideAdapter(context,dataList);
        recyclerView = helper.getView(R.id.item_rv_check_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.getTimeList());
        if(item.getTimeList().size()==0){
            llAllAppoint.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            cvSelectTemplate.setVisibility(View.GONE);
        }else {
            llAllAppoint.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            cvSelectTemplate.setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.item_tv_create_template_time,item.getDate());


    }
}
