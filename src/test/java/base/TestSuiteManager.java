package base;

import config.ConfigManager;
import driver.DriverManager;
import pages.LoginPage;
import utils.ScreenshotManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Enhanced test suite manager with better session management
 * Author: Sivaraman M
 */
public class TestSuiteManager {
    private ConfigManager config;

    @BeforeSuite
    public void suiteSetup() {
        System.out.println("🏁 Starting test suite execution");
        
        config = ConfigManager.getInstance();
        
        // Initialize driver and perform global login
        DriverManager.initializeDriver();
        DriverManager.getDriver().get(config.getBaseUrl());
        
        LoginPage loginPage = new LoginPage();
        boolean loginSuccess = loginPage.performLogin(config.getEmail(), config.getPassword());
        
        if (loginSuccess) {
            System.out.println("✅ Global login successful");
        } else {
            System.out.println("❌ Global login failed");
            ScreenshotManager.takeScreenshot("global_login_failed");
            throw new RuntimeException("Failed to perform global login");
        }
    }

    @AfterSuite
    public void suiteTeardown() {
        System.out.println("🏁 Test suite execution completed");
        
        // Cleanup old screenshots (keep last 7 days)
        ScreenshotManager.cleanupOldScreenshots(7);
        
        // Close browser
        DriverManager.quitDriver();
        System.out.println("✅ Test suite cleanup completed");
    }
}