package com.andy.utils;

/**
 * 密钥管理单例
 */
public class SecretKeyManager {
    private String secretKeyMD5;
    private String secretKeyAES;
    private SecretKeyManager() {
    }
    //创建单例
    public static class SingletonHolder {
        private static final SecretKeyManager instance = new SecretKeyManager();
    }

    //获取单例
    public static SecretKeyManager getInstance() {
        return SingletonHolder.instance;
    }
    public void setSecretKey(String secretKeyMD5,String secretKeyAES) {
        this.secretKeyMD5 = secretKeyMD5;
        this.secretKeyAES = secretKeyAES;
    }
    public String getSecretKeyMD5() {
        if(secretKeyMD5==null){
            return "";
        }
        return secretKeyMD5;
    }

    public void setSecretKeyMD5(String secretKeyMD5) {
        this.secretKeyMD5 = secretKeyMD5;
    }

    public String getSecretKeyAES() {
        if(secretKeyAES==null){
            return "";
        }
        return secretKeyAES;
    }

    public void setSecretKeyAES(String secretKeyAES) {
        this.secretKeyAES = secretKeyAES;
    }
}
