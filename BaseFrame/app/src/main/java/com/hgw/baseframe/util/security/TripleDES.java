package com.hgw.baseframe.util.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 描述：3des对称加密
 * @author hgw
 *
 */
public class TripleDES {

  /**定义加密算法，DESede即3DES*/
  private static final String Algorithm = "DESede";
  /**加密密钥*/
  private static final String PASSWORD_CRYPT_KEY ="AB#CD$%DGABB&*(FGA%*CDEF";


  /**
   * 加密方法
   * @param src 源数据的字节数组
   * @return
   */
  public static byte[] encryptMode(byte[] src) {
    try {
      // 生成密钥
      SecretKey deskey = new SecretKeySpec(build3DesKey2(PASSWORD_CRYPT_KEY), Algorithm);
      // 实例化Cipher
      Cipher cipher = Cipher.getInstance(Algorithm);
      cipher.init(Cipher.ENCRYPT_MODE, deskey);
      return cipher.doFinal(src);
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    } catch (javax.crypto.NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  /**
   * 解密方法
   * @param src 密文的字节数组
   * @return
   */
  public static byte[] decryptMode(byte[] src) {
    try {
      SecretKey deskey = new SecretKeySpec(build3DesKey2(PASSWORD_CRYPT_KEY), Algorithm);
      Cipher c1 = Cipher.getInstance(Algorithm);
      c1.init(Cipher.DECRYPT_MODE, deskey);
      return c1.doFinal(src);
    } catch (NoSuchAlgorithmException e1) {
      e1.printStackTrace();
    } catch (javax.crypto.NoSuchPaddingException e2) {
      e2.printStackTrace();
    } catch (Exception e3) {
      e3.printStackTrace();
    }
    return null;
  }

  /**
   * 密钥生成方式一（根据实际项目规则来配置）
   * 规则：（Key转为字节数组，然后取前面，不大于24位的字节）
   * @param keyStr
   * @return
   * @throws UnsupportedEncodingException
   */
  public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
    byte[] key = new byte[24];
    byte[] temp = keyStr.getBytes();

    if (key.length > temp.length) {
      System.arraycopy(temp, 0, key, 0, temp.length);
    } else {
      System.arraycopy(temp, 0, key, 0, key.length);
    }
    return key;
  }

  /**
   * 密钥生成方式二（根据实际项目规则来配置）
   * 规则：（对Key进行MD5，然后取前24位字符）
   * @param keyStr
   * @return
   */
  public static byte[] build3DesKey2(String keyStr){
    String key   = null;
    try {
      key = MD5.MD5Encode(keyStr);
      key = key.substring(0,24);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return key.getBytes();
  }

}