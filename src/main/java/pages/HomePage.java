package pages;

import org.openqa.selenium.By;
import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Home page object with common navigation and search functionality
 * Author: Sivaraman M
 */
public class HomePage extends BasePage {

    @FindBy(xpath = "//input[@placeholder='Enter skills / designations / companies']")
    private WebElement searchBox;

    @FindBy(xpath = "//input[@placeholder='Enter location']")
    private WebElement locationBox;

    @FindBy(xpath = "//button[contains(@class, 'search-button')]")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@class='nI-gNb-header__logo nI-gNb-company-logo']")
    private WebElement naukriLogo;

    @FindBy(xpath = "//nav//a[contains(@href, 'jobs')]")
    private WebElement jobsLink;

    @FindBy(xpath = "//nav//a[contains(@href, 'companies')]")
    private WebElement companiesLink;

    @FindBy(xpath = "//nav//a[contains(@href, 'services')]")
    private WebElement servicesLink;

    @FindBy(xpath = "//div[@class='nI-gNb-drawer__icon-img-wrapper']")
    private WebElement userMenu;

    @FindBy(xpath = "//div[@class='trending-searches']//a")
    private List<WebElement> trendingSearches;

    public void searchJobs(String skills, String location) {
        ElementUtils.sendKeys(searchBox, skills);
        if (location != null && !location.isEmpty()) {
            ElementUtils.sendKeys(locationBox, location);
        }
        ElementUtils.click(searchButton);
        System.out.println("Searched for jobs: " + skills + " in " + location);
    }

    public void searchJobs(String skills) {
        searchJobs(skills, "");
    }

    public void clickJobsLink() {
        ElementUtils.click(jobsLink);
        System.out.println("Clicked Jobs link");
    }

    public void clickCompaniesLink() {
        ElementUtils.click(companiesLink);
        System.out.println("Clicked Companies link");
    }

    public void clickServicesLink() {
        ElementUtils.click(servicesLink);
        System.out.println("Clicked Services link");
    }

    public void openUserMenu() {
        ElementUtils.click(userMenu);
        System.out.println("Opened user menu");
    }

    public List<String> getTrendingSearches() {
        return trendingSearches.stream()
                .map(ElementUtils::getText)
                .toList();
    }

    public void clickTrendingSearch(String searchTerm) {
        trendingSearches.stream()
                .filter(element -> ElementUtils.getText(element).equals(searchTerm))
                .findFirst()
                .ifPresent(element -> {
                    ElementUtils.click(element);
                    System.out.println("Clicked trending search: " + searchTerm);
                });
    }

    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(naukriLogo);
    }

    public boolean isUserLoggedIn() {
        return ElementUtils.isDisplayed(userMenu);
    }
    public void getProfile(){
        WebElement viewProfileLink = driver.findElement(
                By.xpath("//a[contains(text(),'Update Profile')]")
        );

// Extract and build full URL
        String relativeUrl = viewProfileLink.getAttribute("href");
//        String fullUrl = "https://www.naukri.com" + relativeUrl;

// Navigate directly to profile page
        driver.get(relativeUrl);
        System.out.printf("Get profile link :%s%n", relativeUrl);



    }
}