package com.elin.elindriverschool.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.adapter.ContactListAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.ContactBean;
import com.elin.elindriverschool.model.DriveContactBean;
import com.elin.elindriverschool.sharedpreferences.PreferenceManager;
import com.elin.elindriverschool.util.AppSPUtil;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;
import com.mcxtzhang.indexlib.IndexBar.helper.IndexBarDataHelperImpl;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.BaseFragment.goToActivity;
import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

public class ContactFragment extends Fragment {

    View view;
    RecyclerView rvContact;
    IndexBar indexBarContact;
    TextView tvSideBarHintContact;

    private MyProgressDialog progressDialog;
    private String contactJson = "";
    private ContactBean contactBean;
    private Gson gson = new Gson();
    private LinearLayoutManager mManger;
    private List<DriveContactBean> driveContactBeanList = new ArrayList<>();
    DriveContactBean driveContactBean;
    private SuspensionDecoration mDecoration;

    private ContactListAdapter mAdpter;
    private String[] nameArray;

    private static final String INDEX_STRING_TOP = "#";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        rvContact = (RecyclerView) view.findViewById(R.id.rv_contact);
        indexBarContact = (IndexBar) view.findViewById(R.id.indexBar_contact);
        tvSideBarHintContact = (TextView) view.findViewById(R.id.tvSideBarHint_contact);
        progressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        mManger = new LinearLayoutManager(getActivity());
        rvContact.setLayoutManager(mManger);
        indexBarContact.setmPressedShowTextView(tvSideBarHintContact)
                .setNeedRealIndex(true)
                .setmLayoutManager(mManger);
        if (AppSPUtil.get(getActivity(), PreferenceManager.CONTACTS,"") != null) {
            contactJson =(String) AppSPUtil.get(getActivity(),PreferenceManager.CONTACTS,"");
        }

        getDriveSchoolContact();
        return view;
    }

    private void getDriveSchoolContact() {
        progressDialog.show();
        if (driveContactBeanList.size() != 0) {
            driveContactBeanList.clear();
        }
        Map<String,String> map = new HashMap<>();
        map.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_CONTACT_LIST)
                .post(RequestBody.create(MEDIA_TYPE_JSON,new Gson().toJson(map)))
                .build();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                contactJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            IndexBarDataHelperImpl ibdhi = new IndexBarDataHelperImpl();
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    contactBean = gson.fromJson(contactJson, ContactBean.class);
                    //存入缓存
                    if(!TextUtils.isEmpty(contactJson)){
                        AppSPUtil.put(getActivity(),PreferenceManager.CONTACTS,contactJson);
                    }
                    if (contactBean != null) {
                        if (contactBean.getRc() == 0) {
                            for (int j = 0; j < contactBean.getDepartment_list().size(); j++) {
                                driveContactBean = new DriveContactBean();
                                driveContactBean.setName(contactBean.getDepartment_list().get(j).getDepartment_name()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                                driveContactBean.setPhone(contactBean.getDepartment_list().get(j).getDepartment_phone()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                                driveContactBean.setPhoto(contactBean.getDepartment_list().get(j).getDepartment_photo()).setTop(true).setBaseIndexTag(INDEX_STRING_TOP);
                                driveContactBeanList.add(driveContactBean);
                            }
                            for (int i = 0; i < contactBean.getUser_list().size(); i++) {
                                driveContactBean = new DriveContactBean();
                                driveContactBean.setName(contactBean.getUser_list().get(i).getUser_name());
                                driveContactBean.setPhone(contactBean.getUser_list().get(i).getUser_phone());
                                driveContactBean.setPhoto(contactBean.getUser_list().get(i).getUser_photo());
                                driveContactBeanList.add(driveContactBean);
                            }
                            mAdpter = new ContactListAdapter(driveContactBeanList, getActivity());
                            rvContact.setAdapter(mAdpter);
                            mDecoration = new SuspensionDecoration(getActivity(), driveContactBeanList);
                            rvContact.addItemDecoration(mDecoration);
                            rvContact.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
                            mAdpter.notifyDataSetChanged();
                            indexBarContact.setmSourceDatas(driveContactBeanList).invalidate();
                            mDecoration.setmDatas(driveContactBeanList);

                            mAdpter.setOnItemClickLitener(new ContactListAdapter.OnItemClickLitener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    MobilePhoneUtils.makeCall(driveContactBeanList.get(position).getPhone(), driveContactBeanList.get(position).getName(), getActivity());

                                }

                                @Override
                                public void onItemLongClick(View view, int position) {

                                }
                            });

                        }else if(contactBean.getRc()==3000){
                            LogoutUtil.clearData(getActivity());
                            goToActivity(getActivity(),LoginActivity.class);
                            ToastUtils.ToastMessage(getActivity(), contactBean.getDes());
                        }

                    }

                    break;
                case 1:
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroyView();
    }
}
