package com.manager.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/** 
  * @ClassName : AesUtil
  * @Description : AES加密解密
  * @Author : yangyang 
  * @Date : 2018年3月19日 下午12:52:06 
  * @Version : V1.0
  */
public class AesUtil {
	// AES加密密码(16位密码)
	private static final String AEC_PWD = "1234567890123456";
	
	/**
	 * @Title : encrypt
	 * @Description : 加密
	 * @Date : 2018年3月19日 下午4:49:37
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
    public static String encrypt(String sSrc) throws Exception {
        byte[] raw = AEC_PWD.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        
        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    /**
     * @Title : decrypt
     * @Description : 解密
     * @Date : 2018年3月19日 下午4:49:46
     * @param sSrc
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = AEC_PWD.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
