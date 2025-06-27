@echo off
REM Naukri Automation - Scheduled Test Execution Script (Windows)
REM Runs SingleSessionLoginTest with Allure reporting
REM Author: Sivaraman M

echo ==========================================
echo  NAUKRI SCHEDULED TEST EXECUTION
echo ==========================================
echo Execution Time: %date% %time%
echo Test Suite: SingleSessionLoginTest
echo Reporting: Allure Reports
echo.

REM Set project directory (update this path)
set PROJECT_DIR=C:\path\to\your\naukri-automation-framework
cd /d "%PROJECT_DIR%"

REM Create logs directory if it doesn't exist
if not exist logs mkdir logs

REM Generate log filename with timestamp
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"
set "LOG_FILE=logs\scheduled-test-%YYYY%%MM%%DD%_%HH%%Min%%Sec%.log"

echo Starting test execution... >> "%LOG_FILE%"
echo Starting test execution...

REM Run tests with Allure reporting
mvn clean test -DsuiteXmlFile=single-session-login-only.xml >> "%LOG_FILE%" 2>&1

if %ERRORLEVEL% EQU 0 (
    echo Tests completed successfully >> "%LOG_FILE%"
    echo Tests completed successfully
    
    REM Generate Allure report
    echo Generating Allure report... >> "%LOG_FILE%"
    echo Generating Allure report...
    mvn allure:report >> "%LOG_FILE%" 2>&1
    
    echo Allure report generated at: target\site\allure-maven-plugin\index.html >> "%LOG_FILE%"
    echo Allure report generated at: target\site\allure-maven-plugin\index.html
) else (
    echo Tests failed >> "%LOG_FILE%"
    echo Tests failed
    
    REM Still generate report to see failures
    mvn allure:report >> "%LOG_FILE%" 2>&1
)

echo Execution completed at: %date% %time% >> "%LOG_FILE%"
echo Log file: %LOG_FILE%
echo ==========================================
pause