package Utils;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result){
        String testName = result.getTestName();

        ScreenShotUtil.takeScreenshot(testName+"_failed.png");
    }
}
