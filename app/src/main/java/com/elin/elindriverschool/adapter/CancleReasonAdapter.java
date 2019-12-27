package com.elin.elindriverschool.adapter;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.CancleReasonBean;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 17535 on 2017/10/18.
 */

public class CancleReasonAdapter extends BaseQuickAdapter<CancleReasonBean.DataBean,BaseViewHolder> {
    HashMap<Integer, Boolean> states;
    OnCancalReaonListener listener;
    public CancleReasonAdapter(List<CancleReasonBean.DataBean> data) {
        super(R.layout.item_cancle_reason,data);
        states = new HashMap<>();
    }

    @Override
    public void setNewData(List<CancleReasonBean.DataBean> data) {
        super.setNewData(data);
        for (int i = 0; i < data.size(); i++) {
            states.put(i,false);
        }
    }

    public void setOnCancleReasonListener(OnCancalReaonListener listener) {
        this.listener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, final CancleReasonBean.DataBean item) {
        helper.setText(R.id.item_tv_reason,item.getName());
        CheckBox cbReason = helper.getView(R.id.item_cb_reason);
        cbReason.setEnabled(false);
        LinearLayout llReason = helper.getView(R.id.item_ll_reason);
        final int position = helper.getPosition();
        llReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.cancleReason(item.getName());
                for (Integer key : states.keySet()) {
                    states.put(key, false);
                    Log.e("key",key+"");
                }
                Log.e("position",position+"");
                if (states.get(position) != null && states.get(position)) {
                    states.put(position, false);
                } else {
                    states.put(position, true);
                }
                notifyDataSetChanged();
            }
        });
        boolean res = false;
        if (states.get(position) == null
                || !states.get(position)) {
            res = false;
            states.put(position, false);
        } else {
            res = true;
        }
        cbReason.setChecked(res);
    }

    public interface OnCancalReaonListener{
        void cancleReason(String reason);
    }
}
