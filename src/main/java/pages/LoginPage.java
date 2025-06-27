package pages;

import utils.ElementUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Enhanced login page with better error handling and validation
 * Author: Sivaraman M
 */
public class LoginPage extends BasePage {

    @FindBy(id = "login_Layer")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='login-layer']")
    private WebElement loginModal;

    @FindBy(xpath = "//input[@placeholder='Enter your active Email ID / Username']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@placeholder='Enter your password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//div[@class='error-message']")
    private WebElement errorMessage;

    @FindBy(xpath = "//div[contains(@class, 'user-name')]")
    private WebElement userProfileElement;

    public boolean performLogin(String email, String password) {
        try {
            if (!isLoginButtonVisible()) {
                System.out.println("Login button not visible, user might already be logged in");
                return isUserLoggedIn();
            }

            clickLoginButton();
            waitForLoginModal();
            enterCredentials(email, password);
            submitLogin();
            
            return waitForLoginSuccess();
            
        } catch (Exception e) {
            System.err.println("Login failed with exception: " + e.getMessage());
            return false;
        }
    }

    private boolean isLoginButtonVisible() {
        return ElementUtils.isDisplayed(loginButton);
    }

    private void clickLoginButton() {
        ElementUtils.click(loginButton);
        System.out.println("Clicked login button");
    }

    private void waitForLoginModal() {
        ElementUtils.waitForElementToBeVisible(loginModal);
        System.out.println("Login modal appeared");
    }

    private void enterCredentials(String email, String password) {
        ElementUtils.sendKeys(emailField, email);
        ElementUtils.sendKeys(passwordField, password);
        System.out.println("Entered login credentials");
    }

    private void submitLogin() {
        ElementUtils.click(submitButton);
        System.out.println("Submitted login form");
    }

    private boolean waitForLoginSuccess() {
        try {
            // Wait for either success (profile element) or error message
            Thread.sleep(3000); // Give time for login processing
            
            if (ElementUtils.isDisplayed(errorMessage)) {
                String error = ElementUtils.getText(errorMessage);
                System.err.println("Login error: " + error);
                return false;
            }
            
            return isUserLoggedIn();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private boolean isUserLoggedIn() {
        // Check if user profile element is visible or login button is not visible
        return ElementUtils.isDisplayed(userProfileElement) || !ElementUtils.isDisplayed(loginButton);
    }

    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(loginButton) || isUserLoggedIn();
    }

    public boolean isLoginModalOpen() {
        return ElementUtils.isDisplayed(loginModal);
    }

    public String getErrorMessage() {
        if (ElementUtils.isDisplayed(errorMessage)) {
            return ElementUtils.getText(errorMessage);
        }
        return null;
    }
}