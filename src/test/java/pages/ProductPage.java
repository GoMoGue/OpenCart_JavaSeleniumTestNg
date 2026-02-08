package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends BasePage {

    @FindBy(xpath = "//div[@id='content']//h1")
    WebElement headerProductName;
    @FindBy(id = "input-quantity")
    WebElement txtInputQuantity;
    @FindBy(id = "button-cart")
    WebElement buttonAddToCart;
    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement addToCartSuccessMessage;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeaderPresent() {
        return headerProductName.isDisplayed();
    }

    public String getProductNameHeaderText() {
        return headerProductName.getText();
    }

    public void typeQuantity(String input) {
        txtInputQuantity.clear();
        txtInputQuantity.sendKeys(input);
    }

    public void clickAddToCartButton() {
        buttonAddToCart.click();
    }

    public boolean isSuccessMessagePresent() {
        return addToCartSuccessMessage.isDisplayed();
    }

    public String getSuccessMessageText() {
        return addToCartSuccessMessage.getText();
    }
}
