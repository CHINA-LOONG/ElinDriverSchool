package com.elin.elindriverschool.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.AboutVersionActivity;
import com.elin.elindriverschool.activity.ChangPhoneActivity;
import com.elin.elindriverschool.activity.ChangePwdActivity;
import com.elin.elindriverschool.activity.LoginActivity;
import com.elin.elindriverschool.activity.MyInfoActivity;
import com.elin.elindriverschool.application.BaseApplication;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.model.DynamicBean;
import com.elin.elindriverschool.model.LogoutBean;
import com.elin.elindriverschool.util.LogoutUtil;
import com.elin.elindriverschool.util.MyOkHttpClient;
import com.elin.elindriverschool.util.ToastUtils;
import com.elin.elindriverschool.view.CircularImage;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.bgabanner.BGABanner;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static com.elin.elindriverschool.fragment.MyStusClassTwo.MEDIA_TYPE_JSON;
import static com.umeng.socialize.utils.ContextUtil.getPackageName;

public class UserFragment extends BaseFragment {
    @Bind(R.id.civ_user_head_img)
    CircularImage civUserHeadImg;
    @Bind(R.id.tv_user_id)
    TextView tvUserId;
    @Bind(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @Bind(R.id.ll_user_change_pwd)
    LinearLayout llUserChangePwd;
    @Bind(R.id.tv_user_login)
    ImageView tvUserLogin;
    @Bind(R.id.ll_user_about_version)
    LinearLayout llUserAboutVersion;
    @Bind(R.id.tv_user_phone)
    TextView tvUserPhone;
    @Bind(R.id.ll_user_change_phone)
    LinearLayout llUserChangePhone;
    @Bind(R.id.btn_logout)
    Button btnLogout;

    private Intent intent = new Intent();
    private Handler handler = new Handler();
    public static final int LOGIN_REQUEST_CODE = 10;
    public static final int CHANG_PHONE_REQUEST_CODE = 101;
    private String phone;
    private Map<String,String> paramsMap = new HashMap<>();
    private String paramsJson,responseJson;
    private Gson gson = new Gson();
    MyProgressDialog myProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        myProgressDialog = new MyProgressDialog(getActivity(),R.style.progress_dialog);
        myProgressDialog.setCanceledOnTouchOutside(false);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseApplication.getInstance().getToken() != null && !("".equals(BaseApplication.getInstance().getToken()))) {
            tvUserLogin.setVisibility(View.GONE);
        } else {
            tvUserLogin.setVisibility(View.VISIBLE);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
        phone = BaseApplication.getInstance().getCoachPhone();
        tvUserPhone.setVisibility(View.VISIBLE);
        tvUserId.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.VISIBLE);
        tvUserPhone.setText(phone);
        tvUserId.setText(BaseApplication.getInstance().getCoachName());
    }


    private void initView() {
        Glide.with(getActivity()).load(BaseApplication.getInstance().getCoachPhoto())
                .placeholder(R.drawable.user_default).error(R.drawable.user_default)
                .into(civUserHeadImg);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOGIN_REQUEST_CODE:
                    tvUserLogin.setVisibility(View.GONE);
                    break;
                case CHANG_PHONE_REQUEST_CODE:
                    tvUserPhone.setText(phone);
                    break;
            }
        }
    }

    @OnClick({R.id.tv_user_login, R.id.ll_user_info, R.id.ll_user_change_pwd, R.id.ll_user_change_phone, R.id.ll_user_about_version, R.id.btn_logout})
    public void onClick(View view) {
        if(TextUtils.isEmpty(BaseApplication.getInstance().getToken())){
            goToActivity(getActivity(),LoginActivity.class);
        }else {
            switch (view.getId()) {
                case R.id.tv_user_login:
                    intent.setClass(getActivity(), LoginActivity.class);
                    intent.putExtra("isFromMyInfo", true);
                    startActivityForResult(intent, LOGIN_REQUEST_CODE);
                    break;
                case R.id.ll_user_info:
                    if (BaseApplication.getInstance().getToken() != null && !("".equals(BaseApplication.getInstance().getToken()))) {
                        intent.setClass(getActivity(), MyInfoActivity.class);
                        startActivity(intent);
                    } else {
                        intent.setClass(getActivity(), LoginActivity.class);
                        intent.putExtra("isFromMyInfo", true);
                        startActivity(intent);
                    }
                    break;
                case R.id.ll_user_change_pwd:
                    if (BaseApplication.getInstance().getToken() != null && !("".equals(BaseApplication.getInstance().getToken()))) {
                        intent.setClass(getActivity(), ChangePwdActivity.class);
                        intent.putExtra("flag", 1);
                        startActivity(intent);
                    } else {
                        intent.setClass(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.ll_user_change_phone:
                    Intent intent = new Intent(getActivity(), ChangPhoneActivity.class);
                    startActivityForResult(intent, CHANG_PHONE_REQUEST_CODE);
                    break;
                case R.id.ll_user_about_version:
                    goToActivity(getActivity(), AboutVersionActivity.class);
                    break;
                case R.id.btn_logout:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("操作提示")
                            .setMessage("确认退出登录？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    logout();
                                    ToastUtils.ToastMessage(getActivity(),"退出成功");
                                    Intent intent = new Intent("loginout");
                                    intent.setPackage(getPackageName());
                                    getActivity().sendBroadcast(intent);
                                    LogoutUtil.clearData(getActivity());
                                    btnLogout.setVisibility(View.GONE);
                                    tvUserPhone.setVisibility(View.GONE);
                                    Glide.with(getActivity()).load(BaseApplication.getInstance().getCoachPhoto())
                                            .placeholder(R.drawable.user_default).error(R.drawable.user_default)
                                            .into(civUserHeadImg);
                                    tvUserId.setVisibility(View.GONE);
                                    tvUserLogin.setVisibility(View.VISIBLE);
                                    goToActivity(getActivity(),LoginActivity.class);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).create().show();
                    break;
            }
        }

    }

    private void logout(){
        paramsMap.put("token",BaseApplication.getInstance().getToken());
        paramsMap.put("coach_idnum",BaseApplication.getInstance().getCoachIdNum());
        paramsMap.put("school_id",BaseApplication.getInstance().getSchoolId());
        paramsJson = gson.toJson(paramsMap);
        Request request = new Request.Builder()
                .url(Constant.ServerURL+Constant.LOGIN_OUT)
                .post(RequestBody.create(MEDIA_TYPE_JSON,paramsJson))
                .build();
        Call call = new MyOkHttpClient(getActivity()).newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseJson = String.valueOf(response.body().string());
                call.cancel();
            }
        });
    }

}
