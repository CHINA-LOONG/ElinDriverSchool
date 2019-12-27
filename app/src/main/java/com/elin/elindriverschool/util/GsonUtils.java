package com.elin.elindriverschool.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * 作者：zzy 2018/4/27 8:33
 * 邮箱：zzyflute@163.com
 */
public class GsonUtils {
    private static final String TAG = GsonUtils.class.getSimpleName();

    public static Gson gson = new Gson();

    public static <T> T parseJson(Class<T> cls, String json) {
        try {
            T t=gson.fromJson(json, cls);
            Log.e(TAG,json);
            return t;
        } catch (JsonSyntaxException e) {
            Log.e(TAG,e.getMessage());
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
        return null;
    }

    public static <T> T parseJson(Type type, String json) {
        try {
            T t=gson.fromJson(json, type);
            Log.e(TAG,json);
            return  t;
        } catch (JsonSyntaxException e) {
            Log.e(TAG,e.getMessage());
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
        return null;
    }

    public static String toJson(Object src) {
        try {
            String json=gson.toJson(src);
            Log.e(TAG,json);
            return json;
        } catch (JsonSyntaxException e) {
            Log.e(TAG,e.getMessage());
        } catch (Exception e) {
            Log.e(TAG,e.getMessage());
        }
        return null;
    }

    public static String netJsonResult(Object src) {
        try {
            String json=gson.toJson(src);
            Log.e("NET",json);
            return json;
        } catch (Exception e) {
            Log.e("NET","请求结果非JSON对象:\t"+e.getMessage());
        }
        return null;
    }
}
