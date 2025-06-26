@echo off
echo ========================================
echo  NAUKRI SINGLE SESSION TEST EXECUTION
echo ========================================
echo.
echo This will:
echo 1. Login once at the beginning
echo 2. Execute all 8 test cases sequentially  
echo 3. Logout at the end
echo 4. Close browser and cleanup
echo.
echo Starting test execution...
echo.

mvn clean test -DsuiteXmlFile=single-session-testng.xml

echo.
echo ========================================
echo  TEST EXECUTION COMPLETED
echo ========================================
pause