package pers.hugh.common.practice.io.bytes;

import java.io.*;

/**
 * @author xzding
 * @date 2018/5/21
 */
public class DataIOPractice {

    private static final String FILE_PATH = "common-practice/target/datademo.txt";

    public static void main(String[] args) throws IOException {

        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(FILE_PATH));
        dataOutputStream.writeChar('T');
        dataOutputStream.writeBoolean(true);
        dataOutputStream.writeInt(1);
        dataOutputStream.writeDouble(1.2);
        dataOutputStream.writeChars("我是谁？我在哪？");
        dataOutputStream.close();

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(FILE_PATH));
        System.out.println(dataInputStream.readChar());
        System.out.println(dataInputStream.readBoolean());
        System.out.println(dataInputStream.readInt());
        System.out.println(dataInputStream.readDouble());
        while (dataInputStream.available() > 0){
            System.out.print(dataInputStream.readChar());
        }
        System.out.println();
        dataInputStream.close();
    }
}
