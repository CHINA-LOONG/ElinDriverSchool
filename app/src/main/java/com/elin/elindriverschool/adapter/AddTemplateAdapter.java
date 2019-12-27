package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AddTemplateBean;
import com.elin.elindriverschool.view.ScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17535 on 2017/8/23.
 */

public class AddTemplateAdapter extends BaseQuickAdapter<AddTemplateBean,BaseViewHolder> {
    Context context;
    List<AddTemplateBean.AddTemplateTimeBean> dataList = new ArrayList<>();
    AddTemplateInsideAdapter adapter;
    ScrollRecyclerView recyclerView;
    public AddTemplateAdapter(Context context,List<AddTemplateBean> data) {
        super(R.layout.item_add_template,data);
        this.context = context;

    }

    @Override
    protected void convert(BaseViewHolder helper, AddTemplateBean item) {
        EditText templateName = helper.getView(R.id.item_edt_template_name);
        adapter = new AddTemplateInsideAdapter(dataList);
        recyclerView = helper.getView(R.id.item_rv_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.getData());
        helper.setText(R.id.item_edt_template_name,item.getTemplateName())
            .addOnClickListener(R.id.item_edt_template_name);
        templateName.setSelection(item.getTemplateName().length());
    }
}
