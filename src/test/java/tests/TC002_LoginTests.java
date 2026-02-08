package tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.MyAccountPage;
import utils.ConfigFileReader;
import utils.DataProviders;

/**
 * Test class for login functionality.
 * This class contains test methods to verify both successful and failed login scenarios.
 */
public class TC002_LoginTests extends BaseTest{

    private static final String LOGIN_PAGE_TITLE = "Account Login";
    private static final String MY_ACCOUNT_PAGE_TITLE = "My Account";

    private void navigateToLoginPage() {
        // Navigate to Login page
        getLogger().info("Navigating to Login page");
        HomePage homePage = new HomePage(getDriver());
        homePage.getNavbar().clickMyAccount();
        getLogger().info("Clicked 'My Account' link");
        homePage.getNavbar().clickLogIn();
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

    /**
     * Tests login with valid credentials.
     * Verifies that the user is successfully redirected to the "My Account" page.
     */
    @Test(
            priority = 1,
            groups = "smoke"
    )
    public void testLoginWithValidCredentials() {

        navigateToLoginPage();

        // Test data
        getLogger().info("Retrieving test data from config file");
        String email = ConfigFileReader.getEmail();
        String password = ConfigFileReader.getPassword();

        performLoginAction(email, password);

        // Verification
        getLogger().info("Verifying Login success");
        MyAccountPage myAccountPage = new MyAccountPage(getDriver());
        String expectedUrl = ConfigFileReader.getMyAccountPageURL();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(getDriver().getCurrentUrl(), expectedUrl, "URL mismatch");
        softAssert.assertEquals(getDriver().getTitle(), "My Account", "Title mismatch");
        softAssert.assertTrue(myAccountPage.existsAccountHeader(), "My Account header not present");
        softAssert.assertAll();
        getLogger().info("Login test with valid credentials completed successfully");
    }


    /**
     * Tests login with invalid credentials using data provided by a data provider.
     * Verifies that the user remains on the login page and sees the appropriate error message.
     *
     * @param email               The invalid email address to use for login.
     * @param password            The invalid password to use for login.
     * @param expectedErrorMessage The expected error message for invalid login.
     */
    @Test(
            priority = 2,
            dataProvider = "invalidLoginData",
            dataProviderClass = DataProviders.class,
            groups = "datadriven"
    )
    public void testLoginWithInvalidCredentials(String email, String password, String expectedErrorMessage) {

        navigateToLoginPage();

        // Test data
        String expectedURL = ConfigFileReader.getLoginPageURL();

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
        softAssert.assertEquals(getDriver().getTitle(), LOGIN_PAGE_TITLE, "Title mismatch");
        softAssert.assertTrue(loginPage.existsErrorMessage(), "Error message is not present");
        softAssert.assertEquals(loginPage.getErrorMessage(), expectedErrorMessage);
        softAssert.assertAll();
        getLogger().info("Login test with invalid credentials completed successfully");
    }
}
