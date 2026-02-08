package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.DataProviders;

/**
 * Test class for the search functionality
 * This class tests the search feature of the application using a data provider to supply test data.
 * It verifies the search results page header, URL, title, and the presence or absence of results.
 */
public class TC003_SearchTestsDDT extends BaseTest {

    /**
     * Tests the search functionality using data provided by the data provider.
     *
     * @param searchTerm      The term to search for.
     * @param resultsPresent  Indicates whether results are expected ("yes" or "no").
     * @param expectedTitle   The expected title of the search results page.
     * @param expectedURL     The expected URL of the search results page.
     * @param expectedMessage The expected message when no results are found (or "N/A" if not applicable).
     */
    @Test(
            priority = 1,
            dataProvider = "searchData",
            dataProviderClass = DataProviders.class,
            groups = "datadriven"
    )
    public void testSearchWithDataProvider(String searchTerm, String resultsPresent, String expectedTitle, String expectedURL, String expectedMessage) {

        getLogger().info("Entering search term: {}", searchTerm);
        HomePage homePage = new HomePage(getDriver());
        homePage.typeSearchInput(searchTerm);
        homePage.clickSearch();

        SearchResultsPage resultsPage = new SearchResultsPage(getDriver());
        getLogger().info("Verifying results page header");
        String resultsHeader = resultsPage.getResultsHeader();
        Assert.assertEquals(expectedTitle, resultsHeader, "Test failed: page header does not match expected");
        getLogger().info("Verifying results page URL");
        Assert.assertEquals(expectedURL, getDriver().getCurrentUrl(), "Test failed: page URL does not match expected");
        getLogger().info("Verifying results page Title");
        Assert.assertEquals(expectedTitle, getDriver().getTitle(), "Test failed: page title does not match expected");
        getLogger().info("Verifying results are present");
        int resultsCount = resultsPage.resultsCount();

        if (resultsPresent.equalsIgnoreCase("yes")) {
            Assert.assertTrue(resultsCount > 0, "Test failed: results count not greater than zero");
        } else {
            Assert.assertEquals(resultsCount, 0, "Test failed: results count not equal to zero");
            if (!expectedMessage.equals("N/A")) {
                getLogger().info("Verifying no results found message");
                Assert.assertTrue(resultsPage.getNoResultMessage().contains(expectedMessage), "Test failed: no result message does not match expected message");
            }
        }
        getLogger().info("Search test completed successfully");
    }
}
