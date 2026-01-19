package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;

public class TC002_Login extends BaseTest {

    @Test
    public void testLoginWithValidCredentials() {

        try {
            // Navigate to Login page
            getLogger().info("Navigating to Login page");
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            getLogger().info("Clicked 'My Account' link");
            homePage.clickLogIn();
            getLogger().info("Clicked 'Login' link");

            // Test data
            getLogger().info("Retrieving test data from config file");
            String email = getProperties().getProperty("email");
            String password = getProperties().getProperty("password");

            // Login form
            LoginPage loginPage = new LoginPage(getDriver());
            loginPage.typeEmail(email);
            loginPage.typePassword(password);
            getLogger().info("Entered test data: Email = {}, Password = {}", email, password);
            loginPage.clickSubmitButton();
            getLogger().info("Clicked Submit button");

            // Verification
            getLogger().info("Verifying Login success");
            MyAccountPage myAccountPage = new MyAccountPage(getDriver());
            String expectedUrl = getProperties().getProperty("myAccountPageURL");
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getDriver().getCurrentUrl(), expectedUrl, "URL mismatch");
            softAssert.assertEquals(getDriver().getTitle(), "My Account", "Title mismatch");
            softAssert.assertTrue(myAccountPage.existsAccountHeader(), "My Account header not present");
            softAssert.assertAll();
            getLogger().info("Login test completed successfully");

        } catch (AssertionError assertionError) {
            getLogger().error("Test case failed: {}",  assertionError.getMessage());
            throw assertionError;
        } catch (Exception e) {
            getLogger().error("Test case failed due to unexpected error: {}", e.getMessage());
            Assert.fail();
        }
    }
}
