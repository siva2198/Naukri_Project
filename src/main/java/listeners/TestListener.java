package listeners;

import utils.ScreenshotManager;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Enhanced test listener with better reporting and screenshot management
 * Author: Sivaraman M
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🚀 Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println("❌ Test failed: " + testName);
        
        // Take screenshot on failure
        String screenshotPath = ScreenshotManager.takeScreenshotOnFailure(testName);
        
        // Log the failure reason
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            System.out.println("Failure reason: " + throwable.getMessage());
        }
        
        // Attach screenshot to TestNG report if available
        if (screenshotPath != null) {
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            System.out.println("Screenshot: <a href='" + screenshotPath + "'>View Screenshot</a>");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭️ Test skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            System.out.println("Skip reason: " + result.getThrowable().getMessage());
        }
    }
}