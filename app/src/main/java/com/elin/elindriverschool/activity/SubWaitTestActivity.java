package com.elin.elindriverschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.WaitTestAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.WaitOptionTestStuBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 科目代考人员
 */
public class SubWaitTestActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,WaitTestAdapter.OnStuItemCheckedCallBack {

    @Bind(R.id.rv_wait_test)
    RecyclerView rvWaitTest;
    @Bind(R.id.srl_wait_test)
    SwipeRefreshLayout srlWaitTest;
    @Bind(R.id.indexBar_contact)
    IndexBar indexBarContact;
    @Bind(R.id.tvSideBarHint_contact)
    TextView tvSideBarHintContact;
    @Bind(R.id.fab_operate)
    TextView fabOperate;
    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;

    private Map<String, String> paramsMap = new HashMap<>();
    private Map<String, Object> stuSubmitParamMap = new HashMap<>();
    private String paramsJson, paramsSubmitJson;
    private Gson gson = new Gson();
    public static final MediaType MEDIA_TYPE_JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String responseJson;
    private WaitOptionTestStuBean waitOptionTestStuBean;
    private WaitTestAdapter adapter;
    List<WaitOptionTestStuBean.DataListBean> data_list = new ArrayList<>();
    private List<WaitOptionTestStuBean.DataListBean> checkedList = new ArrayList<>();//选中的学员

    private LinearLayoutManager mManger;
    private SuspensionDecoration mDecoration;
    private Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_wait_test);
        ButterKnife.bind(this);
        tvCusTitleName.setText("全部待考学员");
        srlWaitTest.setOnRefreshListener(this);
        srlWaitTest.setColorSchemeResources(android.R.color.holo_red_light);
        srlWaitTest.setRefreshing(true);
        adapter = new WaitTestAdapter(data_list, this, SubWaitTestActivity.this, false,"1");
        mDecoration = new SuspensionDecoration(this,data_list);
        adapter.openLoadAnimation();
        mManger = new LinearLayoutManager(this);
        rvWaitTest.setLayoutManager(mManger);
        rvWaitTest.setAdapter(adapter);
        rvWaitTest.setLayoutManager(mManger);
        rvWaitTest.addItemDecoration(mDecoration);
        rvWaitTest.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        indexBarContact.setmPressedShowTextView(tvSideBarHintContact)
                .setNeedRealIndex(true)
                .setmLayoutManager(mManger);
        getWaitTestClassOneStu();
        rvWaitTest.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                intent.setClass(SubWaitTestActivity.this, StudentInfoActivity.class);
                intent.putExtra("stuIdNum", bean.getStu_idnum());
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()){
                    case R.id.imv_wait_test_item_phone:
                        WaitOptionTestStuBean.DataListBean bean = (WaitOptionTestStuBean.DataListBean) adapter.getItem(position);
                        MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(),SubWaitTestActivity.this);
                        break;
                }
            }
        });
    }

    //获取学员列表
    private void getWaitTestClassOneStu() {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("exam_sub", "0");//科目一
        paramsMap.put("school_id", BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.WAIT_TEST_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, paramsJson))
                .build();
        Call call = new MyOkHttpClient(BaseApplication.getInstance()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(0);
                call.cancel();
            }
        });
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            srlWaitTest.setRefreshing(false);
            switch (msg.what) {
                case 0:
                    Log.e("请求学员返回的Json-->", String.valueOf(responseJson));
                    if (srlWaitTest.isRefreshing()) {
                        srlWaitTest.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        waitOptionTestStuBean = gson.fromJson(responseJson, WaitOptionTestStuBean.class);
                        if (waitOptionTestStuBean.getRc().equals("0")) {
                            data_list = waitOptionTestStuBean.getData_list();
                            if (data_list != null && data_list.size() != 0) {
                                adapter.setNewData(data_list);
                                adapter.loadMoreComplete();
                                indexBarContact.setmSourceDatas(data_list).invalidate();
                                mDecoration.setmDatas(data_list);
                            } else {
                                adapter.setNewData(data_list);
                                adapter.loadMoreEnd(true);
                            }
                        } else if ("3000".equals(waitOptionTestStuBean.getRc())) {
                            LogoutUtil.clearData(SubWaitTestActivity.this);
                            goToActivity(SubWaitTestActivity.this, LoginActivity.class);
                            ToastUtils.ToastMessage(SubWaitTestActivity.this, waitOptionTestStuBean.getDes());
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onRefresh() {
        getWaitTestClassOneStu();
    }

    @OnClick({R.id.imv_cus_title_back, R.id.fab_operate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.fab_operate:
                checkedList = adapter.getCheckList();
                if (checkedList.size() > 0) {
                    Intent intent = new Intent(this, StudentOperationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "待考学员");
                    bundle.putParcelableArrayList("dataList", (ArrayList<? extends Parcelable>) checkedList);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 100);
                } else {
                    Toast.makeText(this, "请选择学员", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void getCheckedMap(HashMap<Integer, Boolean> map) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
//            onRefresh();
            adapter.clearStates();
            checkedList.clear();
            adapter.notifyDataSetChanged();

        }
    }
}
