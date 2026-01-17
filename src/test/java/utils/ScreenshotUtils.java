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

    public static void captureFullViewPortScreenshot(WebDriver driver, String fileName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screenshotFile = ts.getScreenshotAs(OutputType.FILE);
        Path destination = Paths.get(fileName);
        Files.move(screenshotFile.toPath(), destination, REPLACE_EXISTING);
    }
}
