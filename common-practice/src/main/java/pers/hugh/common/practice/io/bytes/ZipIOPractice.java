package pers.hugh.common.practice.io.bytes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author xzding
 * @date 2018/5/22
 */
public class ZipIOPractice {

    private static final String FILE_PATH = "common-practice/target/zipdemo.zip";

    public static void main(String[] args) throws IOException {
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(FILE_PATH));
        ZipEntry e1 = new ZipEntry("e1.txt");
        ZipEntry e2 = new ZipEntry("e2.txt");
        zipOutputStream.putNextEntry(e1);
        zipOutputStream.write(1);
        zipOutputStream.closeEntry();
        zipOutputStream.putNextEntry(e2);
        zipOutputStream.write(2);
        zipOutputStream.closeEntry();
        zipOutputStream.close();

        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(FILE_PATH));
        ZipEntry e;
        while ((e = zipInputStream.getNextEntry()) != null) {
            System.out.println(e.getName() + " " + zipInputStream.read());
            zipInputStream.closeEntry();
        }
        zipInputStream.close();
    }
}
