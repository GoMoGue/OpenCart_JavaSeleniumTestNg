package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//div[@id='content']//h1")
    WebElement searchTitleHeader;
    @FindBy(xpath = "//div[@class='product-thumb']")
    List<WebElement> productThumbs;
    @FindBy(xpath = "//div[@class='product-thumb']//div[@class='caption']//h4//a")
    List<WebElement> productLinks;
    @FindBy(xpath = "//div[@id='content']//p[2]")
    List<WebElement> noResultsMessage;


    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public String getResultsHeader() {
        return searchTitleHeader.getText();
    }

    public List<WebElement> getProductLinks() {
        return productLinks;
    }

    public int resultsCount() {
        return productLinks.size();
    }

    public boolean isNoResultsMessageDisplayed() {
        return noResultsMessage.size() > 0;
    }

    public String getNoResultMessage() {
        return noResultsMessage.get(0).getText();
    }
}
