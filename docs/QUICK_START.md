# Quick Start Guide - Naukri Automation Framework

## 🚀 Getting Started in 5 Minutes

### 1. **Setup & Configuration**
```bash
# Clone and setup
git clone <repository>
cd naukri-automation-framework

# Update configuration
vim config.properties
```

### 2. **Essential Configuration**
```properties
# Required settings in config.properties
baseURL=https://www.naukri.com/
email=your-email@example.com
password=your-password
browser=chrome
```

### 3. **Run Your First Test**
```bash
# Run all tests
mvn clean test

# Run specific test
mvn clean test -Dtest=LoginTest
```

## 📚 Framework Components Quick Reference

### Core Classes & Their Purpose

| Component | Purpose | Key Methods |
|-----------|---------|-------------|
| `ConfigManager` | Configuration management | `getInstance()`, `getBaseUrl()`, `getEmail()` |
| `DriverManager` | WebDriver lifecycle | `initializeDriver()`, `getDriver()`, `quitDriver()` |
| `ElementUtils` | Element interactions | `click()`, `sendKeys()`, `waitForElement()` |
| `BaseTest` | Test foundation | `setUp()`, `tearDown()`, `navigateToBaseUrl()` |
| `BasePage` | Page object base | `isPageLoaded()`, `waitForPageToLoad()` |

### Creating a New Test

```java
public class MyNewTest extends BaseTest {
    
    @Test
    public void testSomething() {
        // 1. Navigate to application
        navigateToBaseUrl();
        
        // 2. Create page objects
        LoginPage loginPage = new LoginPage();
        HomePage homePage = new HomePage();
        
        // 3. Perform actions
        loginPage.performLogin(config.getEmail(), config.getPassword());
        homePage.searchJobs("Java Developer", "Mumbai");
        
        // 4. Add assertions
        Assert.assertTrue(homePage.isPageLoaded());
    }
}
```

### Creating a New Page Object

```java
public class NewPage extends BasePage {
    
    @FindBy(id = "submit-btn")
    private WebElement submitButton;
    
    public void clickSubmit() {
        ElementUtils.click(submitButton);
    }
    
    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(submitButton);
    }
}
```

## 🔧 Common Usage Patterns

### Safe Element Interactions
```java
// Always use ElementUtils for robust interactions
ElementUtils.click(element);              // Waits then clicks
ElementUtils.sendKeys(element, "text");   // Clears then types
ElementUtils.waitForElementToBeVisible(element);
```

### Configuration Access
```java
// Singleton pattern - thread-safe
ConfigManager config = ConfigManager.getInstance();
String baseUrl = config.getBaseUrl();
int timeout = config.getIntProperty("timeout", 10);
```

### Screenshot on Failure
```java
// Automatic via TestListener, or manual
String path = ScreenshotManager.takeScreenshot("test_name");
```

## 🎯 Best Practices

1. **Always extend BaseTest** for test classes
2. **Always extend BasePage** for page objects  
3. **Use ElementUtils** instead of direct WebElement methods
4. **Implement isPageLoaded()** in every page object
5. **Use meaningful test and method names**
6. **Add proper assertions** with descriptive messages

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Driver not found | Check WebDriverManager can download drivers |
| Element not found | Use explicit waits via ElementUtils |
| Login fails | Verify credentials in config.properties |
| Tests hang | Check for missing waits or infinite loops |

## 📈 Next Steps

1. **Add more page objects** for different sections
2. **Create data-driven tests** using TestDataManager
3. **Implement API testing** integration
4. **Add custom reporting** with Allure or ExtentReports
5. **Set up CI/CD pipeline** with Jenkins/GitHub Actions

---
**Need Help?** Check the full documentation in `/docs/ARCHITECTURE.md`