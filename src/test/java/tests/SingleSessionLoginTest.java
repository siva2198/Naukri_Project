package tests;

import base.SingleSessionBaseTest;
import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login validation tests for single session execution
 * Author: Sivaraman M
 */
public class SingleSessionLoginTest extends SingleSessionBaseTest {

    @Test(priority = 1, description = "Verify user is logged in and home page is accessible")
    public void testUserLoggedInStatus() {
        HomePage homePage = new HomePage();
        
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        
        System.out.println("✅ Test 1: User login status verified");
    }

    @Test(priority = 2, description = "Verify login session persistence")
    public void testSessionPersistence() {
        // Navigate away and back to verify session persistence
        navigateToUrl("https://www.naukri.com/jobs");
        
        // Navigate back to home
        sessionManager.navigateToHome();
        
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should still be logged in after navigation");
        
        System.out.println("✅ Test 2: Session persistence verified");
    }
}