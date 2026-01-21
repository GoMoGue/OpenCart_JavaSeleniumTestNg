import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.BaseTest;

import static java.sql.DriverManager.getDriver;

public class TestClass extends BaseTest {

    @Test
    public void testMixedExceptions() {
        // This will throw AssertionError if the assertion fails
        //Assert.assertEquals("actual", "expected", "Values do not match");

        // This will throw a regular exception (e.g., NoSuchElementException)
        getDriver().findElement(By.id("nonexistent"));
    }
}
