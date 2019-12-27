package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.ImageLoaderHelper;
import com.elin.elindriverschool.util.MobilePhoneUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by imac on 17/1/3.
 */

public class WaitTestStuListAdapter extends BaseAdapter {

    private Context mContext;
    private WaitOptionTestStuBean waitOptionTestStuBean;
    private List<WaitOptionTestStuBean.DataListBean> stuList = new ArrayList<>();
    private static HashMap<Integer, Boolean> isCheckedMap;
//    public List<Boolean> mChecked;
    public boolean isTestOption;

    public interface OnStuItemCheckedCallBack {
        public void getCheckedMap(HashMap<Integer, Boolean> map);
    }

    OnStuItemCheckedCallBack onStuItemCheckedCallBack;


    public WaitTestStuListAdapter(WaitOptionTestStuBean waitOptionTestStuBean, OnStuItemCheckedCallBack onStuItemCheckedCallBack, Context mContext, boolean isTestOption) {
        this.mContext = mContext;
        this.waitOptionTestStuBean = waitOptionTestStuBean;
        this.onStuItemCheckedCallBack = onStuItemCheckedCallBack;
        this.isTestOption = isTestOption;//是否为待处理学员

        isCheckedMap = new HashMap<>();
//        mChecked = new ArrayList<>();
//        for (int i = 0; i < waitOptionTestStuBean.getData_list().size(); i++) {
//            mChecked.add(false);
//        }
        Log.e("几何长度==",waitOptionTestStuBean.getData_list().size()+"");
        initData();
    }

    private void initData() {
        for (int i = 0; i < waitOptionTestStuBean.getData_list().size(); i++) {
            getIsCheckedMap().put(i, false);
        }
    }

    public void setItems(List<WaitOptionTestStuBean.DataListBean> stuList) {
        this.stuList.clear();
        this.stuList = stuList;
        notifyDataSetChanged();
    }

    public static HashMap<Integer, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public static void setIsCheckedMap(HashMap<Integer, Boolean> isCheckedMap) {
        WaitTestStuListAdapter.isCheckedMap = isCheckedMap;
    }

    @Override
    public int getCount() {
        return waitOptionTestStuBean.getData_list() == null ? 0 : waitOptionTestStuBean.getData_list().size();
    }

    @Override
    public Object getItem(int i) {
        return waitOptionTestStuBean.getData_list().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.wait_test_student_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.e("科目三",i+"===="+waitOptionTestStuBean.getData_list().get(i).getStu_detarec_remind_flag());
        if (isTestOption) {
            if (waitOptionTestStuBean.getData_list().get(i).getStu_detarec_remind_flag().equals("1")) {
                //提交过
                viewHolder.cbWaitTestStuItem.setVisibility(View.GONE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.VISIBLE);
            } else {
                //未提交过
                viewHolder.cbWaitTestStuItem.setVisibility(View.VISIBLE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.GONE);
            }
        } else {
            if ("202".equals(String.valueOf(waitOptionTestStuBean.getData_list().get(i).getStu_exam_wait_flag()))) {
                //有预约
                viewHolder.cbWaitTestStuItem.setVisibility(View.GONE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.VISIBLE);
            } else if ("203".equals(String.valueOf(waitOptionTestStuBean.getData_list().get(i).getStu_exam_wait_flag()))) {
                viewHolder.cbWaitTestStuItem.setVisibility(View.GONE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.VISIBLE);
                viewHolder.imvStuItemIsAppointing.setImageResource(R.drawable.icon_auditing);
            } else {
                //无预约
                viewHolder.cbWaitTestStuItem.setVisibility(View.VISIBLE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.GONE);
            }
        }
        Glide.with(mContext).load(waitOptionTestStuBean.getData_list().get(i).getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(viewHolder.imvWaitTestStuHead);
//        new ImageLoaderHelper(mContext).loadImage(waitOptionTestStuBean.getData_list().get(i).getStu_photo(), viewHolder.imvWaitTestStuHead);
        viewHolder.tvWaitTestStuName.setText(waitOptionTestStuBean.getData_list().get(i).getStu_name());
        viewHolder.tvWaitTestStuIdNum.setText(waitOptionTestStuBean.getData_list().get(i).getStu_idnum());
        viewHolder.tvWaitTestStuCarType.setText(waitOptionTestStuBean.getData_list().get(i).getStu_cartype());
        viewHolder.tvWaitTestStuSignUpDate.setText(waitOptionTestStuBean.getData_list().get(i).getStu_reg_date());

        if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
            viewHolder.cbWaitTestStuItem.setChecked(true);
        } else {
            viewHolder.cbWaitTestStuItem.setChecked(false);
        }
        viewHolder.cbWaitTestStuItem.setOnClickListener(new View.OnClickListener() {
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
        onStuItemCheckedCallBack.getCheckedMap(isCheckedMap);
        if (getIsCheckedMap() != null) {
            if (getIsCheckedMap().get(i) != null) {
                viewHolder.cbWaitTestStuItem.setChecked(getIsCheckedMap().get(i));
            }
        }
        viewHolder.imvWaitTestItemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(waitOptionTestStuBean.getData_list().get(i).getStu_phone(), waitOptionTestStuBean.getData_list().get(i).getStu_name(), mContext);
            }
        });
        switch (waitOptionTestStuBean.getData_list().get(i).getHas_premium()){
            case "0":
                viewHolder.tvPretestPremium.setText("否");
                break;
            case "1":
                viewHolder.tvPretestPremium.setText("是");
                break;
        }
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.imv_wait_test_stu_head)
        ImageView imvWaitTestStuHead;
        @Bind(R.id.tv_wait_test_stu_name)
        TextView tvWaitTestStuName;
        @Bind(R.id.imv_wait_test_item_phone)
        ImageView imvWaitTestItemPhone;
        @Bind(R.id.tv_wait_test_stu_id_num)
        TextView tvWaitTestStuIdNum;
        @Bind(R.id.tv_wait_test_stu_sign_up_date)
        TextView tvWaitTestStuSignUpDate;
        @Bind(R.id.tv_wait_test_stu_car_type)
        TextView tvWaitTestStuCarType;
        @Bind(R.id.cb_wait_test_stu_item)
        CheckBox cbWaitTestStuItem;
        @Bind(R.id.imv_stu_item_is_appointing)
        ImageView imvStuItemIsAppointing;
        @Bind(R.id.item_tv_pretest_premium)
        TextView tvPretestPremium;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
