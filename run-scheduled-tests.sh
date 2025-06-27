#!/bin/bash

# Naukri Automation - Scheduled Test Execution Script
# Runs SingleSessionLoginTest with Allure reporting
# Author: Sivaraman M

echo "=========================================="
echo " NAUKRI SCHEDULED TEST EXECUTION"
echo "=========================================="
echo "Execution Time: $(date)"
echo "Test Suite: SingleSessionLoginTest"
echo "Reporting: Allure Reports"
echo ""

# Set project directory
PROJECT_DIR="/path/to/your/naukri-automation-framework"
cd "$PROJECT_DIR" || exit 1

# Create logs directory if it doesn't exist
mkdir -p logs

# Generate log filename with timestamp
LOG_FILE="logs/scheduled-test-$(date +%Y%m%d_%H%M%S).log"

echo "Starting test execution..." | tee -a "$LOG_FILE"

# Run tests with Allure reporting
mvn clean test -DsuiteXmlFile=single-session-login-only.xml 2>&1 | tee -a "$LOG_FILE"

# Check if tests passed
if [ ${PIPESTATUS[0]} -eq 0 ]; then
    echo "✅ Tests completed successfully" | tee -a "$LOG_FILE"
    
    # Generate Allure report
    echo "Generating Allure report..." | tee -a "$LOG_FILE"
    mvn allure:report 2>&1 | tee -a "$LOG_FILE"
    
    # Serve Allure report (optional - comment out for headless servers)
    # mvn allure:serve &
    
    echo "📊 Allure report generated at: target/site/allure-maven-plugin/index.html" | tee -a "$LOG_FILE"
else
    echo "❌ Tests failed" | tee -a "$LOG_FILE"
    
    # Still generate report to see failures
    mvn allure:report 2>&1 | tee -a "$LOG_FILE"
fi

echo "Execution completed at: $(date)" | tee -a "$LOG_FILE"
echo "Log file: $LOG_FILE"
echo "=========================================="