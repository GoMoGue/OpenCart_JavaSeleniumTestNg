package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegistrationPage;

public class TC001_AccountRegistration extends BaseTest {

    private static final String EXPECTED_CONFIRMATION_MESSAGE = "Your Account Has Been Created!";

    @Test
    public void verifyAccountRegistration() {
        getLogger().info("Starting account registration test");

        try {
            // Navigation
            getLogger().info("Navigating to Registration page");
            HomePage homePage = new HomePage(getDriver());
            homePage.clickMyAccount();
            getLogger().info("Clicked 'My Account' link");
            homePage.clickRegister();
            getLogger().info("Clicked 'Register' link");

            // Test data
            String firstName = "El";
            String lastName = "John";
            String email = "emailo@example.com";
            String telephone = "1234567890";
            String password = "securePassword123";
            getLogger().info("Entering test data: First Name = {}, Last Name = {}, Email = {}", firstName, lastName, email);

            // Registration form
            RegistrationPage registrationPage = new RegistrationPage(getDriver());
            registrationPage.typeFirstName(firstName);
            registrationPage.typeLastName(lastName);
            registrationPage.typeEmail(email);
            registrationPage.typeTelephone(telephone);
            registrationPage.typePassword(password);
            registrationPage.typeConfirmPassword(password);
            registrationPage.agreePrivacyPolicy();
            registrationPage.agreeNewsletter();
            getLogger().info("Clicking continue button.");
            registrationPage.clickContinue();

            // Verification
            getLogger().info("Verifying account registration success");

            getLogger().info("Checking if success message is displayed");
            Assert.assertTrue(registrationPage.existsSuccessMessage(),
                    "Confirmation message not present.");
            getLogger().info("Success message is displayed");

            getLogger().info("Verifying confirmation message text");
            Assert.assertEquals(registrationPage.getConfirmationMessage(),
                    EXPECTED_CONFIRMATION_MESSAGE,
                    "Confirmation message text does not match.");
            getLogger().info("Confirmation message matches expected text");

            getLogger().info("Account registration test completed successfully");
        } catch (AssertionError assertionError) {
            getLogger().error("Test case failed: {}",  assertionError.getMessage());
            throw assertionError;
        } catch (Exception e) {
            getLogger().error("Test case failed due to unexpected error: {}", e.getMessage());
            Assert.fail();
        }
    }
}
