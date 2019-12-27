package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.PreSucStuOptionBean;
import com.elin.elindriverschool.util.ImageLoaderHelper;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 17/1/4.
 */

public class PreSucStuWaitOptionListAdapter extends BaseAdapter {

    private Context mContext;
    private PreSucStuOptionBean preSucStuOptionBean;

    public PreSucStuWaitOptionListAdapter(Context mContext, PreSucStuOptionBean preSucStuOptionBean) {
        this.mContext = mContext;
        this.preSucStuOptionBean = preSucStuOptionBean;
    }

    @Override
    public int getCount() {
        return preSucStuOptionBean != null ? preSucStuOptionBean.getData_list().size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return preSucStuOptionBean.getData_list().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.student_item_pre_wait_option, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        new ImageLoaderHelper(mContext).loadImage(preSucStuOptionBean.getData_list().get(i).getStu_photo(), viewHolder.imvStuItemPreWaitOptionHead);
        viewHolder.tvStuItemPreWaitOptionName.setText(preSucStuOptionBean.getData_list().get(i).getStu_name());
        viewHolder.tvStuItemPreWaitOptionDate.setText("支队建档时间："+preSucStuOptionBean.getData_list().get(i).getDetarec_order_date());
        viewHolder.tvStuItemPreWaitOptionIdNum.setText(preSucStuOptionBean.getData_list().get(i).getStu_idnum());
        viewHolder.tvStuItemPreWaitOptionSite.setText(preSucStuOptionBean.getData_list().get(i).getDetarec_order_site());
        if (preSucStuOptionBean.getData_list().get(i).getDetarec_order_bus().equals("1")){
            viewHolder.tvStuItemPreWaitOptionTakeBus.setText("是");
        }else {
            viewHolder.tvStuItemPreWaitOptionTakeBus.setText("否");
        }
        viewHolder.rlStuItemPreWaitOptionPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(preSucStuOptionBean.getData_list().get(i).getStu_phone(),preSucStuOptionBean.getData_list().get(i).getStu_name(),mContext);
            }
        });

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.imv_stu_item_pre_wait_option_head)
        ImageView imvStuItemPreWaitOptionHead;
        @Bind(R.id.tv_stu_item_pre_wait_option_name)
        TextView tvStuItemPreWaitOptionName;
        @Bind(R.id.rl_stu_item_pre_wait_option_phone)
        RelativeLayout rlStuItemPreWaitOptionPhone;
        @Bind(R.id.tv_stu_item_pre_wait_option_id_num)
        TextView tvStuItemPreWaitOptionIdNum;
        @Bind(R.id.tv_stu_item_pre_wait_option_date)
        TextView tvStuItemPreWaitOptionDate;
        @Bind(R.id.tv_stu_item_pre_wait_option_site)
        TextView tvStuItemPreWaitOptionSite;
        @Bind(R.id.tv_stu_item_pre_wait_option_take_bus)
        TextView tvStuItemPreWaitOptionTakeBus;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
