package pers.hugh.common.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static pers.hugh.common.util.ExcelUtil.*;

/**
 * @author xzding
 * @date 2018/5/21
 */
public class ExcelUtilTest {

    @Test
    public void test(){
        String filePath = "D:/city.xlsx";
        String sheetName = "europe";

        //写入
        String[][] writeContent = new String[][]{
                {"巴黎", "伦敦"},
                {"Paris", "London"},
                {"1", "2"}
        };
        boolean result = writeData(filePath, sheetName, writeContent);
        System.out.println("Write result:" + result);

        //读取
        String[][] readContent = readData(filePath, sheetName);
        System.out.println("Read content:");
        for (int i = 0; i < readContent.length; i++) {
            for (int j = 0; j < readContent[i].length; j++) {
                System.out.print(readContent[i][j] + ",");
            }
            System.out.println();
        }
    }

}