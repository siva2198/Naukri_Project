# Naukri Framework - Detailed Flowcharts

## 🔄 Main Test Execution Flow

```mermaid
graph TD
    A[Start Test Execution] --> B[Load Configuration]
    B --> C{Config Valid?}
    C -->|No| D[Throw Runtime Exception]
    C -->|Yes| E[Initialize WebDriver]
    E --> F[Set Browser Options]
    F --> G[Navigate to Base URL]
    G --> H[Execute Test Method]
    H --> I{Test Passed?}
    I -->|Yes| J[Log Success]
    I -->|No| K[Take Screenshot]
    K --> L[Log Failure]
    J --> M[Cleanup Resources]
    L --> M
    M --> N[Quit Driver]
    N --> O[End]
    D --> O
```

## 🔐 Login Process Flow

```mermaid
graph TD
    A[Start Login Process] --> B[Check if Login Button Visible]
    B --> C{Button Visible?}
    C -->|No| D[User Already Logged In]
    C -->|Yes| E[Click Login Button]
    E --> F[Wait for Login Modal]
    F --> G[Enter Email]
    G --> H[Enter Password]
    H --> I[Click Submit]
    I --> J[Wait for Response]
    J --> K{Login Successful?}
    K -->|Yes| L[Return True]
    K -->|No| M[Get Error Message]
    M --> N[Return False]
    D --> L
    L --> O[End]
    N --> O
```

## 🏠 Home Page Interaction Flow

```mermaid
graph TD
    A[Home Page Loaded] --> B[Check User Login Status]
    B --> C{User Logged In?}
    C -->|No| D[Redirect to Login]
    C -->|Yes| E[Display Home Page Elements]
    E --> F[User Action]
    F --> G{Action Type?}
    G -->|Search Jobs| H[Enter Skills & Location]
    G -->|Navigate| I[Click Navigation Link]
    G -->|User Menu| J[Open User Menu]
    H --> K[Click Search Button]
    K --> L[Navigate to Results]
    I --> M[Navigate to Section]
    J --> N[Display Menu Options]
    L --> O[End]
    M --> O
    N --> O
    D --> O
```

## 🛠️ Driver Management Flow

```mermaid
graph TD
    A[Driver Request] --> B{Driver Exists?}
    B -->|Yes| C[Return Existing Driver]
    B -->|No| D[Create New Driver]
    D --> E{Browser Type?}
    E -->|Chrome| F[Setup Chrome Options]
    E -->|Firefox| G[Setup Firefox Options]
    F --> H[Create ChromeDriver]
    G --> I[Create FirefoxDriver]
    H --> J[Configure Timeouts]
    I --> J
    J --> K[Maximize Window]
    K --> L[Store in ThreadLocal]
    L --> M[Return Driver]
    C --> N[End]
    M --> N
```

## 📊 Test Data Management Flow

```mermaid
graph TD
    A[Request Test Data] --> B[Construct File Path]
    B --> C{File Exists?}
    C -->|No| D[Throw Exception]
    C -->|Yes| E[Open Excel File]
    E --> F{Sheet Exists?}
    F -->|No| G[Throw Exception]
    F -->|Yes| H[Read Header Row]
    H --> I[Create Column Map]
    I --> J[Read Data Rows]
    J --> K[Convert to Map List]
    K --> L[Return Test Data]
    D --> M[End]
    G --> M
    L --> M
```

## 📸 Screenshot Management Flow

```mermaid
graph TD
    A[Screenshot Request] --> B{Driver Available?}
    B -->|No| C[Log Error & Return]
    B -->|Yes| D[Take Screenshot]
    D --> E[Generate Timestamp]
    E --> F[Create File Name]
    F --> G[Save to Directory]
    G --> H{Save Successful?}
    H -->|Yes| I[Log Success Path]
    H -->|No| J[Log Error]
    I --> K[Return File Path]
    J --> L[Return Null]
    C --> L
    K --> M[End]
    L --> M
```

