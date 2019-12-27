package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AddTemplateBean;

import java.util.List;

/**
 * Created by 17535 on 2017/8/31.
 */

public class AddTemplateTimeAdapter extends BaseQuickAdapter<AddTemplateBean.AddTemplateTimeBean,BaseViewHolder> {
    Context context;
    public AddTemplateTimeAdapter(Context context,List<AddTemplateBean.AddTemplateTimeBean> data) {
        super(R.layout.item_add_template_time,data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddTemplateBean.AddTemplateTimeBean item) {
        EditText personLimit = helper.getView(R.id.item_edt_person_limit);
        helper.setText(R.id.item_tv_start_time,item.getStart_time())
                .setText(R.id.item_tv_end_time,item.getEnd_time())
                .addOnClickListener(R.id.item_tv_start_time)
                .addOnClickListener(R.id.item_tv_end_time);
        personLimit.setSelection(item.getPerson_limit().length());
    }
}
