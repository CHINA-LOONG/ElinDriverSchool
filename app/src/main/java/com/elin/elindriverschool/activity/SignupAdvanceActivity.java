package com.elin.elindriverschool.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.PreSignUpAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.PreSignupStudentBean;
import com.elin.elindriverschool.util.MobilePhoneUtils;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

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
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MyDriveStudentActivity.MEDIA_TYPE_JSON;

/**
 * 学员预报名
 */
public class SignupAdvanceActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_signup_advance)
    RecyclerView rvSignupAdvance;
    @Bind(R.id.srl_signup_advance)
    SwipeRefreshLayout srlSignupAdvance;

    public static final int REQUESTCODE = 100;
    private Map<String, String> paramsMap = new HashMap<>();
    private String responseJson;
    private Gson gson = new Gson();
    private int rowBegin = 1;
    private List<PreSignupStudentBean.PreStuListBean> data_list = new ArrayList<>();
    private PreSignUpAdapter adapter;
    private PreSignupStudentBean bean;
    private int location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_advance);
        ButterKnife.bind(this);
        tvCusTitleName.setText("学员预报名");
        tvCusTitleRight.setText("报名");

        adapter = new PreSignUpAdapter(data_list,this);
        adapter.openLoadAnimation();
        adapter.setOnLoadMoreListener(this);
        srlSignupAdvance.setRefreshing(true);
        srlSignupAdvance.setOnRefreshListener(this);
        srlSignupAdvance.setColorSchemeResources(android.R.color.holo_red_light);
        rvSignupAdvance.setLayoutManager(new LinearLayoutManager(SignupAdvanceActivity.this));
        rvSignupAdvance.setAdapter(adapter);
        rvSignupAdvance.addItemDecoration(new DividerItemDecoration(SignupAdvanceActivity.this, DividerItemDecoration.VERTICAL_LIST));

        getMyDriveStus(rowBegin);
        rvSignupAdvance.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                PreSignupStudentBean.PreStuListBean bean = (PreSignupStudentBean.PreStuListBean) adapter.getItem(position);
                switch (view.getId()){
                    case R.id.item_iv_phone:
                        MobilePhoneUtils.makeCall(bean.getStu_phone(), bean.getStu_name(), SignupAdvanceActivity.this);
                        break;
                    case R.id.item_tv_del_stu:
                        location = position;
                        showDelDialog(bean.getId());
                        break;
                }
            }
        });
    }
    private void showDelDialog(final String stuId){
        final AlertDialog.Builder delDialog =
                new AlertDialog.Builder(this);
        delDialog.setTitle("删除");
        delDialog.setMessage("删除该预报名学员吗?");
        delDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delPreStu(stuId);
                    }
                });
        delDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        delDialog.show();
    }

    //删除预报名学员
    public void delPreStu(String stuId){
        Map<String, String> delMap = new HashMap<>();
        delMap.put("token", BaseApplication.getInstance().getToken());
        delMap.put("id", stuId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DEL_PRE_STU)
                .post(RequestBody.create(CheckStudentGradeActivity.MEDIA_TYPE_JSON, gson.toJson(delMap)))
                .build();
        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
                mHandler.sendEmptyMessage(3);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                mHandler.sendEmptyMessage(2);
                call.cancel();
            }
        });
    }
    @OnClick({R.id.imv_cus_title_back, R.id.tv_cus_title_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_cus_title_right:
                Intent intent = new Intent(this,SignupActivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }
    private void getMyDriveStus(int pageNo) {
        paramsMap.put("token", BaseApplication.getInstance().getToken());
        paramsMap.put("pageno", pageNo + "");
        paramsMap.put("pagesize", "20");

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_PRE_STU)
                .post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(paramsMap)))
                .build();
        Call call = new MyOkHttpClient(SignupAdvanceActivity.this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());

                mHandler.sendEmptyMessage(1);
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
            switch (msg.what) {
                case 0:
                    Log.e("请求学员返回的Json-->", String.valueOf(responseJson));
                    if(srlSignupAdvance!=null){
                        srlSignupAdvance.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        bean = gson.fromJson(responseJson, PreSignupStudentBean.class);
                        if (bean.getRc().equals("0")) {
                            data_list = bean.getPreStuList();
                            if (data_list != null&&data_list.size()!=0) {
                                if (rowBegin == 1) {
                                    adapter.setNewData(data_list);
                                    srlSignupAdvance.setRefreshing(false);
                                    adapter.loadMoreComplete();
                                } else {
                                    adapter.addData(data_list);
                                    adapter.loadMoreComplete();
                                }
                            }else {
                                if(rowBegin==1){
                                    Log.e("1-->", responseJson);
                                    adapter.setEmptyView(R.layout.layout_empty,rvSignupAdvance);
                                    adapter.setNewData(data_list);
                                    adapter.loadMoreEnd(true);
                                }else {
                                    adapter.loadMoreEnd(false);
                                }
                            }
                        } else {
                            Log.e("2-->", responseJson);
                            adapter.setEmptyView(R.layout.layout_empty,rvSignupAdvance);
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_empty,rvSignupAdvance);
                    ToastUtils.ToastMessage(SignupAdvanceActivity.this,"请检查网络连接");
                    break;
                case 2:
                    ToastUtils.ToastMessage(SignupAdvanceActivity.this,"删除成功");
                    adapter.setEmptyView(R.layout.layout_empty,rvSignupAdvance);
                    adapter.remove(location);
                    break;
                case 3:
                    ToastUtils.ToastMessage(SignupAdvanceActivity.this,"请检查网络连接");
                    break;
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE&&resultCode==RESULT_OK){
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        adapter.setEnableLoadMore(false);
        rowBegin = 1;
        getMyDriveStus(rowBegin);
        srlSignupAdvance.setRefreshing(false);
        adapter.removeAllFooterView();
    }

    @Override
    public void onLoadMoreRequested() {
        rowBegin = rowBegin + 1;
        getMyDriveStus(rowBegin);
    }
}
