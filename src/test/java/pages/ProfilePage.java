package pages;

import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

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
    private WebElement fileUploadInput;

    @FindBy(xpath = "//input[@value='Update resume']")
    private WebElement updateResumeButton;

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
        try {
            System.out.println("Starting resume upload using Robot class");
            
            // Click the file upload input to open file dialog
           ElementUtils.click(fileUploadInput);
            System.out.println("Clicked file upload input");
            
            // Wait for file dialog to open
            Thread.sleep(2000);
            
            // File path
            String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\QA_Engineer_Sivaraman M_June.pdf";
            
            // Copy file path to clipboard
            StringSelection stringSelection = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            
            // Create Robot instance
            Robot robot = new Robot();
            
            // Paste the file path (Ctrl+V)
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            
            // Wait a moment
            Thread.sleep(1000);
            
            // Press Enter to confirm file selection
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            
            System.out.println("File upload completed using Robot class");
            
        } catch (Exception e) {
            System.err.println("Error during file upload: " + e.getMessage());
            e.printStackTrace();
        }
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