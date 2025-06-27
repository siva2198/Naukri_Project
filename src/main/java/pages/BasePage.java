package pages;

import driver.DriverManager;
import utils.ElementUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Base page class with common functionality for all page objects
 * Author: Sivaraman M
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
        PageFactory.initElements(driver, this);
    }

    protected void waitForPageToLoad() {
        ElementUtils.waitForPageToLoad();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
        waitForPageToLoad();
    }

    protected void navigateBack() {
        driver.navigate().back();
        waitForPageToLoad();
    }

    protected void navigateForward() {
        driver.navigate().forward();
        waitForPageToLoad();
    }

    // Abstract method to be implemented by each page for validation
    public abstract boolean isPageLoaded();
}