## 🔧 Element Interaction Flow

```mermaid
graph TD
    A[Element Action Request] --> B{Action Type?}
    B -->|Click| C[Wait for Clickable]
    B -->|SendKeys| D[Wait for Visible]
    B -->|GetText| E[Wait for Visible]
    B -->|Select| F[Wait for Visible]
    C --> G[Perform Click]
    D --> H[Clear & Type Text]
    E --> I[Extract Text]
    F --> J[Select Option]
    G --> K{Action Successful?}
    H --> K
    I --> K
    J --> K
    K -->|Yes| L[Return Success]
    K -->|No| M[Log Error]
    M --> N[Throw Exception]
    L --> O[End]
    N --> O
```

## 🎯 Page Object Lifecycle

```mermaid
graph TD
    A[Page Object Creation] --> B[Initialize WebDriver]
    B --> C[Initialize WebDriverWait]
    C --> D[PageFactory.initElements]
    D --> E[Page Ready]
    E --> F[Method Call]
    F --> G{Method Type?}
    G -->|Action| H[Perform Action]
    G -->|Validation| I[Check Element State]
    G -->|Navigation| J[Navigate to Page]
    H --> K[Update Page State]
    I --> L[Return Status]
    J --> M[Wait for Page Load]
    K --> N[End Method]
    L --> N
    M --> N
    N --> O{More Actions?}
    O -->|Yes| F
    O -->|No| P[Page Cleanup]
    P --> Q[End]
```

## 🧪 Test Suite Execution Flow

```mermaid
graph TD
    A[Suite Start] --> B[Suite Setup]
    B --> C[Global Login]
    C --> D{Login Success?}
    D -->|No| E[Fail Suite]
    D -->|Yes| F[Execute Test Classes]
    F --> G[Run Test Methods]
    G --> H{All Tests Complete?}
    H -->|No| G
    H -->|Yes| I[Suite Teardown]
    I --> J[Cleanup Screenshots]
    J --> K[Close Browser]
    K --> L[Generate Reports]
    L --> M[Suite End]
    E --> M
```

## 📋 Configuration Loading Flow

```mermaid
graph TD
    A[Config Request] --> B{Instance Exists?}
    B -->|Yes| C[Return Existing]
    B -->|No| D[Create New Instance]
    D --> E[Load Properties File]
    E --> F{File Found?}
    F -->|No| G[Throw IOException]
    F -->|Yes| H[Parse Properties]
    H --> I[Validate Required Keys]
    I --> J{All Keys Present?}
    J -->|No| K[Throw Runtime Exception]
    J -->|Yes| L[Store Instance]
    L --> M[Return Config]
    C --> N[End]
    M --> N
    G --> N
    K --> N
```

## 🎨 Method Usage Patterns

### Configuration Usage
```java
// Singleton pattern - always use getInstance()
ConfigManager config = ConfigManager.getInstance();
String url = config.getBaseUrl();
int timeout = config.getIntProperty("timeout", 10);
```

### Driver Usage
```java
// Initialize once per test
WebDriver driver = DriverManager.initializeDriver();
// Use throughout test
WebDriver sameDriver = DriverManager.getDriver();
// Cleanup at end
DriverManager.quitDriver();
```

### Element Interaction
```java
// Safe interactions with built-in waits
ElementUtils.click(element);
ElementUtils.sendKeys(element, "text");
String text = ElementUtils.getText(element);
```

### Page Object Usage
```java
// Create page object
LoginPage loginPage = new LoginPage();
// Verify page loaded
if (loginPage.isPageLoaded()) {
    // Perform actions
    loginPage.performLogin(email, password);
}
```

This comprehensive flowchart documentation helps developers understand:
- **Process Flow**: How each component works internally
- **Decision Points**: Where the framework makes choices
- **Error Handling**: How failures are managed
- **Integration**: How components work together
- **Usage Patterns**: Best practices for using each component