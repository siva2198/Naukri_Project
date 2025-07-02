# Windows Task Scheduler Configuration Guide

## 🕐 Overview

This guide explains how to set up automated daily execution of the SingleSessionLoginTest at 7:00 AM and 3:00 PM using Windows Task Scheduler.

## ⚙️ Setup Instructions

### 1. **Automatic Setup (Recommended)**
```cmd
# Run as Administrator (Right-click Command Prompt → Run as Administrator)
setup-windows-scheduler.bat
```

### 2. **Manual Setup via GUI**
1. Open **Task Scheduler** (Windows + R → `taskschd.msc`)
2. Click **Create Basic Task**
3. Follow the wizard to create tasks

### 3. **Manual Setup via Command Line**
```cmd
# Create morning task (7:00 AM)
schtasks /create /tn "Naukri Tests - Morning" /tr "C:\path\to\run-scheduled-tests.bat" /sc daily /st 07:00

# Create afternoon task (3:00 PM)
schtasks /create /tn "Naukri Tests - Afternoon" /tr "C:\path\to\run-scheduled-tests.bat" /sc daily /st 15:00
```

## 📋 Task Schedule Details

### Task Configuration
- **Task Name**: "Naukri Tests - Morning" / "Naukri Tests - Afternoon"
- **Trigger**: Daily at 7:00 AM and 3:00 PM
- **Action**: Run `run-scheduled-tests.bat`
- **User Account**: Current user or SYSTEM account

### Security Context
- **Run whether user is logged on or not**: ✅
- **Run with highest privileges**: ✅ (if needed)
- **Hidden**: ❌ (for debugging visibility)

## 🚀 Execution Flow

### Daily Execution Process
```
7:00 AM / 3:00 PM
    ↓
Windows Task Scheduler triggers
    ↓
Executes run-scheduled-tests.bat
    ↓
Runs: mvn clean test -DsuiteXmlFile=single-session-login-only.xml
    ↓
Generates Allure report: mvn allure:report
    ↓
Saves execution log with timestamp
    ↓
Task completion notification
```

## 🔧 Management Commands

### View Tasks
```cmd
# List all Naukri tasks
schtasks /query /tn "Naukri Tests*"

# Detailed view
schtasks /query /tn "Naukri Tests - Morning" /v /fo list
```

### Run Tasks Manually
```cmd
# Run morning test now
schtasks /run /tn "Naukri Tests - Morning"

# Run afternoon test now
schtasks /run /tn "Naukri Tests - Afternoon"
```

### Delete Tasks
```cmd
# Delete specific task
schtasks /delete /tn "Naukri Tests - Morning" /f

# Delete all Naukri tasks
schtasks /delete /tn "Naukri Tests*" /f
```

### Modify Tasks
```cmd
# Change schedule time
schtasks /change /tn "Naukri Tests - Morning" /st 08:00

# Enable/Disable task
schtasks /change /tn "Naukri Tests - Morning" /enable
schtasks /change /tn "Naukri Tests - Morning" /disable
```

## 📁 File Structure

### Generated Files
```
project/
├── logs/
│   ├── scheduled-test-20250103_070001.log
│   ├── scheduled-test-20250103_150001.log
│   └── ...
├── target/
│   ├── allure-results/
│   └── site/allure-maven-plugin/
├── setup-windows-scheduler.bat
├── manage-windows-scheduler.bat
└── run-scheduled-tests.bat
```

## 🐛 Troubleshooting

### Common Issues

1. **Task Not Running**
   ```cmd
   # Check task status
   schtasks /query /tn "Naukri Tests - Morning"
   
   # Check task history in Task Scheduler GUI
   taskschd.msc
   ```

2. **Permission Issues**
   - Run setup script as Administrator
   - Ensure user has "Log on as a batch job" rights
   - Check file permissions on batch scripts

3. **Path Issues**
   ```cmd
   # Use absolute paths in task creation
   schtasks /create /tn "TaskName" /tr "C:\full\path\to\script.bat"
   ```

4. **Java/Maven Not Found**
   - Add Java and Maven to system PATH
   - Or specify full paths in run-scheduled-tests.bat

### Debug Steps
1. **Test Script Manually**:
   ```cmd
   run-scheduled-tests.bat
   ```

2. **Check Task History**:
   - Open Task Scheduler GUI
   - Navigate to task → History tab
   - Review execution logs

3. **Enable Task Logging**:
   ```cmd
   # Enable task scheduler logging
   wevtutil sl Microsoft-Windows-TaskScheduler/Operational /e:true
   ```

## 📧 Notifications (Optional)

### Email Notifications via PowerShell
Add to `run-scheduled-tests.bat`:
```cmd
if %ERRORLEVEL% EQU 0 (
    powershell -Command "Send-MailMessage -To 'admin@company.com' -From 'naukri-tests@company.com' -Subject 'Naukri Tests - Success' -Body 'Tests completed successfully' -SmtpServer 'smtp.company.com'"
) else (
    powershell -Command "Send-MailMessage -To 'admin@company.com' -From 'naukri-tests@company.com' -Subject 'Naukri Tests - Failed' -Body 'Tests failed' -SmtpServer 'smtp.company.com'"
)
```

## 🔄 Alternative: PowerShell Scheduled Jobs

### Using PowerShell (Alternative approach)
```powershell
# Register scheduled job
Register-ScheduledJob -Name "NaukriMorningTests" -ScriptBlock {
    Set-Location "C:\path\to\project"
    & ".\run-scheduled-tests.bat"
} -Trigger (New-JobTrigger -Daily -At "7:00 AM")
```

## 🎯 Best Practices

1. **Run as Administrator** for initial setup
2. **Use absolute paths** in all configurations
3. **Test manually first** before scheduling
4. **Monitor task history** regularly
5. **Set up logging** for troubleshooting
6. **Use SYSTEM account** for unattended execution

This Windows Task Scheduler setup provides the same functionality as cron on Linux/Unix systems, allowing your Naukri tests to run automatically twice daily.