package tests;

import base.SingleSessionBaseTest;
import pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Home page functionality tests for single session execution
 * Author: Sivaraman M
 */
public class SingleSessionHomePageTest extends SingleSessionBaseTest {

    @Test(priority = 3, description = "Verify job search functionality")
    public void testJobSearch() {
        HomePage homePage = new HomePage();
        
        homePage.searchJobs("Java Developer", "Bangalore");
        
        // Verify search results page is loaded
        Assert.assertTrue(driver.getCurrentUrl().contains("search"), 
                "Should navigate to search results page");
        
        System.out.println("✅ Test 3: Job search functionality verified");
        
        // Return to home page for next test
        sessionManager.navigateToHome();
    }

    @Test(priority = 4, description = "Verify skills-only job search")
    public void testSkillsOnlySearch() {
        HomePage homePage = new HomePage();
        
        homePage.searchJobs("Python Developer");
        
        Assert.assertTrue(driver.getCurrentUrl().contains("search"), 
                "Should navigate to search results page");
        
        System.out.println("✅ Test 4: Skills-only search verified");
        
        // Return to home page for next test
        sessionManager.navigateToHome();
    }

    @Test(priority = 5, description = "Verify navigation to Jobs section")
    public void testJobsNavigation() {
        HomePage homePage = new HomePage();
        
        homePage.clickJobsLink();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("jobs"), 
                "Should navigate to jobs section");
        
        System.out.println("✅ Test 5: Jobs navigation verified");
        
        // Return to home page for next test
        sessionManager.navigateToHome();
    }

    @Test(priority = 6, description = "Verify navigation to Companies section")
    public void testCompaniesNavigation() {
        HomePage homePage = new HomePage();
        
        homePage.clickCompaniesLink();
        
        Assert.assertTrue(driver.getCurrentUrl().contains("companies"), 
                "Should navigate to companies section");
        
        System.out.println("✅ Test 6: Companies navigation verified");
        
        // Return to home page for next test
        sessionManager.navigateToHome();
    }

    @Test(priority = 7, description = "Verify trending searches functionality")
    public void testTrendingSearches() {
        HomePage homePage = new HomePage();
        
        List<String> trendingSearches = homePage.getTrendingSearches();
        
        Assert.assertFalse(trendingSearches.isEmpty(), 
                "Trending searches should be displayed");
        
        System.out.println("✅ Test 7: Trending searches verified (" + trendingSearches.size() + " items found)");
    }

    @Test(priority = 8, description = "Verify user menu accessibility")
    public void testUserMenuAccess() {
        HomePage homePage = new HomePage();
        
        Assert.assertTrue(homePage.isUserLoggedIn(), 
                "User should be logged in to access user menu");
        
        homePage.openUserMenu();
        
        System.out.println("✅ Test 8: User menu access verified");
        
        // Return to home page for session cleanup
        sessionManager.navigateToHome();
    }
}