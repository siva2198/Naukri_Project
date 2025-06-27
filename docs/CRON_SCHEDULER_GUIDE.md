# Cron Scheduler Configuration Guide

## 🕐 Overview

This guide explains how to set up automated daily execution of the SingleSessionLoginTest at 7:00 AM and 3:00 PM using cron scheduling.

## ⚙️ Setup Instructions

### 1. **Automatic Setup (Recommended)**
```bash
# Make setup script executable and run
chmod +x setup-cron-scheduler.sh
./setup-cron-scheduler.sh
```

### 2. **Manual Setup**
```bash
# Edit crontab
crontab -e

# Add these lines:
# Naukri Automation Framework - Daily Test Execution
# Run SingleSessionLoginTest at 7:00 AM daily
0 7 * * * /path/to/your/project/run-scheduled-tests.sh
# Run SingleSessionLoginTest at 3:00 PM daily
0 15 * * * /path/to/your/project/run-scheduled-tests.sh
```

## 📋 Cron Schedule Explanation

### Cron Format
```
* * * * * command
│ │ │ │ │
│ │ │ │ └─── Day of week (0-7, Sunday = 0 or 7)
│ │ │ └───── Month (1-12)
│ │ └─────── Day of month (1-31)
│ └───────── Hour (0-23)
└─────────── Minute (0-59)
```

### Our Schedule
- **`0 7 * * *`**: Every day at 7:00 AM
- **`0 15 * * *`**: Every day at 3:00 PM (15:00 in 24-hour format)

## 🚀 Execution Flow

### Daily Execution Process
```
7:00 AM / 3:00 PM
    ↓
Cron triggers run-scheduled-tests.sh
    ↓
Script navigates to project directory
    ↓
Executes: mvn clean test -DsuiteXmlFile=single-session-login-only.xml
    ↓
Generates Allure report: mvn allure:report
    ↓
Saves execution log with timestamp
    ↓
Completion notification
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
├── run-scheduled-tests.sh
└── setup-cron-scheduler.sh
```

### Log File Naming
- **Format**: `scheduled-test-YYYYMMDD_HHMMSS.log`
- **Example**: `scheduled-test-20250103_070001.log`
- **Location**: `logs/` directory

## 🔧 Configuration Options

### Modify Schedule
To change execution times, edit crontab:
```bash
crontab -e

# Examples:
# Every hour: 0 * * * *
# Twice daily (9 AM, 6 PM): 0 9,18 * * *
# Weekdays only at 8 AM: 0 8 * * 1-5
# Every 6 hours: 0 */6 * * *
```

### Environment Variables
Update paths in `run-scheduled-tests.sh`:
```bash
# Set your actual project path
PROJECT_DIR="/home/user/naukri-automation-framework"

# Optional: Set JAVA_HOME if needed
export JAVA_HOME="/usr/lib/jvm/java-17-openjdk"

# Optional: Set Maven path if needed
export PATH="$PATH:/opt/maven/bin"
```

## 📊 Monitoring and Logs

### Check Cron Status
```bash
# View current cron jobs
crontab -l

# Check cron service status
sudo systemctl status cron

# View cron logs
sudo tail -f /var/log/cron
```

### Test Execution Logs
```bash
# View latest log
tail -f logs/scheduled-test-*.log

# View all logs
ls -la logs/

# Search for specific patterns
grep -r "✅\|❌" logs/
```

### Allure Reports
- **Location**: `target/site/allure-maven-plugin/index.html`
- **Access**: Open in web browser
- **History**: Maintains execution history across runs

## 🐛 Troubleshooting

### Common Issues

1. **Cron Job Not Running**
   ```bash
   # Check cron service
   sudo systemctl status cron
   
   # Start cron if stopped
   sudo systemctl start cron
   
   # Check system logs
   sudo tail -f /var/log/syslog | grep CRON
   ```

2. **Script Permission Issues**
   ```bash
   # Make script executable
   chmod +x run-scheduled-tests.sh
   
   # Check file permissions
   ls -la run-scheduled-tests.sh
   ```

3. **Path Issues**
   ```bash
   # Use absolute paths in cron
   0 7 * * * /full/path/to/run-scheduled-tests.sh
   
   # Set PATH in script
   export PATH="/usr/local/bin:/usr/bin:/bin"
   ```

4. **Java/Maven Not Found**
   ```bash
   # Add to script
   export JAVA_HOME="/usr/lib/jvm/java-17-openjdk"
   export PATH="$PATH:/opt/maven/bin"
   ```

### Debug Mode
Enable debug logging in `run-scheduled-tests.sh`:
```bash
# Add at the beginning of script
set -x  # Enable debug mode
exec 2>&1  # Redirect stderr to stdout
```

## 📧 Notifications (Optional)

### Email Notifications
Add email notification to script:
```bash
# At end of run-scheduled-tests.sh
if [ ${PIPESTATUS[0]} -eq 0 ]; then
    echo "Tests passed at $(date)" | mail -s "Naukri Tests - Success" admin@company.com
else
    echo "Tests failed at $(date)" | mail -s "Naukri Tests - Failed" admin@company.com
fi
```

### Slack Notifications
```bash
# Add webhook notification
SLACK_WEBHOOK="https://hooks.slack.com/services/YOUR/WEBHOOK/URL"
curl -X POST -H 'Content-type: application/json' \
    --data '{"text":"Naukri tests completed at $(date)"}' \
    $SLACK_WEBHOOK
```

## 🔄 Maintenance

### Regular Tasks
- **Weekly**: Review execution logs
- **Monthly**: Clean old logs and reports
- **Quarterly**: Update dependencies and review schedule

### Log Cleanup Script
```bash
# Add to crontab for weekly cleanup
0 0 * * 0 find /path/to/project/logs -name "*.log" -mtime +30 -delete
```

### Backup Reports
```bash
# Monthly backup of reports
0 0 1 * * tar -czf /backup/naukri-reports-$(date +%Y%m).tar.gz target/site/allure-maven-plugin/
```

This cron scheduler ensures your SingleSessionLoginTest runs automatically twice daily with comprehensive logging and reporting.