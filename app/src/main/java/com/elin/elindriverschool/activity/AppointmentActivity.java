package com.elin.elindriverschool.activity;


import android.app.DialogFragment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.fragment.TabContentFragment;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.OrderListsBean;
import com.elin.elindriverschool.picker.dialog.AppointmentRefuseDialog;
import com.elin.elindriverschool.picker.dialog.DivisionPickerDialog;
import com.elin.elindriverschool.picker.dialog.ActionListener;
import com.elin.elindriverschool.picker.dialog.BaseDialogFragment;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import top.defaults.view.PickerView;

import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

public class AppointmentActivity extends BaseActivity {


    private MyProgressDialog progressDialog;

    private List<String> tabIndicators= Arrays.asList("科目二","科目三");

    @Bind(R.id.iv_title_back)
    ImageView ivBack;

    @Bind(R.id.tl_tab)
    TabLayout mTabTl;
    @Bind(R.id.vp_content)
    ViewPager mContentVp;

    @Bind(R.id.tv_cus_title_name)
    TextView tvTitleDate;


    private List<Fragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    private Calendar calendar; // 当时的日期和时间
    private Calendar selCalendar;//选择的日期和时间
    private String strCalendar;//字符串日期yyyy-MM-dd

    private int selTabIndex = 0;

    Gson gson = new Gson();
    private String orderListsJson="";
    private OrderListsBean orderListsBean;

    private Map<String,Map<String,OrderListsBean>> cacheBean=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);

        // 当时的日期和时间
        calendar = Calendar.getInstance();
        selCalendar = calendar;

        initView();
        initContent();
        initTab();
    }


    private void initTab(){
        mTabTl.setTabMode(TabLayout.MODE_FIXED);
//        mTabTl.setSelectedTabIndicatorHeight(0);
//        mTabTl.setTabTextColors(ContextCompat.getColor(this,R.color.bg_gray),ContextCompat.getColor(this,R.color.white));
        ViewCompat.setElevation(mTabTl,10);
        mTabTl.setupWithViewPager(mContentVp);

        mTabTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.e("选中索引",tab.getPosition()+"");
                selTabIndex = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initContent(){
        tabFragments = new ArrayList<>();
        //根据页签创建页面
        for (String s : tabIndicators) {
            tabFragments.add(TabContentFragment.newInstance(s));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mContentVp.setAdapter(contentAdapter);
    }

    private void initView(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(selCalendar.getTimeInMillis());

        tvTitleDate.setText(format.format(date));

        loadData();
    }



    private void loadData(){
        progressDialog.show();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date(selCalendar.getTimeInMillis());
        strCalendar = format.format(date);

        Map<String,String> map = new HashMap<>();
        map.put("train_date", strCalendar);
        map.put("coach_idnum", BaseApplication.getInstance().getCoachIdNum());
        map.put("train_sub","2");
        map.put("school_id",BaseApplication.getInstance().getSchoolId());

//        Log.i("参数",format.format(date)+"    /n"+
//                BaseApplication.getInstance().getCoachIdNum()+"    /n"+
//                "2"+"    /n"+
//                BaseApplication.getInstance().getSchoolId()+"    /n");

//        map.put("token",BaseApplication.getInstance().getToken());

        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.GET_ORDER_LISTS)
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
                orderListsJson = String.valueOf(response.body().string());
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
                    Log.i("输出：",orderListsJson);
                    orderListsBean = gson.fromJson(orderListsJson,OrderListsBean.class);
                    if (orderListsBean!=null){
                        if(!cacheBean.containsKey(strCalendar)){
                            cacheBean.put(strCalendar,new HashMap<String, OrderListsBean>());
                        }
                        cacheBean.get(strCalendar).put(tabIndicators.get(selTabIndex),orderListsBean);
                    }
                    
                    ((TabContentFragment)tabFragments.get(selTabIndex)).SetData();
                    break;
                case 1:
                    break;
            }
        }
    };


//    BaseDialogFragment picker;
    @OnClick({R.id.iv_title_back,R.id.tv_setting,R.id.rl_helpstudent,R.id.fab_operate,R.id.iv_previous,R.id.iv_next})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_setting:
                goToActivity(this, AppointmentSettingActivity.class);
//                AppointmentRefuseDialog refuse = AppointmentRefuseDialog.newInstance(new AppointmentRefuseDialog.ActionListener() {
//                    @Override
//                    public void onClickType(AppointmentRefuseDialog.ActionType type) {
//                        if (type== AppointmentRefuseDialog.ActionType.CANCEL)
//                            Log.e("取消界面","______________");
//
//                    }
//                });
//                refuse.show(getFragmentManager(), "dialog");
                break;
            case R.id.rl_helpstudent:
                goToActivity(this, AppointmentStudentActivity.class);
//                picker = DivisionPickerDialog.newInstance(BaseDialogFragment.TYPE_DIALOG, new ActionListener() {
//                    @Override
//                    public void onCancelClick(BaseDialogFragment dialog) {}
//
//                    @Override
//                    public void onDoneClick(BaseDialogFragment dialog) {
//                        if (dialog instanceof DivisionPickerDialog) {
////                Division division = ((DivisionPickerDialog) dialog).getSelectedDivision();
////                StringBuilder text = new StringBuilder(division.getText());
////                while (division.getParent() != null) {
////                    division = division.getParent();
////                    text.insert(0, division.getText());
////                }
////                textView.setText(text.toString());
//                        }
//                    }
//                });
//                picker.show(getFragmentManager(), "dialog");
                break;
            case R.id.fab_operate:
                break;

            case R.id.iv_previous:
                selCalendar.add(Calendar.DAY_OF_MONTH, -1);
                initView();
                break;
            case R.id.iv_next:
                selCalendar.add(Calendar.DAY_OF_MONTH, 1);
                initView();
                break;
        }
    }


    class ContentPagerAdapter extends FragmentPagerAdapter{
        public ContentPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }
}
