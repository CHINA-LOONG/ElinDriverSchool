package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.DriveContactBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.List;

/**
 * Created by imac on 17/1/7.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{

    private Context mContext;
//    private ContactBean.UserListBean
    private List<DriveContactBean>driveContactBeanList;
    private LayoutInflater mInflater;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }
    private OnItemClickLitener mOnItemClickLitener;
    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ContactListAdapter(List<DriveContactBean> driveContactBeanList, Context mContext) {
        this.driveContactBeanList = driveContactBeanList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<DriveContactBean> getDriveContactBeanList() {
        return driveContactBeanList;
    }

    public ContactListAdapter setDriveContactBeanList(List<DriveContactBean> driveContactBeanList) {
        this.driveContactBeanList = driveContactBeanList;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.contact_item,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final DriveContactBean driveContactBean = driveContactBeanList.get(position);
        holder.tv_name.setText(driveContactBean.getName());

        Glide.with(mContext).load(driveContactBean.getPhoto())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.icon_contact_default)
                .into(holder.imv_head);

        holder.imv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(driveContactBean.getPhone(),driveContactBean.getName(),mContext);
            }
        });
        if (mOnItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return driveContactBeanList!=null?driveContactBeanList.size():0;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        ImageView imv_head,imv_phone;
        View content;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tvContact_name);
            imv_head = (ImageView) itemView.findViewById(R.id.ivAvatar_contact);
            imv_phone = (ImageView) itemView.findViewById(R.id.imv_contact_phone);
            content = itemView.findViewById(R.id.contact_content);
        }
    }

}
