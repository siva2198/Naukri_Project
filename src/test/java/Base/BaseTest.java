package Base;

import Utils.ConfigReader;
import Utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public abstract class BaseTest {
    public WebDriver driver;
//    @BeforeMethod
//    public void setUp(){
//        ConfigReader.initProperties();
//        driver = DriverFactory.initDriver();
//    }
//    @AfterMethod
//    public void tearDown(){
//        DriverFactory.quitDriver();
//    }

    public BaseTest(){
        this.driver =DriverFactory.getDriver();
    }
}

