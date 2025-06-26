# Naukri Automation Framework - Architecture Guide

## 🏗️ Framework Architecture Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                    NAUKRI AUTOMATION FRAMEWORK                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐         │
│  │   CONFIG    │    │   DRIVER    │    │   UTILS     │         │
│  │             │    │             │    │             │         │
│  │ Properties  │◄───┤ WebDriver   │◄───┤ Element     │         │
│  │ Management  │    │ Management  │    │ Operations  │         │
│  │             │    │             │    │             │         │
│  └─────────────┘    └─────────────┘    └─────────────┘         │
│         │                   │                   │              │
│         └───────────────────┼───────────────────┘              │
│                             │                                  │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                    TEST LAYER                           │   │
│  │                                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐  │   │
│  │  │    BASE     │    │    PAGES    │    │    TESTS    │  │   │
│  │  │             │    │             │    │             │  │   │
│  │  │ BaseTest    │◄───┤ Page Object │◄───┤ Test Cases  │  │   │
│  │  │ Setup/      │    │ Model       │    │ Execution   │  │   │
│  │  │ Teardown    │    │             │    │             │  │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘  │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                 REPORTING LAYER                         │   │
│  │                                                         │   │
│  │  ┌─────────────┐    ┌─────────────┐    ┌─────────────┐  │   │
│  │  │ LISTENERS   │    │ SCREENSHOTS │    │ TEST DATA   │  │   │
│  │  │             │    │             │    │             │  │   │
│  │  │ Test Events │    │ Failure     │    │ Excel       │  │   │
│  │  │ Handling    │    │ Capture     │    │ Management  │  │   │
│  │  └─────────────┘    └─────────────┘    └─────────────┘  │   │
│  └─────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## 🔄 Test Execution Flow

```
START
  │
  ▼
┌─────────────────┐
│ Load Config     │ ──► ConfigManager.getInstance()
│ Properties      │     • Validates required properties
└─────────────────┘     • Loads from config.properties
  │
  ▼
┌─────────────────┐
│ Initialize      │ ──► DriverManager.initializeDriver()
│ WebDriver       │     • Sets up browser (Chrome/Firefox)
└─────────────────┘     • Configures timeouts & options
  │
  ▼
┌─────────────────┐
│ Navigate to     │ ──► BaseTest.navigateToBaseUrl()
│ Base URL        │     • Opens Naukri.com
└─────────────────┘     • Waits for page load
  │
  ▼
┌─────────────────┐
│ Execute Test    │ ──► Test Methods
│ Methods         │     • Login, Search, Navigation
└─────────────────┘     • Uses Page Object Model
  │
  ▼
┌─────────────────┐
│ Handle Results  │ ──► TestListener
│ & Screenshots   │     • Success/Failure logging
└─────────────────┘     • Screenshot on failure
  │
  ▼
┌─────────────────┐
│ Cleanup &       │ ──► DriverManager.quitDriver()
│ Teardown        │     • Close browser
└─────────────────┘     • Clean resources
  │
  ▼
END
```

## 📦 Package Structure & Components

### 1. **CONFIG PACKAGE** (`src/main/java/config/`)

#### ConfigManager.java
```java
Purpose: Centralized configuration management
Methods:
├── getInstance()           → Singleton instance
├── getProperty(key)        → Get string property
├── getIntProperty(key)     → Get integer property
├── getBooleanProperty(key) → Get boolean property
├── getBaseUrl()           → Get application URL
├── getEmail()             → Get login email
└── getPassword()          → Get login password

Usage: ConfigManager.getInstance().getBaseUrl()
```

### 2. **DRIVER PACKAGE** (`src/main/java/driver/`)

#### DriverManager.java
```java
Purpose: Thread-safe WebDriver management
Methods:
├── initializeDriver()           → Create & configure driver
├── initializeDriver(browserType)→ Create specific browser
├── getDriver()                  → Get current driver instance
├── getWait()                    → Get WebDriverWait instance
├── quitDriver()                 → Close & cleanup driver
└── isDriverInitialized()        → Check driver status

Usage: DriverManager.getDriver().get("https://naukri.com")
```

### 3. **UTILS PACKAGE** (`src/main/java/utils/`)

#### ElementUtils.java
```java
Purpose: Enhanced WebElement operations
Methods:
├── click(element)                    → Safe click with wait
├── sendKeys(element, text)           → Clear & type text
├── getText(element)                  → Get element text
├── isDisplayed(element)              → Check visibility
├── selectByVisibleText(dropdown)     → Dropdown selection
├── hoverOver(element)                → Mouse hover action
├── scrollToElement(element)          → Scroll to element
├── waitForElementToBeVisible()       → Explicit wait
├── waitForElementToBeClickable()     → Wait for clickable
└── waitForPageToLoad()               → Wait for page ready

Usage: ElementUtils.click(loginButton)
```

