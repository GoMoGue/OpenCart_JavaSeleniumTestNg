package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MyAccountPage extends BasePage {

    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement headerAccountSection;

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean existsAccountHeader() {
        List<WebElement> accountHeader =  getDriver().findElements(By.xpath("//h2[normalize-space()='My Account']"));
        return !accountHeader.isEmpty();
    }

    public boolean isDisplayedAccountHeader() {
        try {
            return headerAccountSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
