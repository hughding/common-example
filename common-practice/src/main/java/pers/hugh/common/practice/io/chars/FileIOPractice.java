package pers.hugh.common.practice.io.chars;

import java.io.*;

/**
 * @author xzding
 * @date 2018/5/3
 */
public class FileIOPractice {
    private static final String content = "我是谁？我在那？";

    private static final String FILE_PATH = "common-practice/target/filedemo.txt";

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = new FileOutputStream(FILE_PATH);
        Writer writer = new OutputStreamWriter(outputStream);
        writer.append(content);
        writer.close();

        InputStream inputStream = new FileInputStream(FILE_PATH);
        Reader reader = new InputStreamReader(inputStream);
        char[] buffer = new char[12];
        int readLength;
        while ((readLength = reader.read(buffer)) != -1) {
            System.out.print(String.valueOf(buffer, 0, readLength));
        }
        inputStream.close();
    }
}
