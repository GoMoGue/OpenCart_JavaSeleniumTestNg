import utils.ExcelUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestinGrounds {

    public static void main(String[] args) throws IOException {

//        String randomStr = Randomizer.generateRandomAlphaNumericStr(10);
//        String randomEmail = Randomizer.generateRandomUUIDEmail();
//
//        System.out.println(randomStr);
//        System.out.println(randomEmail);

        String excelFileName = "./testData/OpenCart_SearchData.xlsx";

        Path excelFilePath = Paths.get(excelFileName);
        System.out.println("File exists: " + Files.exists(excelFilePath));

        ExcelUtils excelUtils = new ExcelUtils(excelFileName);

//        int rowCount = 0;
//        int cellCount = 0;
//        String cellStr = null;
//
//        try {
//            rowCount = excelUtil.getRowCount("Sheet1");
//            cellCount = excelUtil.getCellCount("Sheet1", 0);
//            cellStr = excelUtil.getCellData("Sheet1", 0, 3);
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//        System.out.println("Row count: " + rowCount);
//        System.out.println("Cell count for row 0: " + cellCount);
//        System.out.println("Cell data: " + cellStr);
        try {
            String[][] data = excelUtils.getAllDataInSheet("Sheet1");
            // Loop through all rows
            for (int i = 0; i < data.length; i++) {
                // Loop through all elements of current row
                for (int j = 0; j < data[i].length; j++) {
                    System.out.print(data[i][j] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
