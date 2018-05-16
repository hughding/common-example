package pers.hugh.common.practice.io.bytes;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author xzding
 * @date 2018/5/3
 */
public class FileIOPractice {

    private static final String content = "我是谁？我在那？";

    private static final String FILE_PATH = "common-practice/target/filedemo.txt";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream(FILE_PATH);
        outputStream.write(content.getBytes(Charset.forName("UTF-8")));
        outputStream.close();


        InputStream inputStream = new FileInputStream(FILE_PATH);
        //我字占了3个字节
        int a = inputStream.read();
        int b = inputStream.read();
        int c = inputStream.read();
        byte[] head = new byte[]{(byte) a, (byte) b, (byte) c};
        System.out.print(new String(head, Charset.forName("UTF-8")));

        //汉字占用2-4个字节，所以取最小公倍数为缓冲池大小
        byte[] buffer = new byte[12];
        int readLength = 0;
        while ((readLength = inputStream.read(buffer)) != -1) {
            System.out.print(new String(buffer, 0, readLength, Charset.forName("UTF-8")));
        }
        inputStream.close();
    }
}
