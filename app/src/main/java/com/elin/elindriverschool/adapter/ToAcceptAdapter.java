package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.ToAcceptBean;
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

public class ToAcceptAdapter extends BaseAdapter {

    private Context mContext;
    private ToAcceptBean waitOptionTestStuBean;
    private List<ToAcceptBean.DataListBean> stuList = new ArrayList<>();
    private static HashMap<Integer, Boolean> isCheckedMap;
//    public List<Boolean> mChecked;
    public boolean isTestOption;

    public interface OnStuItemCheckedCallBack {
        public void getCheckedMap(HashMap<Integer, Boolean> map);
    }

    OnStuItemCheckedCallBack onStuItemCheckedCallBack;


    public ToAcceptAdapter(ToAcceptBean waitOptionTestStuBean, OnStuItemCheckedCallBack onStuItemCheckedCallBack, Context mContext, boolean isTestOption) {
        this.mContext = mContext;
        this.waitOptionTestStuBean = waitOptionTestStuBean;
        this.onStuItemCheckedCallBack = onStuItemCheckedCallBack;
        this.isTestOption = isTestOption;//是否为待处理学员

        isCheckedMap = new HashMap<>();
//        mChecked = new ArrayList<>();
//        for (int i = 0; i < waitOptionTestStuBean.getData_list().size(); i++) {
//            mChecked.add(false);
//        }
        initData();
    }

    private void initData() {
        for (int i = 0; i < waitOptionTestStuBean.getData_list().size(); i++) {
            getIsCheckedMap().put(i, false);
        }
    }

    public void setItems(List<ToAcceptBean.DataListBean> stuList) {
        this.stuList.clear();
        this.stuList = stuList;
        notifyDataSetChanged();
    }

    public void remove(int i) {
        stuList.remove(i);
        notifyDataSetChanged();
    }

    public static HashMap<Integer, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public static void setIsCheckedMap(HashMap<Integer, Boolean> isCheckedMap) {
        ToAcceptAdapter.isCheckedMap = isCheckedMap;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_toaccept_stu, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (isTestOption) {
            if ("102".equals(waitOptionTestStuBean.getData_list().get(i).getStu_detarec_wait_flag())) {
                //提交过
                viewHolder.imvStuItemIsAppointing.setImageResource(R.drawable.icon_toaccept);
                viewHolder.cbWaitTestStuItem.setVisibility(View.GONE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.VISIBLE);
            } else {
                //未提交过
                viewHolder.cbWaitTestStuItem.setVisibility(View.VISIBLE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.GONE);
            }
        }

        new ImageLoaderHelper(mContext).loadImage(waitOptionTestStuBean.getData_list().get(i).getStu_photo(), viewHolder.imvWaitTestStuHead);
        viewHolder.tvWaitTestStuName.setText(waitOptionTestStuBean.getData_list().get(i).getStu_name());
        viewHolder.tvWaitTestStuIdNum.setText(waitOptionTestStuBean.getData_list().get(i).getStu_idnum());
        viewHolder.tvWaitTestStuCarType.setText(waitOptionTestStuBean.getData_list().get(i).getStu_cartype());
        if(TextUtils.isEmpty(waitOptionTestStuBean.getData_list().get(i).getStu_detarec_predate())){
            viewHolder.tvWaitTestStuSignUpDate.setText("暂无受理");
        }else {
            viewHolder.tvWaitTestStuSignUpDate.setText(waitOptionTestStuBean.getData_list().get(i).getStu_detarec_predate());
        }


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

//        viewHolder.cbWaitTestStuItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    mChecked.set(i, true);
//                } else {
//                    mChecked.set(i, false);
//                }
//            }
//        });
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
//        @Bind(R.id.ll_wait_test_bus_ride)
//        LinearLayout llWaitTestBusRide;
//        @Bind(R.id.tv_wait_test_bus_ride)
//        TextView tvWaitTestBusRide;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
