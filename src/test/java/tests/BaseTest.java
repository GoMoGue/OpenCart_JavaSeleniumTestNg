package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.ConfigFileReader;
import utils.DriverFactory;
import java.time.Duration;

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

    private WebDriver driver;
    private Logger logger;
    private String browser;
    private String os;

    public WebDriver getDriver() {
        return driver;
    }

    public Logger getLogger() {
        return logger;
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

        String executionEnvironment = ConfigFileReader.getExecutionEnvironment();
        if (executionEnvironment.equalsIgnoreCase("local")) {
            driver = DriverFactory.createDriver(browser);
        } else if (executionEnvironment.equalsIgnoreCase("remote")) {
            driver = DriverFactory.createRemoteDriver(browser, os, ConfigFileReader.getGridHubUrl());
        } else {
            throw new SkipException("Skipping test: Invalid execution environment - " + executionEnvironment);
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(ConfigFileReader.getHomePageURL());
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
