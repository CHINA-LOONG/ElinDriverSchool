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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.model.UploadGradeStuBean;
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

public class UploadGradeStuListAdapter extends BaseAdapter {

    private Context mContext;
    private UploadGradeStuBean uploadGradeStuBean;
    private List<UploadGradeStuBean.DataListBean> stuList = new ArrayList<>();
    private static HashMap<Integer, Boolean> isCheckedMap;
//    public List<Boolean> mChecked;

    public interface OnUpLoadGradeStuItemCheckedCallBack {
        public void getCheckedMap(HashMap<Integer, Boolean> map);
    }

    OnUpLoadGradeStuItemCheckedCallBack onUpLoadGradeStuItemCheckedCallBack;

    public UploadGradeStuListAdapter(Context mContext, OnUpLoadGradeStuItemCheckedCallBack onUpLoadGradeStuItemCheckedCallBack, UploadGradeStuBean uploadGradeStuBean) {
        this.mContext = mContext;
        this.onUpLoadGradeStuItemCheckedCallBack = onUpLoadGradeStuItemCheckedCallBack;
        this.uploadGradeStuBean = uploadGradeStuBean;

        isCheckedMap = new HashMap<>();
//        mChecked = new ArrayList<>();
//        for (int i = 0; i < uploadGradeStuBean.getData_list().size(); i++) {
//            mChecked.add(false);
//        }
        initData();
    }

    private void initData() {
        for (int i = 0; i < uploadGradeStuBean.getData_list().size(); i++) {
            getIsCheckedMap().put(i, false);
        }
    }

    public void setItems(List<UploadGradeStuBean.DataListBean> stuList) {
        this.stuList.clear();
        this.stuList = stuList;
        notifyDataSetChanged();
    }

    public static HashMap<Integer, Boolean> getIsCheckedMap() {
        return isCheckedMap;
    }

    public static void setIsCheckedMap(HashMap<Integer, Boolean> isCheckedMap) {
        UploadGradeStuListAdapter.isCheckedMap = isCheckedMap;
    }

    @Override
    public int getCount() {
        return uploadGradeStuBean.getData_list() == null ? 0 : uploadGradeStuBean.getData_list().size();
    }

    @Override
    public Object getItem(int i) {
        return uploadGradeStuBean.getData_list().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.upload_grade_student_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(uploadGradeStuBean.getData_list().get(i).getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(viewHolder.imvUploadGradeStuHead);
//        new ImageLoaderHelper(mContext).loadImage(uploadGradeStuBean.getData_list().get(i).getStu_photo(), viewHolder.imvUploadGradeStuHead);
        viewHolder.tvUploadGradeStuName.setText(uploadGradeStuBean.getData_list().get(i).getStu_name());
        viewHolder.tvUploadGradeStuIdNum.setText(uploadGradeStuBean.getData_list().get(i).getStu_idnum());
        viewHolder.tvUploadGradeStuSignUpDate.setText(uploadGradeStuBean.getData_list().get(i).getOrder_date());
        viewHolder.tvUploadGradeStuPhone.setText(uploadGradeStuBean.getData_list().get(i).getStu_phone());


        if (isCheckedMap.get(i) != null && isCheckedMap.get(i)) {
            viewHolder.cbUploadGradeStuItem.setChecked(true);
        } else {
            viewHolder.cbUploadGradeStuItem.setChecked(false);
        }
        viewHolder.cbUploadGradeStuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCheckedMap.get(i) != null) {
                    if (isCheckedMap.get(i)) {
                        isCheckedMap.put(i, false);
                        setIsCheckedMap(isCheckedMap);
                    } else {
                        isCheckedMap.put(i, true);
                        setIsCheckedMap(isCheckedMap);
                    }
                }
            }
        });
//        viewHolder.cbUploadGradeStuItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {
//                    mChecked.set(i, true);
//                } else {
//                    mChecked.set(i, false);
//                }
//            }
//        });
        /*if (isTestOption) {
            if (waitOptionTestStuBean.getData_list().get(i).getStu_detarec_remind_flag() == 1) {
                //提交过
                viewHolder.cbWaitTestStuItem.setVisibility(View.GONE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.VISIBLE);
            } else {
                //未提交过
                viewHolder.cbWaitTestStuItem.setVisibility(View.VISIBLE);
                viewHolder.imvStuItemIsAppointing.setVisibility(View.GONE);
            }
        } else {*/
            if (TextUtils.isEmpty(uploadGradeStuBean.getData_list().get(i).getScoreForCheck())) {
                //没提交
                viewHolder.relaUploadGrade.setVisibility(View.VISIBLE);
                viewHolder.auditing.setVisibility(View.GONE);
            } else {
                //提交成绩
                viewHolder.relaUploadGrade.setVisibility(View.GONE);
                viewHolder.auditing.setVisibility(View.VISIBLE);
            }
//        }
        onUpLoadGradeStuItemCheckedCallBack.getCheckedMap(isCheckedMap);
        if (getIsCheckedMap() != null) {
            if (getIsCheckedMap().get(i) != null) {
                viewHolder.cbUploadGradeStuItem.setChecked(getIsCheckedMap().get(i));            }
        }
        viewHolder.imvUploadGradeStuItemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(uploadGradeStuBean.getData_list().get(i).getStu_phone(), uploadGradeStuBean.getData_list().get(i).getStu_name(), mContext);
            }
        });
        return view;
    }

    static class ViewHolder {
        @Bind(R.id.imv_upload_grade_stu_head)
        ImageView imvUploadGradeStuHead;
        @Bind(R.id.tv_upload_grade_stu_name)
        TextView tvUploadGradeStuName;
        @Bind(R.id.tv_upload_grade_stu_id_num)
        TextView tvUploadGradeStuIdNum;
        @Bind(R.id.tv_upload_grade_stu_phone)
        TextView tvUploadGradeStuPhone;
        @Bind(R.id.imv_upload_grade_stu_item_phone)
        ImageView imvUploadGradeStuItemPhone;
        @Bind(R.id.tv_upload_grade_stu_sign_up_date)
        TextView tvUploadGradeStuSignUpDate;
        @Bind(R.id.cb_upload_grade_stu_item)
        CheckBox cbUploadGradeStuItem;
        @Bind(R.id.item_uploadgrade_appointing)
        ImageView auditing;
@Bind(R.id.item_rela_uploadgrade)
RelativeLayout relaUploadGrade;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
