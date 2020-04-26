package com.ithanlei.util;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;

/**
 * MD5工具类 因为只有存储用户密码时用到，所以放在这里
 */
public class MD5Util {
    // MD5加码。32位
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        //字符串的每一位转为byte
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);
        //StringBuffer线程安全
        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            //按位与运算
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    // 可逆的加密算法
    public static String codeMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            //异或运算
            a[i] = (char) (a[i] ^ 't');
        }
        String s = a.toString();
        return s;
    }

    // 加密后解密
    public  static String decodeMD5(String inStr) {
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String k = a.toString();
        return k;
    }

    // 测试主函数
    public static void main(String args[]) {

        String s = new String("1184099909");
        char[] charArray = s.toCharArray();
        byte[] byteArrar = new byte[charArray.length];
        for(int i = 0; i<charArray.length; i++){
            byteArrar[i] = (byte) charArray[i];
        }
        //这是spring封装好的工具类
        String amd5 = DigestUtils.md5DigestAsHex(byteArrar);
        System.out.println("spring工具类加密后的：" + amd5);

        System.out.println("原始：" + s);
        System.out.println("MD5后：" + MD5(s));
        System.out.println("MD5后再加密：" + codeMD5(MD5(s)));
        System.out.println("解密为MD5后的：" + decodeMD5(codeMD5(MD5(s))));
    }


}
