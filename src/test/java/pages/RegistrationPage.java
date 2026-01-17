package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// Logging: Add logging (e.g., log.info("Setting first name: " + firstName)) for debugging.
// Fluent Design: Consider returning this from methods to enable method chaining
// Centralizing waits in BasePage class for reusability
// Add null checks for WebElements before interacting with them
// Always Clear fields before sending keys to avoid appending text

public class RegistrationPage extends BasePage {

    @FindBy(xpath = "//input[@id='input-firstname']")
    private WebElement txtFirstName;
    @FindBy(xpath = "//input[@id='input-lastname']")
    private WebElement txtLastName;
    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtEmail;
    @FindBy(xpath = "//input[@id='input-telephone']")
    private WebElement txtTelephone;
    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtPassword;
    @FindBy(xpath = "//input[@id='input-confirm']")
    private WebElement txtConfirmPassword;
    @FindBy(xpath = "//input[@name='agree']")
    private WebElement checkboxAgreePrivacyPolicy;
    @FindBy(xpath = "//input[@value='Continue']")
    private WebElement buttonContinue;
    @FindBy(xpath = "//input[@type='radio'][@name='newsletter'][@value='1']")
    private WebElement radioNewsLetterYes;
    @FindBy(xpath = "//input[@type='radio'][@name='newsletter'][@value='0']")
    private WebElement radioNewsLetterNo;
    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    private WebElement successMessage;

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    public void setFirstName(String firstName) {
        txtFirstName.clear();
        txtFirstName.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        txtLastName.clear();
        txtLastName.sendKeys(lastName);
    }

    public void setTelephone(String telephone) {
        txtTelephone.clear();
        txtTelephone.sendKeys(telephone);
    }

    public void setEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void setPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    public void setConfirmPassword(String password) {
        txtConfirmPassword.clear();
        txtConfirmPassword.sendKeys(password);
    }

    public void agreePrivacyPolicy() {
        checkboxAgreePrivacyPolicy.click();
    }

    public void agreeNewsletter() {
        radioNewsLetterYes.click();
    }

    public void notAgreeNewsletter() {
        radioNewsLetterNo.click();
    }

    public void clickContinue() {
        //buttonContinue.sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(buttonContinue)).click();
    }

    public boolean isSuccessMessageDisplayed() {
        return successMessage.isDisplayed();
    }

    public boolean existsSuccessMessage() {
        List<WebElement> successMessage =  driver.findElements(By.xpath("//h1[normalize-space()='Your Account Has Been Created!']"));
        return !successMessage.isEmpty();
    }

    public String getConfirmationMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
        } catch (TimeoutException e) {
            throw new RuntimeException("Confirmation message not displayed within the expected time: " + e.getMessage());
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Confirmation message element not found: " + e.getMessage());
        }
    }
}