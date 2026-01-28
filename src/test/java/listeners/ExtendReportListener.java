package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;
import utils.ScreenshotUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * A custom TestNG listener that integrates with ExtentReports to generate detailed HTML test reports.
 * This listener logs test execution details, captures screenshots on failure, and provides rich reporting
 * for automation test suites.
 */
public class ExtendReportListener implements ITestListener {

    private ExtentReports extentReports;
    private ExtentTest extentTest;

    /**
     * Initializes the ExtentReports instance and configures the report file.
     * This method is called before any test in the suite starts.
     *
     * @param testContext The TestNG ITestContext instance containing information about the test suite.
     */
    @Override
    public void onStart(ITestContext testContext) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd:HHmmss");
        String timestamp = LocalDateTime.now().format(dateFormatter);
        String reportFilename = timestamp + "_ExtentReport.html";
        String reportFileAbsolutePath = String.format("%s/reports/%s",
                System.getProperty("user.dir"),
                reportFilename
        );

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFileAbsolutePath);
        sparkReporter.config().setDocumentTitle("OpenCart Automation Report");
        sparkReporter.config().setReportName("Open Cart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Application", "OpenCart");
        extentReports.setSystemInfo("Username", System.getProperty("user.name"));

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (includedGroups != null && !includedGroups.isEmpty()) {
            extentReports.setSystemInfo("Included Groups", includedGroups.toString());
        }
        List<String> excludedGroups = testContext.getCurrentXmlTest().getExcludedGroups();
        if (excludedGroups != null && !excludedGroups.isEmpty()) {
            extentReports.setSystemInfo("Excluded Groups", excludedGroups.toString());
        }
    }

    /**
     * Called before each test method starts.
     * Creates a new test entry in the ExtentReport and logs test start information,
     * including test name, class, parameters, browser, and environment details.
     *
     * @param testResult The TestNG ITestResult instance containing information about the test method.
     */
    @Override
    public void onTestStart(ITestResult testResult) {
        String testName = String.format("%s.%s",
                testResult.getTestClass().getName(),
                testResult.getMethod().getMethodName()
                );
        extentTest = extentReports.createTest(testName);
        Logger logger = getLogger(testResult);

        // Log test start information
        String methodName = testResult.getMethod().getMethodName();
        String className = testResult.getTestClass().getName();
        // Log to log4j2
        logger.info("TestMethod: '{}'; TestClass: '{}'; StartTime: {}", methodName, className, LocalDateTime.now());
        // Log to ExtentReport
        extentTest.info(String.format("TestMethod: %s; TestClass: %s; StartTime: %s",
                methodName,
                className,
                LocalDateTime.now()));

        // Log parameters if any
        Object[] parameters = testResult.getParameters();
        if (parameters != null && parameters.length > 0) {
            String parametersStr = Arrays.toString(parameters);
            logger.info("Test parameters: {}", parametersStr);
            extentTest.info("Test parameters: " + parametersStr);
        }

        // Log browser/environment info if available
        if (testResult.getInstance() instanceof BaseTest currentInstance) {
            logger.info("Browser: {}; OS: {}", currentInstance.getBrowser(), currentInstance.getOs());
            extentTest.info(String.format("Browser: %s; OS: %s", currentInstance.getBrowser(), currentInstance.getOs()));
        } else {
            logger.warn("Test class is not an instance of BaseTest. Cannot log browser/environment info.");
            extentTest.info("Test class is not an instance of BaseTest. Cannot log browser/environment info.");
        }
    }

    /**
     * Called when a test method is skipped.
     * Logs the skipped status to the ExtentReport.
     *
     * @param testResult The TestNG ITestResult instance containing information about the test method.
     */
    @Override
    public void onTestSkipped(ITestResult testResult) {
        extentTest.log(Status.SKIP, "Test skipped");
        extentTest.log(Status.INFO, testResult.getThrowable());
    }

    /**
     * Called when a test method passes.
     * Logs the passed status to the ExtentReport.
     *
     * @param testResult The TestNG ITestResult instance containing information about the test method.
     */
    @Override
    public void onTestSuccess(ITestResult testResult) {
        extentTest.log(Status.PASS, "Test passed");
    }

    /**
     * Called when a test method fails.
     * Logs the failure status, exception details, and captures a screenshot of the failure.
     * The screenshot is saved to the file system and attached to the ExtentReport.
     *
     * @param testResult The TestNG ITestResult instance containing information about the test method.
     */
    @Override
    public void onTestFailure(ITestResult testResult) {
        extentTest.log(Status.FAIL, "Test failed");
        extentTest.fail(testResult.getThrowable());

        // Take screenShot
        Logger logger = getLogger(testResult);
        WebDriver driver = getWebDriver(testResult);

        String methodName = testResult.getName();
        String exceptionMessage = testResult.getThrowable().getMessage();
        logger.info("{} test method failed. Exception message: {}", methodName, exceptionMessage);
        logger.info("Taking screenshot");
        String screenshotName = String.format("%s/screenshots/%s_failed_%s.png",
                System.getProperty("user.dir"),
                methodName,
                System.currentTimeMillis()
        );

        try {
            ScreenshotUtils.captureFullViewPortScreenshot(driver, screenshotName);
            extentTest.addScreenCaptureFromPath(screenshotName);
            logger.info("Screenshot saved at: {}", screenshotName);
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }

    /**
     * Called after all tests in the suite have finished.
     * Flushes the ExtentReports to ensure all test information is written to the report file.
     *
     * @param testContext The TestNG ITestContext instance containing information about the test suite.
     */
    @Override
    public void onFinish(ITestContext testContext) {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    /**
     * Retrieves the logger instance from the test class.
     * Assumes the test class extends BaseTest.
     *
     * @param testResult The TestNG result object containing the test instance.
     * @return The logger instance from the test class.
     * @throws ClassCastException If the test instance is not a subclass of BaseTest.
     */
    private Logger getLogger(ITestResult testResult) {
        BaseTest currentInstance = (BaseTest) testResult.getInstance();
        Logger logger = currentInstance.getLogger();
        return logger;
    }

    /**
     * Retrieves the WebDriver instance from the test class.
     * Assumes the test class extends BaseTest.
     *
     * @param testResult The TestNG result object containing the test instance.
     * @return The WebDriver instance from the test class.
     * @throws ClassCastException If the test instance is not a subclass of BaseTest.
     */
    private WebDriver getWebDriver(ITestResult testResult) {
        BaseTest currentInstance = (BaseTest) testResult.getInstance();
        return currentInstance.getDriver();
    }
}
