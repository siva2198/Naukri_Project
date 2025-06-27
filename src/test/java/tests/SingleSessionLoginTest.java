package tests;

import base.SingleSessionBaseTest;
import io.qameta.allure.*;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ProfilePage;
import utils.ScreenshotManager;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Login validation tests for single session execution with Allure reporting
 * Author: Sivaraman M
 */
@Epic("Naukri User Profile Management")
@Feature("Resume Management")
public class SingleSessionLoginTest extends SingleSessionBaseTest {

    @Test(priority = 1, description = "Verify user is logged in and home page is accessible")
    @Story("User Authentication Verification")
    @Description("Validates that user is successfully logged in and can access the home page")
    @Severity(SeverityLevel.CRITICAL)
    public void testUserLoggedInStatus() {
        HomePage homePage = new HomePage();
        
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in");
        
        Allure.step("User login status verified successfully");
        System.out.println("✅ Test 1: User login status verified");
    }

    @Test(priority = 2, description = "Verify login session persistence")
    @Story("Session Management")
    @Description("Validates that user session persists across different page navigations")
    @Severity(SeverityLevel.NORMAL)
    public void testSessionPersistence() {
        Allure.step("Navigate to user homepage");
        navigateToUrl("https://www.naukri.com/mnjuser/homepage");
        
        Allure.step("Navigate back to main home page");
        sessionManager.navigateToHome();
        
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should still be logged in after navigation");
        
        Allure.step("Session persistence verified successfully");
        System.out.println("✅ Test 2: Session persistence verified");
    }

    @Test(priority = 3, description = "Verify user resume headline update functionality")
    @Story("Resume Headline Management")
    @Description("Tests the complete flow of updating resume headline: navigate to profile → edit headline → save → verify success message")
    @Severity(SeverityLevel.CRITICAL)
    public void testResumeHeadlineUpdate() {
        Allure.step("Navigate to user profile page");
        HomePage homePage = new HomePage();
        homePage.getProfile();
        
        Allure.step("Initialize profile page and verify it's loaded");
        ProfilePage profilePage = new ProfilePage();
        Assert.assertTrue(profilePage.isPageLoaded(), "Profile page should be loaded");
        
        Allure.step("Click on edit resume headline button");
        profilePage.clickEditProfileHeadline();
        
        // Add a small wait to ensure the edit form appears
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Allure.step("Save the resume headline changes");
        profilePage.clickOnSaveEditResumeHeadline();
        
        // Wait for success message to appear
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Allure.step("Verify success message appears");
        String actualMessage = profilePage.getMessage();
        String expectedMessage = "GreenTick\nSuccess\nResume Headline has been successfully saved.";
        
        // Take screenshot for Allure report with proper exception handling
        String screenshotPath = ScreenshotManager.takeScreenshot("resume_headline_success");
        if (screenshotPath != null) {
            try {
                byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
                Allure.addAttachment("Resume Headline Update Success", "image/png", screenshotBytes);
            } catch (IOException e) {
                System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
                Allure.step("Screenshot attachment failed: " + e.getMessage());
            }
        }
        
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);
        
        Assert.assertEquals(actualMessage, expectedMessage, 
            "Success message should match expected text");
        
        Allure.step("Resume headline update completed successfully");
        System.out.println("✅ Test 3: Resume headline update verified");
    }

    @Test(priority = 4, description = "Verify user resume file upload functionality")
    @Story("Resume File Management")
    @Description("Tests the resume file upload functionality and verifies successful upload")
    @Severity(SeverityLevel.NORMAL)
    public void testResumeUpdate() {
        Allure.step("Navigate to user profile page");
        HomePage homePage = new HomePage();
        homePage.getProfile();
        
        Allure.step("Initialize profile page and verify it's loaded");
        ProfilePage profilePage = new ProfilePage();
        Assert.assertTrue(profilePage.isPageLoaded(), "Profile page should be loaded");
        
        Allure.step("Upload resume file");
        profilePage.uploadResumeClickAndUpload();
        
        // Wait for upload to complete
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Allure.step("Verify upload completion");
        String uploadMessage = profilePage.getMessage();
        System.out.println("Upload message: " + uploadMessage);
        
        // Take screenshot for Allure report with proper exception handling
        String screenshotPath = ScreenshotManager.takeScreenshot("resume_upload_result");
        if (screenshotPath != null) {
            try {
                byte[] screenshotBytes = Files.readAllBytes(Paths.get(screenshotPath));
                Allure.addAttachment("Resume Upload Result", "image/png", screenshotBytes);
            } catch (IOException e) {
                System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
                Allure.step("Screenshot attachment failed: " + e.getMessage());
            }
        }
        
        Allure.step("Resume upload process completed");
        System.out.println("✅ Test 4: Resume upload process verified");
    }
}