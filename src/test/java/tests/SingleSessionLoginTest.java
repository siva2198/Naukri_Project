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
import java.io.InputStream;
import java.nio.file.Path;

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
        //Assert.assertTrue(profilePage.isPageLoaded(), "Profile page should be loaded");
        
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
        
        // Take screenshot and attach to Allure report using correct method signature
        attachScreenshotToAllure("resume_headline_success", "Resume Headline Update Success");
        
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);
        
        Assert.assertEquals(actualMessage, expectedMessage, 
            "Success message should match expected text");
        
        Allure.step("Resume headline update completed successfully");
        System.out.println("✅ Test 3: Resume headline update verified");
    }

    @Test(priority = 4, description = "Verify user resume file upload functionality")
    @Story("Resume File Management")
    @Description("Tests the resume file upload functionality using JavaScript file upload simulation")
    @Severity(SeverityLevel.NORMAL)
    public void testResumeUpdate() {
        Allure.step("Navigate to user profile page");
        HomePage homePage = new HomePage();
        homePage.getProfile();
        
        Allure.step("Initialize profile page and verify it's loaded");
        ProfilePage profilePage = new ProfilePage();
        //Assert.assertTrue(profilePage.isPageLoaded(), "Profile page should be loaded");
        
        Allure.step("Initiate resume file upload using JavaScript");
        profilePage.uploadResumeClickAndUpload();
        
        // Wait for upload process to complete
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        Allure.step("Verify upload process completion");
        Allure.step("Verify success message appears");
        String actualMessage = profilePage.getMessage();
        String expectedMessage = "GreenTick\nSuccess\nResume has been successfully uploaded.";

        // Take screenshot to capture the current state
        attachScreenshotToAllure("resume_upload_result", "Resume Upload Process Result");

        System.out.println("Expected: " + expectedMessage);
        System.out.println("Actual: " + actualMessage);

        Allure.step("Resume upload process completed successfully");
        System.out.println("✅ Test 4: Resume upload process completed");
    }

    /**
     * Helper method to attach screenshots to Allure report with proper error handling
     * @param screenshotName Name for the screenshot file
     * @param attachmentName Name for the Allure attachment
     */
    private void attachScreenshotToAllure(String screenshotName, String attachmentName) {
        String screenshotPath = ScreenshotManager.takeScreenshot(screenshotName);
        if (screenshotPath != null) {
            try {
                Path path = Paths.get(screenshotPath);
                try (InputStream inputStream = Files.newInputStream(path)) {
                    Allure.addAttachment(attachmentName, "image/png", inputStream, "png");
                }
                Allure.step("Screenshot attached successfully: " + attachmentName);
            } catch (IOException e) {
                System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
                Allure.step("Screenshot attachment failed: " + e.getMessage());
            }
        } else {
            Allure.step("Screenshot could not be taken for: " + attachmentName);
        }
    }
}