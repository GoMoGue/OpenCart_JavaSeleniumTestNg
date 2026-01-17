package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileLoader {

    /**
     * Loads a properties file from the filesystem.
     *
     * @param fileName Path to the properties file.
     * @return Properties object containing the file's key-value pairs.
     * @throws IOException If the file cannot be found or read.
     */
    public static Properties loadPropertiesFile(String fileName) throws IOException {

        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty.");
        }

        Properties properties = new Properties();

        try (FileInputStream inputStream = new FileInputStream(fileName.trim())) {
            properties.load(inputStream);
        }

        return properties;
    }
}
