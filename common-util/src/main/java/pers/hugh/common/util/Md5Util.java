package pers.hugh.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/11/9</pre>
 */
public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 生成MD5
     *
     * @param message
     * @return
     */
    public static String getMd5(String message) {
        String md5 = "";
        try {
            // 创建一个md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageByte = message.getBytes("UTF-8");
            // 获得MD5字节数组,16*8=128位
            byte[] md5Byte = md.digest(messageByte);
            // 转换为16进制字符串
            md5 = bytesToHex(md5Byte);
        } catch (Exception e) {
            logger.error("getMd5 error", e);
            e.printStackTrace();
        }
        return md5;
    }


    /**
     * 二进制转十六进制
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexStr = new StringBuilder();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if (num < 0) {
                num += 256;
            }
            if (num < 16) {
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}
