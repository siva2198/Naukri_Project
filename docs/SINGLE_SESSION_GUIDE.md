# Single Session Test Execution Guide

## 🎯 Overview

This framework modification supports executing all test cases in a single browser session with one login/logout cycle. This approach is more efficient and realistic for limited test scenarios.

## 🔄 Execution Flow

```
┌─────────────────┐
│   Suite Start   │
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Initialize      │ ──► SessionManager.initializeSession()
│ Session & Login │     • Opens browser
└─────────┬───────┘     • Navigates to Naukri
          │             • Performs login once
          ▼
┌─────────────────┐
│ Execute Test 1  │ ──► Login Status Verification
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 2  │ ──► Session Persistence Check
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 3  │ ──► Job Search Functionality
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 4  │ ──► Skills-Only Search
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 5  │ ──► Jobs Navigation
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 6  │ ──► Companies Navigation
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 7  │ ──► Trending Searches
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Execute Test 8  │ ──► User Menu Access
└─────────┬───────┘
          │
          ▼
┌─────────────────┐
│ Logout & Close  │ ──► SessionManager.terminateSession()
│ Browser         │     • Performs logout
└─────────┬───────┘     • Closes browser
          │             • Cleans up resources
          ▼
┌─────────────────┐
│   Suite End     │
└─────────────────┘
```

## 🚀 How to Run

### Option 1: Using Batch File (Windows)
```bash
run-single-session-tests.bat
```

### Option 2: Using Shell Script (Linux/Mac)
```bash
./run-single-session-tests.sh
```

### Option 3: Using Maven Command
```bash
mvn clean test -DsuiteXmlFile=single-session-testng.xml
```

## 📋 Test Cases Included

| Priority | Test Name | Description |
|----------|-----------|-------------|
| 1 | `testUserLoggedInStatus` | Verify user is logged in and home page is accessible |
| 2 | `testSessionPersistence` | Verify login session persists across navigation |
| 3 | `testJobSearch` | Verify job search with skills and location |
| 4 | `testSkillsOnlySearch` | Verify job search with skills only |
| 5 | `testJobsNavigation` | Verify navigation to Jobs section |
| 6 | `testCompaniesNavigation` | Verify navigation to Companies section |
| 7 | `testTrendingSearches` | Verify trending searches functionality |
| 8 | `testUserMenuAccess` | Verify user menu accessibility |

## 🔧 Key Components

### SessionManager
- **Purpose**: Manages single login session across all tests
- **Key Methods**:
  - `initializeSession()`: Login once at suite start
  - `navigateToHome()`: Return to home page between tests
  - `terminateSession()`: Logout and cleanup at suite end

### SingleSessionBaseTest
- **Purpose**: Base class for all single-session tests
- **Features**:
  - Ensures session is active before each test
  - Provides navigation utilities
  - Maintains session state

### SingleSessionSuiteManager
- **Purpose**: Manages suite-level setup and teardown
- **Features**:
  - Initializes session at suite start
  - Terminates session at suite end
  - Provides execution summary

## ✅ Benefits

1. **Efficiency**: Login only once instead of 8 times
2. **Speed**: Faster test execution (no repeated login/logout)
3. **Realistic**: Mimics real user behavior
4. **Resource Saving**: Single browser instance
5. **Session Testing**: Can test session persistence

## 🔍 Console Output Example

```
🏁 Starting Single Session Test Suite
📋 Test Execution Plan:
   1. Login once at the beginning
   2. Execute all test cases sequentially
   3. Logout at the end
   4. Close browser and cleanup

🚀 Initializing new test session...
✅ Session initialized successfully - User logged in
🏠 Home page loaded successfully

🚀 Starting test: testUserLoggedInStatus
✅ Test 1: User login status verified
✅ Test passed: testUserLoggedInStatus

🚀 Starting test: testSessionPersistence
✅ Test 2: Session persistence verified
✅ Test passed: testSessionPersistence

... (continuing for all 8 tests)

🔚 Terminating session...
👋 User logged out successfully
✅ Session terminated successfully

🎯 Single Session Test Suite Execution Summary:
   ✓ Single login/logout cycle completed
   ✓ All test cases executed in sequence
   ✓ Browser closed and resources cleaned up
```

## 🐛 Troubleshooting

| Issue | Solution |
|-------|----------|
| Session initialization fails | Check login credentials in config.properties |
| Tests fail after navigation | Ensure `navigateToHome()` is called between tests |
| Logout doesn't work | Update logout selectors in HomePage.java |
| Session state lost | Check if website has session timeout |

## 🔄 Adding New Tests

To add a new test case:

1. **Extend SingleSessionBaseTest**:
```java
public class MyNewTest extends SingleSessionBaseTest {
    @Test(priority = 9, description = "My new test")
    public void testNewFeature() {
        // Your test logic here
        // Session is already active
        // Return to home page at the end if needed
        sessionManager.navigateToHome();
    }
}
```

2. **Add to TestNG XML**:
```xml
<class name="tests.MyNewTest"/>
```

3. **Update priority numbers** to maintain execution order

This single-session approach is perfect for your requirement of executing a limited number of test cases efficiently with one login/logout cycle.