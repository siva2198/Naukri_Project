package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Author: Sivaraman M
 * User:Sivaraman M
 */
public class ScreenShotUtil {
        public static void takeScreenshot(String fileName){
            WebDriver driver = DriverFactory.getDriver();
            File srcFlie =((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile= new File("screenshots/"+fileName);
            destFile.getParentFile().mkdirs();
            try{
                Files.copy(srcFlie.toPath(),destFile.toPath());
                System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());

            }catch (IOException e){
                e.printStackTrace();
            }
            }

}
