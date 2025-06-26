package base;

import session.SessionManager;
import utils.ScreenshotManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Suite manager for single session test execution
 * Author: Sivaraman M
 */
public class SingleSessionSuiteManager {

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup() {
        System.out.println("🏁 Starting Single Session Test Suite");
        System.out.println("📋 Test Execution Plan:");
        System.out.println("   1. Login once at the beginning");
        System.out.println("   2. Execute all test cases sequentially");
        System.out.println("   3. Logout at the end");
        System.out.println("   4. Close browser and cleanup");
        
        SessionManager sessionManager = SessionManager.getInstance();
        boolean sessionInitialized = sessionManager.initializeSession();
        
        if (!sessionInitialized) {
            throw new RuntimeException("❌ Failed to initialize test session. Suite execution aborted.");
        }
        
        System.out.println("✅ Suite setup completed - Ready to execute test cases");
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTeardown() {
        System.out.println("🏁 Completing Single Session Test Suite");
        
        try {
            // Terminate session (includes logout)
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.terminateSession();
            
            // Cleanup old screenshots
            ScreenshotManager.cleanupOldScreenshots(7);
            
            System.out.println("✅ Suite teardown completed successfully");
            
        } catch (Exception e) {
            System.err.println("⚠️ Error during suite teardown: " + e.getMessage());
        }
        
        System.out.println("🎯 Single Session Test Suite Execution Summary:");
        System.out.println("   ✓ Single login/logout cycle completed");
        System.out.println("   ✓ All test cases executed in sequence");
        System.out.println("   ✓ Browser closed and resources cleaned up");
    }
}