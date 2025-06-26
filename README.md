# Naukri Automation Framework

A robust and scalable test automation framework for Naukri.com built with Selenium WebDriver, TestNG, and Java.

## 🏗️ Framework Architecture

### Core Components

- **Config Management**: Centralized configuration using `ConfigManager`
- **Driver Management**: Thread-safe WebDriver management with `DriverManager`
- **Page Object Model**: Clean separation of page elements and actions
- **Utility Classes**: Reusable utilities for common operations
- **Test Data Management**: Excel-based test data handling
- **Reporting**: Enhanced reporting with screenshots and logs

### Project Structure

```
src/
├── main/java/
│   ├── config/
│   │   └── ConfigManager.java          # Configuration management
│   ├── driver/
│   │   └── DriverManager.java          # WebDriver management
│   ├── listeners/
│   │   └── TestListener.java           # TestNG listeners
│   └── utils/
│       ├── ElementUtils.java           # WebElement utilities
│       ├── ScreenshotManager.java      # Screenshot management
│       └── TestDataManager.java        # Test data handling
└── test/java/
    ├── base/
    │   ├── BaseTest.java               # Base test class
    │   └── TestSuiteManager.java       # Suite-level setup/teardown
    ├── pages/
    │   ├── BasePage.java               # Base page class
    │   ├── LoginPage.java              # Login page object
    │   ├── HomePage.java               # Home page object
    │   └── ProfilePage.java            # Profile page object
    └── tests/
        ├── LoginTest.java              # Login test cases
        └── HomePageTest.java           # Home page test cases
```

## 🚀 Key Features

### 1. Enhanced Configuration Management
- Singleton pattern for configuration
- Property validation
- Type-safe property access
- Default value support

### 2. Robust Driver Management
- Thread-safe WebDriver handling
- Multiple browser support (Chrome, Firefox)
- Automatic driver setup with WebDriverManager
- Configurable browser options

### 3. Advanced Element Utilities
- Smart waits and element interactions
- JavaScript execution support
- Scroll and hover operations
- Dropdown handling

### 4. Comprehensive Screenshot Management
- Automatic screenshots on test failures
- Timestamped screenshot files
- Old screenshot cleanup
- Organized screenshot directory structure

### 5. Page Object Model Implementation
- Base page class with common functionality
- Clean separation of concerns
- Reusable page components
- Page load validation

### 6. Test Data Management
- Excel file support
- Dynamic test data loading
- Parameterized test execution
- Data-driven testing capabilities

## 🛠️ Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Chrome/Firefox browser

### Installation
1. Clone the repository
2. Update `config.properties` with your credentials
3. Run tests using Maven:
   ```bash
   mvn clean test
   ```

### Configuration
Update `config.properties` file:
```properties
baseURL=https://www.naukri.com/
email=your-email@example.com
password=your-password
browser=chrome
headless=false
implicit.wait=10
explicit.wait=15
```

## 🧪 Running Tests

### Command Line Execution
```bash
# Run all tests
mvn clean test

# Run specific test class
mvn clean test -Dtest=LoginTest

# Run with specific browser
mvn clean test -Dbrowser=firefox

# Run in headless mode
mvn clean test -Dheadless=true
```

### TestNG XML Execution
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## 📊 Reporting

- **Screenshots**: Automatically captured on test failures
- **Console Logs**: Detailed execution logs with emojis
- **TestNG Reports**: Standard TestNG HTML reports
- **Screenshot Cleanup**: Automatic cleanup of old screenshots

## 🔧 Customization

### Adding New Page Objects
1. Extend `BasePage` class
2. Implement `isPageLoaded()` method
3. Add page-specific elements and methods

### Adding New Test Data
1. Create Excel files in `src/test/resources/testdata/`
2. Use `TestDataManager` to load data
3. Implement data providers in test classes

### Browser Configuration
- Modify `DriverManager` to add new browser support
- Update configuration properties
- Add browser-specific options

## 🤝 Best Practices

1. **Page Objects**: Keep page objects focused and cohesive
2. **Test Data**: Use external data sources for test parameters
3. **Waits**: Use explicit waits over implicit waits
4. **Screenshots**: Automatic capture on failures
5. **Logging**: Comprehensive logging for debugging
6. **Configuration**: Externalize all configuration parameters

## 🐛 Troubleshooting

### Common Issues
1. **Driver Issues**: Ensure WebDriverManager can download drivers
2. **Element Not Found**: Check selectors and wait conditions
3. **Login Failures**: Verify credentials in config.properties
4. **Screenshot Failures**: Check directory permissions

### Debug Mode
Enable debug logging by adding to your test execution:
```bash
mvn clean test -Dlog4j.configuration=debug
```

## 📈 Future Enhancements

- [ ] API testing integration
- [ ] Database validation support
- [ ] CI/CD pipeline integration
- [ ] Advanced reporting with Allure
- [ ] Mobile testing support
- [ ] Performance testing capabilities

## 👥 Contributing

1. Follow the existing code structure
2. Add appropriate tests for new features
3. Update documentation
4. Ensure all tests pass before submitting

---

**Author**: Sivaraman M  
**Framework Version**: 2.0  
**Last Updated**: 2025