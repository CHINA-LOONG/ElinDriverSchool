package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.StudentPreviewActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.CheckTemplateTimeBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.MyOkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.CheckStudentGradeActivity.MEDIA_TYPE_JSON;

/**
 * Created by 17535 on 2017/9/4.
 */

public class AddAppointAdapter extends BaseQuickAdapter<CheckTemplateTimeBean.DataBean,BaseViewHolder> {
    Context context;
    CheckTemplateInsideAdapter adapter;
    RecyclerView recyclerView;
    HashMap<Integer, Boolean> states;
    List<CheckTemplateTimeBean.DataBean.TimeListBean> dataList = new ArrayList<>();
    public AddAppointAdapter(Context context, List<CheckTemplateTimeBean.DataBean> data) {
        super(R.layout.item_check_time_template,data);
        this.context = context;
        states = new HashMap<>();
    }

    @Override
    public void setNewData(List<CheckTemplateTimeBean.DataBean> data) {
        super.setNewData(data);
        for (int i = 0; i < data.size(); i++) {
            states.put(i,false);
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CheckTemplateTimeBean.DataBean item) {
        final CheckBox cbAppointTemplate = helper.getView(R.id.cb_appoint_template);
//        cbAppointTemplate.setEnabled(false);
        CardView cardView = helper.getView(R.id.item_cv_select_template);
        final TextView releaseAppointTemplate = helper.getView(R.id.item_delete_appoint_template);
        cbAppointTemplate.setVisibility(View.VISIBLE);
        releaseAppointTemplate.setVisibility(View.GONE);
        releaseAppointTemplate.setText("发布");
        adapter = new CheckTemplateInsideAdapter(context,dataList);
        recyclerView = helper.getView(R.id.item_rv_check_template);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        adapter.setNewData(item.getTimeList());
        helper.setText(R.id.item_tv_create_template_time,item.getCreate_time())
                .addOnClickListener(R.id.item_delete_appoint_template);

        final int position = helper.getPosition()-1;
        cbAppointTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Integer key : states.keySet()) {
                    states.put(key, false);
                    Log.e("key",key+"");
                }
                Log.e("position",position+"");
                if (states.get(position) != null && states.get(position)) {
                    states.put(position, false);
                } else {
                    states.put(position, true);
                }
                notifyDataSetChanged();
            }
        });

//       recyclerView.addOnItemTouchListener(new com.chad.library.adapter.base.listener.OnItemChildClickListener() {
//           @Override
//           public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//               if(helper.getPosition()==position){
//                   CheckTemplateTimeBean.DataBean.TimeListBean bean = (CheckTemplateTimeBean.DataBean.TimeListBean) adapter.getItem(position);
//                   Bundle bundle = new Bundle();
//                   bundle.putString("modelId",bean.getId());
//                   bundle.putString("trainSub",bean.getTrain_sub());
//                   bundle.putString("startDay", BaseApplication.getInstance().getStartDay());
//                   Intent intent = new Intent(context,StudentPreviewActivity.class);
//                   intent.putExtras(bundle);
//                   context.startActivity(intent);
//               }
//
//           }
//       });
        boolean res = false;
        if (states.get(position) == null
                || !states.get(position)) {
            res = false;
            states.put(position, false);
        } else {
            res = true;
        }
        cbAppointTemplate.setChecked(res);
        if(cbAppointTemplate.isChecked()){
            releaseAppointTemplate.setVisibility(View.VISIBLE);
        }else {
            releaseAppointTemplate.setVisibility(View.GONE);
        }
    }

}
