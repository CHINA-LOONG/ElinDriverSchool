package com.elin.elindriverschool.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.adapter.TrainStudentAdapter;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.fragment.TabStudentFragment;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.StudentListBean;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;

public class AppointmentStudentActivity extends AppCompatActivity implements TrainStudentAdapter.SelectItemListener {

    private MyProgressDialog progressDialog;

    @Bind(R.id.tv_title_text)
    TextView tvTitle;

    @Bind(R.id.et_search)
    EditText etSearch;

    @Bind(R.id.tl_tab)
    TabLayout tlTab;
    @Bind(R.id.vp_content)
    ViewPager vpContent;


    @Bind(R.id.tv_select_student)
    TextView tvSelect;
    @Bind(R.id.tv_student)
    TextView tvStudent;

    private List<String> tabIndicators= Arrays.asList("科目二","科目三");
    private List<TabStudentFragment> tabFragments;
    private ContentPagerAdapter contentAdapter;

    private int selTabIndex = 0;
    private String searchText ="";

    Gson gson = new Gson();
    private String studentListJson = "";
    private StudentListBean studentListBean;
    private List<Integer> selectIndexs = new ArrayList<>();

    private Map<String,Map<String,StudentListBean>> cacheBean = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_student);
        ButterKnife.bind(this);
        tvTitle.setText("选择学员");
        progressDialog = new MyProgressDialog(this, R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);

        //比如drawleft设置图片大小
        //获取图片
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        //第一个0是距左边距离，第二个0是距上边距离，40分别是长宽
        drawable.setBounds(0, 0, 40, 40);
        //只放左边
        etSearch.setCompoundDrawables(drawable, null, null, null);


        initContent();
        initTab();

        initView(true);
    }

    private void initTab(){
        tlTab.setTabMode(TabLayout.MODE_FIXED);
        ViewCompat.setElevation(tlTab,10);
        tlTab.setupWithViewPager(vpContent);
        tlTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("切换页签","当前页签"+selTabIndex);
                selTabIndex = tab.getPosition();
//                if (tlTab.getSelectedTabPosition()==tab.getPosition())
//                    return;
                vpContent.setCurrentItem(selTabIndex);
                initView(false);
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
        for (String s: tabIndicators){
            tabFragments.add(TabStudentFragment.newInstance(this));
        }
        contentAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(contentAdapter);

    }

    private void initView(boolean force){
        studentListBean = null;
        selectIndexs.clear();
        tvSelect.setText("请选择学员");
        tvStudent.setText("");
        Log.i("获取数据-是否强制刷新",force+"");
        if (!force){
            if (cacheBean.containsKey(tabIndicators.get(selTabIndex))){
                Log.i("是否拥有缓存",tabIndicators.get(selTabIndex));
                Map<String,StudentListBean> searchListBean = cacheBean.get(tabIndicators.get(selTabIndex));
                if (searchListBean.containsKey(searchText)) {
                    Log.i("是否拥有缓存","搜索："+searchText);
                    studentListBean = searchListBean.get(searchText);
                }
            }
        }
        if (studentListBean!=null){
            tabFragments.get(selTabIndex).setData(studentListBean);
        }
        else {
            Log.i("获取数据：","网络请求");
            loadData();
        }
    }

    private void loadData(){
        progressDialog.show();

        Map<String,String> map = new HashMap<>();
        map.put("coach_idnum", BaseApplication.getInstance().getCoachIdNum());
        map.put("school_id",BaseApplication.getInstance().getSchoolId());
        map.put("train_sub",selTabIndex==0?"2":"3");
        map.put("token",BaseApplication.getInstance().getToken());
        if (!searchText.isEmpty()){
            map.put("stuName",searchText);
        }

        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.GET_COURSE_STUDENT_LISTS)
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
                studentListJson= String.valueOf(response.body().string());
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
                    Log.i("输出：",studentListJson);
                    studentListBean = gson.fromJson(studentListJson,StudentListBean.class);
                    if (studentListBean!=null){
                        Map<String,StudentListBean> searchListBean;
                        if (!cacheBean.containsKey(tabIndicators.get(selTabIndex))){
                            searchListBean = new HashMap<>();
                            cacheBean.put(tabIndicators.get(selTabIndex),searchListBean);
                        }else {
                            searchListBean = cacheBean.get(tabIndicators.get(selTabIndex));
                        }
                        searchListBean.put(searchText,studentListBean);

                        tabFragments.get(selTabIndex).setData(studentListBean);
                    }
                    break;
                case 1:
                    break;
            }
        }
    };

    @Override
    public void onSelectItem(int index, boolean isSelect) {
        if (isSelect){
            if (!selectIndexs.contains(new Integer(index)))
                selectIndexs.add(new Integer(index));
        }else {
            if (selectIndexs.contains(new Integer(index)))
                selectIndexs.remove(new Integer(index));
        }
        tvSelect.setText(selectIndexs.size()>0?"已选择":"请选择学员");
        String student = "";
        for (Integer i:selectIndexs){
            student+=studentListBean.getData().get(i).getStuname()+" ";
        }
        tvStudent.setText(student);
    }

    @OnClick({R.id.iv_title_back,R.id.tv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_title_back:
                finish();
                break;
            case R.id.tv_search:
                etSearch.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
                searchText = etSearch.getText().toString().trim();
                initView(false);
                break;
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
//        return super.onTouchEvent(event);
//    }


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
