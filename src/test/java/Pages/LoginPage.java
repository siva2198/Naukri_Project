package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.beans.Visibility;
import java.time.Duration;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver){
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="login_Layer")
    WebElement loginPageLoginButton;
    @FindBy(xpath = "//div[@class='login-layer']")
    WebElement loginPageSlideHeader;

    @FindBy(xpath = "//input[@placeholder='Enter your active Email ID / Username']")
    WebElement loginPageEmailTextField;
    @FindBy(xpath = "//input[@placeholder='Enter your password']")
    WebElement loginPagePasswordTextField;
    @FindBy(xpath = "//button[@type='submit']")
    WebElement loginPageSlideLoginButton;

    public void userLogin(String email,String password){
        boolean loginPageLoginButtonS =loginPageLoginButton.isDisplayed();
        Assert.assertTrue(loginPageLoginButtonS);
        loginPageLoginButton.click();
        wait.until(ExpectedConditions.visibilityOf(loginPageSlideHeader));
        loginPageEmailTextField.sendKeys(email);
        loginPagePasswordTextField.sendKeys(password);
        loginPageSlideLoginButton.click();
    }

}
