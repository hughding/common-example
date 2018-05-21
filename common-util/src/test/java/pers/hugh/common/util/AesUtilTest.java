package pers.hugh.common.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static pers.hugh.common.util.AesUtil.*;

/**
 * @author xzding
 * @date 2018/5/21
 */
public class AesUtilTest {

    @Test
    public void test(){
        System.out.println("=======================Hex加解密============================");
        String plaintext1 = "abcdefghijklmn";
        System.out.println("明文:" + plaintext1);
        String ciphertext1 = encrypt2HexStr(plaintext1, "123456");
        System.out.println("密文:" + ciphertext1);
        System.out.println("明文:" + AesUtil.decryptFromHexStr(ciphertext1, "123456"));

        System.out.println("=======================Base64加解密=========================");
        String plaintext2 = "abcdefghijklmn123123";
        System.out.println("明文:" + plaintext2);
        String ciphertext2 = encrypt2Base64Str(plaintext2, "123456");
        System.out.println("密文:" + ciphertext2);
        System.out.println("明文:" + decryptFromBase64Str(ciphertext2, "123456"));
    }

}