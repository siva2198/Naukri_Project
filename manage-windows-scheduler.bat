@echo off
REM Naukri Automation - Windows Task Scheduler Management Script
REM Manage scheduled tasks for Naukri test execution
REM Author: Sivaraman M

:MENU
echo ==========================================
echo  NAUKRI AUTOMATION TASK MANAGEMENT
echo ==========================================
echo.
echo 1. View scheduled tasks
echo 2. Run morning test now
echo 3. Run afternoon test now
echo 4. Delete all scheduled tasks
echo 5. Recreate scheduled tasks
echo 6. Open Task Scheduler GUI
echo 7. Exit
echo.
set /p choice="Enter your choice (1-7): "

if "%choice%"=="1" goto VIEW_TASKS
if "%choice%"=="2" goto RUN_MORNING
if "%choice%"=="3" goto RUN_AFTERNOON
if "%choice%"=="4" goto DELETE_TASKS
if "%choice%"=="5" goto RECREATE_TASKS
if "%choice%"=="6" goto OPEN_GUI
if "%choice%"=="7" goto EXIT
goto INVALID_CHOICE

:VIEW_TASKS
echo.
echo Current Naukri scheduled tasks:
schtasks /query /tn "Naukri Tests*" /fo table
echo.
pause
goto MENU

:RUN_MORNING
echo.
echo Running morning test now...
schtasks /run /tn "Naukri Tests - Morning"
echo Test execution started. Check logs folder for results.
echo.
pause
goto MENU

:RUN_AFTERNOON
echo.
echo Running afternoon test now...
schtasks /run /tn "Naukri Tests - Afternoon"
echo Test execution started. Check logs folder for results.
echo.
pause
goto MENU

:DELETE_TASKS
echo.
echo Deleting all Naukri scheduled tasks...
schtasks /delete /tn "Naukri Tests - Morning" /f 2>nul
schtasks /delete /tn "Naukri Tests - Afternoon" /f 2>nul
echo ✅ All tasks deleted successfully
echo.
pause
goto MENU

:RECREATE_TASKS
echo.
echo Recreating scheduled tasks...
call setup-windows-scheduler.bat
echo.
pause
goto MENU

:OPEN_GUI
echo.
echo Opening Windows Task Scheduler...
start taskschd.msc
echo.
pause
goto MENU

:INVALID_CHOICE
echo.
echo ❌ Invalid choice. Please enter a number between 1-7.
echo.
pause
goto MENU

:EXIT
echo.
echo Goodbye!
exit /b 0