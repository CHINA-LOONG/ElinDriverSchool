package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17535 on 2017/9/4.
 */

public class CheckTemplateAdapter extends BaseQuickAdapter<CheckTemplateTimeBean.DataBean,BaseViewHolder> {
    Context context;
    CheckTemplateInsideAdapter adapter;
    RecyclerView recyclerView;
    List<CheckTemplateTimeBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    public CheckTemplateAdapter(Context context,List<CheckTemplateTimeBean.DataBean> data) {
        super(R.layout.item_check_time_template,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckTemplateTimeBean.DataBean item) {
        CheckBox cbAppointTemplate = helper.getView(R.id.cb_appoint_template);
        cbAppointTemplate.setVisibility(View.GONE);
        adapter = new CheckTemplateInsideAdapter(context,dataList);
        recyclerView = helper.getView(R.id.item_rv_check_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.getTimeList());
        helper.setText(R.id.item_tv_create_template_time,item.getCreate_time())
                .addOnClickListener(R.id.item_delete_appoint_template);
    }

}
