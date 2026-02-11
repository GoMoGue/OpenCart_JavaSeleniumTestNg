import com.opencsv.exceptions.CsvException;
import utils.CsvUtils;

import java.io.IOException;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws IOException, CsvException {
        String csvPath = "./testData/OpenCart_SearchData.csv";

        String[][] csvData = CsvUtils.readAllData(csvPath);

        for (String[] arr : csvData) {
            System.out.println(Arrays.toString(arr));
        }

    }
}
