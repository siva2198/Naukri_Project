package Base;

import Pages.LoginPage;
import Utils.ConfigReader;
import Utils.DriverFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class TestSuiteSetUp {
    @BeforeSuite
        public void globalLogin(){
        ConfigReader.initProperties();
        DriverFactory.initDriver().get(ConfigReader.get("baseURL"));
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.userLogin(ConfigReader.get("email"),ConfigReader.get("password"));
        System.out.println("✅ Logged in once before all tests.");
    }

    @AfterSuite
    public void globalTearDown(){
        DriverFactory.quitDriver();
        System.out.println("✅ Closed browser after all tests.");
    }
}
