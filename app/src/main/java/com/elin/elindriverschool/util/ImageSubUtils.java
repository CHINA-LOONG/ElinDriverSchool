package com.elin.elindriverschool.util;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by imac on 16/12/30.
 */

public class ImageSubUtils {

    private String ImageBaseURL = "http://elindriving.oss-cn-hangzhou.aliyuncs.com/coach/";

    private String img_sub_1,img_sub_2,img_sub_3;

    public ImageSubUtils() {
    }

    public String getEncodeImgUrl(String urlOld){
        img_sub_1 = urlOld.substring(54,urlOld.length());
        Log.e("第一次截取的Img-->",img_sub_1);
        img_sub_2 = img_sub_1.substring(0,img_sub_1.length()-22);//获取中文
        Log.e("第二次截取的Img-->",img_sub_2);
        img_sub_3 = img_sub_1.substring(img_sub_1.length()-22,img_sub_1.length());
        Log.e("第三次截取的Img-->",img_sub_3);
        return ImageBaseURL+ URLEncoder.encode(img_sub_2)+img_sub_3;
    }
}
