package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.ConfigFileLoader;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * A base test class for UI automation tests.
 *
 * <p>This class provides the foundational setup and teardown for all test classes.
 * It initializes the {@link WebDriver} instance, loads configuration properties,
 * and manages the test lifecycle. All test classes should extend this class
 * to ensure consistent setup and cleanup.</p>
 *
 * <p>Supports multiple browsers (e.g., Firefox, Brave) and loads test configuration
 * from a properties file.</p>
 */
public class BaseTest {

    private String braveBrowserLocation = "/var/lib/flatpak/exports/bin/com.brave.Browser";
    private String configFileLocation = "./src/test/resources/config.properties";
    private WebDriver driver;
    private Logger logger;
    private String browser;
    private String os;
    private Properties properties;

    public WebDriver getDriver() {
        return driver;
    }

    public Logger getLogger() {
        return logger;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOs() {
        return os;
    }

    /**
     * Sets up the test environment before the test class runs.
     * <p>This method initializes the logger, loads the configuration properties,
     * and sets up the WebDriver based on the specified browser and OS.
     * It also configures the WebDriver with implicit waits and maximizes the browser window.</p>
     * @param os      The operating system where the test is running.
     * @param browser The browser to use for the test (e.g., "firefox", "brave").
     * @throws SkipException If the properties file cannot be loaded or the browser name is invalid.
     */
    @BeforeClass(alwaysRun = true)
    @Parameters({"os", "browser"})
    public void setUp(String os, String browser) {
        logger = LogManager.getLogger(getClass());
        logger.info("Setting up WebDriver...");
        this.browser = browser;
        this.os = os;

        // Load config.properties file
        try {
            this.properties = ConfigFileLoader.loadPropertiesFile(configFileLocation);
        } catch (IOException e) {
            logger.error("Cannot load properties file: {}.", e.getMessage());
            throw new SkipException("Skipping test: could not read properties file");
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "brave":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setBinary(braveBrowserLocation);
                driver = new ChromeDriver(chromeOptions);
                break;
            default:
                logger.error("Invalid browser name: {}. Skipping test.", browser);
                throw new SkipException("Skipping test: Invalid browser name - " + browser);
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(properties.getProperty("homePageURL"));
        driver.manage().window().maximize();
        logger.info("WebDriver initialized");
    }

    /**
     * Cleans up the test environment after the test class runs.
     * <p>This method quits the WebDriver instance if it exists.</p>
     */
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        // Quit driver if it exists
        if (driver != null) {
            driver.quit();
            logger.info("Webdriver quit");
        }
    }
}
