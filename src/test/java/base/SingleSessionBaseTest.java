package base;

import config.ConfigManager;
import session.SessionManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

/**
 * Base test class for single session test execution
 * Author: Sivaraman M
 */
public abstract class SingleSessionBaseTest {
    protected WebDriver driver;
    protected ConfigManager config;
    protected SessionManager sessionManager;

    @BeforeMethod
    public void setUp() {
        config = ConfigManager.getInstance();
        sessionManager = SessionManager.getInstance();
        
        // Ensure session is active
        if (!sessionManager.isSessionActive()) {
            boolean sessionInitialized = sessionManager.initializeSession();
            if (!sessionInitialized) {
                throw new RuntimeException("Failed to initialize test session");
            }
        }
        
        driver = sessionManager.getDriver();
        
        // Navigate to home page before each test
        sessionManager.navigateToHome();
    }

    protected void navigateToUrl(String url) {
        driver.get(url);
        System.out.println("🔗 Navigated to: " + url);
    }
}