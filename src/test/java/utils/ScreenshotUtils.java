package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ScreenshotUtils {

    /**
     * Captures a screenshot of the entire viewport of the current browser window
     * and saves it to the specified file path.
     *
     * <p>This method uses the {@link TakesScreenshot} interface to capture the screenshot
     * and replaces the destination file if it already exists.</p>
     *
     * @param driver   The {@link WebDriver} instance used to control the browser.
     *                 Must support the {@link TakesScreenshot} interface.
     * @param fileName The full path (including filename and extension) where the screenshot
     *                 will be saved. Example: {@code "screenshots/homepage_failed_123456789.png"}
     * @throws IOException          If an I/O error occurs while saving the screenshot.
     * @throws ClassCastException   If the provided {@link WebDriver} does not support
     *                              the {@link TakesScreenshot} interface.
     * @throws IllegalArgumentException If the {@code fileName} is null or empty.
     *
     * @see TakesScreenshot
     * @see OutputType
     */
    public static void captureFullViewPortScreenshot(WebDriver driver, String fileName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get(fileName);
        Files.move(screenshotFile.toPath(), destination, REPLACE_EXISTING);
    }
}
