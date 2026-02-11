package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvUtils {

    /**
     * Reads all data from a CSV file and returns it as a 2D String array
     * so it can be used in a DataProvider
     *
     * @param fileName The path to the CSV file.
     * @return A 2D array of strings representing the CSV data.
     * @throws IOException  If the file cannot be read.
     * @throws CsvException If there is an error parsing the CSV.
     */
    public static String[][] readAllData(String fileName) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            List<String[]> csvData = reader.readAll();
            // Remove header row
            if (!csvData.isEmpty()) {
                csvData.removeFirst();
            }
            return csvData.toArray(new String[0][]);
        }
    }
}
