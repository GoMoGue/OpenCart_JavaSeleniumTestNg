package listeners;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ScreenshotUtils;

import java.io.IOException;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        // Get the WebDriver instance from the test class
        // Every Test class inherits from BaseTest class
        BaseTest currentInstance= (BaseTest) result.getInstance();
        if (currentInstance == null) {
            System.err.println("Test instance is null. Cannot capture screenshot.");
            return;
        }

        Logger logger = currentInstance.getLogger();
        WebDriver driver = currentInstance.getDriver();

        String methodName = result.getName();
        String exceptionMessage = result.getThrowable().getMessage();
        logger.info("{} test method failed. Exception message: {}", methodName, exceptionMessage);
        logger.info("Taking screenshot");
        String screenshotName = "screenshots/" + methodName + "_failed_" + System.currentTimeMillis() + ".png";
        try {
            ScreenshotUtils.captureFullViewPortScreenshot(driver, screenshotName);
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
        logger.info("Screenshot saved at: {}", screenshotName);
    }

}
