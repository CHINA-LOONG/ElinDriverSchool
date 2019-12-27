package com.elin.elindriverschool.util;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by imac on 16/12/30.
 */

public class MyOkHttpClient extends OkHttpClient {

    private Context mContext;
    private OkHttpClient mOkHttpClient;

    public MyOkHttpClient(Context mContext) {
        this.mContext = mContext;
        initOkHttpClient();
    }
    private void initOkHttpClient() {
        File sdcache = mContext.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }

}
