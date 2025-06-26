package utils;

import driver.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Enhanced screenshot utility with better file management
 * Author: Sivaraman M
 */
public class ScreenshotManager {
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    static {
        createScreenshotDirectory();
    }

    private static void createScreenshotDirectory() {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                Files.createDirectories(screenshotPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to create screenshot directory: " + e.getMessage());
        }
    }

    public static String takeScreenshot(String testName) {
        if (!DriverManager.isDriverInitialized()) {
            System.err.println("Cannot take screenshot: Driver not initialized");
            return null;
        }

        try {
            WebDriver driver = DriverManager.getDriver();
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String fileName = String.format("%s_%s.png", testName, timestamp);
            Path destinationPath = Paths.get(SCREENSHOT_DIR, fileName);
            
            Files.copy(sourceFile.toPath(), destinationPath);
            
            String absolutePath = destinationPath.toAbsolutePath().toString();
            System.out.println("📸 Screenshot saved: " + absolutePath);
            return absolutePath;
            
        } catch (IOException e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String takeScreenshotOnFailure(String testName) {
        return takeScreenshot(testName + "_FAILED");
    }

    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            Path screenshotPath = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotPath)) {
                return;
            }

            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24L * 60L * 60L * 1000L);
            
            Files.list(screenshotPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> {
                        try {
                            return Files.getLastModifiedTime(path).toMillis() < cutoffTime;
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                            System.out.println("Deleted old screenshot: " + path.getFileName());
                        } catch (IOException e) {
                            System.err.println("Failed to delete screenshot: " + path.getFileName());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Failed to cleanup old screenshots: " + e.getMessage());
        }
    }
}