package com.elin.elindriverschool.adapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AppointTrainingStudentActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.decoration.EventDecorator;
import com.elin.elindriverschool.model.AppointAlreadyStuBean;
import com.elin.elindriverschool.model.AppointTrainBean;
import com.elin.elindriverschool.model.CommonDataBean;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.TrainScheduleListBean;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.elin.elindriverschool.activity.MyDriveStudentActivity.MEDIA_TYPE_JSON;

/**
 * Created by 17535 on 2017/9/8.
 */

public class AppointAlreadyStudentAdapter extends BaseQuickAdapter<AppointAlreadyStuBean.StudentsBean,BaseViewHolder> {
    Context context;
    String canSign;
    String responseJson;
    Button btnSign;
    private LocationManager locationManager;
    private String locationProvider;       //位置提供器
    String coachLocation;
    public AppointAlreadyStudentAdapter(Context context,List<AppointAlreadyStuBean.StudentsBean> data,String canSign) {
        super(R.layout.item_appoint_already_student,data);
        this.context = context;
        this.canSign = canSign;
        getLocation(context);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final AppointAlreadyStuBean.StudentsBean item) {
        helper.setText(R.id.tv_appoint_stu_name,item.getStu_name())
                .setText(R.id.tv_appoint_stu_idnum,item.getStu_idnum())
                .setText(R.id.tv_appoint_stu_date,"预约时间："+item.getCreate_date())
                .addOnClickListener(R.id.img_appoint_stu_phone)
                .addOnClickListener(R.id.item_tv_cancle_appoint);

        ImageView ivHead = helper.getView(R.id.imv_appoint_stu_portrait);
        TextView tvCancleAppoint = helper.getView(R.id.item_tv_cancle_appoint);
        TextView tvAppointStuStatus = helper.getView(R.id.tv_appoint_stu_status);
        btnSign = helper.getView(R.id.item_btn_sign);
        ImageView ivSignedIn = helper.getView(R.id.item_iv_signed_in);

        Glide.with(mContext).load(item.getStu_photo())
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(ivHead);
        if(TextUtils.equals("1",canSign)){
            switch (item.getComplete_flag()){
                case "0":
                    tvAppointStuStatus.setText("预约中");
                    tvCancleAppoint.setVisibility(View.VISIBLE);
                    btnSign.setVisibility(View.GONE);
                    break;
                case "1":
                    tvAppointStuStatus.setText("预约成功");
                    tvCancleAppoint.setVisibility(View.VISIBLE);
                    switch (item.getStatus()){
                        case "1":
                            btnSign.setVisibility(View.GONE);
                            ivSignedIn.setVisibility(View.GONE);
                            break;
                        case "2":
                            btnSign.setVisibility(View.VISIBLE);
                            btnSign.setText("签到");
                            ivSignedIn.setVisibility(View.GONE);
                            break;
                        case "3":
                            btnSign.setText("等待学员确认");
                            btnSign.setVisibility(View.VISIBLE);
                            ivSignedIn.setVisibility(View.GONE);
                            break;
                        case "4":
                            ivSignedIn.setVisibility(View.VISIBLE);
                            btnSign.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case "2":
                    tvAppointStuStatus.setText("预约失败");
                    tvCancleAppoint.setVisibility(View.GONE);
                    btnSign.setVisibility(View.GONE);
                    break;
                case "3":
                case "4":
                    tvAppointStuStatus.setText("教练取消");
                    tvCancleAppoint.setVisibility(View.GONE);
                    btnSign.setVisibility(View.GONE);
                    break;
                case "5":
                case "6":
                    tvAppointStuStatus.setText("学员取消");
                    tvCancleAppoint.setVisibility(View.GONE);
                    btnSign.setVisibility(View.GONE);
                    break;
            }
        }else {
            btnSign.setVisibility(View.GONE);
            tvCancleAppoint.setVisibility(View.GONE);
        }
        if(TextUtils.equals("签到",btnSign.getText().toString().trim())){
            btnSign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSign = helper.getView(R.id.item_btn_sign);
                    setSignedIn(item.getId());
                }
            });
        }
    }

    //发起签到
    private  void setSignedIn(String stuId){
        Map<String, String> scheduleMap = new HashMap<>();
        scheduleMap.put("token", BaseApplication.getInstance().getToken());
        scheduleMap.put("id", stuId);
        scheduleMap.put("coach_location", coachLocation);

        Request request = new Request.Builder()
                .url(Constant.ServerURL + Constant.TRAIN_SIGN_IN)
                .post(RequestBody.create(MEDIA_TYPE_JSON, new Gson().toJson(scheduleMap)))
                .build();
        Call call = new MyOkHttpClient(context).newCall(request);
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
                    CommonDataBean bean = new Gson().fromJson(responseJson, CommonDataBean.class);
                    if (TextUtils.equals("0",bean.getRc())){
                        btnSign.setText("等待学员确认");
                    }else {
                        ToastUtils.ToastMessage(context,bean.getDes());
                    }
                    break;
                case 1:
                    ToastUtils.ToastMessage(context, "网络连接错误");
                    break;
            }
        }
    };

    private void getLocation(Context context) {
        //1.获取位置管理器
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(context, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "请开启定位权限", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location!=null){
            showLocation(location);
        }else{
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0,mListener);
        }
    }

    private void showLocation(Location location){
        coachLocation =location.getLatitude()+","+location.getLongitude();
    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(String provider) {

        }
        @Override
        public void onProviderDisabled(String provider) {

        }
        // 如果位置发生变化，重新显示
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };
}
