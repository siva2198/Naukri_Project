# Allure Reporting Configuration Guide

## 🎯 Overview

This guide explains how to use Allure reporting with the Naukri Automation Framework, specifically configured for the SingleSessionLoginTest.

## 📊 Allure Features Implemented

### 1. **Test Organization**
- **@Epic**: Groups tests by major functionality (User Profile Management)
- **@Feature**: Groups tests by specific features (Resume Management)
- **@Story**: Describes individual test scenarios
- **@Description**: Detailed test descriptions
- **@Severity**: Test importance levels (CRITICAL, NORMAL, MINOR)

### 2. **Step-by-Step Reporting**
- **Allure.step()**: Documents each test step
- **Screenshots**: Automatic attachment on key actions
- **Detailed Logs**: Comprehensive execution tracking

### 3. **Rich Attachments**
- Screenshots automatically attached to reports
- Test execution logs
- Error details and stack traces

## 🚀 Running Tests with Allure

### Generate Test Results
```bash
# Run tests and generate Allure results
mvn clean test -DsuiteXmlFile=single-session-login-only.xml
```

### Generate Allure Report
```bash
# Generate HTML report
mvn allure:report

# Serve report in browser (opens automatically)
mvn allure:serve
```

### View Reports
- **Local Report**: `target/site/allure-maven-plugin/index.html`
- **Live Server**: `http://localhost:port` (when using allure:serve)

## 📋 Report Structure

### Test Execution Overview
```
Epic: Naukri User Profile Management
├── Feature: Resume Management
│   ├── Story: User Authentication Verification
│   │   └── Test: testUserLoggedInStatus
│   ├── Story: Session Management
│   │   └── Test: testSessionPersistence
│   ├── Story: Resume Headline Management
│   │   └── Test: testResumeHeadlineUpdate
│   └── Story: Resume File Management
│       └── Test: testResumeUpdate
```

### Test Details Include
- **Execution Time**: Start/end timestamps
- **Test Steps**: Detailed step-by-step execution
- **Screenshots**: Visual evidence of test execution
- **Parameters**: Test input data
- **Attachments**: Logs, screenshots, files
- **History**: Trend analysis across executions

## 🔧 Configuration Files

### Maven Configuration (pom.xml)
- Allure TestNG integration
- AspectJ weaver for step reporting
- Surefire plugin configuration
- Allure Maven plugin

### TestNG Configuration (single-session-login-only.xml)
- Allure TestNG listener
- Test execution order
- Suite configuration

### Allure Properties (allure.properties)
- Results directory configuration
- Link patterns for issue tracking
- Custom configurations

## 📈 Advanced Features

### Custom Annotations
```java
@Epic("Major Feature Area")
@Feature("Specific Functionality")
@Story("User Scenario")
@Description("Detailed test description")
@Severity(SeverityLevel.CRITICAL)
```

### Step Documentation
```java
Allure.step("Navigate to profile page");
Allure.step("Click edit button");
Allure.step("Verify success message");
```

### Screenshot Attachments
```java
Allure.addAttachment("Screenshot Name", "image/png", 
    Files.readAllBytes(Paths.get(screenshotPath)));
```

## 📊 Report Analysis

### Dashboard Metrics
- **Total Tests**: Number of executed tests
- **Pass Rate**: Success percentage
- **Execution Time**: Total and average duration
- **Flaky Tests**: Tests with inconsistent results

### Detailed Views
- **Suites**: Test organization by suites
- **Graphs**: Visual representation of results
- **Timeline**: Execution timeline
- **Behaviors**: BDD-style test organization
- **Packages**: Code organization view

## 🛠️ Troubleshooting

### Common Issues

1. **Allure Results Not Generated**
   ```bash
   # Check if results directory exists
   ls -la target/allure-results/
   
   # Verify AspectJ weaver is working
   mvn clean test -X | grep aspectj
   ```

2. **Report Not Opening**
   ```bash
   # Generate report manually
   mvn allure:report
   
   # Check report location
   ls -la target/site/allure-maven-plugin/
   ```

3. **Screenshots Not Attached**
   - Verify screenshot path is correct
   - Check file permissions
   - Ensure screenshot is taken before attachment

### Debug Commands
```bash
# Verbose test execution
mvn clean test -X -DsuiteXmlFile=single-session-login-only.xml

# Check Allure version
mvn allure:help

# Validate TestNG configuration
mvn test -DsuiteXmlFile=single-session-login-only.xml -Dverbose=true
```

## 📁 File Structure
```
project/
├── target/
│   ├── allure-results/          # Raw test results
│   └── site/
│       └── allure-maven-plugin/ # Generated HTML reports
├── allure.properties            # Allure configuration
├── single-session-login-only.xml # TestNG suite
└── src/test/java/tests/
    └── SingleSessionLoginTest.java # Test with Allure annotations
```

This configuration provides comprehensive test reporting with visual evidence, detailed steps, and historical tracking for your SingleSessionLoginTest execution.