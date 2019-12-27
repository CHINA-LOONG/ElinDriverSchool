package com.elin.elindriverschool.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.ContactListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.ContactBean;
import com.elin.elindriverschool.model.DriveContactBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

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

import static com.elin.elindriverschool.activity.LoginActivity.MEDIA_TYPE_MARKDOWN;

/**
 * Created by imac on 17/1/10.
 */

public class ContactsActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_contact_activity)
    RecyclerView rvContactActivity;
    @Bind(R.id.indexBar_contact_activity)
    IndexBar indexBarContactActivity;
    @Bind(R.id.tvSideBarHint_contact_activity)
    TextView tvSideBarHintContactActivity;


    private MyProgressDialog progressDialog;
    private String contactJson;
    private ContactBean contactBean;
    private Gson gson = new Gson();
    private LinearLayoutManager mManger;
    private List<DriveContactBean> driveContactBeanList = new ArrayList<>();
    DriveContactBean driveContactBean;
    private SuspensionDecoration mDecoration;

    private ContactListAdapter mAdpter;
    private String [] nameArray;
    private static final String INDEX_STRING_TOP = "#";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        imvCusTitleBack.setOnClickListener(this);
        tvCusTitleName.setText("电话簿");
        progressDialog = new MyProgressDialog(this,R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        mManger = new LinearLayoutManager(this);
        rvContactActivity.setLayoutManager(mManger);

        indexBarContactActivity.setmPressedShowTextView(tvSideBarHintContactActivity)
                .setNeedRealIndex(true)
                .setmLayoutManager(mManger);
        getDriveSchoolContact();
    }
    private void getDriveSchoolContact(){
        progressDialog.show();
        if (driveContactBeanList.size()!=0){
            driveContactBeanList.clear();
        }
        Map<String,String> parmsMap = new HashMap<>();
        parmsMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_CONTACT_LIST)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmsMap)))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
                contactJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("获取联系人Json-->",contactJson);
                    contactBean = gson.fromJson(contactJson,ContactBean.class);
                    if (contactBean.getRc()==0){

                        for (int j = 0; j < contactBean.getDepartment_list().size(); j++) {
                            driveContactBean = new DriveContactBean();
                            driveContactBean.setName(contactBean.getDepartment_list().get(j).getDepartment_name()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                            driveContactBean.setPhone(contactBean.getDepartment_list().get(j).getDepartment_phone()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                            driveContactBean.setPhoto(contactBean.getDepartment_list().get(j).getDepartment_photo()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                            driveContactBeanList.add(driveContactBean);

                        }
                        for (int i = 0; i <contactBean.getUser_list().size() ; i++) {
                            driveContactBean = new DriveContactBean();
                            driveContactBean.setName(contactBean.getUser_list().get(i).getUser_name());
                            driveContactBean.setPhone(contactBean.getUser_list().get(i).getUser_phone());
                            driveContactBean.setPhoto(contactBean.getUser_list().get(i).getUser_photo());
                            driveContactBeanList.add(driveContactBean);
                        }
//
                        mAdpter = new ContactListAdapter(driveContactBeanList,ContactsActivity.this);
                        rvContactActivity.setAdapter(mAdpter);
                        mDecoration = new SuspensionDecoration(ContactsActivity.this,driveContactBeanList);
                        rvContactActivity.addItemDecoration(mDecoration);
                        rvContactActivity.addItemDecoration(new DividerItemDecoration(ContactsActivity.this,DividerItemDecoration.VERTICAL_LIST));
                        mAdpter.notifyDataSetChanged();
                        indexBarContactActivity.setmSourceDatas(driveContactBeanList).invalidate();
                        mDecoration.setmDatas(driveContactBeanList);

                        mAdpter.setOnItemClickLitener(new ContactListAdapter.OnItemClickLitener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                MobilePhoneUtils.makeCall(driveContactBeanList.get(position).getPhone(),driveContactBeanList.get(position).getName(),ContactsActivity.this);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {

                            }
                        });
                    }else {
                        ToastUtils.ToastMessage(ContactsActivity.this,"获取失败，请重试");
                    }


                    break;
            }
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imv_cus_title_back:
                ContactsActivity.this.finish();
                break;
        }
    }
}
