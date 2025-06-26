package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for managing test data from Excel files
 * Author: Sivaraman M
 */
public class TestDataManager {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/";

    public static Object[][] getTestData(String fileName, String sheetName) {
        List<Map<String, String>> testData = readExcelData(fileName, sheetName);
        Object[][] data = new Object[testData.size()][1];
        
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        
        return data;
    }

    public static List<Map<String, String>> readExcelData(String fileName, String sheetName) {
        List<Map<String, String>> testData = new ArrayList<>();
        String filePath = TEST_DATA_PATH + fileName;

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in file: " + fileName);
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Header row not found in sheet: " + sheetName);
            }

            List<String> headers = new ArrayList<>();
            for (Cell cell : headerRow) {
                headers.add(getCellValueAsString(cell));
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Map<String, String> rowData = new HashMap<>();
                for (int j = 0; j < headers.size(); j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = cell != null ? getCellValueAsString(cell) : "";
                    rowData.put(headers.get(j), cellValue);
                }
                testData.add(rowData);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return testData;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}