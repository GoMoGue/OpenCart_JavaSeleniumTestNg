package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ScreenshotUtils;

import java.io.IOException;
import java.time.Duration;


public class BaseTest {

    private WebDriver driver;
    private Logger logger;

    public WebDriver getDriver() {
        return driver;
    }

    public Logger getLogger() {
        return logger;
    }

    @BeforeMethod
    public void setUp() {
        logger = LogManager.getLogger(getClass());
        logger.info("Setting up WebDriver...");
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
        logger.info("WebDriver initialized");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        // Capture screenshot if driver not null and test case failed
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            logger.info("Taking screenshot");
            String screenshotName = "screenshots/" + result.getName() + "_failed_" + System.currentTimeMillis() + ".png";
            try {
                ScreenshotUtils.captureFullViewPortScreenshot(driver, screenshotName);
            } catch (IOException e) {
                logger.error("Failed to capture screenshot: {}", e.getMessage());
            }
            logger.info("Screenshot saved at: {}", screenshotName);
        }

        // Quit driver if it exists
        if (driver != null) {
            driver.quit();
            logger.info("Webdriver quit");
        }
    }
}
