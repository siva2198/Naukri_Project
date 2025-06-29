package pages;

import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.JavascriptExecutor;

/**
 * Profile page object for managing user profile operations
 * Author: Sivaraman M
 */
public class ProfilePage extends BasePage {

    @FindBy(xpath = "//h1[contains(@class, 'profile-name')]")
    private WebElement profileName;

    @FindBy(xpath = "//div[@class='widgetHead']//span[@class='edit icon'][normalize-space()='editOneTheme']")
    private WebElement editProfileHeadlineButton;

    @FindBy(xpath = "//button[normalize-space()='Save']")
    private WebElement saveButtonHeadline;

    @FindBy(xpath = "//button[contains(@class, 'edit-profile')]")
    private WebElement editProfileButton;

    @FindBy(xpath = "//div[@class='profile-completion']//span[@class='percentage']")
    private WebElement profileCompletionPercentage;

    @FindBy(xpath = "//section[@class='resume-section']")
    private WebElement resumeSection;

    @FindBy(xpath = "//input[@value='Update resume']")
    private WebElement uploadResumeButton;

    @FindBy(xpath = "//div[@class='experience-section']")
    private WebElement experienceSection;

    @FindBy(xpath = "//div[@class='education-section']")
    private WebElement educationSection;

    @FindBy(xpath = "//div[@class='skills-section']")
    private WebElement skillsSection;

    @FindBy(xpath = "//div[@class='msgBox success ']")
    private WebElement messageBoxSucessOrFailure;


    public String getProfileName() {
        ElementUtils.waitForElementToBeVisible(profileName);
        return ElementUtils.getText(profileName);
    }

    public String getProfileHeadline() {
        return ElementUtils.getText(editProfileHeadlineButton);
    }
    public void clickEditProfileHeadline(){
        ElementUtils.click(editProfileHeadlineButton);
        System.out.println("Clicked on resume headline");
    }

    public void clickEditResumeHeadline() {
        ElementUtils.click(editProfileButton);
        System.out.println("Clicked edit profile button");
    }

    public void clickOnSaveEditResumeHeadline(){
        ElementUtils.click(saveButtonHeadline);
        System.out.println("Clicked on save Resume headline");
    }

    public String getMessage(){
        if (ElementUtils.isDisplayed(messageBoxSucessOrFailure)){
            return ElementUtils.getText(messageBoxSucessOrFailure);
        }
        return "0%";
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

    public void uploadResumeClickAndUpload() {
        System.out.println("Starting resume upload process");
        
        // Create a hidden file input element using JavaScript
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testdata/QA_Engineer_Sivaraman M_June.pdf";
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        // Create hidden file input element
        js.executeScript(
            "var input = document.createElement('input');" +
            "input.type = 'file';" +
            "input.style.display = 'none';" +
            "input.id = 'hiddenFileInput';" +
            "document.body.appendChild(input);"
        );
        
        // Set the file path to the hidden input
        WebElement hiddenInput = driver.findElement(org.openqa.selenium.By.id("hiddenFileInput"));
        hiddenInput.sendKeys(filePath);
        
        // Trigger the upload by simulating the file selection
        js.executeScript(
            "var input = document.getElementById('hiddenFileInput');" +
            "var event = new Event('change', { bubbles: true });" +
            "input.dispatchEvent(event);"
        );
        
        System.out.println("Resume file upload initiated: " + filePath);
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