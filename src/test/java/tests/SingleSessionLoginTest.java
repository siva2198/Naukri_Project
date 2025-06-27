package tests;

import base.SingleSessionBaseTest;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProfilePage;

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
        navigateToUrl("https://www.naukri.com/mnjuser/homepage");
        
        // Navigate back to home
        sessionManager.navigateToHome();
        
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should still be logged in after navigation");
        
        System.out.println("✅ Test 2: Session persistence verified");
    }
    @Test(priority = 3, description = "Verify user update resume headline")
    public void testResumeHeadlineUpdate(){
        HomePage homePage = new HomePage();
        homePage.getProfile();
        ProfilePage profilePage = new ProfilePage();
        profilePage.clickEditProfileHeadline();
        profilePage.clickOnSaveEditResumeHeadline();
        System.out.println(profilePage.getMessage());
       Assert.assertEquals(profilePage.getMessage(),"GreenTick\n" +
               "Success\n" +
               "Resume Headline has been successfully saved.");
        System.out.println("✅ Test 3: Session persistence verified");
    }

    @Test(priority = 4 ,description = "Verify user upload resume")
    public void testResumeUpdate(){
        HomePage homePage = new HomePage();
        homePage.getProfile();
        ProfilePage profilePage = new ProfilePage();
        profilePage.uploadResumeClickAndUpload();
        System.out.println(profilePage.getMessage());
        System.out.println("✅ Test 4: Session persistence verified");
    }
}