#### ScreenshotManager.java
```java
Purpose: Screenshot capture & management
Methods:
├── takeScreenshot(testName)          → Capture screenshot
├── takeScreenshotOnFailure(testName) → Failure screenshot
└── cleanupOldScreenshots(days)       → Remove old files

Usage: ScreenshotManager.takeScreenshot("login_test")
```

#### TestDataManager.java
```java
Purpose: Excel test data handling
Methods:
├── getTestData(fileName, sheetName)  → Get test data array
├── readExcelData(fileName, sheet)    → Read Excel to Map
└── getCellValueAsString(cell)        → Convert cell to string

Usage: TestDataManager.getTestData("login_data.xlsx", "LoginData")
```

### 4. **BASE PACKAGE** (`src/test/java/base/`)

#### BaseTest.java
```java
Purpose: Common test setup & teardown
Methods:
├── setUp(browserName)     → Initialize driver & config
├── tearDown()             → Cleanup resources
├── navigateToBaseUrl()    → Go to main URL
└── navigateToUrl(url)     → Go to specific URL

Usage: Extend this class for all test classes
```

#### TestSuiteManager.java
```java
Purpose: Suite-level operations
Methods:
├── suiteSetup()           → Global login & initialization
└── suiteTeardown()        → Cleanup & screenshot management

Usage: Add to TestNG suite for global setup
```

### 5. **PAGES PACKAGE** (`src/test/java/pages/`)

#### BasePage.java
```java
Purpose: Common page functionality
Methods:
├── isPageLoaded()         → Abstract method for validation
├── getCurrentUrl()        → Get current page URL
├── getPageTitle()         → Get page title
├── refreshPage()          → Refresh current page
└── waitForPageToLoad()    → Wait for page ready

Usage: Extend for all page object classes
```

#### LoginPage.java
```java
Purpose: Login page interactions
Methods:
├── performLogin(email, password)     → Complete login flow
├── clickLoginButton()                → Click login button
├── enterCredentials(email, pass)     → Fill login form
├── isUserLoggedIn()                  → Check login status
└── getErrorMessage()                 → Get error text

Usage: LoginPage loginPage = new LoginPage();
       loginPage.performLogin("email", "password");
```

#### HomePage.java
```java
Purpose: Home page operations
Methods:
├── searchJobs(skills, location)      → Job search
├── clickJobsLink()                   → Navigate to jobs
├── clickCompaniesLink()              → Navigate to companies
├── openUserMenu()                    → Access user menu
├── getTrendingSearches()             → Get trending list
└── isUserLoggedIn()                  → Check login status

Usage: HomePage homePage = new HomePage();
       homePage.searchJobs("Java", "Bangalore");
```

### 6. **TESTS PACKAGE** (`src/test/java/tests/`)

#### LoginTest.java
```java
Purpose: Login functionality testing
Test Methods:
├── testValidLogin()       → Test successful login
├── testInvalidLogin()     → Test login failure
└── testEmptyCredentials() → Test empty fields

Usage: Run via TestNG or Maven
```

#### HomePageTest.java
```java
Purpose: Home page functionality testing
Test Methods:
├── testJobSearch()            → Test job search
├── testJobsNavigation()       → Test navigation
├── testTrendingSearches()     → Test trending features
└── testUserMenuAccess()       → Test user menu

Usage: Run via TestNG or Maven
```

## 🔗 Component Interactions

```
Test Execution Flow:
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Test Class  │───►│ Base Test   │───►│ Config Mgr  │
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       ▼                   ▼                   ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│ Page Object │───►│ Driver Mgr  │───►│ Element     │
└─────────────┘    └─────────────┘    │ Utils       │
       │                   │          └─────────────┘
       ▼                   ▼                   │
┌─────────────┐    ┌─────────────┐            ▼
│ Test Data   │    │ Screenshot  │    ┌─────────────┐
│ Manager     │    │ Manager     │    │ Test        │
└─────────────┘    └─────────────┘    │ Listener    │
                                      └─────────────┘
```

## 🚀 Usage Examples

### Basic Test Creation
```java
public class MyTest extends BaseTest {
    @Test
    public void testExample() {
        navigateToBaseUrl();
        LoginPage loginPage = new LoginPage();
        loginPage.performLogin(config.getEmail(), config.getPassword());
        
        HomePage homePage = new HomePage();
        homePage.searchJobs("Java Developer", "Mumbai");
    }
}
```

### Adding New Page Object
```java
public class NewPage extends BasePage {
    @FindBy(id = "element-id")
    private WebElement element;
    
    public void performAction() {
        ElementUtils.click(element);
    }
    
    @Override
    public boolean isPageLoaded() {
        return ElementUtils.isDisplayed(element);
    }
}
```

This architecture ensures:
- **Separation of Concerns**: Each component has a specific responsibility
- **Reusability**: Utilities and base classes can be reused
- **Maintainability**: Clear structure makes updates easy
- **Scalability**: Easy to add new pages and tests
- **Reliability**: Robust error handling and waits