package pers.hugh.common.practice.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author xzding
 * @date 2018/7/17
 */
public class FileNIOPractice {

    private static final String CONTENT = "乾坤屯蒙，需讼师比，小畜履泰否。\n"
            + "同人大有谦豫随，蛊临观，噬嗑贲。\n"
            + "剥复无妄大畜颐，大过坎离。\n"
            + "咸恒遯，大壮晋明夷。\n"
            + "家人睽蹇解损益。\n"
            + "夬姤萃，升困井革鼎，震艮渐归妹。\n"
            + "丰旅巽兑，涣节中孚小过，既济未济。\n\n";

    private static final String FILE_PATH = "common-practice/target/fileniodemo.txt";

    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(FILE_PATH);
        FileChannel outChannel = fos.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(CONTENT.getBytes(Charset.forName("UTF-8")));
        //position=0, limit=position
        byteBuffer.flip();
        outChannel.write(byteBuffer);

        //position=0, limit=capacity
        byteBuffer.clear();
        FileInputStream fis = new FileInputStream(FILE_PATH);
        FileChannel inChannel = fis.getChannel();
        inChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), Charset.forName("UTF-8")));

        //position=0, limit=position
        byteBuffer.flip();
        outChannel.write(byteBuffer);

        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();

    }
}
