package tests;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Login functionality test cases
 * Author: Sivaraman M
 */
public class LoginTest extends BaseTest {

    @Test(priority = 1, description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        navigateToBaseUrl();
        
        LoginPage loginPage = new LoginPage();
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page should be loaded");
        
        boolean loginSuccess = loginPage.performLogin(config.getEmail(), config.getPassword());
        Assert.assertTrue(loginSuccess, "Login should be successful with valid credentials");
        
        HomePage homePage = new HomePage();
        Assert.assertTrue(homePage.isUserLoggedIn(), "User should be logged in on home page");
    }

    @Test(priority = 2, description = "Verify login failure with invalid credentials")
    public void testInvalidLogin() {
        navigateToBaseUrl();
        
        LoginPage loginPage = new LoginPage();
        boolean loginSuccess = loginPage.performLogin("invalid@email.com", "wrongpassword");
        
        Assert.assertFalse(loginSuccess, "Login should fail with invalid credentials");
        
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should be displayed for invalid login");
    }

    @Test(priority = 3, description = "Verify login with empty credentials")
    public void testEmptyCredentialsLogin() {
        navigateToBaseUrl();
        
        LoginPage loginPage = new LoginPage();
        boolean loginSuccess = loginPage.performLogin("", "");
        
        Assert.assertFalse(loginSuccess, "Login should fail with empty credentials");
    }
}