package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductPage;
import pages.SearchResultsPage;

/**
 * Test class for "Add to Cart" functionality.
 * This class contains test methods to verify that products can be added to the cart
 * from both the product details page and the search results page.
 */
public class TC004_AddToCartTests extends BaseTest {

    /**
     * Tests adding a product to the cart from the product details page.
     * Steps:
     * 1. Search for a product.
     * 2. Navigate to the product details page.
     * 3. Add the product to the cart.
     * 4. Verify the success message presence and text.
     */
    @Test(
            priority = 1,
            groups = "smoke"
    )
    public void testAddToCartFromProductPage() {

        String productName = "imac";
        getLogger().info("Entering search term: {}", productName);
        HomePage homePage = new HomePage(getDriver());
        homePage.typeSearchInput(productName);
        homePage.clickSearch();

        getLogger().info("Navigating to product page");
        SearchResultsPage resultsPage = new SearchResultsPage(getDriver());
        getLogger().info("Clicking product link");
        resultsPage.clickProductLink(productName);

        getLogger().info("Clicking Add To Cart button");
        ProductPage productPage = new ProductPage(getDriver());
        Assert.assertTrue(productPage.getProductNameHeaderText().equalsIgnoreCase(productName));
        productPage.clickAddToCartButton();

        Assert.assertTrue(productPage.isSuccessMessagePresent(), "Add to cart success message not present");
        String expectedSuccessText = String.format("Success: You have added %s to your shopping cart!", productName).toLowerCase();
        String actualSuccessText = productPage.getSuccessMessageText().toLowerCase().trim();
        Assert.assertTrue(actualSuccessText.contains(expectedSuccessText), "Success message not match");

        getLogger().info("Add to cart from product page completed successfully");
    }

    /**
     * Tests adding a product to the cart directly from the search results page.
     * Steps:
     * 1. Search for a product.
     * 2. Add the product to the cart from the search results.
     * 3. Verify the success message presence and text.
     */
    @Test(
            priority = 2
    )
    public void testAddToCartFromSearchResultsPage() {

        String productName = "imac";
        getLogger().info("Entering search term: {}", productName);
        HomePage homePage = new HomePage(getDriver());
        homePage.typeSearchInput(productName);
        homePage.clickSearch();

        SearchResultsPage resultsPage = new SearchResultsPage(getDriver());
        getLogger().info("Clicking add to cart");
        resultsPage.clickAddToCart(productName);

        getLogger().info("Verifying Success message");
        Assert.assertTrue(resultsPage.isSuccessMessagePresent(), "Add to cart success message not present");
        String expectedSuccessText = String.format("Success: You have added %s to your shopping cart!", productName).toLowerCase();
        String actualSuccessText = resultsPage.getSuccessMessageText().toLowerCase().trim();
        System.out.println(actualSuccessText);
        Assert.assertTrue(actualSuccessText.contains(expectedSuccessText), "Success message not match");

        getLogger().info("Add to cart from search results page completed successfully");
    }
}
