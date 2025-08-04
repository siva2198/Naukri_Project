package driver;

import config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Enhanced driver manager with support for multiple browsers and better configuration
 * Author: Sivaraman M
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    private static final ConfigManager config = ConfigManager.getInstance();

    public enum BrowserType {
        CHROME, FIREFOX
    }

    public static WebDriver initializeDriver() {
        return initializeDriver(BrowserType.CHROME);
    }

    public static WebDriver initializeDriver(BrowserType browserType) {
        if (driverThreadLocal.get() != null) {
            return driverThreadLocal.get();
        }

        WebDriver driver = createDriver(browserType);
        driverThreadLocal.set(driver);
        
        // Configure driver timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().window().maximize();
        
        // Initialize WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(config.getExplicitWait()));
        waitThreadLocal.set(wait);
        
        return driver;
    }

    private static WebDriver createDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                return createChromeDriver();
            case FIREFOX:
                return createFirefoxDriver();
            default:
                throw new IllegalArgumentException("Browser type not supported: " + browserType);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        return new FirefoxDriver(options);
    }

    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait = waitThreadLocal.get();
        if (wait == null) {
            throw new IllegalStateException("WebDriverWait not initialized. Call initializeDriver() first.");
        }
        return wait;
    }

    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                System.err.println("Error while quitting driver: " + e.getMessage());
            } finally {
                driverThreadLocal.remove();
                waitThreadLocal.remove();
            }
        }
    }

    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}