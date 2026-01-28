package utils;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A utility class for reading and extracting data from Excel files.
 *
 * <p>This class provides methods to interact with Excel files, such as retrieving
 * row counts, cell counts, cell data, and entire sheets as 2D arrays. It uses
 * Apache POI to handle Excel file operations.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     ExcelUtils excelUtils = new ExcelUtils("path/to/excel.xlsx");
 *     String[][] data = excelUtils.getAllDataInSheet("Sheet1");
 * </pre>
 */
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

    /**
     * Returns the total number of rows in the specified sheet.
     *
     * @param sheetName The name of the sheet.
     * @return The total number of rows in the sheet.
     * @throws IOException If the file is not accessible or the sheet does not exist.
     * @throws IllegalArgumentException If the sheet is not found.
     */
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

    /**
     * Returns the total number of cells in the specified row of the sheet.
     *
     * @param sheetName The name of the sheet.
     * @param rowIndex The index of the row.
     * @return The total number of cells in the row.
     * @throws IOException If the file is not accessible or the sheet does not exist.
     * @throws IllegalArgumentException If the sheet or row is not found.
     */
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

    /**
     * Returns the data in the specified cell as a string.
     *
     * @param sheetName The name of the sheet.
     * @param rowIndex The index of the row.
     * @param cellIndex The index of the cell.
     * @return The data in the cell as a string. Returns an empty string if the cell is blank.
     * @throws IOException If the file is not accessible or the sheet does not exist.
     * @throws IllegalArgumentException If the sheet or row is not found.
     */
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

            return cell.getStringCellValue();
        }
    }

    /**
     * Returns all data in the specified sheet as a 2D array of strings.
     *
     * <p>This method reads all data from the sheet, starting from the second row
     * (assuming the first row is a header), and returns it as a 2D array.</p>
     *
     * @param sheetName The name of the sheet.
     * @return A 2D array of strings containing all data in the sheet.
     * @throws IOException If the file is not accessible or the sheet does not exist.
     * @throws IllegalArgumentException If the sheet is not found.
     */
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

    /**
     * Helper method to safely convert a cell's value to a string.
     *
     * <p>Handles different cell types (e.g., string, numeric, boolean, formula, blank)
     * and converts them to their string representation.</p>
     *
     * @param cell The cell whose value is to be converted.
     * @return The cell's value as a string. Returns an empty string for blank cells.
     */
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

    /**
     * Helper private method to test if the Excel file exists and is accessible.
     *
     * @throws IOException If the file does not exist or is not accessible.
     */
    private void checkFileExistence() throws IOException {
        Path file = Paths.get(fileFullPath);
        if (!Files.exists(file) || !Files.isRegularFile(file)) {
            throw new IOException("File not found or not accessible: " + fileFullPath);
        }
    }
}
