package pages;

import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.ArrayList;

/**
 * Enhanced Home page object with logout functionality
 * Author: Sivaraman M
 */
public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Enter skills / designations / companies']")
    private WebElement searchBox;

    @FindBy(xpath = "//input[@placeholder='Enter location']")
    private WebElement locationBox;

    @FindBy(xpath = "//button[contains(@class, 'search-button')] | //button[contains(text(), 'Search')]")
    private WebElement searchButton;

    @FindBy(xpath = "//div[@class='naukri-logo'] | //a[contains(@class, 'naukri-logo')]")
    private WebElement naukriLogo;

    @FindBy(xpath = "//nav//a[contains(@href, 'jobs')] | //a[contains(text(), 'Jobs')]")
    private WebElement jobsLink;

    @FindBy(xpath = "//nav//a[contains(@href, 'companies')] | //a[contains(text(), 'Companies')]")
    private WebElement companiesLink;

    @FindBy(xpath = "//nav//a[contains(@href, 'services')] | //a[contains(text(), 'Services')]")
    private WebElement servicesLink;

    @FindBy(xpath = "//div[contains(@class, 'user-menu')] | //div[contains(@class, 'dropdown')] | //span[contains(@class, 'user')]")
    private WebElement userMenu;

    @FindBy(xpath = "//a[contains(text(), 'Logout')] | //span[contains(text(), 'Logout')]")
    private WebElement logoutLink;

    @FindBy(xpath = "//div[@class='trending-searches']//a | //div[contains(@class, 'trending')]//a")
    private List<WebElement> trendingSearches;

    public void searchJobs(String skills, String location) {
        ElementUtils.sendKeys(searchBox, skills);
        if (location != null && !location.isEmpty()) {
            ElementUtils.sendKeys(locationBox, location);
        }
        ElementUtils.click(searchButton);
        System.out.println("🔍 Searched for jobs: " + skills + " in " + location);
    }

    public void searchJobs(String skills) {
        searchJobs(skills, "");
    }

    public void clickJobsLink() {
        ElementUtils.click(jobsLink);
        System.out.println("🔗 Clicked Jobs link");
    }

    public void clickCompaniesLink() {
        ElementUtils.click(companiesLink);
        System.out.println("🔗 Clicked Companies link");
    }

    public void clickServicesLink() {
        ElementUtils.click(servicesLink);
        System.out.println("🔗 Clicked Services link");
    }

    public void openUserMenu() {
        ElementUtils.click(userMenu);
        System.out.println("👤 Opened user menu");
    }

    public void performLogout() {
        try {
            openUserMenu();
            Thread.sleep(1000); // Wait for menu to open
            
            if (ElementUtils.isDisplayed(logoutLink)) {
                ElementUtils.click(logoutLink);
                System.out.println("👋 Clicked logout link");
                Thread.sleep(2000); // Wait for logout to complete
            } else {
                System.out.println("⚠️ Logout link not found in user menu");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Logout interrupted: " + e.getMessage());
        }
    }

    public List<String> getTrendingSearches() {
        List<String> searches = new ArrayList<>();
        try {
            for (WebElement element : trendingSearches) {
                if (ElementUtils.isDisplayed(element)) {
                    searches.add(ElementUtils.getText(element));
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Could not retrieve trending searches: " + e.getMessage());
        }
        return searches;
    }

    public void clickTrendingSearch(String searchTerm) {
        trendingSearches.stream()
                .filter(element -> ElementUtils.getText(element).equals(searchTerm))
                .findFirst()
                .ifPresent(element -> {
                    ElementUtils.click(element);
                    System.out.println("🔍 Clicked trending search: " + searchTerm);
                });
    }

    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(naukriLogo) && 
               ElementUtils.isDisplayed(searchBox);
    }

    public boolean isUserLoggedIn() {
        return ElementUtils.isDisplayed(userMenu);
    }
}