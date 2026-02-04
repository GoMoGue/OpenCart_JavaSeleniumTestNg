package utils;

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

public class DriverFactory {

    private static String braveBrowserLocation = "/var/lib/flatpak/exports/bin/com.brave.Browser";

    /**
     * Creates and returns a WebDriver instance based on the specified browser.
     *
     * @param browser The browser name (e.g., "chrome", "firefox", "brave").
     * @return A configured WebDriver instance.
     * @throws RuntimeException If the browser is not supported.
     */
    public static WebDriver createDriver(String browser) {
        Objects.requireNonNull(browser, "Browser name cannot be null");

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
    public static WebDriver createRemoteDriver(String browser, String os, String gridHubUrl) {
        Objects.requireNonNull(browser, "Browser name cannot be null");
        Objects.requireNonNull(gridHubUrl, "GridHub URL cannot be null");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.toLowerCase());
        capabilities.setPlatform(Platform.fromString(os.toUpperCase()));

        try {
            return new RemoteWebDriver(new URL(gridHubUrl), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Selenium GridHub URL:" + gridHubUrl, e);
        }
    }
}
