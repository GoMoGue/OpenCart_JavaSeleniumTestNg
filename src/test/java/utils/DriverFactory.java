package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.SkipException;

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
                throw new SkipException("Skipping test: Invalid browser name - " + browser);
        }

        return driver;
    }
}
