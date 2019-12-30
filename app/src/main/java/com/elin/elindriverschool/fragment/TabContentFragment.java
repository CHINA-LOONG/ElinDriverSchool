package com.elin.elindriverschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.node.TreeAdapter;
import com.elin.elindriverschool.node.TreeItem;

import java.util.ArrayList;
import java.util.List;

public class TabContentFragment extends Fragment {

    private static  final String EXTRA_CONTENT ="content";


    private RecyclerView mRecyclerView;
//    private NodeTreeAdapter adapter = new NodeTreeAdapter();

    public static TabContentFragment newInstance(String content){
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT,content);
        TabContentFragment tabContentFragment = new TabContentFragment();
        tabContentFragment.setArguments(arguments);

        return tabContentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_content,null);

        mRecyclerView=(RecyclerView)contentView.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TreeAdapter treeAdapter = new TreeAdapter(getContext(), initList());
        mRecyclerView.setAdapter(treeAdapter);

        return  contentView;
    }



    private List<TreeItem> initList() {
        List<TreeItem> list = new ArrayList<>();

        TreeItem item_0_0 = new TreeItem();
        item_0_0.title = "第0级，第0个";
        item_0_0.child = new ArrayList<>();

        TreeItem item_1_0 = new TreeItem();
        item_1_0.title = "第1级，第0个";
        item_1_0.child = new ArrayList<>();
        TreeItem item_2_0 = new TreeItem();
        item_2_0.title = "第2级，第0个";
        TreeItem item_2_1 = new TreeItem();
        item_2_1.title = "第2级，第1个";
        TreeItem item_2_2 = new TreeItem();
        item_2_2.title = "第2级，第2个";
        item_1_0.child.add(item_2_0);
        item_1_0.child.add(item_2_1);
        item_1_0.child.add(item_2_2);
        item_0_0.child.add(item_1_0);

        TreeItem item_1_1 = new TreeItem();
        item_1_1.title = "第1级，第1个";
        item_1_1.child = new ArrayList<>();
        TreeItem item_2_3 = new TreeItem();
        item_2_3.title = "第2级，第3个";
        TreeItem item_2_4 = new TreeItem();
        item_2_4.title = "第2级，第4个";
        item_2_3.child = new ArrayList<>();
        TreeItem item_3_0 = new TreeItem();
        item_3_0.title = "第3级，第0个";
        TreeItem item_3_1 = new TreeItem();
        item_3_1.title = "第3级，第1个";
        TreeItem item_3_2 = new TreeItem();
        item_3_2.title = "第3级，第2个";
        item_2_3.child.add(item_3_0);
        item_2_3.child.add(item_3_1);
        item_2_3.child.add(item_3_2);
        item_1_1.child.add(item_2_3);
        item_1_1.child.add(item_2_4);
        item_0_0.child.add(item_1_1);
        list.add(item_0_0);


        TreeItem item_0_1 = new TreeItem();
        item_0_1.title = "第0级，第1个";
        item_0_1.child = new ArrayList<>();

        TreeItem item_1_2 = new TreeItem();
        item_1_2.title = "第1级，第2个";
        item_1_2.child = new ArrayList<>();
        TreeItem item_2_5 = new TreeItem();
        item_2_5.title = "第2级，第5个";
        TreeItem item_2_6 = new TreeItem();
        item_2_6.title = "第2级，第6个";
        TreeItem item_2_7 = new TreeItem();
        item_2_7.title = "第2级，第7个";
        item_1_2.child.add(item_2_5);
        item_1_2.child.add(item_2_6);
        item_1_2.child.add(item_2_7);
        item_0_1.child.add(item_1_2);

        TreeItem item_1_3 = new TreeItem();
        item_1_3.title = "第1级，第3个";
        item_1_3.child = new ArrayList<>();
        TreeItem item_2_8 = new TreeItem();
        item_2_8.title = "第2级，第8个";
        TreeItem item_2_9 = new TreeItem();
        item_2_9.title = "第2级，第9个";
        item_2_8.child = new ArrayList<>();
        TreeItem item_3_3 = new TreeItem();
        item_3_3.title = "第3级，第3个";
        TreeItem item_3_4 = new TreeItem();
        item_3_4.title = "第3级，第4个";
        TreeItem item_3_5 = new TreeItem();
        item_3_5.title = "第3级，第5个";
        item_2_8.child.add(item_3_3);
        item_2_8.child.add(item_3_4);
        item_2_8.child.add(item_3_5);
        item_1_3.child.add(item_2_8);
        item_1_3.child.add(item_2_9);
        item_0_1.child.add(item_1_3);
        list.add(item_0_1);

        return list;
    }
}
