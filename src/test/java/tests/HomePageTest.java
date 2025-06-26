package tests;

import base.BaseTest;
import pages.HomePage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Home page functionality test cases
 * Author: Sivaraman M
 */
public class HomePageTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod
    public void loginAndNavigateToHome() {
        navigateToBaseUrl();
        
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin(config.getEmail(), config.getPassword());
        
        homePage = new HomePage();
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded");
    }

    @Test(description = "Verify job search functionality")
    public void testJobSearch() {
        homePage.searchJobs("Java Developer", "Bangalore");
        
        // Verify that search results page is loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("search"), 
                "Should navigate to search results page");
    }

    @Test(description = "Verify job search with skills only")
    public void testJobSearchWithSkillsOnly() {
        homePage.searchJobs("Python Developer");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("search"), 
                "Should navigate to search results page");
    }

    @Test(description = "Verify navigation to Jobs section")
    public void testJobsNavigation() {
        homePage.clickJobsLink();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("jobs"), 
                "Should navigate to jobs section");
    }

    @Test(description = "Verify navigation to Companies section")
    public void testCompaniesNavigation() {
        homePage.clickCompaniesLink();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("companies"), 
                "Should navigate to companies section");
    }

    @Test(description = "Verify trending searches are displayed")
    public void testTrendingSearches() {
        List<String> trendingSearches = homePage.getTrendingSearches();
        
        Assert.assertFalse(trendingSearches.isEmpty(), 
                "Trending searches should be displayed");
        
        System.out.println("Trending searches found: " + trendingSearches.size());
        trendingSearches.forEach(search -> System.out.println("- " + search));
    }

    @Test(description = "Verify user menu is accessible")
    public void testUserMenuAccess() {
        Assert.assertTrue(homePage.isUserLoggedIn(), 
                "User should be logged in to access user menu");
        
        homePage.openUserMenu();
        // Additional assertions can be added based on user menu options
    }
}