@echo off
REM Naukri Automation - Windows Task Scheduler Setup Script
REM Sets up scheduled tasks to run tests at 7:00 AM and 3:00 PM daily
REM Author: Sivaraman M

echo ==========================================
echo  NAUKRI AUTOMATION WINDOWS SCHEDULER SETUP
echo ==========================================

REM Get current directory
set CURRENT_DIR=%cd%
set SCRIPT_PATH=%CURRENT_DIR%\run-scheduled-tests.bat

echo Setting up Windows scheduled tasks for daily test execution...
echo Schedule: 7:00 AM and 3:00 PM daily
echo Script: %SCRIPT_PATH%
echo.

REM Create scheduled task for 7:00 AM
echo Creating 7:00 AM daily task...
schtasks /create /tn "Naukri Tests - Morning" /tr "%SCRIPT_PATH%" /sc daily /st 07:00 /f
if %ERRORLEVEL% EQU 0 (
    echo ✅ Morning task created successfully
) else (
    echo ❌ Failed to create morning task
)

REM Create scheduled task for 3:00 PM
echo Creating 3:00 PM daily task...
schtasks /create /tn "Naukri Tests - Afternoon" /tr "%SCRIPT_PATH%" /sc daily /st 15:00 /f
if %ERRORLEVEL% EQU 0 (
    echo ✅ Afternoon task created successfully
) else (
    echo ❌ Failed to create afternoon task
)

echo.
echo ✅ Windows Task Scheduler setup completed!
echo.
echo 📋 Schedule Summary:
echo    • 7:00 AM daily  - Naukri Tests - Morning
echo    • 3:00 PM daily  - Naukri Tests - Afternoon
echo.
echo 📁 Logs will be saved in: %CURRENT_DIR%\logs\
echo 📊 Allure reports will be generated in: %CURRENT_DIR%\target\site\allure-maven-plugin\
echo.
echo 🔧 To manage tasks:
echo    • View tasks: schtasks /query /tn "Naukri Tests*"
echo    • Delete tasks: schtasks /delete /tn "Naukri Tests - Morning" /f
echo    • Run task now: schtasks /run /tn "Naukri Tests - Morning"
echo.
echo 📝 You can also manage tasks through:
echo    Windows Settings → System → About → Advanced system settings → Task Scheduler
echo ==========================================
pause