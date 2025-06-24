package Test;

import Base.BaseTest;
import Utils.ConfigReader;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class SampleDryRun extends BaseTest {
    @Test
    public void loginprofile() throws InterruptedException {
        Thread.sleep(8000);
        System.out.println(driver.getCurrentUrl());

    }
}
