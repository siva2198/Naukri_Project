# Jenkins Setup Guide for Naukri Automation

## 🎯 Overview
This guide will help you set up Jenkins to automatically run your Naukri automation tests every 6 hours using the `single-session-login-only.xml` suite.

## 📋 Prerequisites

### 1. Jenkins Installation
```bash
# For Ubuntu/Debian
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
sudo apt update
sudo apt install jenkins

# For Windows - Download from https://jenkins.io/download/
```

### 2. Required Jenkins Plugins
Install these plugins via Jenkins → Manage Jenkins → Manage Plugins:

- **Pipeline Plugin** (for Jenkinsfile support)
- **Git Plugin** (for source code management)
- **Allure Plugin** (for test reporting)
- **Email Extension Plugin** (for notifications)
- **TestNG Results Plugin** (for test result publishing)
- **Build Timeout Plugin** (for build timeouts)

## 🔧 Jenkins Configuration

### 1. Global Tool Configuration
Go to **Manage Jenkins → Global Tool Configuration**:

#### Java Configuration
- **Name**: `JDK17`
- **JAVA_HOME**: `/usr/lib/jvm/java-17-openjdk` (Linux) or `C:\Program Files\Java\jdk-17` (Windows)

#### Maven Configuration
- **Name**: `Maven`
- **MAVEN_HOME**: `/opt/maven` (Linux) or `C:\Program Files\Apache\maven` (Windows)

#### Git Configuration
- **Name**: `Default`
- **Path to Git executable**: `/usr/bin/git` (Linux) or `C:\Program Files\Git\bin\git.exe` (Windows)

### 2. System Configuration
Go to **Manage Jenkins → Configure System**:

#### Email Configuration
```
SMTP Server: smtp.gmail.com
SMTP Port: 587
Use SMTP Authentication: ✓
Username: your-email@gmail.com
Password: your-app-password
Use SSL: ✓
```

#### Allure Configuration
- **Allure Commandline**: Auto-install latest version

## 🚀 Job Setup

### 1. Create New Pipeline Job
1. Click **New Item**
2. Enter name: `Naukri-Automation-Tests`
3. Select **Pipeline**
4. Click **OK**

### 2. Configure Pipeline
In the job configuration:

#### General Settings
- **Description**: `Automated Naukri tests running every 6 hours`
- **Discard old builds**: Keep 30 builds
- **Build Timeout**: 60 minutes

#### Pipeline Configuration
- **Definition**: Pipeline script from SCM
- **SCM**: Git
- **Repository URL**: Your Git repository URL
- **Credentials**: Add your Git credentials
- **Branch**: `*/main` or `*/master`
- **Script Path**: `Jenkinsfile`

### 3. Advanced Configuration

#### Build Triggers
The cron schedule is defined in the Jenkinsfile:
```groovy
triggers {
    cron('0 */6 * * *')  // Every 6 hours
}
```

#### Parameters
The pipeline includes these parameters:
- **BROWSER**: Choice between chrome/firefox
- **HEADLESS**: Boolean for headless execution

## 📊 Monitoring & Reports

### 1. Allure Reports
- Access via: `http://jenkins-url/job/Naukri-Automation-Tests/allure/`
- Historical trends and detailed test results
- Screenshots attached to failed tests

### 2. TestNG Reports
- Access via: `http://jenkins-url/job/Naukri-Automation-Tests/testReport/`
- Standard TestNG HTML reports

### 3. Build Artifacts
- Screenshots: `http://jenkins-url/job/Naukri-Automation-Tests/lastBuild/artifact/screenshots/`
- Logs: `http://jenkins-url/job/Naukri-Automation-Tests/lastBuild/artifact/logs/`

## 📧 Notifications

### Email Notifications
The pipeline sends emails for:
- ✅ **Success**: All tests passed
- ❌ **Failure**: Pipeline/build failed
- ⚠️ **Unstable**: Some tests failed

### Slack Integration (Optional)
Add Slack plugin and configure:
```groovy
slackSend(
    channel: '#automation',
    color: 'good',
    message: "✅ Naukri Tests Passed - Build #${BUILD_NUMBER}"
)
```

## 🔍 Troubleshooting

### Common Issues

1. **Permission Denied**
   ```bash
   sudo usermod -aG docker jenkins
   sudo systemctl restart jenkins
   ```

2. **Maven Not Found**
   - Verify Maven installation in Global Tool Configuration
   - Check PATH environment variable

3. **Browser Issues**
   ```bash
   # Install Chrome for headless execution
   sudo apt-get install google-chrome-stable
   
   # Install Xvfb for virtual display
   sudo apt-get install xvfb
   ```

4. **Git Authentication**
   - Use SSH keys or personal access tokens
   - Configure credentials in Jenkins

### Debug Commands
```bash
# Check Jenkins logs
sudo tail -f /var/log/jenkins/jenkins.log

# Check build workspace
ls -la /var/lib/jenkins/workspace/Naukri-Automation-Tests/

# Test Maven command manually
mvn clean test -DsuiteXmlFile=single-session-login-only.xml
```

## 🎯 Best Practices

1. **Resource Management**
   - Use headless mode for server execution
   - Clean up old artifacts automatically
   - Set build timeouts

2. **Security**
   - Store credentials securely in Jenkins
   - Use environment variables for sensitive data
   - Restrict job permissions

3. **Monitoring**
   - Set up email notifications
   - Monitor build trends
   - Review failed test screenshots

4. **Maintenance**
   - Regular Jenkins updates
   - Plugin updates
   - Workspace cleanup

## 📅 Schedule Options

Current: Every 6 hours (`0 */6 * * *`)

Other options:
```groovy
// Every 4 hours
cron('0 */4 * * *')

// Twice daily (9 AM, 6 PM)
cron('0 9,18 * * *')

// Business hours only (9 AM - 6 PM, every 3 hours)
cron('0 9,12,15,18 * * 1-5')

// Daily at specific times
cron('0 8,14,20 * * *')
```

This setup ensures your Naukri automation tests run reliably every 6 hours with comprehensive reporting and notifications.