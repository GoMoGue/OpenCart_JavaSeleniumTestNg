package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.ConfigFileLoader;
import utils.ScreenshotUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;


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

    @BeforeClass
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

    @BeforeMethod
    public void logTestDetails(Method method) {
        logger.info(
                "Starting test: {} | Class: {} | Thread: {} | OS: {} | Browser: {}",
                method.getName(),                           // Test Method name
                method.getDeclaringClass().getSimpleName(), // Test Class name
                Thread.currentThread().getName(),           // Thread name
                os,
                browser
        );
    }

//    @AfterMethod
//    public void captureScreenshotOnFailure(ITestResult result) {
//        // Capture screenshot if driver not null and test case failed
//        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
//            logger.error("Test failed: {}", result.getName());
//            logger.info("Taking screenshot");
//            String screenshotName = "screenshots/" + result.getName() + "_failed_" + System.currentTimeMillis() + ".png";
//            try {
//                ScreenshotUtils.captureFullViewPortScreenshot(driver, screenshotName);
//            } catch (IOException e) {
//                logger.error("Failed to capture screenshot: {}", e.getMessage());
//            }
//            logger.info("Screenshot saved at: {}", screenshotName);
//        }
//    }

    @AfterClass
    public void tearDown() {
        // Quit driver if it exists
        if (driver != null) {
            driver.quit();
            logger.info("Webdriver quit");
        }
    }
}
