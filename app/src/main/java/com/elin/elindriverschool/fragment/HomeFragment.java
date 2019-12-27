package com.elin.elindriverschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.widget.Toast;

import com.elin.elindriverschool.R;
import com.elin.elindriverschool.activity.DriveCenterActivity;
import com.elin.elindriverschool.activity.HomeWebInfoActivity;
import com.elin.elindriverschool.model.Constant;
import com.elin.elindriverschool.view.MyProgressDialog;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private View view;
    private BridgeWebView webView;
    private MyProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private Handler handler = new Handler();
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new MyProgressDialog(getActivity(), R.style.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        webView = (BridgeWebView) view.findViewById(R.id.web_view_home);
        progressDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        },2500);
        //通过jsbridge 实现H5与原生进行交互
        if (webView != null) {
            webView.setWebViewClient(new MyWebViewClient(webView));
            webView.setDefaultHandler(new myHadlerCallBack());
            webView.setWebChromeClient(new WebChromeClient() );
            webView.loadUrl("http://busdriving.elin365.com:8080/DrivingForStudent/coachHomePage.html");
//            webView.loadUrl("file:///android_asset/coachHomePage.html");
            webView.registerHandler("clickDrivingCenter", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
//                    ToastUtils.ToastMessage(getActivity(),"驾考中心--");
//                    if (progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
                    Log.e("Js回调Data-->", data + " AAA");
                    jumpToActivity();
                    function.onCallBack(data);
                }
            });
            webView.registerHandler("clickSignUp", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
//                    ToastUtils.ToastMessage(getActivity(),data+"--");
                    Log.e("学员报名回调Data-->", data);
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                       String url =  jsonObject.getString("url");
                        jumpToWebActivity(Constant.ServerStuURL+"/"+url,1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    function.onCallBack(data);
                }
            });
            webView.registerHandler("clickMessageAll", new BridgeHandler() {
                @Override
                public void handler(String data, CallBackFunction function) {
//                    ToastUtils.ToastMessage(getActivity(),data+"--");
                    Log.e("学员报名回调Data-->", data);
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        String url =  jsonObject.getString("url");
                        jumpToWebActivity(Constant.ServerStuURL+"/"+url,2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    function.onCallBack(data);
                }
            });

        }
        return view;
    }


    /**
     * 自定义的WebViewClient
     */
    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
//            if (progressDialog.isShowing()){
//                        progressDialog.dismiss();
//                    }
        }
    }

    /**
     * 自定义回调
     */
    class myHadlerCallBack extends DefaultHandler {

        @Override
        public void handler(String data, CallBackFunction function) {
            if (function != null) {

                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class JSHook {
        @JavascriptInterface
        public void javaMethod(String param) {
            Log.d("HomeFragment-->", "JSHook.javaMethod() called+" + param);
        }

        @JavascriptInterface
        public void toDriveCenter() {
            //
            jumpToActivity();
        }
    }

    //    @JavascriptInterface
    public void  jumpToActivity() {
        //此处应该定义常量对应，同时提供给web页面编写者
        startActivity(new Intent(getActivity(), DriveCenterActivity.class));
    }

    public void jumpToWebActivity(String url,int flag) {// flag 1 学员报名 2 陪驾考动态
        //此处应该定义常量对应，同时提供给web页面编写者
        Intent intent = new Intent(getActivity(), HomeWebInfoActivity.class);
        intent.putExtra("loadUrl", url);
        intent.putExtra("webFlag",flag);
        startActivity(intent);
    }
}
