package com.elin.elindriverschool.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by KingLone on 2016/5/23.
 */
public class MD5Util {
//    /*
//    * MD5加密
//    */
//    public static String getMD5Str(String str) {
//        MessageDigest messageDigest = null;
//
//        try {
//            messageDigest = MessageDigest.getInstance("MD5");
//
//            messageDigest.reset();
//
//            messageDigest.update(str.getBytes("UTF-8"));
//        } catch (NoSuchAlgorithmException e) {
//            System.out.println("NoSuchAlgorithmException caught!");
//            System.exit(-1);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        byte[] byteArray = messageDigest.digest();
//
//        StringBuffer md5StrBuff = new StringBuffer();
//
//        for (int i = 0; i < byteArray.length; i++) {
//            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
//                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
//            else
//                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
//        }
//        //16位加密，从第9位到25位
//        return md5StrBuff.substring(8, 24).toString().toUpperCase();
//    }
    /**
     * MD5 32位加密方法二 小写
     * @param str
     * @return
     */

    public final static String get32MD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
//            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
//                md5StrBuff.append("%02x").append(Integer.toHexString(0xFF & byteArray[i]));
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return md5StrBuff.toString();
    }
}
