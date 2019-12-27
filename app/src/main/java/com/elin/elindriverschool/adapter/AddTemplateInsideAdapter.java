package com.elin.elindriverschool.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.AddTemplateBean;

import java.util.List;

/**
 * Created by 17535 on 2017/9/1.
 */

public class AddTemplateInsideAdapter extends BaseQuickAdapter<AddTemplateBean.AddTemplateTimeBean,BaseViewHolder> {

    public AddTemplateInsideAdapter(List<AddTemplateBean.AddTemplateTimeBean> data) {
        super(R.layout.item_inside_add_template,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddTemplateBean.AddTemplateTimeBean item) {
        TextView tvSub = helper.getView(R.id.item_inside_sub);
        switch (item.getTrain_sub()){
            case "2":
                tvSub.setText("科目二");
                break;
            case "3":
                tvSub.setText("科目三");
                break;
            case "5":
                tvSub.setText("科目二和科目三");
                break;
        }
        helper.setText(R.id.item_inside_person_limit,item.getPerson_limit());
    }
}
