pipeline {
    agent any
    
    triggers {
        // Run every 6 hours (at 00:00, 06:00, 12:00, 18:00)
        cron('0 */6 * * *')
    }
    
    environment {
        MAVEN_HOME = tool 'Maven'
        JAVA_HOME = tool 'JDK17'
        PATH = "${MAVEN_HOME}/bin:${JAVA_HOME}/bin:${env.PATH}"
    }
    
    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox'],
            description: 'Browser to run tests on'
        )
        booleanParam(
            name: 'HEADLESS',
            defaultValue: true,
            description: 'Run tests in headless mode'
        )
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '🔄 Checking out source code...'
                checkout scm
            }
        }
        
        stage('Environment Setup') {
            steps {
                echo '🔧 Setting up environment...'
                script {
                    // Display environment info
                    sh 'java -version'
                    sh 'mvn -version'
                    
                    // Create necessary directories
                    sh 'mkdir -p target/allure-results'
                    sh 'mkdir -p screenshots'
                    sh 'mkdir -p logs'
                }
            }
        }
        
        stage('Run Tests') {
            steps {
                echo '🚀 Running Naukri Single Session Tests...'
                script {
                    try {
                        sh """
                            mvn clean test \
                            -DsuiteXmlFile=single-session-login-only.xml \
                            -Dbrowser=${params.BROWSER} \
                            -Dheadless=${params.HEADLESS} \
                            -Dmaven.test.failure.ignore=true
                        """
                    } catch (Exception e) {
                        echo "⚠️ Tests completed with some failures: ${e.getMessage()}"
                        currentBuild.result = 'UNSTABLE'
                    }
                }
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                echo '📊 Generating Allure Report...'
                script {
                    try {
                        sh 'mvn allure:report'
                        echo '✅ Allure report generated successfully'
                    } catch (Exception e) {
                        echo "⚠️ Allure report generation failed: ${e.getMessage()}"
                    }
                }
            }
        }
        
        stage('Archive Results') {
            steps {
                echo '📁 Archiving test results...'
                
                // Archive test results
                publishTestResults testResultsPattern: 'target/surefire-reports/*.xml'
                
                // Archive screenshots
                archiveArtifacts artifacts: 'screenshots/**/*.png', allowEmptyArchive: true
                
                // Archive logs
                archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
                
                // Publish Allure report
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }
    
    post {
        always {
            echo '🧹 Cleaning up workspace...'
            
            // Clean old screenshots (keep last 7 days)
            sh 'find screenshots -name "*.png" -mtime +7 -delete || true'
            
            // Clean old logs (keep last 30 days)
            sh 'find logs -name "*.log" -mtime +30 -delete || true'
        }
        
        success {
            echo '✅ Pipeline completed successfully!'
            
            // Send success notification
            emailext (
                subject: "✅ Naukri Tests - SUCCESS - Build #${BUILD_NUMBER}",
                body: """
                    <h2>✅ Naukri Automation Tests - SUCCESS</h2>
                    <p><strong>Build:</strong> #${BUILD_NUMBER}</p>
                    <p><strong>Execution Time:</strong> ${new Date()}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Headless Mode:</strong> ${params.HEADLESS}</p>
                    
                    <h3>📊 Reports:</h3>
                    <ul>
                        <li><a href="${BUILD_URL}allure/">Allure Report</a></li>
                        <li><a href="${BUILD_URL}testReport/">TestNG Report</a></li>
                        <li><a href="${BUILD_URL}artifact/">Screenshots & Logs</a></li>
                    </ul>
                    
                    <p>All tests passed successfully! 🎉</p>
                """,
                to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@company.com'}",
                mimeType: 'text/html'
            )
        }
        
        failure {
            echo '❌ Pipeline failed!'
            
            // Send failure notification
            emailext (
                subject: "❌ Naukri Tests - FAILED - Build #${BUILD_NUMBER}",
                body: """
                    <h2>❌ Naukri Automation Tests - FAILED</h2>
                    <p><strong>Build:</strong> #${BUILD_NUMBER}</p>
                    <p><strong>Execution Time:</strong> ${new Date()}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Headless Mode:</strong> ${params.HEADLESS}</p>
                    
                    <h3>🔍 Investigation Links:</h3>
                    <ul>
                        <li><a href="${BUILD_URL}console">Console Output</a></li>
                        <li><a href="${BUILD_URL}allure/">Allure Report</a></li>
                        <li><a href="${BUILD_URL}artifact/screenshots/">Screenshots</a></li>
                    </ul>
                    
                    <p>Please investigate the failure and take necessary action.</p>
                """,
                to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@company.com'}",
                mimeType: 'text/html'
            )
        }
        
        unstable {
            echo '⚠️ Pipeline completed with test failures!'
            
            // Send unstable notification
            emailext (
                subject: "⚠️ Naukri Tests - UNSTABLE - Build #${BUILD_NUMBER}",
                body: """
                    <h2>⚠️ Naukri Automation Tests - UNSTABLE</h2>
                    <p><strong>Build:</strong> #${BUILD_NUMBER}</p>
                    <p><strong>Execution Time:</strong> ${new Date()}</p>
                    <p><strong>Browser:</strong> ${params.BROWSER}</p>
                    <p><strong>Headless Mode:</strong> ${params.HEADLESS}</p>
                    
                    <h3>📊 Reports:</h3>
                    <ul>
                        <li><a href="${BUILD_URL}allure/">Allure Report</a></li>
                        <li><a href="${BUILD_URL}testReport/">TestNG Report</a></li>
                        <li><a href="${BUILD_URL}artifact/screenshots/">Failed Test Screenshots</a></li>
                    </ul>
                    
                    <p>Some tests failed. Please review the reports for details.</p>
                """,
                to: "${env.CHANGE_AUTHOR_EMAIL ?: 'admin@company.com'}",
                mimeType: 'text/html'
            )
        }
    }
}