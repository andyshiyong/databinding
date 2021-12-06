package com.andy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by on 2019/9/8.
 */
public class MD5Utils {
    /**
     * 32位MD5加密方法
     * 16位小写加密只需getMd5Value("xxx").substring(8, 24);即可
     *
     * @param sSecret
     * @return
     */
    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Deprecated
    public static String genSign(String deviceId, String clientVersion, String clientAgent, String nonce, String reqString) {
        return getMd5Value(deviceId +
                clientVersion +
                clientAgent +
                nonce +
                reqString +
                SecretKeyManager.getInstance().getSecretKeyMD5()).toUpperCase();
    }
    public static String genSign(String deviceId, String reqString, String nonce, String timeStamp) {
        //md5(设备ID + AES加密过的请求字符串 + nonce + 时间戳 + secret)
        return getMd5Value(deviceId +
                reqString +
                nonce +
                timeStamp +
                SecretKeyManager.getInstance().getSecretKeyMD5());
    }

    /**
     * 获取MD5加密的数据
     * @param content
     * @return
     */
    public static String getContentMd5(String content) {
        return getMd5Value(content);
    }
}
