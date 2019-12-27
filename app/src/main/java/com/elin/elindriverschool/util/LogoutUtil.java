package com.elin.elindriverschool.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.elin.elindriverschool.activity.LoginActivity;

/**
 * 作者：zzy 2018/4/27 13:25
 * 邮箱：zzyflute@163.com
 */
public class LogoutUtil {
    private static SharedPreferences sp_token,sp_phone,sp_idnum,sp_photo,sp_name,sp_schoolId;
    private static SharedPreferences.Editor editor_token,editor_phone,editor_idnum,editor_photo,editor_name,editor_schoolId;
    public static void clearData(Context context){
        sp_token = context.getSharedPreferences("sp_token", Context.MODE_PRIVATE);
        editor_token = sp_token.edit();
        sp_idnum = context.getSharedPreferences("sp_idnum", Context.MODE_PRIVATE);
        editor_idnum = sp_idnum.edit();
        sp_name = context.getSharedPreferences("sp_name", Context.MODE_PRIVATE);
        editor_name = sp_name.edit();
        sp_phone = context.getSharedPreferences("sp_phone", Context.MODE_PRIVATE);
        editor_phone = sp_phone.edit();
        sp_photo = context.getSharedPreferences("sp_photo", Context.MODE_PRIVATE);
        editor_photo = sp_photo.edit();
        sp_schoolId = context.getSharedPreferences("sp_schoolId", Context.MODE_PRIVATE);
        editor_schoolId = sp_schoolId.edit();
        editor_token.clear();
        editor_photo.clear();
        editor_phone.clear();
        editor_name.clear();
        editor_idnum.clear();
        editor_schoolId.clear();
        editor_token.apply();
        editor_idnum.apply();
        editor_name.apply();
        editor_phone.apply();
        editor_photo.apply();
        editor_schoolId.apply();
    }
}
