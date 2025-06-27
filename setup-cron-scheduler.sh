#!/bin/bash

# Naukri Automation - Cron Scheduler Setup Script
# Sets up cron jobs to run tests at 7:00 AM and 3:00 PM daily
# Author: Sivaraman M

echo "=========================================="
echo " NAUKRI AUTOMATION CRON SCHEDULER SETUP"
echo "=========================================="

# Get current directory
CURRENT_DIR=$(pwd)
SCRIPT_PATH="$CURRENT_DIR/run-scheduled-tests.sh"

# Make the script executable
chmod +x "$SCRIPT_PATH"

echo "Setting up cron jobs for daily test execution..."
echo "Schedule: 7:00 AM and 3:00 PM daily"
echo ""

# Create temporary cron file
TEMP_CRON_FILE="/tmp/naukri_cron_temp"

# Get existing cron jobs (if any)
crontab -l > "$TEMP_CRON_FILE" 2>/dev/null || true

# Add new cron jobs
echo "# Naukri Automation Framework - Daily Test Execution" >> "$TEMP_CRON_FILE"
echo "# Run SingleSessionLoginTest at 7:00 AM daily" >> "$TEMP_CRON_FILE"
echo "0 7 * * * $SCRIPT_PATH" >> "$TEMP_CRON_FILE"
echo "# Run SingleSessionLoginTest at 3:00 PM daily" >> "$TEMP_CRON_FILE"
echo "0 15 * * * $SCRIPT_PATH" >> "$TEMP_CRON_FILE"
echo "" >> "$TEMP_CRON_FILE"

# Install the new cron jobs
crontab "$TEMP_CRON_FILE"

# Clean up temporary file
rm "$TEMP_CRON_FILE"

echo "✅ Cron jobs installed successfully!"
echo ""
echo "Current cron schedule:"
crontab -l | grep -A 5 -B 1 "Naukri Automation"
echo ""
echo "📋 Schedule Summary:"
echo "   • 7:00 AM daily  - SingleSessionLoginTest execution"
echo "   • 3:00 PM daily  - SingleSessionLoginTest execution"
echo ""
echo "📁 Logs will be saved in: $CURRENT_DIR/logs/"
echo "📊 Allure reports will be generated in: $CURRENT_DIR/target/site/allure-maven-plugin/"
echo ""
echo "To remove cron jobs later, run: crontab -e"
echo "=========================================="