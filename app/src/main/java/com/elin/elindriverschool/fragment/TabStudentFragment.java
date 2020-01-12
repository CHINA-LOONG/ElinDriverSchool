package com.elin.elindriverschool.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.TrainStudentAdapter;
import com.elin.elindriverschool.model.StudentListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabStudentFragment extends Fragment {

    @Bind(R.id.rv_list)
    RecyclerView mRecyclerView;

    private TrainStudentAdapter.SelectItemListener listener;
    private TrainStudentAdapter trainStudentAdapter;
    private List<StudentListBean.StudentDataBean> studentList;

    public static TabStudentFragment newInstance(TrainStudentAdapter.SelectItemListener listener){
//        Bundle arguments =new Bundle();
//        arguments putString()
        TabStudentFragment tabStudentFragment = new TabStudentFragment();
        tabStudentFragment.listener = listener;
        return tabStudentFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_tab_student,null);
        ButterKnife.bind(this,contentView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        studentList = new ArrayList<>();
        trainStudentAdapter = new TrainStudentAdapter(getContext(), studentList, this.listener);

        mRecyclerView.setAdapter(trainStudentAdapter);

        return contentView;
    }

    public void setData(StudentListBean studentListBean){
        trainStudentAdapter.refrenshData(studentListBean.getData());
    }

}
