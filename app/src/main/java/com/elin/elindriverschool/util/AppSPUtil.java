package com.elin.elindriverschool.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.elin.elindriverschool.sharedpreferences.PreferenceManager;


/**
 * Created by zengzhaoyun on 2016/9/14.
 */
public class AppSPUtil {

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *  @param context
     * @param key
     * @param object
     */
    public static boolean put(Context context, String key, Object object)
    {

        SharedPreferences sp = context.getSharedPreferences(PreferenceManager.NATIVE_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
        return false;
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject)
    {
        SharedPreferences sp = context.getSharedPreferences(PreferenceManager.NATIVE_FILE,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(PreferenceManager.NATIVE_FILE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
