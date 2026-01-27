package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import utils.DataProviders;

public class TC002_LoginTests extends BaseTest{

    private void navigateToLoginPage() {
        // Navigate to Login page
        getLogger().info("Navigating to Login page");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickMyAccount();
        getLogger().info("Clicked 'My Account' link");
        homePage.clickLogIn();
        getLogger().info("Clicked 'Login' link");
    }

    private void performLoginAction(String email, String password) {
        // Login form
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeEmail(email);
        loginPage.typePassword(password);
        getLogger().info("Entered test data: Email = {}, Password = {}", email, password);
        loginPage.clickSubmitButton();
        getLogger().info("Clicked Submit button");
    }


    @Test(
            priority = 1,
            groups = "smoke"
    )
    public void testLoginWithValidCredentials() {

        navigateToLoginPage();

        // Test data
        getLogger().info("Retrieving test data from config file");
        String email = getProperties().getProperty("email");
        String password = getProperties().getProperty("password");

        performLoginAction(email, password);

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
    }


    @Test(
            priority = 2,
            dataProvider = "invalidLoginData",
            dataProviderClass = DataProviders.class,
            groups = "datadriven"
    )
    public void testLoginWithInvalidCredentials(String email, String password, String expectedErrorMessage) {

        navigateToLoginPage();

        // Test data
        String expectedURL = "https://tutorialsninja.com/demo/index.php?route=account/login";
        String expectedTitle = "Account Login";

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeEmail(email);
        loginPage.typePassword(password);
        getLogger().info("Entered test data: Email = {}, Password = {}", email, password);
        loginPage.clickSubmitButton();
        getLogger().info("Clicked Submit button");

        // Verification
        getLogger().info("Verifying Login error message");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getDriver().getCurrentUrl(), expectedURL, "URL mismatch");
        softAssert.assertEquals(getDriver().getTitle(), expectedTitle, "Title mismatch");
        softAssert.assertTrue(loginPage.existsErrorMessage(), "Error message is not present");
        softAssert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
        softAssert.assertAll();
        getLogger().info("Login test completed successfully");
    }
}
