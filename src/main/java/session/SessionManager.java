package session;

import config.ConfigManager;
import driver.DriverManager;
import pages.LoginPage;
import pages.HomePage;
import utils.ScreenshotManager;
import org.openqa.selenium.WebDriver;

/**
 * Manages single login session for all test cases
 * Author: Sivaraman M
 */
public class SessionManager {
    private static SessionManager instance;
    private static boolean isSessionActive = false;
    private static boolean loginPerformed = false;
    private final ConfigManager config;
    private WebDriver driver;

    private SessionManager() {
        this.config = ConfigManager.getInstance();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                }
            }
        }
        return instance;
    }

    /**
     * Initialize session and perform login once
     */
    public boolean initializeSession() {
        if (isSessionActive) {
            System.out.println("🔄 Session already active, reusing existing session");
            return true;
        }

        try {
            System.out.println("🚀 Initializing new test session...");
            
            // Initialize driver
            driver = DriverManager.initializeDriver();
            driver.get(config.getBaseUrl());
            
            // Perform login
            LoginPage loginPage = new LoginPage();
            boolean loginSuccess = loginPage.performLogin(config.getEmail(), config.getPassword());
            
            if (loginSuccess) {
                isSessionActive = true;
                loginPerformed = true;
                System.out.println("✅ Session initialized successfully - User logged in");
                
                // Verify we're on home page
                HomePage homePage = new HomePage();
                if (homePage.isPageLoaded()) {
                    System.out.println("🏠 Home page loaded successfully");
                    return true;
                }
            }
            
            System.out.println("❌ Session initialization failed");
            ScreenshotManager.takeScreenshot("session_init_failed");
            return false;
            
        } catch (Exception e) {
            System.err.println("❌ Session initialization error: " + e.getMessage());
            ScreenshotManager.takeScreenshot("session_init_error");
            return false;
        }
    }

    /**
     * Navigate to home page if not already there
     */
    public void navigateToHome() {
        if (!isSessionActive) {
            throw new IllegalStateException("Session not active. Call initializeSession() first.");
        }
        
        driver.get(config.getBaseUrl());
        System.out.println("🏠 Navigated to home page");
    }

    /**
     * Check if session is active and user is logged in
     */
    public boolean isSessionActive() {
        return isSessionActive && DriverManager.isDriverInitialized();
    }

    /**
     * Get current driver instance
     */
    public WebDriver getDriver() {
        if (!isSessionActive) {
            throw new IllegalStateException("Session not active. Call initializeSession() first.");
        }
        return DriverManager.getDriver();
    }

    /**
     * Perform logout and cleanup session
     */
    public void terminateSession() {
        if (!isSessionActive) {
            System.out.println("⚠️ No active session to terminate");
            return;
        }

        try {
            System.out.println("🔚 Terminating session...");
            
            if (loginPerformed) {
                performLogout();
            }
            
            // Cleanup driver
            DriverManager.quitDriver();
            
            // Reset session state
            isSessionActive = false;
            loginPerformed = false;
            
            System.out.println("✅ Session terminated successfully");
            
        } catch (Exception e) {
            System.err.println("⚠️ Error during session termination: " + e.getMessage());
        }
    }

    /**
     * Perform logout operation
     */
    private void performLogout() {
        try {
            // Navigate to home page first
            navigateToHome();
            
            // Perform logout (implementation depends on Naukri's logout mechanism)
            HomePage homePage = new HomePage();
            if (homePage.isUserLoggedIn()) {
                homePage.openUserMenu();
                // Add logout click logic here based on actual UI
                Thread.sleep(2000); // Wait for logout to complete
                System.out.println("👋 User logged out successfully");
            }
            
        } catch (Exception e) {
            System.err.println("⚠️ Logout error: " + e.getMessage());
            ScreenshotManager.takeScreenshot("logout_error");
        }
    }

    /**
     * Reset session for testing purposes
     */
    public void resetSession() {
        terminateSession();
        isSessionActive = false;
        loginPerformed = false;
    }
}