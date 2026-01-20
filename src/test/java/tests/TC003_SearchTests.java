package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.MyAccountPage;
import pages.SearchResultsPage;

public class TC003_SearchTests extends BaseTest {

    @Test(priority = 1)
    public void testSearchExistingProduct() {

        try {
            String searchTerm = "macbooke";
            String expectedURL = "https://tutorialsninja.com/demo/index.php?route=product/search&search=" + searchTerm;
            String expectedTitle = "Search - " + searchTerm;

            getLogger().info("Entering search term: {}", searchTerm);
            HomePage homePage = new HomePage(getDriver());
            homePage.typeSearchInput(searchTerm);
            homePage.clickSearch();

            // Verification
            getLogger().info("Verifying search success");
            SearchResultsPage searchPage = new SearchResultsPage(getDriver());
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getDriver().getCurrentUrl(), expectedURL, "URL mismatch");
            softAssert.assertEquals(getDriver().getTitle(), expectedTitle, "Title mismatch");
            softAssert.assertEquals(searchPage.getResultsHeader(), expectedTitle, "Header mismatch");
            softAssert.assertTrue(searchPage.resultsCount() > 0, "Products count not greater than zero");
            softAssert.assertAll();
            getLogger().info("Valid Search test completed successfully");
        } catch (AssertionError assertionError) {
            getLogger().error("Test case failed: {}",  assertionError.getMessage());
            throw assertionError;
        } catch (Exception e) {
            getLogger().error("Test case failed due to unexpected error: {}", e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 2)
    public void testNoResultsSearch() {
        try {
            String searchTerm = "dafasdfasd";
            String expectedURL = "https://tutorialsninja.com/demo/index.php?route=product/search&search=" + searchTerm;
            String expectedTitle = "Search - " + searchTerm;

            getLogger().info("Entering search term: {}", searchTerm);
            HomePage homePage = new HomePage(getDriver());
            homePage.typeSearchInput(searchTerm);
            homePage.clickSearch();

            // Verification
            getLogger().info("Verifying search success");
            SearchResultsPage searchPage = new SearchResultsPage(getDriver());
            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(getDriver().getCurrentUrl(), expectedURL, "URL mismatch");
            softAssert.assertEquals(getDriver().getTitle(), expectedTitle, "Title mismatch");
            softAssert.assertEquals(searchPage.getResultsHeader(), expectedTitle, "Header mismatch");
            softAssert.assertTrue(searchPage.resultsCount() == 0, "Products count not zero");
            softAssert.assertTrue(searchPage.isNoResultsMessageDisplayed(), "No results found message not displayed");
            softAssert.assertAll();
            getLogger().info("Valid Search test completed successfully");
        } catch (AssertionError assertionError) {
            getLogger().error("Test case failed: {}",  assertionError.getMessage());
            throw assertionError;
        } catch (Exception e) {
            getLogger().error("Test case failed due to unexpected error: {}", e.getMessage());
            Assert.fail();
        }
    }
}
