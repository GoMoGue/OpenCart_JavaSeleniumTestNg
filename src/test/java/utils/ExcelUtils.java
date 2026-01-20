package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelUtils {

    private String fileFullPath;

    public ExcelUtils(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    private void checkFileExistence() throws IOException {
        Path file = Paths.get(fileFullPath);
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            throw new IOException("File not found or not accessible: " + fileFullPath);
        }
    }

    public int getRowCount(String sheetName) throws IOException {

        try (FileInputStream inputStream = new FileInputStream(fileFullPath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found");
            }
            int lastRowWithData = sheet.getLastRowNum();
            return lastRowWithData + 1;
        }
    }

    public int getCellCount(String sheetName, int rowIndex) throws IOException {

        try (FileInputStream inputStream = new FileInputStream(fileFullPath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found");
            }
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                throw new IllegalArgumentException("Row not found");
            }
            return row.getLastCellNum();
        }
    }

    public String getCellData(String sheetName, int rowIndex, int cellIndex) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fileFullPath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found");
            }
            Row row = sheet.getRow(rowIndex);
            if (row == null) {
                throw new IllegalArgumentException("Row not found");
            }
            Cell cell = row.getCell(cellIndex);
            if (cell == null) {
                return "";
            }
            //  For blank cells we return an empty string.
            return cell.getStringCellValue();
        }
    }

    public String[][] getAllDataInSheet(String sheetName) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(fileFullPath);
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet not found");
            }

            int lastRowIndex = sheet.getLastRowNum();
            int colCount =  sheet.getRow(0).getLastCellNum();

            String[][] data = new String[lastRowIndex][colCount];

            for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                for (int cellIndex = 0; cellIndex < colCount; cellIndex++) {
                    Cell cell = currentRow.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    //  Note: For blank cells returns an empty string.
                    data[rowIndex - 1][cellIndex] = getCellValueAsString(cell);
                }
            }

            return data;
        }
    }

    // Helper method to safely get cell value as String
    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }
}
