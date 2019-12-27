package com.elin.elindriverschool.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.ChatAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.ChatBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.util.ACache;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ScreenUtils;
import com.elin.elindriverschool.util.ToastUtils;
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

import static com.elin.elindriverschool.activity.MessageDetailActivity.MEDIA_TYPE_MARKDOWN;

public class ChatActivity extends BaseActivity {

    @Bind(R.id.imv_cus_title_back)
    ImageView imvCusTitleBack;
    @Bind(R.id.tv_cus_title_name)
    TextView tvCusTitleName;
    @Bind(R.id.tv_cus_title_right)
    TextView tvCusTitleRight;
    @Bind(R.id.rv_chat)
    RecyclerView rvChat;
    @Bind(R.id.tv_send)
    Button tvSend;
    @Bind(R.id.reply_edit)
    EditText replyEdit;
    @Bind(R.id.rl_chat)
    RelativeLayout rlChat;
    @Bind(R.id.rl_chat_root)
    RelativeLayout rlChatRoot;
    private Map<String, String> parmasMap = new HashMap<>();
    List<ChatBean.DataBean> data_list = new ArrayList<>();
    private String responseJson;
    private ChatAdapter adapter;
    private String detailId, replyContent,isDel;
    private LinearLayoutManager layoutManager;
    private int screenHeight;//屏幕高度
    private int stateHeight;//状态栏的高度
    private int titlebarHeight;//标题栏高度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        //拿到屏幕的高度
        screenHeight = ScreenUtils.getScreenHeightPixels(this);
        //拿到状态栏的高度
        stateHeight = ScreenUtils.getStatusHeight(this);
        //拿到标题栏的高度
        titlebarHeight = 48;
        tvCusTitleName.setText("聊天记录");
        detailId = getIntent().getExtras().getString("detail_id");
        isDel = getIntent().getExtras().getString("is_del");
        if(TextUtils.equals("0",isDel)){
            replyEdit.setEnabled(true);
            tvSend.setEnabled(true);
        }else {
            replyEdit.setEnabled(false);
            tvSend.setEnabled(false);
            replyEdit.setHint("此消息已被删除，禁止回复");
        }
        adapter = new ChatAdapter(data_list);
        layoutManager = new LinearLayoutManager(this);
        rvChat.setLayoutManager(layoutManager);
        rvChat.setAdapter(adapter);
        loadData();
        if (ACache.get(ChatActivity.this).getAsJSONObject("chatContent") != null) {
            responseJson = ACache.get(ChatActivity.this).getAsJSONObject("chatContent").toString();
        }
        replyEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                controlKeyboardLayout(rlChatRoot);
                replyEdit.requestFocus();
                showSoftInput(ChatActivity.this, replyEdit);
                mHandler.sendEmptyMessageDelayed(4,250);
                return false;
            }
        });
        rvChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftInput(ChatActivity.this, replyEdit);
                return false;
            }
        });
    }


    private void loadData() {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("detail_id", detailId);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.GET_NOTICE)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
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
                    if (responseJson != null) {
                        ChatBean bean = new Gson().fromJson(responseJson, ChatBean.class);
                        //存入缓存
                        ACache.get(ChatActivity.this).put("chatContent", responseJson, 60 * 60 * 24 * 365 * 60);
                        if (TextUtils.equals("0", bean.getRc())) {
                            data_list = bean.getData();
                            if (data_list != null && data_list.size() != 0) {
                                adapter.setNewData(data_list);
                                adapter.loadMoreComplete();
                                rvChat.smoothScrollToPosition(data_list.size()-1);
                            } else {
                                adapter.setEmptyView(R.layout.layout_empty);
                                adapter.setNewData(data_list);
                                adapter.loadMoreEnd(true);
                            }
                        } else {
                            adapter.setEmptyView(R.layout.layout_empty,rvChat);
                            adapter.loadMoreEnd(true);
                        }
                    }
                    break;
                case 1:
                    adapter.setEmptyView(R.layout.layout_empty,rvChat);
                    break;
                case 2:
                    CommonDataBean bean = new Gson().fromJson(responseJson, CommonDataBean.class);
                    if(TextUtils.equals("0",bean.getRc())){

                    }else {
                        ToastUtils.ToastMessage(ChatActivity.this,bean.getDes());
                    }
                    break;
                case 3:
                    ToastUtils.ToastMessage(ChatActivity.this,"请检查网络连接");
                    break;
                case 4:
                    rvChat.smoothScrollToPosition(data_list.size()-1);
                    break;
            }
        }
    };

    @OnClick({R.id.imv_cus_title_back, R.id.tv_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_cus_title_back:
                finish();
                break;
            case R.id.tv_send:
                replyContent = replyEdit.getText().toString().trim();
                if (TextUtils.isEmpty(replyContent)) {
                    ToastUtils.ToastMessage(this, "回复内容不能为空");
                } else {
                    sendReply();
                    replyEdit.setText("");
                    ChatBean.DataBean bean = new ChatBean.DataBean();
                    bean.setReply_person("0");
                    bean.setReply_content(replyContent);
                    adapter.addData(bean);
                    rvChat.smoothScrollToPosition(data_list.size());
                }
                break;

        }
    }

    private void sendReply() {
        parmasMap.put("token", BaseApplication.getInstance().getToken());
        parmasMap.put("detail_id", detailId);
        parmasMap.put("reply_content", replyContent);
        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.SEND_REPLY)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, new Gson().toJson(parmasMap)))
                .build();

        Call call = new MyOkHttpClient(this).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendEmptyMessage(3);
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

    /**
     * 在聊天的内容比较多的时候没有问题,当时在内容比较少的时候上面的内容就会看不见,所以这个方法也是不可取的
     * @param root             最外层布局
     */
    private void controlKeyboardLayout(final View root) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Rect r = new Rect();
            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                ChatActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = ChatActivity.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                //内容部分的View
                ViewGroup.LayoutParams layoutParams = rvChat.getLayoutParams();
                //拿到需要调整的高度,这里需要按照实际情况计算你们自己的高度
                //拿到            屏幕高度 -状态栏高度 - 键盘高度 = 获得内容（聊天recyclerView）的高度
                int height = screenHeight - stateHeight - heightDifference ;
                //如果计算出来的和原来的不一样,那么就调整一下
                //给内容View 设置新的高度
                layoutParams.height = height;
                //该activity根布局重新布局
                rlChatRoot.requestLayout();
                //滑动recyclerView到最底部
                rvChat.getLayoutManager().scrollToPosition(data_list.size()-1);

            }
        });
    }
    public static void showSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
