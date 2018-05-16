package pers.hugh.common.practice.io.bytes;

import java.io.*;

/**
 * @author xzding
 * @date 2018/5/16
 */
public class ObjectIOPractice {

    private static final String FILE_PATH = "common-practice/target/objectdemo.txt";

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        Integer obj = Integer.MAX_VALUE;
        System.out.println("obj value:" + obj + " class:" + obj.getClass());
        objectOutputStream.writeObject(obj);
        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));
        Integer readObj = (Integer) objectInputStream.readObject();
        System.out.println("readObj value:" + readObj + " class:" + readObj.getClass());

        System.out.println("obj ?= readObj:" + String.valueOf(obj == readObj));
    }
}
