package utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

/**
 * A utility class for providing test data to TestNG test methods using the {@link DataProvider} annotation.
 *
 * <p>This class contains methods annotated with {@link DataProvider} that supply test data
 * from Excel files to TestNG test methods. Each method reads data from a specific Excel file
 * and sheet, and returns it as a 2D array of strings for use in data-driven testing.</p>
 *
 * <p>Example usage in a TestNG test:</p>
 * <pre>
 *     {@code @Test(dataProvider = "invalidLoginData", dataProviderClass = DataProviders.class)}
 *     public void testInvalidLogin(String username, String password) {
 *         // Test logic
 *     }
 * </pre>
 */
public class DataProviders {

    /**
     * Provides invalid login test data from an Excel file.
     *
     * <p>This method reads data from the "Sheet1" of the "OpenCart_LoginData.xlsx" file
     * and returns it as a 2D array of strings. Each row in the array represents a set of
     * test data for invalid login scenarios.</p>
     *
     * @return A 2D array of strings containing invalid login data.
     * @throws IOException If there is an error reading the Excel file.
     */
    @DataProvider(name = "invalidLoginData")
    public String[][] getInvalidLoginData() throws IOException {

        String excelFilePath = "./testData/OpenCart_LoginData.xlsx";

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);

        return excelUtils.getAllDataInSheet("Sheet1");
    }

    /**
     * Provides search test data from an Excel file.
     *
     * <p>This method reads data from the "Sheet1" of the "OpenCart_SearchData.xlsx" file
     * and returns it as a 2D array of strings. Each row in the array represents a set of
     * test data for search scenarios.</p>
     *
     * @return A 2D array of strings containing search data.
     * @throws IOException If there is an error reading the Excel file.
     */
    @DataProvider(name = "searchData")
    public String[][] getSearchData() throws IOException {

        String excelFilePath = "./testData/OpenCart_SearchData.xlsx";

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath);

        return excelUtils.getAllDataInSheet("Sheet1");
    }
}
