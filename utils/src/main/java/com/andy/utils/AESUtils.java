package com.andy.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
  private static final String KEY_ALGORITHM = "AES";
  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";


  public static String encrypt(String value) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(SecretKeyManager.getInstance().getSecretKeyAES().getBytes("UTF-8"), KEY_ALGORITHM);

      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

      byte[] encrypted = cipher.doFinal(value.getBytes());
      return Base64.encodeToString(encrypted, Base64.DEFAULT);
    } catch (Exception ex) {

    }

    return null;
  }

  public static String decrypt(String encrypted) {
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(SecretKeyManager.getInstance().getSecretKeyAES().getBytes("UTF-8"), KEY_ALGORITHM);

      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
      cipher.init(Cipher.DECRYPT_MODE, skeySpec);

      byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));

      return new String(original);
    } catch (Exception ex) {

    }

    return null;
  }

}
