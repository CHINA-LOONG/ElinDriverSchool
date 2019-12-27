package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 17535 on 2017/6/23.
 */

public class WaitTestAdapter extends BaseQuickAdapter<WaitOptionTestStuBean.DataListBean,BaseViewHolder> {
    List<WaitOptionTestStuBean.DataListBean> checkList = new ArrayList<>();
    Context context;
    private static HashMap<Integer, Boolean> isCheckedMap;
    public List<Boolean> mChecked;
    public boolean isTestOption;
    private String flag;
    public interface OnStuItemCheckedCallBack {
        void getCheckedMap(HashMap<Integer, Boolean> map);
    }

    OnStuItemCheckedCallBack onStuItemCheckedCallBack;

    public WaitTestAdapter(List<WaitOptionTestStuBean.DataListBean> data, OnStuItemCheckedCallBack onStuItemCheckedCallBack, Context context, boolean isTestOption,String flag) {
        super(R.layout.wait_test_student_item,data);
        this.context = context;
        this.onStuItemCheckedCallBack = onStuItemCheckedCallBack;
        this.isTestOption = isTestOption;//是否为待处理学员
        this.flag = flag;
        isCheckedMap = new HashMap<>();
        mChecked = new ArrayList<>();


    }
    public void clearStates(){
        isCheckedMap.clear();
    }
    @Override
    public int getItemCount() {
        for (int i = 0; i < super.getItemCount(); i++) {
            mChecked.add(false);
        }
        for (int i = 0; i < super.getItemCount(); i++) {
            getIsCheckedMap().put(i, false);
        }
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder helper, final WaitOptionTestStuBean.DataListBean item) {
        helper.setText(R.id.tv_wait_test_stu_name,item.getStu_name())
                .setText(R.id.tv_wait_test_stu_id_num,item.getStu_idnum())
                .setText(R.id.tv_wait_test_stu_car_type,item.getStu_cartype())
                .setText(R.id.tv_wait_test_stu_sign_up_date,item.getStu_reg_date())
                .addOnClickListener(R.id.cb_wait_test_stu_item)
                .addOnClickListener(R.id.imv_wait_test_item_phone);
        ImageView ivHead = helper.getView(R.id.imv_wait_test_stu_head);
        CheckBox checkBox = helper.getView(R.id.cb_wait_test_stu_item);
        ImageView ivAppointing = helper.getView(R.id.imv_stu_item_is_appointing);
        ImageView ivPhone = helper.getView(R.id.imv_wait_test_item_phone);
        TextView premium = helper.getView(R.id.item_tv_pretest_premium);
        if(TextUtils.equals("0",item.getHas_premium())){
            premium.setText("否");
        }else {
            premium.setText("是");
        }
        Glide.with(mContext).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivHead);
        switch (flag){
            case "1":
                checkBox.setVisibility(View.VISIBLE);
                if (isTestOption) {
                    if (item.getStu_detarec_remind_flag().equals("1")) {
                        //提交过
                        checkBox.setVisibility(View.GONE);
                        ivAppointing.setVisibility(View.VISIBLE);
                    } else {
                        //未提交过
                        checkBox.setVisibility(View.VISIBLE);
                        ivAppointing.setVisibility(View.GONE);
                    }
                } else {
                    if ("202".equals(String.valueOf(item.getStu_exam_wait_flag()))) {
                        //有预约
                        checkBox.setVisibility(View.GONE);
                        ivAppointing.setVisibility(View.VISIBLE);
                    } else if ("203".equals(String.valueOf(item.getStu_exam_wait_flag()))) {
                        checkBox.setVisibility(View.GONE);
                        ivAppointing.setVisibility(View.VISIBLE);
                        ivAppointing.setImageResource(R.drawable.icon_auditing);
                    } else {
                        //无预约
                        checkBox.setVisibility(View.VISIBLE);
                        ivAppointing.setVisibility(View.GONE);
                    }
                }
                break;
            case "2":
                checkBox.setVisibility(View.GONE);
                ivAppointing.setVisibility(View.GONE);
                break;
        }

        final int i = helper.getPosition();
        if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckedMap != null) {
                    if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
                        isCheckedMap.put(i, false);
                        setIsCheckedMap(isCheckedMap);
                    } else {
                        isCheckedMap.put(i, true);
                        setIsCheckedMap(isCheckedMap);
                    }
                }

            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mChecked.set(i,true);
                    checkList.add(item);
                } else {
                    mChecked.set(i, false);
                    checkList.remove(item);
                }
            }
        });
        onStuItemCheckedCallBack.getCheckedMap(isCheckedMap);
        if (getIsCheckedMap() != null) {
            if (getIsCheckedMap().get(i) != null) {
                checkBox.setChecked(getIsCheckedMap().get(i));
            }
        }
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(item.getStu_phone(), item.getStu_name(), mContext);
            }
        });
    }

    public static HashMap<Integer, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public static void setIsCheckedMap(HashMap<Integer, Boolean> isCheckedMap) {
        WaitTestAdapter.isCheckedMap = isCheckedMap;
    }

    public List<WaitOptionTestStuBean.DataListBean> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<WaitOptionTestStuBean.DataListBean> checkList) {
        this.checkList = checkList;
    }
}
