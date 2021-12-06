package com.andy.binding.http;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author cfeng
 * @version 1.0
 * @date 2020/11/2 16:23
 */
public class AesUtil {
    public static void main(String[] args) throws UnsupportedEncodingException {
        for (int i = 0; i < 10; i++) {
            String key = generateKey(); // 提前生成的一个key
            String ps = encode(key, "123");
            System.out.println("加密: " + ps);
            String res = decode(key, ps);
            System.out.println("解密: " + res);
        }
    }

    /**
     * 生成key
     * @return
     */
    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
//            return Hex.encodeHexString(byteKey);
            return Hex.encodeHex(byteKey,true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES加密
     * @param thisKey
     * @param data
     * @return
     */
    public static String encode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(Hex.decodeHex(thisKey),"AES");
            //System.out.println(thisKey);

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] result = cipher.doFinal(data.getBytes());
//            return Hex.encodeHexString(result);
            return Hex.encodeHex(result,true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * AES解密
     * @param thisKey
     * @param data
     * @return
     */
    public static String decode(String thisKey, String data) {
        try {
            // 转换KEY
            Key key = new SecretKeySpec(thisKey.getBytes(),"AES");
            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Base64.decode(data,Base64.NO_WRAP));
            return new String(result);
        } catch (Exception e) {
            Log.e("", "",e);
        }
        return null;
    }
}
