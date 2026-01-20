package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.DataProviders;

public class TC003_SearchTestsDDT extends BaseTest {

    @Test(
            priority = 1,
            dataProvider = "searchData",
            dataProviderClass = DataProviders.class
    )
    public void testSearchWithDataProvider(String searchTerm, String resultsPresent, String expectedTitle, String expectedURL, String expectedMessage) {

        getLogger().info("Entering search term: {}", searchTerm);
        HomePage homePage = new HomePage(getDriver());
        homePage.typeSearchInput(searchTerm);
        homePage.clickSearch();

        SearchResultsPage resultsPage = new SearchResultsPage(getDriver());
        getLogger().info("Verifying results page header");
        String resultsHeader = resultsPage.getResultsHeader();
        Assert.assertEquals(expectedTitle, resultsHeader);
        getLogger().info("Verifying results page URL");
        Assert.assertEquals(expectedURL, getDriver().getCurrentUrl());
        getLogger().info("Verifying results page Title");
        Assert.assertEquals(expectedTitle, getDriver().getTitle());
        getLogger().info("Verifying results are present");
        int resultsCount = resultsPage.resultsCount();
        if (resultsPresent.equalsIgnoreCase("yes")) {
            Assert.assertTrue(resultsCount > 0);
        } else {
            Assert.assertEquals(resultsCount, 0);
            if (!expectedMessage.equals("N/A")) {
                getLogger().info("Verifying no results found message");
                Assert.assertTrue(resultsPage.getNoResultMessage().contains(expectedMessage));
            }
        }
        getLogger().info("Search test completed successfully");
    }
}
