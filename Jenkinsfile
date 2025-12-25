pipeline {
    agent {
        dockerfile {
            filename 'Dockerfile'
            args '-u root'
        }
    }

    environment {
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean compileTestJava --no-daemon'
            }
        }

        stage('Run Tests') {
            steps {
                sh './gradlew test -Dselenide.headless=true --no-daemon'
            }
            post {
                always {
                    allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
                }
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'build/reports/tests/test',
                reportFiles: 'index.html',
                reportName: 'TestNG Report'
            ])

            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'

            cleanWs()
        }
        success {
            echo '✅ Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
