#!/bin/bash

# Jenkins Docker Setup Script for Naukri Automation
# Author: Sivaraman M

echo "=========================================="
echo " JENKINS DOCKER SETUP FOR NAUKRI TESTS"
echo "=========================================="

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

echo "🐳 Starting Jenkins and Chrome containers..."

# Start containers
docker-compose up -d

echo "⏳ Waiting for Jenkins to start..."
sleep 30

# Get Jenkins initial admin password
echo "🔑 Jenkins Initial Admin Password:"
docker exec naukri-jenkins cat /var/jenkins_home/secrets/initialAdminPassword

echo ""
echo "✅ Setup completed!"
echo ""
echo "📋 Access Information:"
echo "   Jenkins UI: http://localhost:8080"
echo "   Chrome VNC: http://localhost:7900 (password: secret)"
echo "   Selenium Hub: http://localhost:4444"
echo ""
echo "🔧 Next Steps:"
echo "   1. Open Jenkins UI and complete setup wizard"
echo "   2. Install required plugins"
echo "   3. Configure tools (Java, Maven, Git)"
echo "   4. Create pipeline job with Jenkinsfile"
echo "   5. Configure email notifications"
echo ""
echo "📚 Refer to jenkins-setup-guide.md for detailed instructions"
echo "=========================================="