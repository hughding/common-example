package pers.hugh.common.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author xzding
 * @version 1.0
 * @since <pre>2017/10/9</pre>
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 读取Excel数据内容
     *
     * @param filePath excel文件地址
     * @return
     */
    public static String[][] readData(String filePath, String sheetName) {
        try {
            //创建XSSF,对应 excel.xlsx
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = workbook.getSheet(sheetName);
            // 得到总行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            //得到总列数
            int colNum = sheet.getRow(0).getPhysicalNumberOfCells();

            String[][] content = new String[rowNum][colNum];
            for (int i = 0; i < rowNum; i++) {
                for (int j = 0; j < colNum; j++) {
                    content[i][j] = getCellFormatValue(sheet.getRow(i).getCell(j));
                }
            }
            return content;
        } catch (IOException e) {
            logger.error("readData error", e);
            return null;
        }
    }

    /**
     * 向Excel写入内容，若存在文件，则覆盖
     *
     * @param filePath  excel文件地址
     * @param sheetName sheet名称
     * @param content   写入内容
     * @return
     */
    public static boolean writeData(String filePath, String sheetName, String[][] content) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        for (int i = 0; i < content.length; i++) {
            XSSFRow row = sheet.createRow(i);
            for (int j = 0; j < content[i].length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(content[i][j]);
            }
        }
        try (FileOutputStream fout = new FileOutputStream(filePath);) {
            workbook.write(fout);
            return true;
        } catch (IOException e) {
            logger.error("writeData error", e);
            return false;
        }
    }

    /**
     * 获取cell中的值
     *
     * @param cell
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String getCellFormatValue(XSSFCell cell) {
        String cellValue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为“数字”或者“公式”
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                // 如果当前Cell的Type为“字符串”
                case XSSFCell.CELL_TYPE_STRING:
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                // 如果当前Cell的Type为“BOOLEAN”
                case XSSFCell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                // 默认的Cell值
                default:
                    cellValue = " ";
            }
        }
        return cellValue;
    }

    public static void main(String[] args) {
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
