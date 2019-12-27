package com.elin.elindriverschool.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MessageBean;
import com.elin.elindriverschool.model.NoticeBean;
import com.elin.elindriverschool.model.PreSucStuTestBean;
import com.elin.elindriverschool.util.ImageLoaderHelper;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.adapter.MessageListAdapter.MEDIA_TYPE_MARKDOWN;

/**
 * Created by imac on 17/1/4.
 */

public class PreSucStuWaitTestListAdapter extends BaseAdapter {

    private Context mContext;
    private PreSucStuTestBean preSucStuTestBean;
    private Map<String, Object> parmasMap = new HashMap<>();
    private int location;
    private String responseJson;

    public PreSucStuWaitTestListAdapter(Context mContext, PreSucStuTestBean preSucStuTestBean) {
        this.mContext = mContext;
        this.preSucStuTestBean = preSucStuTestBean;
    }

    @Override
    public int getCount() {
        return preSucStuTestBean != null ? preSucStuTestBean.getData_list().size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return preSucStuTestBean.getData_list().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.student_item_pre_wait_test, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(preSucStuTestBean.getData_list().get(i).getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(viewHolder.imvStuItemPreWaitTestHead);
//        new ImageLoaderHelper(mContext).loadImage(preSucStuTestBean.getData_list().get(i).getStu_photo(), viewHolder.imvStuItemPreWaitTestHead);
        viewHolder.tvStuItemPreWaitTestName.setText(preSucStuTestBean.getData_list().get(i).getStu_name());
        viewHolder.tvStuItemPreWaitTestDate.setText("考试时间：" + preSucStuTestBean.getData_list().get(i).getOrder_date());
        viewHolder.tvStuItemPreWaitTestNum.setText(preSucStuTestBean.getData_list().get(i).getStu_idnum());
        viewHolder.tvStuItemPreWaitTestSite.setText(preSucStuTestBean.getData_list().get(i).getOrder_site());
        if (preSucStuTestBean.getData_list().get(i).getOrder_bus().equals("1")) {
            viewHolder.tvStuItemPreWaitTestTakeBus.setText("是");
        } else {
            viewHolder.tvStuItemPreWaitTestTakeBus.setText("否");
        }
        viewHolder.rlStuItemPreWaitTestPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobilePhoneUtils.makeCall(preSucStuTestBean.getData_list().get(i).getStu_phone(), preSucStuTestBean.getData_list().get(i).getStu_name(),mContext);
            }
        });
        viewHolder.tvCancleAppointment.setEnabled(true);
        viewHolder.tvCancleAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> idList = new ArrayList<>();
                location = i;
                idList.add(preSucStuTestBean.getData_list().get(i).getInner_id());
                showNormalDialog(mContext,
                        "取消预约",
                        "是否取消"+ preSucStuTestBean.getData_list().get(i).getStu_name()+"的考试预约",
                        idList,
                        preSucStuTestBean.getData_list().get(i).getOrder_sub()
                );
            }
        });
        return view;
    }


    static class ViewHolder {
        @Bind(R.id.imv_stu_item_pre_wait_test_head)
        ImageView imvStuItemPreWaitTestHead;
        @Bind(R.id.tv_stu_item_pre_wait_test_name)
        TextView tvStuItemPreWaitTestName;
        @Bind(R.id.rl_stu_item_pre_wait_test_phone)
        RelativeLayout rlStuItemPreWaitTestPhone;
        @Bind(R.id.tv_stu_item_pre_wait_test_num)
        TextView tvStuItemPreWaitTestNum;
        @Bind(R.id.tv_stu_item_pre_wait_test_date)
        TextView tvStuItemPreWaitTestDate;
        @Bind(R.id.tv_stu_item_pre_wait_test_site)
        TextView tvStuItemPreWaitTestSite;
        @Bind(R.id.tv_stu_item_pre_wait_test_take_bus)
        TextView tvStuItemPreWaitTestTakeBus;
        @Bind(R.id.item_tv_cancle_appointment)
        TextView tvCancleAppointment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void showNormalDialog(Context context, String title, String msg, final List<String> stuList, final String sub){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle(title);
        normalDialog.setMessage(msg);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancleAppoint(stuList,sub);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void cancleAppoint(List<String> stuList,String sub) {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("stu_list", stuList);
        parmasMap.put("order_sub", sub);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.EXAMSUB_ORDER_CANCLE)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(mContext).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(1);
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    responseJson = String.valueOf(response.body().string());
                    mHandler.sendEmptyMessage(0);
                    call.cancel();
                }
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(responseJson!=null){
                        CommonDataBean commonDataBean = new Gson().fromJson(responseJson, CommonDataBean.class);
                        if(TextUtils.equals("0",commonDataBean.getRc())){
                            ToastUtils.ToastMessage(mContext,"取消成功");
                            preSucStuTestBean.getData_list().remove(location);
                            notifyDataSetChanged();
                        }else {
                            ToastUtils.ToastMessage(mContext,commonDataBean.getDes());
                        }
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(mContext, "网络连接出现问题");
                    break;

            }
        }
    };

}
