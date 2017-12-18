package pers.hugh.common.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xzding on 2017/8/17.
 */
public class ExcelUtil {

    /**
     * 读取Excel数据内容
     *
     * @param filePath excel文件地址
     * @return
     */
    public static String[][] readExcelContent(String filePath) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
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
            e.printStackTrace();
            return null;
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
        String filePath = ExcelUtil.class.getClassLoader().getResource("sample.xlsx").getPath();
        String[][] content = readExcelContent(filePath);
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                System.out.print(content[i][j] + ",");
            }
            System.out.println();
        }
    }
}
