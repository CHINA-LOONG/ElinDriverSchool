package com.elin.elindriverschool.node;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elin.elindriverschool.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.ViewHolder> implements TreeStateChangeListener {
    private final static int ITEM_STATE_CLOSE = 0;
    private final static int ITEM_STATE_OPEN = 1;
    private Context mContext;
    private List<TreeItem> mList;

    public TreeAdapter(Context context, List<TreeItem> list) {
        initList(list, 0);
        this.mContext = context;
        this.mList = new LinkedList<>();
        this.mList.addAll(list);
    }

    //列表对象划分等级
    private void initList(List<TreeItem> list, int level) {
        if (list == null || list.size() <= 0) return;
        for (TreeItem item : list) {
            item.itemLevel = level;
            if (item.child != null && item.child.size() > 0) {
                initList(item.child, level + 1);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview_tree, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TreeItem treeItem = mList.get(position);
//        holder.mTextView.setText(treeItem.title);
//        if (position==mList.size()-1){
//            holder.mDivider.setVisibility(View.VISIBLE);
//        }else if (mList.get(position + 1).itemLevel == 0) {
//            holder.mDivider.setVisibility(View.VISIBLE);
//        } else {
//            holder.mDivider.setVisibility(View.INVISIBLE);
//        }
//
//        if (treeItem.child!=null&&treeItem.child.size()>0){
//            holder.tvState.setVisibility(View.VISIBLE);
//            if (treeItem.itemState==ITEM_STATE_OPEN){
//                holder.tvState.setText(" - ");
//            }else {
//                holder.tvState.setText(" + ");
//            }
//        }else {
//            holder.tvState.setVisibility(View.INVISIBLE);
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (treeItem.itemState==ITEM_STATE_CLOSE){
                    onOpen(treeItem,holder.getAdapterPosition());
                }else {
                    onClose(treeItem,holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onOpen(TreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            mList.addAll(position + 1, treeItem.child);
            treeItem.itemState = ITEM_STATE_OPEN;
            notifyItemRangeInserted(position + 1, treeItem.child.size());
            notifyItemChanged(position);
        }
    }

    @Override
    public void onClose(TreeItem treeItem, int position) {
        if (treeItem.child != null && treeItem.child.size() > 0) {
            int nextSameOrHigherLevelNodePosition = mList.size() - 1;
            if (mList.size() > position + 1) {
                for (int i = position + 1; i < mList.size(); i++) {
                    if (mList.get(i).itemLevel <= mList.get(position).itemLevel) {
                        nextSameOrHigherLevelNodePosition = i - 1;
                        break;
                    }
                }
                closeChild(mList.get(position));
                if (nextSameOrHigherLevelNodePosition > position) {
                    mList.subList(position + 1, nextSameOrHigherLevelNodePosition + 1).clear();
                    treeItem.itemState = ITEM_STATE_CLOSE;
                    notifyItemRangeRemoved(position + 1, nextSameOrHigherLevelNodePosition - position);
                    notifyItemChanged(position);
                }
            }
        }
    }


    private void closeChild(TreeItem treeItem) {
        if (treeItem.child != null) {
            for (TreeItem child : treeItem.child) {
                child.itemState = ITEM_STATE_CLOSE;
                closeChild(child);
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {


//        public View mIndicator;
//        public TextView tvState;
//        public TextView mTextView;
//        public View mDivider;


        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
//            tvState = (TextView) itemView.findViewById(R.id.tvState);
//            mTextView =(TextView) itemView.findViewById(R.id.tvTime);
//            mDivider = itemView.findViewById(R.id.vDivider);
        }
    }
}
