package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.fragment.StudentTaskFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 学员任务
 */
public class StudentTaskActivity extends BaseActivity {

    @Bind(R.id.img_task_return)
    ImageView imgTaskReturn;
    @Bind(R.id.rb_not_student_terminal)
    RadioButton rbNotStudentTerminal;
    @Bind(R.id.rb_not_task)
    RadioButton rbNotTask;
    @Bind(R.id.rg_task_title)
    RadioGroup rgTaskTitle;
    @Bind(R.id.rl_task_container)
    RelativeLayout rlTaskContainer;

    StudentTaskFragment terminalFragment;
    StudentTaskFragment taskFragment;
    StudentTaskFragment assistingTaskFragment;

    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_task);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        terminalFragment = StudentTaskFragment.newInstance("1");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.rl_task_container, terminalFragment);
        transaction.commit();
        rgTaskTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = manager.beginTransaction();
                hideFragment(transaction);
                switch (checkedId){
                    case R.id.rb_not_student_terminal:
                        if(terminalFragment==null){
                            terminalFragment = StudentTaskFragment.newInstance("1");
                            transaction.add(R.id.rl_task_container,terminalFragment);
                        }else {
                            transaction.show(terminalFragment);
                        }
                        break;
                    case R.id.rb_not_task:
                        if(taskFragment==null){
                            taskFragment = StudentTaskFragment.newInstance("2");
                            transaction.add(R.id.rl_task_container,taskFragment);
                        }else {
                            transaction.show(taskFragment);
                        }
                        break;
                    case R.id.rb_assisting_task:
                        if(assistingTaskFragment==null){
                            assistingTaskFragment = StudentTaskFragment.newInstance("3");
                            transaction.add(R.id.rl_task_container,assistingTaskFragment);
                        }else {
                            transaction.show(assistingTaskFragment);
                        }
                    break;
                }
                transaction.commit();
            }
        });
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (terminalFragment != null) {
            transaction.hide(terminalFragment);
        }
        if (taskFragment != null) {
            transaction.hide(taskFragment);
        }
        if (assistingTaskFragment != null){
            transaction.hide(assistingTaskFragment);
        }
    }
    @OnClick(R.id.img_task_return)
    public void onClick() {
        finish();
    }
}
