package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.DynamicBean;
import com.elin.elindriverschool.util.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 17535 on 2017/2/18.
 * 驾校动态adapter
 */

public class HomeDynamicAdapter extends BaseAdapter {
    List<DynamicBean.DataListBean> list = new ArrayList<>();
    Context context;
    LayoutInflater inflater;
    public HomeDynamicAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void addList(List<DynamicBean.DataListBean> list1) {
        list.addAll(list1);
        notifyDataSetChanged();
    }
    public void clear() {
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeViewHolder holder = null;
        if (convertView == null) {
            holder = new HomeViewHolder();
            convertView = inflater.inflate(R.layout.item_list_home, null);
            holder.homeTitle = (TextView) convertView.findViewById(R.id.item_dynamic_title);
            holder.homeDate = (TextView) convertView.findViewById(R.id.item_dynamic_date);
            holder.homeImg = (ImageView) convertView.findViewById(R.id.item_dynamic_img);
            convertView.setTag(holder);
        } else {
            holder = (HomeViewHolder) convertView.getTag();
        }
        DynamicBean.DataListBean dataListBean = list.get(position);
        holder.homeTitle.setText(dataListBean.getDynamic_title());
        holder.homeDate.setText(dataListBean.getDynamic_date());
        Glide.with(context).load(dataListBean.getDynamic_img())
                .placeholder(R.drawable.img_default).error(R.drawable.img_default)
                .transform(new GlideRoundTransform(context))
                .into(holder.homeImg);
        return convertView;
    }

    class HomeViewHolder {
        TextView homeTitle;
        TextView homeDate;
        ImageView homeImg;
    }
}
