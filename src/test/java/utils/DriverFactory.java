package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Factory class for creating WebDriver instances.
 * Supports both local and remote (Selenium Grid) WebDriver initialization.
 * Automatically configures WebDriver based on the execution environment and browser type.
 */
public class DriverFactory {

    // Thread local variable to store. Each threat that accesses it (via its get or set method) has its own,
    // independently initialized copy of the variable.
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String braveBrowserLocation = "/var/lib/flatpak/exports/bin/com.brave.Browser";
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    /**
     * Returns the WebDriver instance stored in ThreadLocal.
     * When a thread calls DriverFactory.getDriver(), it retrieves only its own
     * WebDriver instance from the ThreadLocal storage.
     *
     * @return The WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Initializes and stores a WebDriver instance for the current thread based on the specified browser,
     * OS, and execution environment.
     * The WebDriver instance is stored in a ThreadLocal variable,
     * ensuring thread safety for parallel test execution.
     *
     * @param browser The browser name (e.g., "chrome", "firefox", "brave").
     * @param os      The operating system (e.g., "linux", "windows").
     * @throws RuntimeException If the execution environment is invalid.
     */
    public static void initializeDriver(String browser, String os) {

        try {

            WebDriver webDriver;
            String executionEnvironment = ConfigFileReader.getExecutionEnvironment();

            if (executionEnvironment.equalsIgnoreCase("local")) {
                webDriver = createLocalDriver(browser);
            } else if (executionEnvironment.equalsIgnoreCase("remote")) {
                webDriver = createRemoteDriver(browser, os, ConfigFileReader.getGridHubUrl());
            } else {
                throw new RuntimeException("Invalid execution environment: " + executionEnvironment);
            }

            // Store Webdriver instance in Local Thread variable
            driver.set(webDriver);
            logger.info("WebDriver initialized for browser: {}, OS: {}", browser, os);

        } catch (Exception e) {
            logger.error("Failed to initialize driver: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }

    /**
     * Creates and returns a WebDriver instance for local execution.
     *
     * @param browser The browser name (e.g., "chrome", "firefox", "brave").
     * @return A configured WebDriver instance.
     * @throws RuntimeException If the browser is not supported.
     */
    private static WebDriver createLocalDriver(String browser) {
        Objects.requireNonNull(browser, "Browser name cannot be null");
        logger.info("Creating WebDriver for browser: {}", browser);
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "brave":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setBinary(braveBrowserLocation);
                driver = new ChromeDriver(chromeOptions);
                break;
            default:
                logger.error("Invalid browser name: {}", browser);
                throw new RuntimeException("Invalid browser name:" + browser);
        }

        return driver;
    }

    /**
     * Creates and returns a RemoteWebDriver instance for Selenium Grid.
     *
     * @param browser The browser name (e.g., "chrome", "firefox").
     * @param os The operating system (e.g., "linux", "windows").
     * @param gridHubUrl The URL of the Selenium Grid hub.
     * @return A configured RemoteWebDriver instance.
     * @throws RuntimeException If the browser is not supported or the Grid URL is invalid.
     */
    private static WebDriver createRemoteDriver(String browser, String os, String gridHubUrl) {
        Objects.requireNonNull(browser, "Browser name cannot be null");
        Objects.requireNonNull(gridHubUrl, "GridHub URL cannot be null");
        logger.info("Creating RemoteWebDriver for browser: {}, OS: {}, Grid URL: {}", browser, os, gridHubUrl);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.toLowerCase());
        capabilities.setPlatform(Platform.fromString(os.toUpperCase()));

        try {
            return new RemoteWebDriver(new URL(gridHubUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium GridHub URL:" + gridHubUrl, e);
        }
    }

    /**
     * Quits the WebDriver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            // End webdriver session and close all browser windows
            driver.get().quit();
            // Remove the WebDriver instance from the ThreadLocal container
            driver.remove();
            logger.info("WebDriver quit and removed from ThreadLocal");
        }
    }
}
