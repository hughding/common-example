package pers.hugh.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密，支持密文编码模式为：16进制，Base64
 *
 * @author xzding
 * @version 1.0
 * @since <pre>2018/1/8</pre>
 */
public class AesUtil {

    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);

    private static final String KEY_ALGORITHM = "AES";
    /**
     * 默认的加密算法
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final BASE64Encoder BASE_64_ENCODER = new BASE64Encoder();
    private static final BASE64Decoder BASE_64_DECODER = new BASE64Decoder();

    private enum Mode {
        /**
         * Base64模式
         */
        BASE64,
        /**
         * 16进制模式
         */
        HEX,;
    }

    /**
     * AES 加密操作
     *
     * @param mode     加密数据转码模式
     * @param content  明文
     * @param password 密码
     * @return mode模式转码后的加密数据
     */
    private static String encrypt(Mode mode, String content, String password) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);

            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(password));

            // 加密
            byte[] result = cipher.doFinal(byteContent);

            if (mode == Mode.HEX) {
                //返回十六进制转码后的加密数据
                return parseByte2HexStr(result);
            } else {
                //返回Base64转码后的加密数据
                return BASE_64_ENCODER.encode(result);
            }
        } catch (Exception e) {
            logger.error("encrypt exception", e);
            return null;
        }
    }

    /**
     * AES 解密操作
     *
     * @param mode     content的转码模式
     * @param content  mode模式的密文
     * @param password 密码
     * @return 明文
     */
    private static String decrypt(Mode mode, String content, String password) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));

            byte[] byteContent;
            if (mode == Mode.HEX) {
                //十六进制转byte[]
                byteContent = parseHexStr2Byte(content);
            } else {
                //Base64转byte[]
                byteContent = BASE_64_DECODER.decodeBuffer(content);
            }
            //执行操作
            byte[] result = cipher.doFinal(byteContent);

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("decrypt exception", e);
            return null;
        }
    }

    /**
     * 生成加密秘钥
     *
     * @param password
     * @return
     */
    private static SecretKeySpec getSecretKey(final String password) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

            //AES 要求密钥长度为 128
            kg.init(128, new SecureRandom(password.getBytes()));

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();

            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            logger.error("getSecretKey exception", e);
            return null;
        }
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static String encrypt2HexStr(String content, String password) {
        return encrypt(Mode.HEX, content, password);
    }

    public static String decryptFromHexStr(String content, String password) {
        return decrypt(Mode.HEX, content, password);
    }

    public static String encrypt2Base64Str(String content, String password) {
        return encrypt(Mode.BASE64, content, password);
    }

    public static String decryptFromBase64Str(String content, String password) {
        return decrypt(Mode.BASE64, content, password);
    }

}
