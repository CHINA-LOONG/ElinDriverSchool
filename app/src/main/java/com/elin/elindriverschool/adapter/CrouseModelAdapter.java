package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.bean.ModelBean;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CrouseModelAdapter extends RecyclerView.Adapter<CrouseModelAdapter.ViewHolder> {

    private Context mContext;
    private List<ModelBean.TimeBean> mList;

    public CrouseModelAdapter(Context context, List<ModelBean.TimeBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_appointment_course, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ModelBean.TimeBean itemBean = mList.get(position);
        holder.tvTimeStart.setText(itemBean.start_time);
        holder.tvTimeEnd.setText(itemBean.start_time);
        holder.tvSubject.setText(itemBean.train_sub=="2"?"科目二":itemBean.train_sub=="3"?"科目三":"不限");
        holder.tvNumber.setText(String.format("{0}人",itemBean.person_limit));
        holder.tvType.setText(itemBean.car_type);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_time_start)
        TextView tvTimeStart;
        @Bind(R.id.tv_time_end)
        TextView tvTimeEnd;
        @Bind(R.id.tv_subject)
        TextView tvSubject;
        @Bind(R.id.tv_number)
        TextView tvNumber;
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
