package utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;


public class DataProviders {

    @DataProvider(name = "invalidLoginData")
    public String[][] getInvalidLoginData() throws IOException {

        String excelFilePath = "./testData/OpenCart_LoginData.xlsx";

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);

        return excelUtils.getAllDataInSheet("Sheet1");
    }

    @DataProvider(name = "searchData")
    public String[][] getSearchData() throws IOException {

        String excelFilePath = "./testData/OpenCart_SearchData.xlsx";

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);

        return excelUtils.getAllDataInSheet("Sheet1");
    }
}
