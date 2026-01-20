package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class LoginPage extends BasePage{


    @FindBy(xpath = "//input[@id='input-email']")
    private WebElement txtInputEmail;
    @FindBy(xpath = "//input[@id='input-password']")
    private WebElement txtInputPassword;
    @FindBy(xpath = "//div[@class='form-group']//a[normalize-space()='Forgotten Password']")
    private WebElement linkForgotPassword;
    @FindBy(xpath = "//input[@value='Login']")
    private WebElement buttonSubmit;
    @FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void typeEmail(String email) {
        txtInputEmail.clear();
        txtInputEmail.sendKeys(email);
    }

    public void typePassword(String password) {
        txtInputPassword.clear();
        txtInputPassword.sendKeys(password);
    }

    public void clickForgotPasswordLink() {
        linkForgotPassword.click();
    }

    public void clickSubmitButton() {
        buttonSubmit.click();
    }

    public boolean existsErrorMessage() {
        List<WebElement> accountHeader =  getDriver().findElements(By.xpath("//div[@class='alert alert-danger alert-dismissible']"));
        return !accountHeader.isEmpty();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
