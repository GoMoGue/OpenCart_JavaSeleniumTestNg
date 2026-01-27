package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * A base class for all page objects in the UI automation framework.
 * <p>This class provides a foundation for implementing the Page Object Model (POM) pattern.
 * It initializes web elements using {@link PageFactory} and provides access to the
 * {@link WebDriver} instance for all child page classes.</p>
 * <p>All page classes in the framework should extend this class to ensure consistent
 * initialization and access to the WebDriver instance.</p>
 */
public class BasePage {

    /** The WebDriver instance used to interact with the browser. */
    private WebDriver driver;

    /**
     * Constructs a new BasePage and initializes its web elements.
     * <p>This constructor uses {@link PageFactory#initElements(WebDriver, Object)}
     * to initialize all web elements annotated with {@link FindBy} or {@link FindBys}
     * in child classes.</p>
     * @param driver The {@link WebDriver} instance used to control the browser.
     * @throws IllegalArgumentException If the provided {@code driver} is null.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns the WebDriver instance associated with this page.
     * @return The {@link WebDriver} instance.
     */
    public WebDriver getDriver() {
        return driver;
    }
}
