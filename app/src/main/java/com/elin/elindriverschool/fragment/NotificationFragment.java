package com.elin.elindriverschool.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.MessageDetailActivity;
import com.elin.elindriverschool.activity.NoticeStuListActivity;
import com.elin.elindriverschool.adapter.MessageListAdapter;
import com.elin.elindriverschool.adapter.NoticeAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.DividerItemDecoration;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.MessageBean;
import com.elin.elindriverschool.model.NoticeBean;
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

import static android.app.Activity.RESULT_OK;
import static com.elin.elindriverschool.fragment.FragmentMsg.MEDIA_TYPE_MARKDOWN;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{


    @Bind(R.id.rv_notification)
    RecyclerView rvNotification;
    @Bind(R.id.srl_notification)
    SwipeRefreshLayout srlNotification;

    private String flag;
    List<MessageBean.DataListBean> msgList = new ArrayList<>();
    List<NoticeBean.NoticeInfoBean> noticeList = new ArrayList<>();
    private MessageListAdapter msgAdapter;
    private NoticeAdapter noticeAdapter;
    private String responseJson;
    private Map<String, String> parmasMap = new HashMap<>();
    int rowBegin = 1;
    int rowMax = 20;
    private PopupMenu popupMenu;
    private Menu menu;
    private int location;
    private int msgLocation;
    public NotificationFragment() {
        // Required empty public constructor
    }
    public static NotificationFragment newInstance(String flag) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        flag = getArguments().getString("flag");
        srlNotification.setOnRefreshListener(this);
        srlNotification.setColorSchemeResources(android.R.color.holo_red_light);
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotification.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));
        if(TextUtils.equals("0",flag)){
            msgAdapter = new MessageListAdapter(msgList, getActivity());
            msgAdapter.openLoadAnimation();
            msgAdapter.setOnLoadMoreListener(this);
            msgAdapter.setEmptyView(R.layout.layout_empty,rvNotification);
            rvNotification.setAdapter(msgAdapter);
            loadData(Constant.GET_REMINDER_LIST,flag, rowBegin);
            rvNotification.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    msgLocation = position;
                    TextView tvTitle = (TextView) view.findViewById(R.id.item_tv_message_title);
                    TextView tvDate = (TextView) view.findViewById(R.id.item_tv_message_date);
                    TextView tvContent = (TextView) view.findViewById(R.id.item_tv_message_content);
                    MessageBean.DataListBean bean = (MessageBean.DataListBean) adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("reminderId",bean.getReminder_id());
                    bundle.putString("date",bean.getReminder_date());
                    bundle.putString("content",bean.getReminder_content());
                    bundle.putString("title",bean.getReminder_title());
                    bundle.putInt("position",position);
                    Intent intent = new Intent(getActivity(),MessageDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,100);
                    if(2==bean.getReminder_status()){
                        markRead(bean.getReminder_id());
                    }
                    bean.setReminder_status(1);
                    tvTitle.setTextColor(Color.LTGRAY);
                    tvDate.setTextColor(Color.LTGRAY);
                    tvContent.setTextColor(Color.LTGRAY);
                }
            });
        }else {
            noticeAdapter = new NoticeAdapter(noticeList, getActivity());
            noticeAdapter.openLoadAnimation();
            noticeAdapter.setOnLoadMoreListener(this);
            noticeAdapter.setEmptyView(R.layout.layout_empty,rvNotification);
            rvNotification.setAdapter(noticeAdapter);
            loadData(Constant.GET_NOTICES_BY_TYPE_ID,flag, rowBegin);
            rvNotification.addOnItemTouchListener(new OnItemLongClickListener() {
                @Override
                public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                    location = position;
                    final NoticeBean.NoticeInfoBean noticeInfoBean = (NoticeBean.NoticeInfoBean) adapter.getItem(position);
                    if(TextUtils.equals("0",noticeInfoBean.getNo_read_count())){
                        popupMenu = new PopupMenu(getActivity(), adapter.getViewByPosition(rvNotification,position,R.id.item_tv_notice_date),Gravity.CENTER);
                        menu = popupMenu.getMenu();
                        // 通过XML文件添加菜单项
                        MenuInflater menuInflater = getActivity().getMenuInflater();
                        menuInflater.inflate(R.menu.menu_del_notice, menu);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.menu_del_notice:
                                        delNotice(noticeInfoBean.getId());
                                        break;
                                }
                                return false;
                            }
                        });
                        popupMenu.show();
                    }else {
                        ToastUtils.ToastMessage(getActivity(),"该消息未读不可删除");
                    }
                }

                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    super.onItemChildLongClick(adapter, view, position);
                    NoticeBean.NoticeInfoBean noticeInfoBean = (NoticeBean.NoticeInfoBean) adapter.getItem(position);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",noticeInfoBean.getTitle());
                    bundle.putString("detail_id",noticeInfoBean.getId());
                    goToActivity(getActivity(), NoticeStuListActivity.class,bundle);
                }
            });
        }
        return view;
    }
    public void markRead(String reminderId){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getInstance().getToken());
        params.put("reminder_id", reminderId);
        params.put("del_flag","0");
        params.put("school_id", BaseApplication.getInstance().getSchoolId());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.READ_MESSAGES)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(params)))
                .build();
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("请求失败-->", e.toString() + "\n" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    call.cancel();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&&resultCode ==RESULT_OK){
            msgAdapter.remove(msgLocation);
        }
    }

    private void delNotice(String detailId) {
        srlNotification.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("detail_id", detailId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.DEL_REPLY)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(getActivity()).newCall(request);
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
                    mHandler.sendEmptyMessage(2);
                    call.cancel();
                }
            }
        });
    }

    private void loadData(String url,String id, int pageNo) {
        srlNotification.setRefreshing(true);
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("pageno", pageNo + "");
        parmasMap.put("pagesize", rowMax + "");
        parmasMap.put("type_id", id + "");
//        parmasMap.put("reminder_status", 0 + "");
        Request request = new Request.Builder()
                .url(Constant.ServerURL + url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(getActivity()).newCall(request);
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
                    if (srlNotification != null&&srlNotification.isRefreshing()) {
                        srlNotification.setRefreshing(false);
                    }
                    if (responseJson != null) {
                        if (TextUtils.equals("0",flag)) {
                            MessageBean bean = new Gson().fromJson(responseJson, MessageBean.class);
                            if (bean.getRc() == 0) {
                                msgList = bean.getData_list();
                                if (msgList != null && msgList.size() != 0) {
                                    if (rowBegin == 1) {
                                        msgAdapter.setNewData(msgList);
                                    } else {
                                        msgAdapter.addData(msgList);
                                    }
                                    msgAdapter.loadMoreComplete();
                                } else {
                                    if (rowBegin == 1) {
                                        msgAdapter.setNewData(msgList);
                                        msgAdapter.loadMoreEnd(true);
                                    } else {
                                        msgAdapter.loadMoreEnd(false);
                                    }
                                }
                            } else {
                                msgAdapter.loadMoreEnd(true);
                            }
                        } else {
                            NoticeBean bean = new Gson().fromJson(responseJson, NoticeBean.class);
                            if (TextUtils.equals("0", bean.getRc())) {
                                noticeList = bean.getNotice_info();
                                if (noticeList != null && noticeList.size() != 0) {
                                    if (rowBegin == 1) {
                                        noticeAdapter.setNewData(noticeList);
                                    } else {
                                        noticeAdapter.addData(noticeList);
                                    }
                                    noticeAdapter.loadMoreComplete();
                                } else {
                                    if (rowBegin == 1) {
                                        noticeAdapter.setNewData(noticeList);
                                        noticeAdapter.loadMoreEnd(true);
                                    } else {
                                        noticeAdapter.loadMoreEnd(false);
                                    }
                                }
                            } else {
                                noticeAdapter.loadMoreEnd(true);
                            }
                        }
                    }
                    break;
                case 1:
                    if (srlNotification != null&&srlNotification.isRefreshing()) {
                        srlNotification.setRefreshing(false);
                    }
                    ToastUtils.ToastMessage(getActivity(), "网络连接出现问题");
                    break;
                case 2:
                    if (srlNotification != null&&srlNotification.isRefreshing()) {
                        srlNotification.setRefreshing(false);
                    }
                    if(responseJson!=null){
                        CommonDataBean commonDataBean = new Gson().fromJson(responseJson, CommonDataBean.class);
                        if(TextUtils.equals("0",commonDataBean.getRc())){
                            ToastUtils.ToastMessage(getActivity(),"删除成功");
                            noticeAdapter.remove(location);
                        }else {
                            ToastUtils.ToastMessage(getActivity(),commonDataBean.getDes());
                        }
                    }
                    break;
                case 3:
                    if(responseJson!=null) {
                        CommonDataBean commonDataBean = new Gson().fromJson(responseJson, CommonDataBean.class);
                        if (TextUtils.equals("0", commonDataBean.getRc())) {
                            loadData(Constant.GET_REMINDER_LIST, flag, rowBegin);
                        } else {
                            ToastUtils.ToastMessage(getActivity(), commonDataBean.getDes());
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        rowBegin = 1;
        if(TextUtils.equals("0",flag)){
            msgAdapter.setEnableLoadMore(false);
            loadData(Constant.GET_REMINDER_LIST,flag, rowBegin);
            msgAdapter.removeAllFooterView();
        }else {
            noticeAdapter.setEnableLoadMore(false);
            noticeAdapter.removeAllFooterView();
            loadData(Constant.GET_NOTICES_BY_TYPE_ID, flag,rowBegin);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        rowBegin = rowBegin + 1;
        if(TextUtils.equals("0",flag)){
            loadData(Constant.GET_REMINDER_LIST,flag, rowBegin);
        }else {
            loadData(Constant.GET_NOTICES_BY_TYPE_ID,flag, rowBegin);
        }
    }

    //一键全读
    public void readAllReminder(){
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.READ_ALL_REMINDER)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(getActivity()).newCall(request);
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
                    mHandler.sendEmptyMessage(3);
                    call.cancel();
                }
            }
        });
    }
}
