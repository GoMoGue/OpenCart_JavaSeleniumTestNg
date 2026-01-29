package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath="//span[normalize-space()='My Account']")
    private WebElement myAccountDropDown;
    @FindBy(xpath="//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Register']")
    private WebElement registerLink;
    @FindBy(xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
    private WebElement loginLink;
    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement txtInputSearch;
    @FindBy(xpath = "//i[@class='fa fa-search']")
    private WebElement buttonSearch;

    public HomePage(WebDriver driver) {
        super(driver);
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

    public void typeSearchInput(String searchInput) {
        txtInputSearch.clear();
        txtInputSearch.sendKeys(searchInput);
    }

    public void clickSearch() {
        buttonSearch.click();
    }
}
