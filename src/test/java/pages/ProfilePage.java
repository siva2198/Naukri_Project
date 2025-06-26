package pages;

import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Profile page object for managing user profile operations
 * Author: Sivaraman M
 */
public class ProfilePage extends BasePage {

    @FindBy(xpath = "//h1[contains(@class, 'profile-name')]")
    private WebElement profileName;

    @FindBy(xpath = "//div[contains(@class, 'profile-headline')]")
    private WebElement profileHeadline;

    @FindBy(xpath = "//button[contains(@class, 'edit-profile')]")
    private WebElement editProfileButton;

    @FindBy(xpath = "//div[@class='profile-completion']//span[@class='percentage']")
    private WebElement profileCompletionPercentage;

    @FindBy(xpath = "//section[@class='resume-section']")
    private WebElement resumeSection;

    @FindBy(xpath = "//button[contains(@class, 'upload-resume')]")
    private WebElement uploadResumeButton;

    @FindBy(xpath = "//div[@class='experience-section']")
    private WebElement experienceSection;

    @FindBy(xpath = "//div[@class='education-section']")
    private WebElement educationSection;

    @FindBy(xpath = "//div[@class='skills-section']")
    private WebElement skillsSection;

    public String getProfileName() {
        ElementUtils.waitForElementToBeVisible(profileName);
        return ElementUtils.getText(profileName);
    }

    public String getProfileHeadline() {
        return ElementUtils.getText(profileHeadline);
    }

    public void clickEditProfile() {
        ElementUtils.click(editProfileButton);
        System.out.println("Clicked edit profile button");
    }

    public String getProfileCompletionPercentage() {
        if (ElementUtils.isDisplayed(profileCompletionPercentage)) {
            return ElementUtils.getText(profileCompletionPercentage);
        }
        return "0%";
    }

    public boolean hasResume() {
        return ElementUtils.isDisplayed(resumeSection);
    }

    public void uploadResume() {
        ElementUtils.click(uploadResumeButton);
        System.out.println("Clicked upload resume button");
    }

    public boolean hasExperience() {
        return ElementUtils.isDisplayed(experienceSection);
    }

    public boolean hasEducation() {
        return ElementUtils.isDisplayed(educationSection);
    }

    public boolean hasSkills() {
        return ElementUtils.isDisplayed(skillsSection);
    }

    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(profileName);
    }

    public void waitForProfileToLoad() {
        ElementUtils.waitForElementToBeVisible(profileName);
        waitForPageToLoad();
    }
}