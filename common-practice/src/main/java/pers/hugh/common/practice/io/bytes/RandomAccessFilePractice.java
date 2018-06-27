package pers.hugh.common.practice.io.bytes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

/**
 * @author xzding
 * @date 2018/6/27
 */
public class RandomAccessFilePractice {

    private static final String CONTENT = "我是谁？我在那？";

    private static final String FILE_PATH = "common-practice/target/randomAccessFileDemo.txt";

    public static void main(String[] args) throws IOException {
        RandomAccessFile rafw = new RandomAccessFile(FILE_PATH,"rw");
        rafw.seek(10);
        long wPointerBegain = rafw.getFilePointer();
        rafw.write(CONTENT.getBytes(Charset.forName("UTF-8")));
        long wPointerEnd = rafw.getFilePointer();
        rafw.close();
        System.out.println(String.format("wPointerBegain: %s, wPointerEnd:%s",wPointerBegain, wPointerEnd));


        RandomAccessFile rafr = new RandomAccessFile(FILE_PATH,"rw");
        rafr.seek(10);
        long rPointerBegain = rafr.getFilePointer();
        byte[] content = new byte[1024];
        int readLength;
        while((readLength = rafr.read(content)) != -1){
            System.out.println(String.format("Content:%s", new String(content,0,readLength,Charset.forName("UTF-8"))));
        }
        long rPointerEnd = rafr.getFilePointer();
        rafr.close();

        System.out.println(String.format("rPointerBegain: %s, rPointerEnd:%s",rPointerBegain, rPointerEnd));

    }
}
