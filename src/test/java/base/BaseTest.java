package base;

import config.ConfigManager;
import driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Enhanced base test class with better setup and teardown
 * Author: Sivaraman M
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected ConfigManager config;

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browserName) {
        config = ConfigManager.getInstance();
        
        DriverManager.BrowserType browserType;
        try {
            browserType = DriverManager.BrowserType.valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid browser: " + browserName + ". Using Chrome as default.");
            browserType = DriverManager.BrowserType.CHROME;
        }
        
        driver = DriverManager.initializeDriver(browserType);
        System.out.println("🌐 Browser initialized: " + browserType);
    }

    @AfterMethod
    public void tearDown() {
        if (DriverManager.isDriverInitialized()) {
            DriverManager.quitDriver();
            System.out.println("🔚 Browser closed");
        }
    }

    protected void navigateToBaseUrl() {
        driver.get(config.getBaseUrl());
        System.out.println("🔗 Navigated to: " + config.getBaseUrl());
    }

    protected void navigateToUrl(String url) {
        driver.get(url);
        System.out.println("🔗 Navigated to: " + url);
    }
}