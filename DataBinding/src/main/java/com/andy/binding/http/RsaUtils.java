package com.andy.binding.http;

import android.util.Base64;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * @author cfeng
 * @version 1.0
 * @date 2020/11/3 14:14
 */
public class RsaUtils {
    public static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "RSA";

    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }
    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] decode=base642Byte(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(decode);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
//        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }
    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }
    /**
     * 私钥解密
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, PrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] content=base642Byte(data);
            byte[] bytes = cipher.doFinal(content);
            return new String(bytes, CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
        return Base64.encodeToString(bytes,Base64.NO_WRAP);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException{
        return Base64.decode(base64Key,Base64.NO_WRAP);
    }
    public static final String PRIVATE_KEY = "TUlJQ2RRSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NBbDh3Z2dKYkFnRUFBb0dCQUpjeFVYM1JnbjcxVlhnVTFoK1p3OWJ1NFgyRQ0KN0t1cFNPbGlBUU0vanAzbElZdkhGVlRFYmZWQlFIUERZSXNiaEhlL3RrUDQxNE1GZzhneFZNK05tU2Y5K0NOV3BFcS90UkE2OFpWaw0KZ0Z3c25UaUZoaVRPc1VLYUtvbUhKdVZGZS9lcUI0MDhpM2h5WkVKT0dVQVpweWFFV2xtY0g1RTM2NGxGUDZJaitIZ2pBZ01CQUFFQw0KZ1lCQWxxd0dnT01yazJqd3hJMzRyL3FPcmp3Y0lCWHUrNEdQV2ZPdXpJOERldWdLeTRDY1Z0SFdqUXFZcWFWcnA5UytSMkw0eEt4aw0KWjQyMWp3VjVLVGQ4eW5YQ0QyTnhlL1Yxcjl3Nkd6SDdPeTRlNkdVWmFZUWdnWWRPd1ptSm5SYkxTclhNTktKYzV0RlBRakhVbGlLdw0Kbk5jRzN6Y1hjRm1kVzFLUklBVGtnUUpCQU5ad2l3dXlrRmpkNHlxYk1yTkppOFJTc2pBSTY0YTFlK241Z25zRTdsdE1tT09YL0xmUA0Kd2gzd01jZi9xZTFBQThvUHIxQzFoWHZOZmg1UW8zTnplMEVDUVFDMGZzUXVqRXYxY3NWUzlkRlkyejVxV0dMUWgwK280RFVPdU9KUg0KUkR2Uko1WFlCeGpTVy9aVGNoUmpGbDYzWlZQVm00dUtxWmxDVklIR2tUK0lRazVqQWtCR0NPT2dvbVQ2WVIxOElNdmlPamJubTRxTw0KblpzdWw3OHNuMHk2R05Zd0EwZzdyTHZnM0gydGdYRUF2TU1tdFMySHBLL0JVRWVVTTN6cE1iVHdVOXNCQWtBY1gyajdZRFo0aU9nWA0KMkdobUxXQ3NXdVI5bnllWWhsWVEwR05USHdUQUpoYlo5Sk9rVGF4MUJaUEsxN05mNU4rK3prcXJLMm5hRVdxdENod1FrVXU5QWtCSA0KT3Jvbk53ZWRlU3BJZU40RUp4ODVzWnNNWExpUFlIM1o3M0RPMk5TUTR3YmV3WDZyZTM0QUp6b0RXaGlSb1M5VnVQYURlVWVBVnpSbg0KbnV2czd2dXg=";

    /*public static void main (String[] args) throws Exception {


        String secret="dC9Eys5Eq75lXTDNklhe5bXBDlrmaEr6H0QJSrVJEwf99dpbMsHSWEYeCtr3jqrYVa3i/LAuKQdhTJ81cT/3cxQeIvRSYXf6yr6FL/oZXmJAlej/JqI4Y9+Dmx+YBZI15WBarWRI7xKULSuycz7QJivB8MnGJyBnBo7LpoxF3ms=";//aes密码
        *//*解密*//*
        PrivateKey privateKey = string2PrivateKey(PRIVATE_KEY);
        byte[] base642Byte = base642Byte(secret);
        byte[] privateDecrypt = privateDecrypt(base642Byte, privateKey);
        String secretStr=new String(privateDecrypt);
        System.out.println(secretStr);

    }*/
    public static Map<String, String> createKeys(int keySize) throws Exception {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPair keyPair=getKeyPair();
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put(RSAUtil.KEY_NAMES.publicKey.toString(),  getPublicKey(keyPair));
        keyPairMap.put(RSAUtil.KEY_NAMES.privateKey.toString(), getPrivateKey(keyPair));

        return keyPairMap;
    }
    public static void main (String[] args) throws Exception {

        Map<String, String> keyMap = createKeys(512);
        String  publicKey = keyMap.get("publicKey");
        String  privateKey = keyMap.get("privateKey");
        System.out.println("公钥: \n\r" + publicKey+" "+publicKey.length());
        System.out.println("私钥： \n\r" + privateKey+" "+privateKey.length());

        System.out.println("公钥加密——私钥解密");
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文大小：\r\n" + str.getBytes().length);
        String encodedData = RSAUtil.publicEncrypt(str, RSAUtil.getPublicKey(publicKey));
        System.out.println("密文：\r\n" + encodedData);
        String decodedData = RSAUtil.privateDecrypt(encodedData, RSAUtil.getPrivateKey(privateKey));
        System.out.println("解密后文字: \r\n" + decodedData);

    }
}
