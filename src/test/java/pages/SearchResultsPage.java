package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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
    @FindBy(id = "cart-total")
    WebElement cartButton;
    @FindBy(xpath = "//div[@class='alert alert-success alert-dismissible']")
    WebElement addToCartSuccessMessage;


    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Gets the text of the search results page header.
     * @return The text of the search results page header.
     */
    public String getResultsHeader() {
        return searchTitleHeader.getText();
    }

    /**
     * Gets the displayed text of all product links on the page.
     * @return A list of strings representing the displayed text of each product link.
     */
    public List<String> getProductNames() {
        List<String> productNames = new ArrayList<>();
        for (WebElement link : productLinks) {
            String linkText = link.getText().trim();
            productNames.add(linkText);
        }
        return productNames;
    }

    /**
     * Gets the count of products displayed in the search results.
     * @return The number of products in the search results.
     */
    public int resultsCount() {
        return productThumbs.size();
    }

    /**
     * Checks if the "No results" p element is displayed.
     * @return true if the "No results" message is displayed, false otherwise.
     */
    public boolean isNoResultsMessageDisplayed() {
        return noResultsMessage.size() > 0;
    }

    /**
     * Gets the text of the "No results found" message.
     * @return The text of the "No results found" message.
     */
    public String getNoResultMessage() {
        return noResultsMessage.get(0).getText();
    }

    /**
     * Clicks the product link associated with the specified product name in the search results.
     * This action navigates the user to the product details page for the selected product.
     * @param productName The name of the product whose link should be clicked.
     * @throws RuntimeException If the product is not found in the search results.
     */
    public void clickProductLink(String productName) {
        for (WebElement element : productLinks) {
            if (element.getText().equalsIgnoreCase(productName)) {
                element.click();
                return;
            }
        }

        throw new RuntimeException(String.format("Product name: %s not found in search results", productName));
    }

    /**
     * Clicks the "Add to Cart" button for a specified product in the search results.
     * @param productName The name of the product for which to click "Add to Cart".
     * @throws RuntimeException If the product is not found in the search results.
     */
    public void clickAddToCart(String productName) {
        // Iterate through all product containers
        for (WebElement productThumb : productThumbs) {
            // Find the product link within the current product container
            WebElement productLink = productThumb.findElement(By.xpath(".//div[@class='caption']//h4//a"));

            if (productLink.getText().equalsIgnoreCase(productName)) {
                String cartButtonText = getCartButtonText();

                // Find and click the "Add to Cart" button within the same product container
                WebElement addToCartButton = productThumb.findElement(By.xpath(".//button[contains(@onclick, 'cart.add')]"));
                addToCartButton.click();

                // Wait for the cart button text to update
                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
                wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(cartButton, cartButtonText)));
                return;
            }
        }

        throw new RuntimeException(String.format("Product name: %s not found in search results", productName));
    }

    public void clickCartButton() {
        cartButton.click();
    }

    public String getCartButtonText() {
        return cartButton.getText();
    }

    public boolean isSuccessMessagePresent() {
        return addToCartSuccessMessage.isDisplayed();
    }

    public String getSuccessMessageText() {
        return addToCartSuccessMessage.getText();
    }
}
