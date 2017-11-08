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
     * @param filePath excel文件地址
     * @return Map 包含单元格数据内容的Map对象
     */
    public static Map<Integer,Map<Integer,String>> readExcelContent(String filePath) {
        Map<Integer,Map<Integer,String>> content = new HashMap<Integer,Map<Integer,String>>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 得到总行数
            int rowNum = sheet.getLastRowNum();
            int colNum = sheet.getRow(0).getPhysicalNumberOfCells();
            for (int i = 0; i <= rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                for (int j = 0; j < colNum; j++) {
                    if(content.get(i) == null){
                        content.put(i, new HashMap<Integer,String>());
                    }
                    content.get(i).put(j,getCellFormatValue(row.getCell(j)).trim());
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 根据XSSFCell类型设置数据
     * @param cell
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String getCellFormatValue(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
                // 如果当前Cell的Type为NUMERIC
                case XSSFCell.CELL_TYPE_NUMERIC:
                case XSSFCell.CELL_TYPE_FORMULA: {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case XSSFCell.CELL_TYPE_STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}
