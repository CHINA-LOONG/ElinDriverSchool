package com.elin.elindriverschool.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by imac on 16/12/7.
 */

public class DES {

    private final static String iv = "01234567";
    private final static String desPwdKey = "91zW860f6CBCB81edriving7777";
    private final static String encoding = "utf-8";
    //    private static byte[] iv = {,2,3,4,5,6,7,8};


    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(desPwdKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return Base64.encode(encryptData);
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(desPwdKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));

        return new String(decryptData, encoding);
    }


//
//    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
////      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
//        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
//        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
//        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
//
//        return Base64.encode(encryptedData);
//    }
//    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
//        byte[] byteMi = new Base64().decode(decryptString);
//        IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
////      IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
//        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
//        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
//        byte decryptedData[] = cipher.doFinal(byteMi);
//
//        return new String(decryptedData);
//    }
}
