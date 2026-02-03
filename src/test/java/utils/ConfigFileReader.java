package utils;

import org.testng.SkipException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from a properties file.
 * This class loads the configuration file once and provides static methods to access the properties.
 */
public class ConfigFileReader {

    private static Properties properties;

    /**
     * Static initialization block.
     * Runs once when the class is loaded into memory, before any static methods or instances are created.
     * Loads the configuration file "config.properties" from the resources directory.
     *
     * @throws SkipException if the properties file cannot be read.
     */
    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new SkipException("Skipping test: could not read properties file. " + e.getMessage());
        }
    }

    public static String getExecutionEnvironment() {
        return properties.getProperty("execution_environment").trim();
    }

    public static String getGridHubUrl() {
        return properties.getProperty("grid_hub_url", "http://localhost:4444").trim();
    }

    public static String getHomePageURL() {
        return properties.getProperty("homePageURL").trim();
    }

    public static String getLoginPageURL() {
        return properties.getProperty("loginPageURL").trim();
    }

    public static String getRegistrationPageURL() {
        return properties.getProperty("registrationPageURL").trim();
    }

    public static String getMyAccountPageURL() {
        return properties.getProperty("myAccountPageURL").trim();
    }

    public static String getEmail() {
        return properties.getProperty("email").trim();
    }

    public static String getPassword() {
        return properties.getProperty("password").trim();
    }
}