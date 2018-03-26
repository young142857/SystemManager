package com.manager.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

/** 
  * @ClassName : AesUtil
  * @Description : AES���ܽ���
  * @Author : yangyang 
  * @Date : 2018��3��19�� ����12:52:06 
  * @Version : V1.0
  */
public class AesUtil {
	// AES��������(16λ����)
	private static final String AEC_PWD = "1234567890123456";
	
	/**
	 * @Title : encrypt
	 * @Description : ����
	 * @Date : 2018��3��19�� ����4:49:37
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
    public static String encrypt(String sSrc) throws Exception {
        byte[] raw = AEC_PWD.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"�㷨/ģʽ/���뷽ʽ"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        
        return new Base64().encodeToString(encrypted);//�˴�ʹ��BASE64��ת�빦�ܣ�ͬʱ����2�μ��ܵ����á�
    }

    /**
     * @Title : decrypt
     * @Description : ����
     * @Date : 2018��3��19�� ����4:49:46
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
            byte[] encrypted1 = new Base64().decode(sSrc);//����base64����
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
