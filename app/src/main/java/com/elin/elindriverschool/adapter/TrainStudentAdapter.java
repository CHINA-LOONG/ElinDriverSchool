package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.StudentListBean;
import com.elin.elindriverschool.view.CircularImage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TrainStudentAdapter extends RecyclerView.Adapter<TrainStudentAdapter.ViewHolder> {

    public interface SelectItemListener{
        void onSelectItem(int index,boolean isSelect);
    }


    private Context mContext;
    private SelectItemListener selectItemListener;
    private List<StudentListBean.StudentDataBean> mList;
    private List<Boolean> mListState;

    private List<Integer> selectIndexs = new ArrayList<>();



    public TrainStudentAdapter(Context context, List<StudentListBean.StudentDataBean> list,SelectItemListener selectItemListener){
        this.mContext = context;
        this.mList = list;
        this.selectItemListener = selectItemListener;
        mListState=new ArrayList<>(Arrays.asList(new Boolean[mList.size()]));
        Collections.fill(mListState, Boolean.FALSE);
    }

    public void refrenshData(List<StudentListBean.StudentDataBean> list){
        if (list==null)
            return;
        mList.clear();
        mList.addAll(list);
        mListState=new ArrayList<>(Arrays.asList(new Boolean[mList.size()]));
        Collections.fill(mListState, Boolean.FALSE);
        selectIndexs.clear();
        for (Boolean b:mListState){
            Log.i("状态:",b+"___________________"+mListState.size()+"  "+mList.size());
        }
        Log.i("刷新数据","个数："+mList.size());
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_appointment_student,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final StudentListBean.StudentDataBean itemBean = mList.get(position);

        Log.i("更新列表：列表数量：",mList.size()+""+itemBean.getStuname()+"    "+mListState.get(position));
//        holder.setData(itemBean,mListState.get(position));
        holder.ivSelect.setImageResource(mListState.get(position)?R.drawable.xzxy_xz:R.drawable.xzxy_wxz);
        holder.tvName.setText(itemBean.getStuname());
        Glide.with(mContext).load(itemBean.getPhoto())
                .placeholder(R.drawable.user_default).error(R.drawable.user_default)
                .into(holder.civHeadImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListState.set(position,!mListState.get(position));
                holder.ivSelect.setImageResource(mListState.get(position)?R.drawable.xzxy_xz:R.drawable.xzxy_wxz);
                if (selectItemListener!=null){
                    selectItemListener.onSelectItem(position,mListState.get(position));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.civ_user_head_img)
        CircularImage civHeadImg;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.iv_select)
        ImageView ivSelect;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
//
//        public void setData(StudentListBean.StudentDataBean bean,boolean isSelect){
//            ivSelect.setImageResource(isSelect?R.drawable.xzxy_xz:R.drawable.xzxy_wxz);
//        }
    }
}
