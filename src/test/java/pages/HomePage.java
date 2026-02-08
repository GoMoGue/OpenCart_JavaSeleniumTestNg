package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement txtInputSearch;
    @FindBy(xpath = "//i[@class='fa fa-search']")
    private WebElement buttonSearch;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void typeSearchInput(String searchInput) {
        txtInputSearch.clear();
        txtInputSearch.sendKeys(searchInput);
    }

    public void clickSearch() {
        buttonSearch.click();
    }
}
