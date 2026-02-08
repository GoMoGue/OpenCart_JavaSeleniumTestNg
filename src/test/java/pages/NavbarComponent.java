package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Represents the navbar component that appears across multiple pages.
 */
public class NavbarComponent {

    private WebDriver driver;

    @FindBy(xpath="//span[normalize-space()='My Account']")
    private WebElement myAccountDropDown;
    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
    private WebElement registerLink;
    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
    private WebElement loginLink;

    public NavbarComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickMyAccount() {
        myAccountDropDown.click();
    }

    public void clickRegister() {
        registerLink.click();
    }

    public void clickLogIn() {
        loginLink.click();
    }
}
